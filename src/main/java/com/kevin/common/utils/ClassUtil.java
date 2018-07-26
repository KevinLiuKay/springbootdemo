package com.kevin.common.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kevin.common.GlobalConstant.GlobalConstant;

/**
 * 反射工具类
 * 
 * @author dell2
 * @create 2016.04.13
 */
public class ClassUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	public static Set<Class<?>> getClasses(String pack) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		boolean recursive = true;
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), GlobalConstant.UTF_8);
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					JarFile jar;
					try {
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								if (idx != -1) {
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								if ((idx != -1) || recursive) {
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader()
							.loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Object getInstance(String name) throws Exception {
		Class<?> cls = Class.forName(name);
		return cls.newInstance();
	}

	public static void reflect(Object obj) {
		Class<?> cls = obj.getClass();
		logger.debug("-----> 构 造 器");
		Constructor<?>[] constructors = cls.getConstructors();
		for (Constructor<?> constructor : constructors) {
			logger.debug("-----> 构造器名称:" + constructor.getName() + "\t"
					+ "构造器参数类型:" + Arrays.toString(constructor.getParameterTypes()));
		}
		logger.debug("-----> 属性");
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			logger.debug("-----> 属性名称:" + field.getName() + "\t" 
					+ "属性类型:" + field.getType() + "\t");
		}
		logger.debug("-----> 方法");
		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			logger.debug("-----> 方法名:" + method.getName() + "\t" 
					+ "方法返回类型：" + method.getReturnType() + "\t" 
					+ "方法参数类型:" + Arrays.toString(method.getParameterTypes()));
		}
	}

	public static Object getFieldValue(Object obj, String filedname)
			throws Exception {
		Class<?> cls = obj.getClass();
		Field field = null;
		try {
			field = cls.getDeclaredField(filedname);
			field.setAccessible(true);
			Object val = field.get(obj);
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("-----> catch Exception 没有这个字段：" + filedname);
		}
		return null;

	}

	public static Object setIdKeyValue(Object obj, String filedname,
			String value) throws Exception {
		Class<?> cls = obj.getClass();
		Field field = null;
		try {
			field = cls.getDeclaredField(filedname);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("-----> catch Exception  没有这个字段： " + filedname);
		}
		if (field == null) {
			return null;
		}
		field.setAccessible(true);
		if (!field.getType().getName().contains("Integer")) {
			field.set(obj, value);
		}
		Object val = field.get(obj);
		field.setAccessible(false);
		return val;
	}

	public static Object setFieldValue(Object obj, String filedname,
			String value) throws Exception {
		Class<?> cls = obj.getClass();
		Field field = null;
		try {
			field = cls.getDeclaredField(filedname);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("-----> catch Exception  没有这个字段：  " + filedname);
		}
		if (field == null) {
			return null;
		}
		field.setAccessible(true);
		Object val = field.get(obj);
		field.setAccessible(false);
		return val;
	}

	public static Object readObjMethod(Object obj, String methodName,
			Class<?>[] paramTypes, Object[] params) throws Exception {
		Class<?> cls = obj.getClass();
		Method method = cls.getDeclaredMethod(methodName, paramTypes);
		method.setAccessible(true);
		Object val = method.invoke(obj, params);
		return val;
	}
}
