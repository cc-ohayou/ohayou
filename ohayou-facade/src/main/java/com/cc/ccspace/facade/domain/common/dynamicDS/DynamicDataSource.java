package com.cc.ccspace.facade.domain.common.dynamicDS;

import org.slf4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * AbstractRoutingDataSource又是继承于org.springframework.jdbc.datasource.AbstractDataSource，
 * AbstractDataSource实现了统一的DataSource接口，
 * 所以DynamicDataSource同样可以当一个DataSource使用。
 * @author CAI.F
 * @date: 日期：2016年12月20日 时间:下午4:19:17
 *@version
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(DynamicDataSource.class);
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		logger.info(DBContextHolder.getDBType(),DynamicDataSource.class);
		return	DBContextHolder.getDBType();
		 
	}

}
