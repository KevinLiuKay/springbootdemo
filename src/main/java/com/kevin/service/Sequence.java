package com.kevin.service;

import com.kevin.dao.mapper.AutoIdBeanMapper;
import com.kevin.model.AutoIdBean;
import com.kevin.model.AutoIdBeanExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生成序列号
 * @date 2017年8月9日
 * @done 高并发速度问题(极限取决于数据库连接的最大次数) 可以一下生成多个序列号（极限为JVM的分配给堆的大小和int的最大值）
 * @todo TODO 可以回收序列号  
 */
@Component
@Scope("singleton")
public class Sequence {
	
	private final static Logger log = LoggerFactory.getLogger(Sequence.class);

	public static String PROJECT_NAME = "qxzwb";
	
	/** 生成多个序列号器锁 */
	private final static ReentrantLock locks = new ReentrantLock();
	
	/**
	 * 操作数据库的锁
	 */
	private final static ReentrantLock DBlock = new ReentrantLock();
	
	/**
	 * 线程池的最大值
	 */
	private final static int THREAD_POOL_MAX_NUM = 10;
	
	/**
	 * 线程池
	 */
	public final static ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_MAX_NUM);
	
	
	@Autowired
	private AutoIdBeanMapper dao;
	
	
	/**
	 * 根据序列规则的前缀为key序列为V的map，用做入库
	 */
	private static Map<String,AutoIdBean> keyMap = new ConcurrentHashMap <String,AutoIdBean>();
	

	/**
	 *	查询AutoID表请严格检查主键是否在表中有，否则会有异常
	 * @param code
	 * @param projectName
	 * @return
	 */
	private  AutoIdBean getBeans(String code,String projectName){
		if(!keyMap.containsKey(code)){//缓存没有当前的seq
			AutoIdBean bean = new AutoIdBean();
			AutoIdBeanExample example = new AutoIdBeanExample();
			AutoIdBeanExample.Criteria criteria = example.createCriteria();
			criteria.andCodeEqualTo(code);
			criteria.andNameEqualTo(projectName);
			bean = dao.selectByPrimaryKey(code);
			if(bean == null){
				log.error("请检查下code,project_name");
				throw new RuntimeException("请检查下code,project_name");
			}
			keyMap.put(code, bean);
			return bean;
		}
		
		return keyMap.get(code);
			
	}
	
	/**
	 * 预生成的序列和最大值比较
	 * @param bean
	 * @param preNum
	 * @return
	 */
	private  boolean jugeMaxNum(AutoIdBean bean,long preNum){
		return bean.getMaxNum()>preNum;
	}
	
	/**
	 * 新增序列规则
	 * @param bean
	 */
	public void addNewSeq(AutoIdBean bean){
		dao.insert(bean);
	}
	
	
	/**
	 * 删除序列规则 
	 * @param key
	 */
	public void delSeq(String key){
		dao.deleteByPrimaryKey(key);
	}
	
	/**
	 * 更新序列的CurNum
	 * @param bean
	 */
	private void updateSeqCurnum(AutoIdBean bean){
				DBlock.lock();
				try {
					dao.updateByPrimaryKey(keyMap.get(bean.getCode()));
				} finally {
					DBlock.unlock();
				}
			
	}
	
	
	/**
	 * 根据序列的key去查询
	 * @param code
	 * @param projectName
	 * @return
	 */
	public  String getSequenceStr(String code,String projectName){
		return getSequenceStr(code, projectName, 1)[0];
	}
	
	/**
	 * 根据序列的key去查询
	 * @param code
	 * @return
	 */
	public  String getSequenceStr(String code){
		return getSequenceStr(code, PROJECT_NAME, 1)[0];
	}
	
	/**
	 * 根据序列的key去查询
	 * @param code
	 * @param num
	 * @return
	 */
	public  String[] getSequenceStr(String code,int num){
		return getSequenceStr(code, PROJECT_NAME, num);
	}
	
	/**
	 * 
	 * @param code
	 * @param projectName
	 * @param num 序列号的个数
	 * @return
	 */
	public String[] getSequenceStr(String code,String projectName,int num){
		locks.lock();
		String[] seqs = new String[num];
		long[] seqNum = new long[num];
		AutoIdBean bean = getBeans(code, projectName);
		try {
			long curNum = reBeginCurNum(bean,false);
			if(jugeMaxNum(bean, curNum)){
				//序号可以使用
				for (int i = 0; i < num; i++) {
					seqs[i] = getWholeSeq(curNum,bean);
					curNum++;
					seqNum[i] = curNum;
				}
				bean.setCurNum(curNum);
				bean.setUpdateUser("xh");
				bean.setUpdateTime(new Date());
				keyMap.put(code,bean);
				updateSeqCurnum(bean);
			}
		} finally {
			locks.unlock();
		}
		return seqs;
	}
	
	
	/**
	 * 得到完成的序列号
	 * @param curNum
	 * @param bean
	 * @return
	 */
	private  String getWholeSeq(long curNum,AutoIdBean bean){
        int length = String.valueOf(bean.getMaxNum()).length();
        if(String.valueOf(bean.getMaxNum()-1).length() == length){//如果是最大数是1000这种类型的数字长度减一
            length--;
		}
		StringBuffer numFormat = new StringBuffer();
        for (int i = 0; i < length; i++) {
            numFormat.append(0);
        }
        DecimalFormat df=new DecimalFormat(numFormat.toString());
		return bean.getCode()+df.format(curNum);
	}
	
	/**
	 *
	 * @param bean
	 * @param flag  如果是true 新的一天那么序号要重新开始
	 * @return
	 */
	private  long reBeginCurNum(AutoIdBean bean,boolean flag){
		long num = 0;
		if(!jugeCurDay(new Date(), bean.getUpdateTime())&&flag){
			AutoIdBean idBean = getBeans(bean.getCode(), bean.getName());//最新的bean
			AutoIdBean autoIdBean = new AutoIdBean();//将要更新的Bean
			//插入数据
			autoIdBean.setCode(bean.getCode());
			autoIdBean.setName(bean.getName());
			autoIdBean.setCurNum((long)idBean.getMinNum());
			autoIdBean.setUpdateUser("xh");
			autoIdBean.setUpdateTime(new Date());
			if( !jugeCurDay(keyMap.get(bean.getCode()).getUpdateTime(), new Date())){
				updateSeqCurnum(idBean);
			}
			keyMap.put(bean.getCode(), bean);
			num = idBean.getMinNum();
			return num;
		}
		num = bean.getCurNum();
		return num;
	}

	private static boolean jugeCurDay(Date d,Date n){
		Calendar instance = Calendar.getInstance();
		instance.setTime(d);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(n);
		return (instance.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) && (instance.get(Calendar.DAY_OF_MONTH)==calendar.get(Calendar.DAY_OF_MONTH)) && (instance.get(Calendar.MONTH)==calendar.get(Calendar.MONTH));
	}
	
	
}
