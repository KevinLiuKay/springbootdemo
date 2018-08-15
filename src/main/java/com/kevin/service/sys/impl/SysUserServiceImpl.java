package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.shiro.PasswordHelper;
import com.kevin.common.utils.ExcelUtil;
import com.kevin.common.utils.JsonResult;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.extMapper.sys.SysRoleExtMapper;
import com.kevin.dao.extMapper.sys.SysUserExtMapper;
import com.kevin.dao.mapper.SysUserMapper;
import com.kevin.exception.CommonException;
import com.kevin.model.*;
import com.kevin.model.ext.sys.SysUserExt;
import com.kevin.service.pub.IFileService;
import com.kevin.service.sys.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class SysUserServiceImpl implements ISysUserService {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserExtMapper sysUserExtMapper;
    @Resource
    private IFileService fileService;
    @Resource
    private ISysOrgService sysOrgService;
    @Resource
    private ISysUserOrgService sysUserOrgService;
    @Resource
    private SysRoleExtMapper sysRoleExtMapper;
    @Resource
    private ISysUserRoleService sysUserRoleService;

//    @Autowired
//    private RedisUtil redisUtil;

    @Override
    @CachePut(value = "currUser",key = "'sysUser_'+#sysUser.getUserId()")
    public int save(SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getUserId())) {
            //新增
            sysUser.setUserId(UUIDUtil.getUUID());
            sysUser.setUserPwd(PasswordHelper.encryptPassword(sysUser.getUserId(), GlobalConstant.RESET_PASSWORD));
            GeneralMethod.setRecordInfo(sysUser, true);
            return sysUserMapper.insertSelective(sysUser);
        } else {
            //修改
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
        SysUserExample.Criteria criteria = example.createCriteria();
        SysUserExample.Criteria criteria2 = example.createCriteria();
        if (StringUtils.isNotBlank(sysUser.getUserAcc())) {
            criteria.andUserAccEqualTo(sysUser.getUserAcc());
            criteria.andRecordStateEqualTo(GlobalConstant.Y);
        }
        if (StringUtils.isNotBlank(sysUser.getUserPhone())) {
            criteria2.andUserPhoneEqualTo(sysUser.getUserPhone());
            criteria2.andRecordStateEqualTo(GlobalConstant.Y);
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
        Map<String, Object> resultMap = new HashMap<String, Object>();
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
        if (userListByExcel != null && !userListByExcel.isEmpty()) {
            for (SysUser sysUser : userListByExcel) {
                String userId = UUIDUtil.getUUID();
                sysUser.setUserId(userId);
                String pwd = PasswordHelper.encryptPassword(userId,GlobalConstant.ROOT_PASSWORD);
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
    public JsonResult updateCurrtUserPwd(String oldUserPwd, String newUserPwd) {
        JsonResult jsonResult = new JsonResult();
        try {
            if(StringUtils.isNotBlank(oldUserPwd) && StringUtils.isNotBlank(newUserPwd)) {
                //获取session原密码
                SysUser currUser = HttpServletContext.getCurrentUser();
                //数据库存加密的
                String currUserPwd = currUser.getUserPwd();
                //旧密码加密
                oldUserPwd = PasswordHelper.encryptPassword(currUser.getUserId(), oldUserPwd);
                //比对加密结果是否一致
                if (!currUserPwd.equals(oldUserPwd)) {
                    jsonResult.setStatus(false);
                    jsonResult.setMessage("原密码错误！");
                }
                SysUser sysUser = new SysUser();
                sysUser.setUserId(currUser.getUserId());
                //新密码加密
                sysUser.setUserPwd(PasswordHelper.encryptPassword(currUser.getUserId(), newUserPwd));
                int result = save(sysUser);
                if (GlobalConstant.ZERO != result) {
                    jsonResult.setMessage(GlobalConstant.UPDATE_SUCCESSED);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @Override
    public JsonResult resetPwd(String userId, String userPwd) {
        JsonResult jsonResult = new JsonResult();
        try {
            if(StringUtils.isNotBlank(userId)) {
                SysUser sysUser = new SysUser();
                sysUser.setUserId(userId);
                //重置密码如果用户输入密码则使用新密码否则使用默认密码
                if(StringUtils.isNotBlank(userPwd)) {
                    sysUser.setUserPwd(PasswordHelper.encryptPassword(userId, userPwd));
                } else {
                    sysUser.setUserPwd(PasswordHelper.encryptPassword(userId, GlobalConstant.RESET_PASSWORD));
                }
                int result = save(sysUser);
                if (GlobalConstant.ZERO!= result) {
                    jsonResult.setStatus(true);
                    jsonResult.setMessage(GlobalConstant.UPDATE_SUCCESSED);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @Override
    public List<SysUserExt> queryUserExtList(SysUser sysUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sysUser", sysUser);
        map.put("orderByClause", "u.create_time DESC");
        //查询用户和部门信息
        List<SysUserExt> userExtList = sysUserExtMapper.queryUserExtList(map);
        List<String> userIds = new ArrayList<String>();
        if(userExtList != null || !userExtList.isEmpty()){
            for(SysUserExt userExt: userExtList) {
                userIds.add(userExt.getUserId());
            }
            //根据用户userIdList查询所有用户角色（避免一对多分页问题）
            List<SysUserExt> userRoleList = queryUserRoleList(userIds);
            //userId 对应的RoleList放在Map中
            if(userRoleList != null && !userRoleList.isEmpty()) {
                Map<String, List<SysRole>> userExtMap = new HashMap<>();
                for (SysUserExt ext : userRoleList) {
                    userExtMap.put(ext.getUserId() , ext.getRoleList());
                }
                //从Map中取出RoleList set到用户列表（包括部门信息）
                for(SysUserExt userExt: userExtList) {
                    userExt.setRoleList(userExtMap.get(userExt.getUserId()));
                }
            }
        }
        return userExtList;
    }

    @Override
    public JsonResult saveUserExt(SysUser sysUser, String orgId, List<String> roleIds) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(false);
        SysUser currUser = HttpServletContext.getCurrentUser();
        if(currUser == null) {
            throwException(GlobalConstant.ZERO,GlobalConstant.SESSION_OUT_TIME);
        }
        SysUser user = new SysUser();
        //保存用户信息
        if(sysUser != null) {
            int savaUserResult = save(sysUser);
            BeanUtils.copyProperties(sysUser,user);
            throwException(savaUserResult, "保存用户表失败!");
        }
        //保存用户部门关联表信息
        //查询用户部门关联表中是否存在记录
        SysUserOrg sysUserOrg = new SysUserOrg();
        if(StringUtils.isNotBlank(user.getUserId())) {
            sysUserOrg.setUserId(user.getUserId());
            //查询用户组织关联表中是否存在记录
            List<SysUserOrg> userOrgList =  sysUserOrgService.queryList(sysUserOrg,"");
            if(userOrgList != null && !userOrgList.isEmpty()){
                SysUserOrg userOrg = userOrgList.get(0);
                sysUserOrg.setUserOrgId(userOrg.getUserOrgId());
            }
        }
        //orgId为空，但数据库存在用户组织之前的关联，删除关联
        if(StringUtils.isBlank(orgId) && StringUtils.isNotBlank(sysUserOrg.getUserOrgId())){
            int deleteUserOrgResult = sysUserOrgService.logicallyDeleteById(sysUserOrg.getUserOrgId());
            throwException(deleteUserOrgResult, "删除用户组织关联表失败!");
        }
        //org不为空，则保存用户组织关联关系
        if(StringUtils.isNotBlank(orgId)){
            sysUserOrg.setOrgId(orgId);
            sysUserOrg.setRecordState(GlobalConstant.Y);
            int saveUserOrgResult = sysUserOrgService.save(sysUserOrg);
            throwException(saveUserOrgResult, "保存用户组织关联表失败!");
        }
        //保存用户角色关联信息
        //查询用户角色关联表中是否存在记录
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", user.getUserId());
        List<SysUserRole> userRoleList =  sysRoleExtMapper.queryUserRoleList(paramMap);
        if(userRoleList != null && !userRoleList.isEmpty()){
            //将userRole关联表中，当前userId下的所有角色逻辑删除
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(user.getUserId());
            userRole.setUpdateUserId(currUser.getUserId());
            userRole.setUpdateTime(Calendar.getInstance().getTime());
            throwException(sysRoleExtMapper.deleteRoleUser(userRole), "删除用户组织关联表失败!");
        }
        SysUserRole sysUserRole = null;
        if(roleIds != null && !roleIds.isEmpty()) {
            int result = GlobalConstant.ZERO;
            for (String roleId : roleIds) {
                // 是否新增 false：不新增 true:新增
                boolean insert = true;
                // 数据库不为空
                if (userRoleList != null && !userRoleList.isEmpty()) {
                    for (SysUserRole existUR : userRoleList) {
                        if (existUR.getRoleId().equals(roleId)) {
                            sysUserRole = existUR;
                            insert = false;
                            break;
                        }
                    }
                    // 新增
                    if (insert) {
                        sysUserRole = new SysUserRole();
                        sysUserRole.setRoleId(roleId);
                        sysUserRole.setUserId(user.getUserId());
                        result += sysUserRoleService.save(sysUserRole);
                    } else {
                        // 修改
                        sysUserRole.setRecordState(GlobalConstant.Y);
                        sysUserRole.setUpdateUserId(currUser.getUserId());
                        sysUserRole.setUpdateTime(Calendar.getInstance().getTime());
                        result += sysUserRoleService.save(sysUserRole);
                    }
                } else {
                    // 新增全部
                    sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(roleId);
                    sysUserRole.setUserId(sysUser.getUserId());
                    result += sysUserRoleService.save(sysUserRole);
                }
                throwException(result, "保存用户角色关联表失败!");
            }
        }
        jsonResult.setStatus(true);
        jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
        return jsonResult;
    }

    @Override
    public List<SysUserExt> queryUserRoleList(List<String> list) {
        if(list != null && !list.isEmpty()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list",list);
            return sysUserExtMapper.queryUserRoleList(map);
        }
        return null;
    }


    @Override
    @CachePut(value = "currUserCache", key = "#sysUser.userId")
    public SysUser cachePut(SysUser sysUser) {
        logger.debug("-----> @CachePut currUser, key:" + sysUser.getUserId());
        return sysUser;
    }

    @Override
    @CacheEvict(value = "currUserCache", key = "#userId")
    public void cacheEvict(String userId) {
        logger.debug("-----> @CacheEvict currUser, key:" + userId);
    }

    @Override
    @Cacheable(value = "currUserCache", key = "#userId")
    public SysUser cacheable(String userId) {
        logger.debug("-----> @Cacheable currUser, key:" + userId);
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
        // workbook中的sheet数
		int sheetNum = wb.getNumberOfSheets();
		if (sheetNum > 0) {
			List<SysUser> userList = new ArrayList<>();
			Sheet sheet;
			try {
                // 得到第一个sheet（以excel的版本来分别）
				sheet = (HSSFSheet) wb.getSheetAt(0);
			} catch (Exception e) {
				sheet = (XSSFSheet) wb.getSheetAt(0);
			}
            // excel的总行数
			int row_num = sheet.getLastRowNum();
            // 读取每行
			for (int i = 1; i <= row_num; i++) {
                // -----单元格begin------
                Row r = sheet.getRow(i);
                // 定死列数 防止读其他空的列
                int cell_num = 5;
                SysUser sysUser = new SysUser();
                String key = GlobalConstant.EMPTY;
                // 遍历一行每列
                for (int j = 0; j < cell_num; j++) {
                    String value = "";
                    Cell cell = r.getCell((short) j);
                    if (cell == null) {
                        continue;
                    }
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        // 将cell中的值付给value
                        value = r.getCell((short) j).getStringCellValue();
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
    public void throwException(int result,String name){
        if (result == 0) {
            throw new RuntimeException(name);
        }
    }
}
