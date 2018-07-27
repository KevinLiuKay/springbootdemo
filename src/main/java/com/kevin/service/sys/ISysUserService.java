package com.kevin.service.sys;

import com.kevin.model.SysUser;
import com.kevin.service.ICommonService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
public interface ISysUserService extends ICommonService<SysUser> {
    /**
     * 超级管理员 首次创建
     * @return
     */
    public SysUser insertRoot();

    /**
     * 查找用户
     * @author lzk
     * @param userAcc 用户账号
     * @return
     */
    public SysUser getSysUserByUserAcc(String userAcc);

    /**
     * 检查用户唯一性
     * @param sysUser
     * @return
     */
    public long checkUnique(SysUser sysUser);

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
    public Map<String, Object> batchInsertUserByExcel(MultipartFile file) throws Exception;

    /**********************************ehcache缓存*******************************************/
    /**
     * @CachePut : 使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * @param sysUser
     */
    public void cachePut(SysUser sysUser);

    /**
     * @CacheEvict : 清除缓存
     * @param userId
     */
    public void cacheEvict(String userId);

    /**
     * @Cacheable : Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
     * @param userId
     */
    public SysUser cacheable(String userId);

}
