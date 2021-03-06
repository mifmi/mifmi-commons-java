/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

public class StrAlphabetValidator extends AbstractValidator<Object> {
	
	public static final String VR_NOT_VALID = createResultValue(StrAlphabetValidator.class, "NOT_VALID");

	public StrAlphabetValidator() {
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{};
	}

	@Override
	public String doValidate(Object value) {
		if (value == null) {
			return null;
		}
		
		String strValue = value.toString();
		for (int i = 0; i < strValue.length(); i++) {
			char c = strValue.charAt(i);
			if (!(('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z'))) {
				return VR_NOT_VALID;
			}
		}
		
		return null;
	}
}
