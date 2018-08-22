package com.kevin.common.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralEnum;
import com.kevin.common.utils.ClassUtil;
import com.kevin.common.utils.EnumUtil;
import com.kevin.enums.sys.DictTypeEnum;
import com.kevin.model.SysCfg;
import com.kevin.model.SysDict;
import com.kevin.service.sys.ISysCfgService;
import com.kevin.service.sys.ISysDictService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 监听ServletContext对象的生命周期，实际上就是监听Web应用的生命周期。
 * 当Servlet容器启动或终止Web应用时，会触发ServletContextEvent事件，该事件由 ServletContextListener 来处理。
 * 在 ServletContextListener 接口中定义了处理ServletContextEvent事件的两个方法。
 * 处理Web应用的 servlet上下文(context)的变化的通知。
 * @author Tiger Mo
 * @create 2016.04.13
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

	private final static Logger logger = LoggerFactory.getLogger(ServletContextListenerImpl.class);

	private static ISysDictService dictService;
	private static ISysCfgService cfgService;
	
	@Autowired
    public void setService(ISysDictService dictService) {
		ServletContextListenerImpl.dictService = dictService;
    }
	@Autowired
	public void setService(ISysCfgService cfgService) {
		ServletContextListenerImpl.cfgService = cfgService;
	}

	/**
	 * 当Servlet容器终止Web应用时调用该方法。
	 * 在调用该方法之前，容器会先销毁所有的Servlet和Filter过滤器。
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	/**
	 * 当Servlet容器启动Web应用时调用该方法。
	 * 在调用完该方法之后，容器再对Filter初始化，并且对那些在Web应用启动时就需要被初始化的Servlet进行初始化。
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.debug("----> 系统初始化。。。。。");		
		ServletContext context = event.getServletContext();
		try {
			//加载字典
			_loadDict(context);
			//加载系统配置
			_loadSysCfg(context);
			//加载系统代码
			_loadEnum(context);
		} catch (Exception e) {
			logger.debug("----> 系统初始化异常:" + e.getMessage());
		}
	}
	

	/**
	 * 刷新服务器
	 * @param context
	 */
	public static String refresh(ServletContext context){
		logger.debug("----> 刷新服务器。。。。。。");
		try {
			//加载字典
			_loadDict(context);
			//加载系统配置
			_loadSysCfg(context);
			//加载系统代码
			_loadEnum(context);
			return GlobalConstant.OPERATE_SUCCESSED;
		} catch (Exception e) {
			logger.debug("----> 刷新服务器异常:" + e.getMessage());
			return GlobalConstant.OPERATE_FAIL;
		}
	}
	
	
	/**
	 * 加载字典
	 */
	private static void _loadDict(ServletContext context) {
		Map<String, List<SysDict>> sysListDictMap = new HashMap<String, List<SysDict>>();
		Map<String, String> sysDictIdMap = new HashMap<String, String>();
		//从字典枚举类从读取枚举数据
		List<DictTypeEnum> dictTypeEnumList = (List<DictTypeEnum>) EnumUtil.toList(DictTypeEnum.class);
		for(DictTypeEnum dictTypeEnum : dictTypeEnumList){		
			String dictTypeId = dictTypeEnum.getId();
			SysDict sysDict = new SysDict();
			sysDict.setDictTypeId(dictTypeId);
			sysDict.setRecordState(GlobalConstant.Y);
			List<SysDict> sysDictList = dictService.queryList(sysDict, GlobalConstant.SORT_KEY);
			for(SysDict dict : sysDictList){
				String typeId = dict.getDictTypeId()+"."+dict.getDictId();
				sysDictIdMap.put(typeId, dict.getDictValue());
			}
			sysListDictMap.put(dictTypeId, sysDictList);
			context.setAttribute("dictTypeEnum"+dictTypeEnum.name()+"List", sysDictList);
		}
		DictTypeEnum.sysDictIdMap = sysDictIdMap;
		DictTypeEnum.sysListDictMap = sysListDictMap;
	}
	
	/**
	 * 加载枚举
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void _loadEnum(ServletContext context){
		Set<Class<?>> set = ClassUtil.getClasses("com.kevin.enums");
		for(Class<?> cls : set){		
			List<GeneralEnum> enumList = (List<GeneralEnum>) EnumUtil.toList((Class<? extends GeneralEnum>) cls);
			context.setAttribute(StringUtils.uncapitalize(cls.getSimpleName())+"List", enumList);
			for(@SuppressWarnings("rawtypes") GeneralEnum genum : enumList){	
				context.setAttribute(StringUtils.uncapitalize(cls.getSimpleName()) +genum.name(), genum);			
			}			
		}	
	}

	/**
	 * 加载系统配置
	 */
	private static Map<String,String> sysCfgMap;
	private static Map<String,String> sysCfgDescMap;
	private static void _loadSysCfg(ServletContext context) {
		sysCfgMap = new HashMap<String, String>();
		sysCfgDescMap = new HashMap<String, String>();
		List<SysCfg> sysCfgList = cfgService.queryList(new SysCfg(), null);
		for(SysCfg sysCfg : sysCfgList){
			if(StringUtils.isNotBlank(sysCfg.getCfgDesc())){
				sysCfgDescMap.put(sysCfg.getCfgCode(), sysCfg.getCfgDesc());
			}
			sysCfgMap.put(sysCfg.getCfgCode(), sysCfg.getCfgValue());
		}
		context.setAttribute("sysCfgMap",sysCfgMap);
		context.setAttribute("sysCfgDescMap", sysCfgDescMap);
	}
	
	public static String getSysCfg(String key){
		return StringUtils.defaultString(sysCfgMap.get(key));
	}

}
