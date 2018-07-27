package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.shiro.PasswordHelper;
import com.kevin.common.utils.ExcelUtil;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.extMapper.sys.SysUserExtMapper;
import com.kevin.dao.mapper.SysUserMapper;
import com.kevin.exception.CommonException;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserExample;
import com.kevin.service.pub.IFileService;
import com.kevin.service.sys.ISysUserService;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements ISysUserService {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserExtMapper sysUserExtMapper;
    @Resource
    private IFileService fileService;

//    @Autowired
//    private RedisUtil redisUtil;

    @Override
    @CachePut(value = "currUser",key = "'sysUser_'+#sysUser.getUserId()")
    public int save(SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getUserId())) {//新增
            sysUser.setUserId(UUIDUtil.getUUID());
            sysUser.setUserPwd(PasswordHelper.encryptPassword(sysUser.getUserId(), GlobalConstant.RESET_PASSWORD));
            GeneralMethod.setRecordInfo(sysUser, true);
            return sysUserMapper.insertSelective(sysUser);
        } else {//修改
            GeneralMethod.setRecordInfo(sysUser, false);
            return sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
            SysUser user = new SysUser();
            //set
            user.setRecordState(GlobalConstant.N);
            //where
            user.setUserId(id);
            return save(user);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysUserMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
//    @Cacheable(value = "currUser",key="'sysUser_'+#sysUser.getUserId()")
    public List<SysUser> queryList(SysUser sysUser, String orderByClause) {
//        System.err.println("没有走缓存！"+sysUser.getUserId());
//        SysUser setRedisCurrUser = (SysUser)redisUtil.get("currUser");
//        logger.debug("-------------redis缓存中获取当前用户信息:--------------------" + setRedisCurrUser.getUserName() + setRedisCurrUser.getUserAcc());
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        andCrieria(sysUser, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysUserMapper.selectByExample(example);
    }

    @Override
    public SysUser getById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysUserMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    private void andCrieria(SysUser user, SysUserExample.Criteria criteria) {
        if (StringUtils.isNotBlank(user.getUserName())) {
            criteria.andUserNameLike(GlobalConstant.PERCENT + user.getUserName() + GlobalConstant.PERCENT);
        }
        if (StringUtils.isNotBlank(user.getUserPhone())) {
            criteria.andUserPhoneLike(GlobalConstant.PERCENT + user.getUserPhone() + GlobalConstant.PERCENT);
        }
    }

    @Override
    public SysUser insertRoot() {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(GlobalConstant.ROOT_USER_ID);
        sysUser.setUserAcc(GlobalConstant.ROOT_USER_ACC);
        sysUser.setUserName(GlobalConstant.ROOT_USER_NAME);
        String newPwd = PasswordHelper.encryptPassword(GlobalConstant.ROOT_USER_ID, GlobalConstant.RESET_PASSWORD);
        sysUser.setUserPwd(newPwd);
        //sysUser.setStatusId(UserStatusEnum.Activated.getId());
        sysUser.setRecordState(GlobalConstant.Y);
        sysUser.setCreateUserId(GlobalConstant.ROOT_USER_ID);
        sysUser.setCreateTime(Calendar.getInstance().getTime());
        sysUserMapper.insertSelective(sysUser);
        return sysUser;
    }

    @Override
    public SysUser getSysUserByUserAcc(String userAcc) {
        if(StringUtils.isNotBlank(userAcc)){
            SysUserExample example = new SysUserExample();
            example.createCriteria().andUserAccEqualTo(userAcc).andRecordStateEqualTo(GlobalConstant.Y);
            List<SysUser> userList = sysUserMapper.selectByExample(example);
            if (userList != null && !userList.isEmpty()) {
                return userList.get(0);
            }
        }
        return null;
    }

    @Override
    public long checkUnique(SysUser sysUser) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        SysUserExample.Criteria criteria2 = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        if (StringUtils.isNotBlank(sysUser.getUserAcc())) {
            criteria.andUserAccEqualTo(sysUser.getUserAcc());
        }
        if (StringUtils.isNotBlank(sysUser.getUserPhone())) {
            criteria2.andUserPhoneEqualTo(sysUser.getUserPhone());
        }
        //非自己！
        if (StringUtils.isNotBlank(sysUser.getUserId())) {
            criteria.andUserIdNotEqualTo(sysUser.getUserId());
            criteria2.andUserIdNotEqualTo(sysUser.getUserId());
        }
        example.or(criteria2);
        return sysUserMapper.countByExample(example);
    }

    @Override
    public int batchInsert(List<SysUser> list) {
        if(list != null && !list.isEmpty()) {
            return sysUserExtMapper.batchInsert(list);
        }
        return 0;
    }

    @Override
    public int batchLogicDelete(List<String> list) {
        if(list != null && !list.isEmpty()) {
            SysUser currentUser = HttpServletContext.getCurrentUser();
            Date currentTime = Calendar.getInstance().getTime();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("updateTime", currentTime);
            map.put("updateUserId", currentUser.getUserId());
            map.put("recordState", GlobalConstant.N);
            map.put("list", list);
            return sysUserExtMapper.batchLogicDelete(map);
        }
        return 0;
    }

    @Override
    public Map<String, Object> batchInsertUserByExcel(MultipartFile file) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        /*
         * 允许上传Excel的MIME类型、后缀、大小
         */
        String acceptMessage = fileService.acceptExcel(file);
        if (!GlobalConstant.Y.equals(acceptMessage)) {
            resultMap.put("acceptMessage", acceptMessage);
            return resultMap;
        }
        //将上传文件解析为工作簿
        Workbook wb = parseFileToWorkbook(file);
        // 从工作簿中对象封装为对应的List
        List<SysUser> userListByExcel = workbookEncapIntoList(wb);
        //保存Excel中获取List
        SysUser currentUser = HttpServletContext.getCurrentUser();
        Date currentTime = Calendar.getInstance().getTime();
        PasswordHelper passwordHelper = new PasswordHelper();
        if (userListByExcel != null && !userListByExcel.isEmpty()) {
            for (SysUser sysUser : userListByExcel) {
                String userId = UUIDUtil.getUUID();
                sysUser.setUserId(userId);
                String pwd = passwordHelper.encryptPassword(userId,GlobalConstant.ROOT_PASSWORD);
                sysUser.setUserPwd(pwd);
                if(currentUser != null) {
                    sysUser.setCreateUserId(currentUser.getUserId());
                    sysUser.setCreateTime(currentTime);
                }
                sysUser.setRecordState(GlobalConstant.Y);
            }
            int result = batchInsert(userListByExcel);
            resultMap.put("result", result);
        }
        return null;
    }


    @Override
    @CachePut(value = "currUser", key = "#sysUser.userId")
    public void cachePut(SysUser sysUser) {
        logger.debug("为id、key为:" + sysUser.getUserId() + "数据做了缓存");
    }

    @Override
    @CacheEvict(value = "currUser", key = "#userId")
    public void cacheEvict(String userId) {
        logger.debug("删除了id、key为" + userId + "的数据缓存");
    }

    @Override
    @Cacheable(value = "currUser", key = "#userId")
    public SysUser cacheable(String userId) {
        logger.debug("为id、key为:" + userId + "数据做了缓存");
        return getById(userId);
    }

    /**
     * 上传文件解析成工作薄
     * @param excelFile
     * @return
     * @throws Exception
     */
    private Workbook parseFileToWorkbook(MultipartFile excelFile) throws Exception{
        InputStream is  = null;
        try {
            //1、解析Excel
            is = excelFile.getInputStream();
            byte[] fileData = new byte[(int) excelFile.getSize()];
            is.read(fileData);
            Workbook wb = ExcelUtil.createCommonWorkbook(new ByteInputStream(fileData, (int) excelFile.getSize()));
            //Workbook wb = ExcelUtil.createCommonWorkbook(new POIFSFileSystem(excelFile.getInputStream()));
            return wb;
        }catch (Exception e){
            // ******************* 抛出自定义异常 ！！！！！*********************
            throw new CommonException(e.getMessage());
        }finally {
            try {
                if(is != null) {
                    is.close();
                }
            }catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }
    }

    /**
     * 封装成list对象
     * @param wb
     * @return
     * @throws Exception
     */

	private List<SysUser> workbookEncapIntoList(Workbook wb) {
		int sheetNum = wb.getNumberOfSheets();// workbook中的sheet数
		if (sheetNum > 0) {
			List<SysUser> userList = new ArrayList<>();
			Sheet sheet;
			try {
				sheet = (HSSFSheet) wb.getSheetAt(0);// 得到第一个sheet（以excel的版本来分别）
			} catch (Exception e) {
				sheet = (XSSFSheet) wb.getSheetAt(0);
			}
			int row_num = sheet.getLastRowNum(); // excel的总行数
			for (int i = 1; i <= row_num; i++) {// 读取每行
                // -----单元格begin------
                Row r = sheet.getRow(i);
                int cell_num = 5;// 定死列数 防止读其他空的列
                SysUser sysUser = new SysUser();
                String key = GlobalConstant.EMPTY;
                for (int j = 0; j < cell_num; j++) {// 遍历一行每列
                    String value = "";
                    Cell cell = r.getCell((short) j);
                    if (cell == null) {
                        continue;
                    }
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        value = r.getCell((short) j).getStringCellValue();// 将cell中的值付给value
                    } else {
                        value = ExcelUtil._doubleTrans(r.getCell((short) j).getNumericCellValue());
                    }
                    // Excel数据封装成java对象
                    value = value.trim();
                    logger.debug("第" + i + "行" + (j + 1) + "列：" + value);
                    if (j == 0) {
                        sysUser.setUserName(value);
                    } else if (j == 1) {
                        sysUser.setUserAcc(value);
                    } else if (j == 2) {
                        sysUser.setUserGender(Integer.valueOf(value));
                    } else if (j == 3) {
                        sysUser.setUserPhone(value);
                    } else if (j == 4) {
                        sysUser.setUserAddr(value);
                    }

                } // --------读取每行单元格end-----------
                long result = checkUnique(sysUser);
                if (result > 0) {
                    try {
                        // throw new CommonException("导入失败，第" + i + "条记录 第" + (j + 1) + "列 ,用户名或者手机号不唯一！");
                        throw new CommonException("导入失败，第" + i + "条记录,用户名或者手机号不唯一！");
                    } catch (CommonException e) {
                        e.printStackTrace();
                    }
                }
                // 读取完一行记录
                userList.add(sysUser);
			} // ----------读取Excel所有行end
				return userList;
		}
		return null;
	}

}
