package com.iris.foundation.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 正则表达式工具
 * 
 * @author yang_qingan
 * 
 */
public class RegexUtil {

	/**
	 * 最新手机号码验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isPhoneNumberNew(String mobiles) {
		/*
		 * 电信 中国电信手机号码开头数字 2G/3G号段（CDMA2000网络）133、153、180、181、189 4G号段 177
		 * 
		 * 联通 2G号段（GSM网络）130、131、132、155、156 3G上网卡145 3G号段（WCDMA网络）185、186 4G号段
		 * 176、185[1]
		 * 
		 * 移动
		 * 2G号段（GSM网络）有134x（0-8）、135、136、137、138、139、150、151、152、158、159、182、183
		 * 、184。 3G号段（TD-SCDMA网络）有157、187、188 3G上网卡 147 4G号段 178
		 * 
		 * 从以上可以看到第一位是【1】开头，第二位则则有【3,4,5,7,8】，第三位则是【0-9】，第三位之后则是数字【0-9】。
		 * 从而可以得出一个符合当前的手机号码验证正则表达式。
		 * 
		 * /^1[3|4|5|7|8][0-9]{9}$/; //验证规则
		 * 
		 * 这个第二位代码可能随时增加一个，比如以16开头、19开头、所以代码要保证几年没问题，
		 * 还可以不验证第二位规则：/^1[0-9]{10}$/;
		 * 
		 */

		// ^1(3|4|5|7|8)\d{9}$
		Pattern p = Pattern.compile("^1(3|4|5|7|8)\\d{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 固定电话验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public boolean isFixedTelephone(String mobiles) {
		// ^\\d{3}-\\d{7,8}|\\d{4}-\\d{7,8}||\\d{7,8}$
		// 0917-6521101
		Pattern p = Pattern.compile("^\\d{3}-\\d{7,8}|\\d{4}-\\d{7,8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 验证手机号码 old
	public static boolean isPhoneNumber(String mobiles) {
		/*
		 * 170 号段为虚拟运营商专属号段
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188, 1705
		 * 联通：130、131、132、152、155、156、185、186, 1709 电信：133、153、180、189、（1349卫通）,
		 * 1700 总结起来就是第一位必定为1，第二位必定为3或5或7,8，其他位置的可以为0-9
		 */
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Pattern p1 = Pattern.compile("^(170[0,5,9])\\d{7}$");
		Matcher m = p.matcher(mobiles), m1 = p1.matcher(mobiles);
		return m.matches() || m1.matches();
	}

	// 验证邮箱
	public static boolean isEmailAddress(String emailAddress) {
		String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		if (TextUtils.isEmpty(emailAddress))
			return false;
		else
			return emailAddress.matches(emailRegex);
	}

	/**
	 * 验证自定义正则表达式
	 * 
	 * @param regex
	 * @param source
	 * @return
	 */
	public static boolean check(String regex, String source) {
		return source.matches(regex);
	}

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			throw new IllegalArgumentException("Bank card code must be number!");
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

}
