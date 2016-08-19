/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper methods for working with String and CharSequence instances.
 * 
 * @author mozq
 */
public final class StringUtilz {
	private static final char[] HALF_WIDTH_KANA = {
			'｡', '｢', '｣', '､', '･',
			'ｦ', 'ｧ', 'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ', 'ｮ', 'ｯ', 'ｰ',
			'ｱ', 'ｲ', 'ｳ', 'ｴ', 'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ', 'ｺ',
			'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ', 'ﾀ', 'ﾁ', 'ﾂ', 'ﾃ', 'ﾄ',
			'ﾅ', 'ﾆ', 'ﾇ', 'ﾈ', 'ﾉ', 'ﾊ', 'ﾋ', 'ﾌ',
			'ﾍ', 'ﾎ', 'ﾏ', 'ﾐ', 'ﾑ', 'ﾒ', 'ﾓ', 'ﾔ', 'ﾕ', 'ﾖ',
			'ﾗ', 'ﾘ', 'ﾙ', 'ﾚ', 'ﾛ', 'ﾜ', 'ﾝ'
			};

	private static final char[] FULL_WIDTH_KANA = {
			'。', '「', '」', '、', '・',
			'ヲ', 'ァ', 'ィ', 'ゥ', 'ェ', 'ォ', 'ャ', 'ュ', 'ョ', 'ッ', 'ー',
			'ア', 'イ', 'ウ', 'エ', 'オ', 'カ', 'キ', 'ク', 'ケ', 'コ',
			'サ', 'シ', 'ス', 'セ', 'ソ', 'タ', 'チ', 'ツ', 'テ', 'ト',
			'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'ヒ', 'フ', 'ヘ', 'ホ',
			'マ', 'ミ', 'ム', 'メ', 'モ', 'ヤ', 'ユ', 'ヨ',
			'ラ', 'リ', 'ル', 'レ', 'ロ', 'ワ', 'ン'
			};
	  
	private StringUtilz() {
		// NOP
	}
	
	public static int count(String str, char ch) {
		if (str == null) {
			return 0;
		}
		
		int count = 0;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (str.charAt(i) == ch) {
				count++;
			}
		}
		
		return count;
	}
	
	public static String left(String str, int len) {
		return left(str, len, null);
	}
	
	public static String left(String str, int len, String appendStrIfOver) {
		if (str == null) {
			return null;
		}
		if (str.length() <= len) {
			return str;
		}
		if (appendStrIfOver == null || appendStrIfOver.isEmpty()) {
			return str.substring(0, len);
		} else {
			return str.substring(0, len - appendStrIfOver.length()) + appendStrIfOver;
		}
	}

	public static String[] split(String str, String separatorRegex) {
		return split(str, separatorRegex, false);
	}
	public static String[] split(String str, String separatorRegex, boolean trim) {
		if (str == null) {
			return null;
		}
		if (separatorRegex == null) {
			throw new NullPointerException();
		}
		if (trim) {
			str = str.trim();
			separatorRegex = "\\s*" + separatorRegex + "\\s*";
		}
		
		if (str.isEmpty()) {
			return new String[0];
		}
		
		return str.split(separatorRegex, -1);
	}
	
	public static String join(String separator, Object... values) {
		return join(new StringBuilder(), separator, values).toString();
	}
	public static StringBuilder join(StringBuilder sb, String separator, Object... values) {
		if (values == null || values.length == 0) {
			return sb;
		}
		
		boolean isFirst = true;
		for (Object value : values) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(separator);
			}
			if (value != null) {
				sb.append(value.toString());
			}
		}
		
		return sb;
	}
	
	public static String join(String separator, Collection<Object> values) {
		return join(new StringBuilder(), separator, values).toString();
	}
	public static StringBuilder join(StringBuilder sb, String separator, Collection<Object> values) {
		if (values == null || values.isEmpty()) {
			return sb;
		}
		return join(sb, separator, values.toArray(new Object[values.size()]));
	}
	
	public static String paddingLeft(String str, int len, char paddingChar) {
		StringBuilder sb = new StringBuilder(len);
		paddingLeft(sb, str, len, paddingChar);
		
		return sb.toString();
	}
	
	public static void paddingLeft(StringBuilder sb, String str, int len, char paddingChar) {
		if (str == null) {
			str = "";
		}
		
		int strLen = str.length();
		
		if (len <= strLen) {
			sb.append(str);
			return;
		}
		
		for (int i = strLen; i < len; i++) {
			sb.append(paddingChar);
		}
		sb.append(str);
	}
	
	public static String paddingRight(String str, int len, char paddingChar) {
		StringBuilder sb = new StringBuilder(len);
		paddingRight(sb, str, len, paddingChar);
		
		return sb.toString();
	}
	
	public static void paddingRight(StringBuilder sb, String str, int len, char paddingChar) {
		if (str == null) {
			str = "";
		}
		
		int strLen = str.length();
		
		if (len <= strLen) {
			sb.append(str);
			return;
		}

		sb.append(str);
		for (int i = strLen; i < len; i++) {
			sb.append(paddingChar);
		}
	}
	
	public static String repeat(String str, int repeat) {
		return repeat(str, repeat, null);
	}
	
	public static String repeat(String str, int repeat, String separator) {
		int strLen = (str == null) ? 0 : str.length();
		int separatorLen = (separator == null) ? 0 : separator.length();
		int bufSize = (strLen + separatorLen) * ((repeat < 0) ? 0 : repeat);
		return repeat(new StringBuilder(bufSize), str, repeat, separator).toString();
	}
	
	public static StringBuilder repeat(StringBuilder sb, String str, int repeat) {
		return repeat(sb, str, repeat, null);
	}
	public static StringBuilder repeat(StringBuilder sb, String str, int repeat, String separator) {
		
		for (int i = 0; i < repeat; i++) {
			if (i != 0 && separator != null) {
				sb.append(separator);
			}
			if (str != null) {
				sb.append(str);
			}
		}
		
		return sb;
	}
	
	public static String replaceAll(String str, char newChar, char... oldChars) {
		if (str == null) {
			return null;
		}
		if (oldChars == null || oldChars.length == 0) {
			return str;
		}
		
		int len = str.length();
		
		int idx = -1;
		outer: for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			for (char oldChar: oldChars) {
				if (ch == oldChar) {
					idx = i;
					break outer;
				}
			}
		}
		if (idx == -1) {
			return str;
		}
		
		char[] chars = str.toCharArray();
		for (int i = idx; i < len; i++) {
			char ch = chars[i];
			for (char oldChar : oldChars) {
				if (ch == oldChar) {
					chars[i] = newChar;
					break;
				}
			}
		}
		
		return new String(chars);
	}
	
	public static StringBuilder replaceAll(StringBuilder sb, char newChar, char... oldChars) {
		if (sb == null) {
			return null;
		}
		if (oldChars == null || oldChars.length == 0) {
			return sb;
		}
		
		int len = sb.length();
		
		for (int i = 0; i < len; i++) {
			char ch = sb.charAt(i);
			for (char oldChar : oldChars) {
				if (ch == oldChar) {
					sb.setCharAt(i, newChar);
					break;
				}
			}
		}
		
		return sb;
	}
	
	public static String turncate(String str, int len) {
		if (str == null) {
			return null;
		}
		if (str.length() <= len) {
			return str;
		}
		
		return str.substring(0, len);
	}

	public static int indexOf(CharSequence charSeq, char ch) {
		return indexOf(charSeq, ch, 0);
	}

	public static int indexOf(CharSequence charSeq, char ch, int fromIndex) {
		if (charSeq == null) {
			return -1;
		}
		int len = charSeq.length();
		for (int i = fromIndex; i < len; i++) {
			char c = charSeq.charAt(i);
			if (c == ch) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(CharSequence charSeq, char[] ch) {
		return indexOf(charSeq, ch, 0);
	}

	public static int indexOf(CharSequence charSeq, char[] ch, int fromIndex) {
		if (charSeq == null) {
			return -1;
		}
		int len = charSeq.length();
		for (int i = fromIndex; i < len; i++) {
			char c = charSeq.charAt(i);
			for (int j = 0; j < ch.length; j++) {
				if (c == ch[j]) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int lastIndexOf(CharSequence charSeq, char ch) {
		if (charSeq == null) {
			return -1;
		}
		return lastIndexOf(charSeq, ch, charSeq.length() - 1);
	}

	public static int lastIndexOf(CharSequence charSeq, char ch, int fromIndex) {
		if (charSeq == null) {
			return -1;
		}
		for (int i = fromIndex; 0 <= i; i--) {
			char c = charSeq.charAt(i);
			if (c == ch) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(CharSequence charSeq, char[] ch) {
		if (charSeq == null) {
			return -1;
		}
		return lastIndexOf(charSeq, ch, charSeq.length() - 1);
	}

	public static int lastIndexOf(CharSequence charSeq, char ch[], int fromIndex) {
		if (charSeq == null) {
			return -1;
		}
		for (int i = fromIndex; 0 <= i; i--) {
			char c = charSeq.charAt(i);
			for (int j = 0; j < ch.length; j++) {
				if (c == ch[j]) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static String substringBefore(String str, int endChar) {
		if (str == null) {
			return null;
		}
		if (endChar < 0) {
			return str;
		}
		
		int idx = str.indexOf(endChar);
		if (idx == -1) {
			return str;
		}
		
		return str.substring(0, idx);
	}
	
	public static String substringBefore(String str, String endStr) {
		if (str == null) {
			return null;
		}
		if (endStr == null) {
			return str;
		}
		
		int idx = str.indexOf(endStr);
		if (idx == -1) {
			return str;
		}
		
		return str.substring(0, idx);
	}
	
	public static String substringAfter(String str, int beginChar, boolean last) {
		if (str == null) {
			return null;
		}
		if (beginChar < 0) {
			return str;
		}
		
		int idx = (last) ? str.lastIndexOf(beginChar) : str.indexOf(beginChar);
		if (idx == -1) {
			return str;
		}
		
		return str.substring(idx);
	}
	
	public static String substringAfter(String str, String beginStr, boolean last) {
		if (str == null) {
			return null;
		}
		if (beginStr == null) {
			return str;
		}
		
		int idx = (last) ? str.lastIndexOf(beginStr) : str.indexOf(beginStr);
		if (idx == -1) {
			return str;
		}
		
		return str.substring(idx);
	}
	
	public static String separateByLength(String str, String separator, int length) {
		if (str == null) {
			return null;
		}
		if (separator == null) {
			return str;
		}
		if (str.length() <= length) {
			return str;
		}
		
		int strLen = str.length();
		StringBuilder sb = new StringBuilder(strLen + (separator.length() * (strLen / length)));
		int curLen = 0;
		for (int i = 0; i < strLen; i++) {
			char ch = str.charAt(i);
			sb.append(ch);
			if (Character.isHighSurrogate(ch)) {
				i++;
				if (i < strLen) {
					char lowCh = str.charAt(i);
					sb.append(lowCh);
				}
			}
			curLen++;
			
			if (length <= curLen && (i + 1 != strLen)) {
				sb.append(separator);
			}
		}
		
		return sb.toString();
	}
	
	public static String escape(String str, char escapeChar, char[] targetChars, char[] escapedChars) {
		if (escapedChars != null) {
			if (targetChars.length != escapedChars.length) {
				throw new IllegalArgumentException();
			}
		}
		
		if (str == null) {
			return null;
		}
		if (targetChars == null || targetChars.length == 0) {
			return str;
		}
		
		int len = str.length();
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			if (ch == escapeChar) {
				sb.append(escapeChar);
				sb.append(escapeChar);
			} else {
				int targetIdx = -1;
				for (int j = 0; j < targetChars.length; j++) {
					char targetChar = targetChars[j];
					if (ch == targetChar) {
						targetIdx = j;
						break;
					}
				}
				if (targetIdx == -1) {
					sb.append(ch);
				} else {
					sb.append(escapeChar);
					if (escapedChars == null) {
						sb.append(ch);
					} else {
						sb.append(escapedChars[targetIdx]);
					}
				}
			}
		}
		return sb.toString();
	}

	public static String unescape(String str, char escapeChar, char[] targetChars, char[] escapedChars) {
		return unescape(str, escapeChar, targetChars, escapedChars, false);
	}
	public static String unescape(String str, char escapeChar, char[] targetChars, char[] escapedChars, boolean useUnicodeEscape) {
		if (targetChars != null && escapedChars != null) {
			if (targetChars.length != escapedChars.length) {
				throw new IllegalArgumentException();
			}
		}
		
		if (str == null) {
			return null;
		}
		if ((targetChars == null || targetChars.length == 0) && !useUnicodeEscape) {
			return str;
		}
		
		int idx = str.indexOf(escapeChar);
		if (idx == -1) {
			return str;
		}
		
		int len = str.length();
		StringBuilder sb = new StringBuilder(len);
		boolean escape = false;
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			if (ch == escapeChar) {
				if (escape) {
					sb.append(escapeChar);
					escape = false;
				} else {
					escape = true;
				}
			} else {
				if (escape) {
					int targetIdx = -1;
					if (escapedChars != null) {
						for (int j = 0; j < escapedChars.length; j++) {
							if (ch == escapedChars[j]) {
								targetIdx = j;
								break;
							}
						}
					}
					if (targetIdx == -1) {
						if (useUnicodeEscape && ch == 'u') {
							i++;
							String codePointHexStr = str.substring(i, Math.min(i + 4, len));
							i += 3;
							try {
								sb.append((char)Integer.parseInt(codePointHexStr, 16));
							} catch (NumberFormatException e) {
								// parse failed
								sb.append('u');
								sb.append(codePointHexStr);
							}
						} else if (useUnicodeEscape && ch == 'U') {
							i++;
							String codePointHexStr = str.substring(i, Math.min(i + 8, len));
							i += 7;
							try {
								int codePoint = (int)Long.parseLong(codePointHexStr, 16);
								sb.appendCodePoint(codePoint);
							} catch (IllegalArgumentException e) {
								// parse failed
								sb.append('U');
								sb.append(codePointHexStr);
							}
						} else {
							sb.append(ch);
						}
					} else {
						if (targetChars == null) {
							sb.append(ch);
						} else {
							sb.append(targetChars[targetIdx]);
						}
					}
					escape = false;
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
	
	public static String null2Str(String str) {
		return null2Str(str, "");
	}
	
	public static String null2Str(String str, String nullStr) {
		if (str == null) {
			return nullStr;
		}
		return str;
	}
	
	public static String format(String pattern, Object... params) {
		if (pattern == null) {
			return null;
		}
		if (params == null || params.length == 0) {
			return pattern;
		}
		return MessageFormat.format(pattern, params);
	}

	public static String format(String pattern, Map<String, Object> params) {
		return format(pattern, params, "{", "}");
	}
	public static String format(String pattern, Map<String, Object> params, String varPrefix, String varSuffix) {
		int len = pattern.length();
		
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++) {
			int beginIdx = pattern.indexOf(varPrefix, i);
			if (beginIdx == -1) {
				sb.append(pattern, i, len);
				break;
			}
			sb.append(pattern, i, beginIdx);
			int varBeginIdx = beginIdx + varPrefix.length();
			
			int endIdx = pattern.indexOf(varSuffix, varBeginIdx);
			if (endIdx == -1) {
				sb.append(pattern, beginIdx, len);
				break;
			}
			int varEndIdx = endIdx + varSuffix.length();
			
			String varName = pattern.substring(varBeginIdx, varEndIdx);
			Object val = params.get(varName);
			if (val != null) {
				sb.append(val);
			}
		}
		
		return sb.toString();
	}
	
	public Map<String, String> parse(String str, String pattern, String varPrefix, String varSuffix) {
		int len = pattern.length();
		
		List<String> varNameList = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++) {
			int beginIdx = pattern.indexOf(varPrefix, i);
			if (beginIdx == -1) {
				sb.append(Pattern.quote(pattern.substring(i, len)));
				break;
			}
			sb.append(Pattern.quote(pattern.substring(i, beginIdx)));
			int varBeginIdx = beginIdx + varPrefix.length();
			
			int endIdx = pattern.indexOf(varSuffix, varBeginIdx);
			if (endIdx == -1) {
				sb.append(Pattern.quote(pattern.substring(beginIdx, len)));
				break;
			}
			int varEndIdx = endIdx + varSuffix.length();
			
			String varName = pattern.substring(varBeginIdx, varEndIdx);
			varNameList.add(varName);
			sb.append("(.*)");
		}
		
		Pattern p = Pattern.compile(sb.toString());
		Matcher m = p.matcher(str);
		if (!m.matches()) {
			return null;
		}
		
		Map<String, String> varMap = new LinkedHashMap<String, String>(varNameList.size());
		for (int i = 0; i < varNameList.size(); i++) {
			String varName = varNameList.get(i);
			String val = m.group(i + 1);
			
			varMap.put(varName, val);
		}
		
		return varMap;
	}
	
	public static String toHalfWidth(String str, boolean alpha, boolean num, boolean symbols, boolean space, boolean kana, boolean hangul) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		
		int codeOffset = 'Ａ' - 'A';
		
		int len = str.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			
			int ch = -1;
			int nch = -1;
			if (alpha && ch == -1) {
				if ('Ａ' <= c && c <= 'Ｚ') {
					ch = (char)(c - codeOffset);
				} else if ('ａ' <= c && c <= 'ｚ') {
					ch = (char)(c - codeOffset);
				}
			}
			if (num && ch == -1) {
				if ('０' <= c && c <= '９') {
					ch = (char)(c - codeOffset);
				}
			}
			if (symbols && ch == -1) {
				if ('！' <= c && c <= '／') {
					ch = (char)(c - codeOffset);
				} else if ('：' <= c && c <= '＠') {
					ch = (char)(c - codeOffset);
				} else if ('［' <= c && c <= '｀') {
					ch = (char)(c - codeOffset);
				} else if ('｛' <= c && c <= '～') {
					ch = (char)(c - codeOffset);
				} else if (c == '€') {
					ch = '€';
				} else if (c == '￠') {
					ch = '¢';
				} else if (c == '￡') {
					ch = '£';
				} else if (c == '￣') {
					ch = '¯';
				} else if (c == '￤') {
					ch = '¦';
				} else if (c == '￢') {
					ch = '¬';
				} else if (c == '￥') {
					ch = '¥';
				} else if (c == '￦') {
					ch = '₩';
				} else if (c == '│') {
					ch = '￨';
				} else if (c == '←') {
					ch = '￩';
				} else if (c == '↑') {
					ch = '￪';
				} else if (c == '→') {
					ch = '￫';
				} else if (c == '↓') {
					ch = '￬';
				} else if (c == '■') {
					ch = '￭';
				} else if (c == '○') {
					ch = '￮';
				}
			}
			if (space && ch == -1) {
				if (c == '\u3000') {
					ch = '\u0020';
				}
			}
			if (kana && ch == -1) {
				int kch = -1;
				int nkch = -1;
				for (int j = 0; j < FULL_WIDTH_KANA.length; j++) {
					if (c == FULL_WIDTH_KANA[j]) {
						kch = HALF_WIDTH_KANA[j];
						break;
					}
				}
				if (kch == -1) {
					switch (c) {
					case 'ガ': kch = 'ｶ'; nkch = 'ﾞ'; break;
					case 'ギ': kch = 'ｷ'; nkch = 'ﾞ'; break;
					case 'グ': kch = 'ｸ'; nkch = 'ﾞ'; break;
					case 'ゲ': kch = 'ｹ'; nkch = 'ﾞ'; break;
					case 'ゴ': kch = 'ｺ'; nkch = 'ﾞ'; break;
					case 'ザ': kch = 'ｻ'; nkch = 'ﾞ'; break;
					case 'ジ': kch = 'ｼ'; nkch = 'ﾞ'; break;
					case 'ズ': kch = 'ｽ'; nkch = 'ﾞ'; break;
					case 'ゼ': kch = 'ｾ'; nkch = 'ﾞ'; break;
					case 'ゾ': kch = 'ｿ'; nkch = 'ﾞ'; break;
					case 'ダ': kch = 'ﾀ'; nkch = 'ﾞ'; break;
					case 'ヂ': kch = 'ﾁ'; nkch = 'ﾞ'; break;
					case 'ヅ': kch = 'ﾂ'; nkch = 'ﾞ'; break;
					case 'デ': kch = 'ﾃ'; nkch = 'ﾞ'; break;
					case 'ド': kch = 'ﾄ'; nkch = 'ﾞ'; break;
					case 'バ': kch = 'ﾊ'; nkch = 'ﾞ'; break;
					case 'ビ': kch = 'ﾋ'; nkch = 'ﾞ'; break;
					case 'ブ': kch = 'ﾌ'; nkch = 'ﾞ'; break;
					case 'ベ': kch = 'ﾍ'; nkch = 'ﾞ'; break;
					case 'ボ': kch = 'ﾎ'; nkch = 'ﾞ'; break;
					case 'ヴ': kch = 'ｳ'; nkch = 'ﾞ'; break;
					case 'パ': kch = 'ﾊ'; nkch = 'ﾟ'; break;
					case 'ピ': kch = 'ﾋ'; nkch = 'ﾟ'; break;
					case 'プ': kch = 'ﾌ'; nkch = 'ﾟ'; break;
					case 'ペ': kch = 'ﾍ'; nkch = 'ﾟ'; break;
					case 'ポ': kch = 'ﾎ'; nkch = 'ﾟ'; break;
					case '゛': kch = 'ﾞ'; break;
					case '゜': kch = 'ﾟ'; break;
					}
				}
				if (kch != -1) {
					ch = (char)kch;
					if (nkch != -1) {
						nch = (char)nkch;
					}
				}
			}
			if (hangul && ch == -1) {
				if (c == 'ㅤ') {
					ch = '\uFFA0';
				} else if ('ㄱ' <= c && c <= 'ㅎ') {
					ch = (char)(c - ('ㄱ' - '\uFFA1'));
				} else if ('ㅏ' <= c && c <= 'ㅔ') {
					ch = (char)(c - ('ㅏ' - '\uFFC2'));
				} else if ('ㅕ' <= c && c <= 'ㅚ') {
					ch = (char)(c - ('ㅕ' - '\uFFCA'));
				} else if ('ㅛ' <= c && c <= 'ㅠ') {
					ch = (char)(c - ('ㅛ' - '\uFFD2'));
				} else if ('ㅡ' <= c && c <= 'ㅣ') {
					ch = (char)(c - ('ㅡ' - '\uFFDA'));
				}
			}
			if (ch == -1) {
				ch = c;
			}
			
			sb.append((char)ch);
			if (nch != -1) {
				sb.append((char)nch);
			}
		}
		
		return sb.toString();
	}
	
	public static String toFullWidth(String str, boolean alpha, boolean num, boolean symbols, boolean space, boolean kana, boolean hangul) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		
		int codeOffset = 'Ａ' - 'A';
		
		int len = str.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			char nc = (i + 1 == str.length()) ? 0 : str.charAt(i + 1);
			
			int ch = -1;

			if (alpha && ch == -1) {
				if ('A' <= c && c <= 'Z') {
					ch = (char)(c + codeOffset);
				} else if ('a' <= c && c <= 'z') {
					ch = (char)(c + codeOffset);
				}
			}
			if (num && ch == -1) {
				if ('0' <= c && c <= '9') {
					ch = (char)(c + codeOffset);
				}
			}
			if (symbols && ch == -1) {
				if ('!' <= c && c <= '/') {
					ch = (char)(c + codeOffset);
				} else if (':' <= c && c <= '@') {
					ch = (char)(c + codeOffset);
				} else if ('[' <= c && c <= '`') {
					ch = (char)(c + codeOffset);
				} else if ('{' <= c && c <= '~') {
					ch = (char)(c + codeOffset);
				} else if (c == '€') {
					ch = '€';
				} else if (c == '¢') {
					ch = '￠';
				} else if (c == '£') {
					ch = '￡';
				} else if (c == '¯') {
					ch = '￣';
				} else if (c == '¦') {
					ch = '￤';
				} else if (c == '¬') {
					ch = '￢';
				} else if (c == '¥') {
					ch = '￥';
				} else if (c == '₩') {
					ch = '￦';
				} else if (c == '￨') {
					ch = '│';
				} else if (c == '￩') {
					ch = '←';
				} else if (c == '￪') {
					ch = '↑';
				} else if (c == '￫') {
					ch = '→';
				} else if (c == '￬') {
					ch = '↓';
				} else if (c == '￭') {
					ch = '■';
				} else if (c == '￮') {
					ch = '○';
				}
			}
			if (space && ch == -1) {
				if (c == '\u0020') {
					ch = '\u3000';
				}
			}
			if (kana && ch == -1) {
				int kch = -1;
				if (nc == 'ﾞ') {
					switch (c) {
					case 'ｶ': kch = 'ガ'; break;
					case 'ｷ': kch = 'ギ'; break;
					case 'ｸ': kch = 'グ'; break;
					case 'ｹ': kch = 'ゲ'; break;
					case 'ｺ': kch = 'ゴ'; break;
					case 'ｻ': kch = 'ザ'; break;
					case 'ｼ': kch = 'ジ'; break;
					case 'ｽ': kch = 'ズ'; break;
					case 'ｾ': kch = 'ゼ'; break;
					case 'ｿ': kch = 'ゾ'; break;
					case 'ﾀ': kch = 'ダ'; break;
					case 'ﾁ': kch = 'ヂ'; break;
					case 'ﾂ': kch = 'ヅ'; break;
					case 'ﾃ': kch = 'デ'; break;
					case 'ﾄ': kch = 'ド'; break;
					case 'ﾊ': kch = 'バ'; break;
					case 'ﾋ': kch = 'ビ'; break;
					case 'ﾌ': kch = 'ブ'; break;
					case 'ﾍ': kch = 'ベ'; break;
					case 'ﾎ': kch = 'ボ'; break;
					case 'ｳ': kch = 'ヴ'; break;
					}
					if (kch != -1) {
						i++;
					}
				} else if (nc == 'ﾟ') {
					switch (c) {
					case 'ﾊ': kch = 'パ'; break;
					case 'ﾋ': kch = 'ピ'; break;
					case 'ﾌ': kch = 'プ'; break;
					case 'ﾍ': kch = 'ペ'; break;
					case 'ﾎ': kch = 'ポ'; break;
					}
					if (kch != -1) {
						i++;
					}
				}
				if (kch == -1) {
					for (int j = 0; j < HALF_WIDTH_KANA.length; j++) {
						if (c == HALF_WIDTH_KANA[j]) {
							kch = FULL_WIDTH_KANA[j];
							break;
						}
					}
				}
				if (kch == -1) {
					switch (c) {
					case 'ﾞ': kch = '\u3099'; break;
					case 'ﾟ': kch = '\u309A'; break;
					}
				}
				if (kch != -1) {
					ch = (char)kch;
				}
			}
			if (hangul && ch == -1) {
				if (c == '\uFFA0') {
					ch = 'ㅤ';
				} else if ('\uFFA1' <= c && c <= '\uFFBE') {
					ch = (char)(c + ('ㄱ' - '\uFFA1'));
				} else if ('\uFFC2' <= c && c <= '\uFFC7') {
					ch = (char)(c + ('ㅏ' - '\uFFC2'));
				} else if ('\uFFCA' <= c && c <= '\uFFCF') {
					ch = (char)(c + ('ㅕ' - '\uFFCA'));
				} else if ('\uFFD2' <= c && c <= '\uFFD7') {
					ch = (char)(c + ('ㅛ' - '\uFFD2'));
				} else if ('\uFFDA' <= c && c <= '\uFFDC') {
					ch = (char)(c + ('ㅡ' - '\uFFDA'));
				}
			}
			if (ch == -1) {
				ch = c;
			}
			
			sb.append((char)ch);
		}
		
		return sb.toString();
	}
	
	public static String toCamelCase(String str, boolean firstCapital) {
		return changeSeparator(str, -1, true, Boolean.valueOf(firstCapital), null);
	}
	
	public static String toSnakeCase(String str, boolean capitalize, Boolean upper) {
		return changeSeparator(str, '_', capitalize, null, upper);
	}
	
	public static String toChainCase(String str, boolean capitalize, Boolean upper) {
		return changeSeparator(str, '-', capitalize, null, upper);
	}
	
	public static String changeSeparator(String str, int separatorChar, boolean capitalize, Boolean firstCapital, Boolean upper) {
		if (str == null) {
			return null;
		}
		
		int len = str.length();
		
		if (len == 0) {
			return "";
		}
		
		int cnt = Character.codePointCount(str, 0, len);
		
		StringBuilder sb = new StringBuilder(len);
		boolean inWord = false;
		boolean isFirstWord = false;
		boolean isFirstLineWord = true;
		boolean isBeforeUpper = false;
		for (int i = 0; i < cnt; i++) {
			int cp = str.codePointAt(i);
			
			if (isASCII(cp)) {
				if (Character.isAlphabetic(cp) || Character.isDigit(cp)) {
					if (Character.isUpperCase(cp)) {
						isFirstWord = !isBeforeUpper;
						if (!isFirstWord && i + 1 < cnt) {
							int ncp = str.codePointAt(i + 1);
							if (Character.isAlphabetic(ncp) && !(Character.isUpperCase(ncp) || Character.isDigit(cp))) {
								isFirstWord = true;
							}
						}
						isBeforeUpper = true;
					} else {
						isFirstWord = !inWord;
						isBeforeUpper = false;
					}
					inWord = true;
				} else if (cp == '\r' || cp == '\n') {
					isFirstWord = false;
					inWord = false;
					isBeforeUpper = false;
					
					isFirstLineWord = true;
				} else {
					isFirstWord = false;
					inWord = false;
					isBeforeUpper = false;
				}
			} else {
				isFirstWord = !inWord;
				inWord = true;
				isBeforeUpper = false;
			}
			
			if (inWord) {
				if (isFirstWord) {
					if (isFirstLineWord) {
						isFirstLineWord = false;
					} else {
						if (0 <= separatorChar) {
							sb.append((char) separatorChar);
						}
					}
				}
				if (capitalize) {
					if (isFirstWord) {
						if (i == 0) {
							if (firstCapital == null || firstCapital.booleanValue()) {
								sb.appendCodePoint(Character.toUpperCase(cp));
							} else {
								sb.appendCodePoint(Character.toLowerCase(cp));
							}
						} else {
							sb.appendCodePoint(Character.toUpperCase(cp));
						}
					} else {
						sb.appendCodePoint(Character.toLowerCase(cp));
					}
				} else {
					if (upper == null) {
						sb.appendCodePoint(cp);
					} else if(upper.booleanValue()) {
						sb.appendCodePoint(Character.toUpperCase(cp));
					} else {
						sb.appendCodePoint(Character.toLowerCase(cp));
					}
				}
			} else {
				if (cp == '\r' || cp == '\n') {
					sb.appendCodePoint(cp);
				}
			}
		}
		
		return sb.toString();
	}
	
	public static boolean isBlank(String str) {
		if (str == null) {
			return true;
		}
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			if (!Character.isWhitespace(ch)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isASCII(char c) {
		if ('\u007F' < c) {
			return false;
		}
		return true;
	}
	
	public static boolean isASCII(int codePoint) {
		if ('\u007F' < codePoint) {
			return false;
		}
		return true;
	}
	
	public static boolean isASCII(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			if (!isASCII(ch)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isHalfWidth(char ch) {
		return '\u0000' <= ch && ch <= '\u00FF'
				|| '\uFF61' <= ch && ch <= '\uFFDC'
				|| '\uFFE8' <= ch && ch <= '\uFFEE';
	}
	
	public static boolean isHalfWidth(int codePoint) {
		return '\u0000' <= codePoint && codePoint <= '\u00FF'
				|| '\uFF61' <= codePoint && codePoint <= '\uFFDC'
				|| '\uFFE8' <= codePoint && codePoint <= '\uFFEE';
	}
	
	public static boolean isHalfWidth(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			if (!isHalfWidth(ch)) {
				return false;
			}
		}
		return true;
	}
}
