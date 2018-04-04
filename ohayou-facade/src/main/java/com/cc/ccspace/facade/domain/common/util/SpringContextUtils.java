/**
 * @ClassName:Test.java
 * @Title:
 * @Description:
 * @Copyright:Copyright(c) 2014
 * @Company:www.si-tech.com.cn
 * 
 * @auther:chenghg
 * @date:2014-12-2
 * @version 1.0
 */
package com.cc.ccspace.facade.domain.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // SpringӦ�������Ļ���

	/**
	 * ʵ��ApplicationContextAware�ӿڵĻص����������������Ļ���
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * ��ȡ����
	 * 
	 * @param name
	 * @return Object һ�����������ע���bean��ʵ��
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * ��ȡ����ΪrequiredType�Ķ���
	 * ���bean���ܱ�����ת������Ӧ���쳣���ᱻ�׳���BeanNotOfRequiredTypeException��
	 * 
	 * @param name
	 *            beanע����
	 * @param requiredType
	 *            ���ض�������
	 * @return Object ����requiredType���Ͷ���
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class<Object> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * ���BeanFactory��һ����������ƥ���bean���壬�򷵻�true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * �ж��Ը�����ע���bean������һ��singleton����һ��prototype��
	 * ������������Ӧ��bean����û�б��ҵ��������׳�һ���쳣��NoSuchBeanDefinitionException��
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
	 * @return Class ע����������
	 * @throws NoSuchBeanDefinitionException
	 */
	@SuppressWarnings("rawtypes")
	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * �����bean������bean�������б����򷵻���Щ����
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
}
