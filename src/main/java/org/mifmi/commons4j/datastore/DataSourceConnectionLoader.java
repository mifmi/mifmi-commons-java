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

public class DataSourceConnectionLoader extends AbstractConnectionLoader {
	
	public DataSourceConnectionLoader() {
	}
	
	public Connection getConnection(String name) {
		return getDataSourceConnection(name);
	}

}
