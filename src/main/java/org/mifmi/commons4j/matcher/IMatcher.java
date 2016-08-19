/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public interface IMatcher<T> {
	
	Class<? super T> getType();
	
	boolean matches(T value);

	IMatcher<T> and(IMatcher<T> matcher);

	IMatcher<T> or(IMatcher<T> matcher);

	IMatcher<T> not();
}
