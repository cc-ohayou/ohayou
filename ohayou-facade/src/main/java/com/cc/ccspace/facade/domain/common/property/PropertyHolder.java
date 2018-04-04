package com.cc.ccspace.facade.domain.common.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//资源配置器  将properities文件中的配置的属性和值放入map中 需要在springxml中配置获得某个properities文件
public class PropertyHolder extends PropertyPlaceholderConfigurer {

	private static Map<String, Object> ctxPropertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		//ClassPathXmlApplicationContext cxt=new ClassPathXmlApplicationContext("spring-jdbc-properties.xml");
		//ShowPasswordBean  b=cxt.getBean("cc",ShowPasswordBean.class);//此种容易被人从xml配置中追踪到代码 最迷惑人的方式还是采用注释中的方式虽然笨一点
		/*String username=DESUtil.decrypt(props.get("cc.encrypt.u").toString());//此处将加密的用户名和密码编译回来
		String password=DESUtil.decrypt(props.get("cc.encrypt.p").toString());
		props.put("dataSource.username",username);//替换掉dataSource.username  用于迷惑普通人
		props.put("dataSource.password", password);//偷梁换柱 config中的对应属性迷惑人使用  由于生产上未知的问题 加密暂不使用 
			*/
		//  dataSource.username=jxsd
		//	#dataSource.password=jxsd
		//props.put("dataSource.username","cqsd");//替换掉dataSource.username  用于迷惑普通人
		//props.put("dataSource.password", "CQSD_123");//偷梁换柱 config中的对应属性迷惑人使用
		/*Set<Entry<Object, Object>> c= props.entrySet();
		for(Entry<Object, Object> o:c)
		{
			
			System.out.println(o.getKey()+"+++=:"+o.getValue());
			
		}
         		Enumeration<?> u= props.propertyNames();
         		System.out.println("*&*&*&*&*&*&*_===-=---=-=-=&**&*&*&*&*");
         		while(u.hasMoreElements())
         		{
         		Object e=	u.nextElement();
         		System.out.println(e.toString());
         			
         		}*/
		super.processProperties(beanFactoryToProcess, props);//关键的一行
		ctxPropertiesMap = new HashMap<>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

	public static String getStringProperty(String name) {
		return String.valueOf(ctxPropertiesMap.get(name));
	}
	
}
