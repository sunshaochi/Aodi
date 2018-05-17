package com.iris.cada.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class CKNumFormat {
	// ---------------------字符操作开始--------------------------
	/**
	 * 判断某个字符串是否不为空,若为空,则返回false,若不为空则返回true
	 * 
	 * @param str
	 * @return
	 */
	public static Boolean stringNotNull(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断某个字符串是否等于某个特殊字符,若等于,则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static Boolean stringIsHadSpecial(String str) {
		if (stringNotNull(str) && (str.equals("N/A") || str.equals("-"))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否全为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (stringNotNull(str)) {
			Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*");
			return pattern.matcher(str).matches();
		}
		return false;
	}

	/**
	 * 如果字符串为null或者空将值设为0
	 * 
	 * @param str
	 * @return
	 */
	public static int stringNotNull2Int(String str) {
		int temp = 0;
		if (isNumeric(str)) {
			temp = Integer.parseInt(str);
		}
		return temp;
	}

	/**
	 * 如果字符串为null或者空将值设为0
	 * 
	 * @param str
	 * @return
	 */
	public static float stringNotNull2Float(String str) {
		float temp = 0f;
		if (isNumeric(str)) {
			temp = Float.parseFloat(str);
		}
		return temp;
	}

	// --------------------------------字符操作结束----------------------------
	// ----------------------------------数字的格式化,包括进位方式开始-------------------
	public static int SCALE = 1, SCALE_MODE = BigDecimal.ROUND_HALF_UP;

	/**
	 * 对一个float数进行进位操作,默认模式为四舍五入,默认有效位数为小数点两位
	 * 
	 * @param num
	 * @return
	 */
	public static float decimalRound(float num) {
		float result = num;
		BigDecimal bd = new BigDecimal(result);
		result = bd.setScale(SCALE, SCALE_MODE).floatValue();
		return result;
	}

	/**
	 * 对一个float数进行进位操作,默认模式为四舍五入,默认有效位数为小数点两位
	 * 
	 * @param num
	 * @return
	 */
	public static float decimalRound(String num) {
		float result = 0;
		if (isNumeric(num)) {
			result = Float.parseFloat(num);
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(SCALE, SCALE_MODE).floatValue();
		}
		return result;
	}

	// ----------------------------数字的格式化,包括进位方式结束--------------------------
	// -----------------------------百分比开始------------------------------------
	/**
	 * 计算除数和被除数的百分比,默认模式为四舍五入,默认有效位数为小数点1位
	 * 
	 * @param first
	 *            分子
	 * @param second
	 *            分母
	 * @param noneStr
	 *            若分母为零返回的默认字符
	 * @return
	 */
	public static String percentGet(int first, int second, String noneStr) {
		if (second == 0) {
			return noneStr;
		}
		float temp = first * 100f / second;
		String result = "" + decimalRound(temp);
		result = result + "%";
		return result;
	}

	/**
	 * 计算除数和被除数的百分比,默认模式为四舍五入,默认有效位数为小数点两位
	 * 
	 * @param first
	 *            分子
	 * @param second
	 *            分母
	 * @param noneStr
	 *            若分母为零返回的默认字符
	 * @return
	 */
	public static String percentGet(float first, float second, String noneStr) {
		if (second == 0) {
			return noneStr;
		}
		float temp = first * 100f / second;
		String result = "" + decimalRound(temp);
		result = result + "%";
		return result;
	}

	/**
	 * 计算除数和被除数的百分比,默认模式为四舍五入,默认有效位数为小数点两位
	 * 
	 * @param first
	 *            分子
	 * @param second
	 *            分母
	 * @param noneStr
	 *            若分母为零返回的默认字符
	 * @return
	 */
	public static String percentGet(String first, String second, String noneStr) {
		float firstF = 0, secondF = 0;
		if (isNumeric(first) && isNumeric(second)) {// 如果是数字字符串
			firstF = stringNotNull2Float(first);
			secondF = stringNotNull2Float(second);
		} else {
			return noneStr;
		}
		if (secondF == 0) {
			return noneStr;
		}
		float temp = firstF * 100f / secondF;
		String result = "" + decimalRound(temp);
		result = result + "%";
		return result;
	}

	/**
	 * 将百分比的string转化为float类型
	 * 
	 * @param str
	 * @return
	 */
	public float percent2float(String str) {
		float result = 0f;
		if (stringNotNull(str) && str.contains("%")) {
			str = str.replace("%", "");
			if (isNumeric(str)) {
				return Float.parseFloat(str);
			}
		}
		return result;
	}

	/**
	 * 将百分比的string转化为float类型
	 * 
	 * @param str
	 * @return
	 */
	public float percent2int(String str) {
		float result = 0f;
		if (stringNotNull(str) && str.contains("%")) {
			str = str.replace("%", "");
			if (isNumeric(str)) {
				return Integer.parseInt(str);
			}
		}
		return result;
	}
	// -----------------------------百分比开始------------------------------------
}
