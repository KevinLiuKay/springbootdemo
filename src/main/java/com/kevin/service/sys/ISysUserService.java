package com.kevin.service.sys;

import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysOrg;
import com.kevin.model.SysRole;
import com.kevin.model.SysUser;
import com.kevin.model.ext.sys.SysUserExt;
import com.kevin.service.ICommonService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
/**
 * @author lzk
 */
public interface ISysUserService extends ICommonService<SysUser> {
    /**
     * 超级管理员 首次创建
     * @return
     */
    SysUser insertRoot();

    /**
     * 查找用户
     * @author lzk
     * @param userAcc 用户账号
     * @return
     */
    SysUser getSysUserByUserAcc(String userAcc);

    /**
     * 检查用户唯一性
     * @param sysUser
     * @return
     */
    long checkUnique(SysUser sysUser);

    /**
     * 批量插入用户
     * @param list
     * @return
     */
    int batchInsert(List<SysUser> list);

    /**
     * 批量逻辑删除用户
     * @param list
     * @return
     */
    int batchLogicDelete(List<String> list);

    /**
     * 从Excel批量导入用户
     * @param file
     * @return
     * @throws Exception
     */
    Map<String, Object> batchInsertUserByExcel(MultipartFile file) throws Exception;

    /**
     *修改当前登录用户的密码
     * @param oldUserPwd
     * @param newUserPwd
     * @return
     */
    JsonResult updateCurrtUserPwd(String oldUserPwd, String newUserPwd);

    /**
     *保存重置密码
     * @param userId
     * @param userPwd
     * @return
     */
    JsonResult resetPwd(String userId,String userPwd);

    /**
     * 查询用户所有信息包括部门，角色
     * @param sysUser
     * @return
     */
    List<SysUserExt> queryUserExtList(SysUser sysUser);

    /**
     * 保存用户信息（包括部门,角色信息）
     * @param sysUser
     * @param orgId
     * @param roleIds
     * @return
     */
    JsonResult saveUserExt(SysUser sysUser, String orgId, List<String> roleIds);
    /**
     * 查询用户下角色信息
     * @param list
     * @return
     */
    List<SysUserExt> queryUserRoleList(List<String> list);

    /**********************************ehcache缓存*******************************************/
    /**
     * @CachePut : 使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * @param sysUser
     */
    void cachePut(SysUser sysUser);

    /**
     * @CacheEvict : 清除缓存
     * @param userId
     */
    void cacheEvict(String userId);

    /**
     * @Cacheable : Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
     * @param userId
     */
    SysUser cacheable(String userId);

}
