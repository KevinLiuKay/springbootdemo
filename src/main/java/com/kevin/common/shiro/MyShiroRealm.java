package com.kevin.common.shiro;


import com.kevin.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.service.sys.ISysUserService;

/**
 * 配置自定义Realm
 * 在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。
 * 通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。
 * 可以说，Realm是专用于安全框架的DAO.
 */
@Service
public class MyShiroRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    private ISysUserService userService;

    @Autowired
    public void setUserService(ISysUserService userService) {
        this.userService = userService;
    }

    /**
     * 判断此Realm是否支持此Token
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
	    String token = principals.toString();
	    if (StringUtils.isNotBlank(token) && token.startsWith(GlobalConstant.BEARER)) {
	    		token = token.replace(GlobalConstant.BEARER, GlobalConstant.EMPTY);
	    }
	    logger.debug("-----> token:"+token);
        String username = JWTUtil.getUsername(token);
        logger.debug("-----> userId:"+username);
        SysUser sysUser = userService.cacheable(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //simpleAuthorizationInfo.addRole(user.getRole());
       // Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        //simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证信息(身份验证)
     * Authentication 是用来验证用户身份
     *
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        if (StringUtils.isNotBlank(token) && token.startsWith(GlobalConstant.BEARER)) {
	    		token = token.replace(GlobalConstant.BEARER, GlobalConstant.EMPTY);
	    }
        logger.debug("-----> token:"+token);
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        logger.debug("-----> userId:"+username);
        if (username == null) {
            logger.debug("-----> token invalid");
            throw new AuthenticationException("token invalid");
        }
        //通过username从数据库中查找SysUser对象
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser sysUser = userService.cacheable(username);
        if (sysUser == null) {
            logger.debug("-----> User didn't existed!");
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtil.verify(token, username, sysUser.getUserPwd())) {
            logger.debug("-----> Username or password error");
            throw new AuthenticationException("Username or password error");
        }
        // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(token, token, "my_realm");//返回一个唯一的Realm名字"my_realm"
    }
}
