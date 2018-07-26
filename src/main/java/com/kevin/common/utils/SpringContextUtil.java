package com.kevin.common.utils;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

/**
 * spring上下文环境实现类
 * 
 * @author Tiger Mo
 * @create 2016.04.13
 */
public class SpringContextUtil {

	private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

	private static ApplicationContext applicationContext;
	
	 /**
	  * 设置上下文环境  
	  * @param applicationContext
	  */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		synchronized (SpringContextUtil.class) {
			logger.debug("setApplicationContext, notifyAll");
			SpringContextUtil.applicationContext = applicationContext;
			SpringContextUtil.class.notifyAll();
		}
	}

	public static ApplicationContext getApplicationContext() {
		synchronized (SpringContextUtil.class) {
			while (applicationContext == null) {
				try {
					logger.debug("getApplicationContext, wait...");
					SpringContextUtil.class.wait(60000);
					if (applicationContext == null) {
						logger.warn(
								"Have been waiting for ApplicationContext to be set for 1 minute",
								new Exception());
					}
				} catch (InterruptedException ex) {
					logger.debug("getApplicationContext, wait interrupted");
				}
			}
			return applicationContext;
		}
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 获取类型为requiredType的对象
	 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name bean注册名
	 * @param requiredType 返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class<?> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}

	public static <T> T getBean(Class<T> cls) {
		return getApplicationContext().getBean(cls);
	}

	public static String getMessage(String code) {
		return getApplicationContext().getMessage(code, null, Locale.CHINA);
	}

	public static Resource getResource(String name) {
		return getApplicationContext().getResource(name);
	}

}
