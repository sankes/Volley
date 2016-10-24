package com.shankes.util.volley.encode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unicode�����ַ��������ַ���ת������
 * 
 * @author shankesmile
 */
public class UnicodeUtil {
	/**
	 * description���������ַ���ת��Ϊʮ������Unicode�����ַ���(assic���ַ���ת��)
	 * 
	 * @param string
	 *            �����ַ�
	 * @return Unicode�����ַ�
	 */
	public static String stringToUnicode(String string) {
		String str = "";

		for (int i = 0; i < string.length(); i++) {
			int ch = (int) string.charAt(i);

			if (ch > 255) {
				str += "\\u" + Integer.toHexString(ch);
			} else {
				// str += "\\" + Integer.toHexString(ch);
				str += string.substring(i, i + 1);
			}
		}
		return str;
	}

	/**
	 * ��ʮ������Unicode�����ַ���ת��Ϊ�����ַ���(assic���ַ���ת��)
	 * 
	 * @param unicodeString
	 *            Unicode�����ַ�
	 * @return
	 */
	public static String unicodeToString(String unicodeString) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(unicodeString);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			unicodeString = unicodeString.replace(matcher.group(1), ch + "");
		}
		return unicodeString;
	}
}
