/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.datastore;

import java.sql.Connection;

import org.mifmi.commons4j.config.Config;

public class ConfigConnectionLoader extends AbstractConnectionLoader {

	private Config conf;
	private String configKeyPrefix;
	
	public ConfigConnectionLoader(Config config, String configKeyPrefix) {
		this.conf = config;
		this.configKeyPrefix = configKeyPrefix;
	}
	
	public Connection getConnection(String name) {
		String keyPrefix = this.configKeyPrefix + name + ".";

		Connection conn;
		String datasource = this.conf.get(keyPrefix + "datasource", null);
		if (datasource != null) {
			conn = getDataSourceConnection(name);
		} else {
			String driver = this.conf.get(keyPrefix + "driver");
			String url = this.conf.get(keyPrefix + "url");
			String user = this.conf.get(keyPrefix + "user", null);
			String password = this.conf.get(keyPrefix + "password", null);
			
			conn = getJDBCConnection(driver, url, user, password);
		}
		
		return conn;
	}

}
