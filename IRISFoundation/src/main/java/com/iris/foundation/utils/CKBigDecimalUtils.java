package com.iris.foundation.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

/**
 * 大数字计算辅助类,默认格式为金融格式.
 * 包含了百分数和金额的计算
 * 
 * @author CK
 *
 */
public class CKBigDecimalUtils {
	private int scale = 1;
	private int scaleMode = BigDecimal.ROUND_HALF_UP;
	private String numFormatType = "##,##0.0";
	private DecimalFormat decimalFormat;

	/**
	 * 默认小数点后一位,四舍五入
	 */
	public CKBigDecimalUtils() {
		this(1, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 默认四舍五入
	 * 
	 * @param scale
	 *            小数点后位数
	 */
	public CKBigDecimalUtils(int scale) {
		this(scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * @param scale
	 *            小数点后位数
	 * @param scaleMode
	 *            四舍五入类型
	 */
	public CKBigDecimalUtils(int scale, int scaleMode) {
		this.scale = scale;
		this.scaleMode = scaleMode;
		if (scale > 0) {
			for (int i = 1; i < scale; i++) {
				numFormatType += "0";
			}
		} else {
			numFormatType = "##,##0";
		}
		decimalFormat = new DecimalFormat(numFormatType);
	}

	/**
	 * @param scale
	 * @param scaleMode
	 * @param numFormatType
	 *            数字format格式
	 */
	public CKBigDecimalUtils(int scale, int scaleMode, String numFormatType) {
		this.scale = scale;
		this.scaleMode = scaleMode;
		this.numFormatType = numFormatType;
		decimalFormat = new DecimalFormat(numFormatType);
	}

	/**
	 * 根据传入的数字进行四舍五入进位
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal decimalRound(BigDecimal bigDecimal) {
		return bigDecimal.setScale(scale, scaleMode);
	}

	/**
	 * 根据传入的字符串进行四舍五入进位
	 * 
	 * @param num
	 * @return
	 */
	public BigDecimal decimalRound(String num) {
		return decimalRound(parse(num));
	}

	/**
	 * 格式化数字,在内部已经进行了进位操作
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public String format(BigDecimal bigDecimal) {
		String result = decimalFormat.format(decimalRound(bigDecimal));
		return result;
	}
	/**
	 * 将数字字符串格式化格式化数字,在内部已经进行了进位操作
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public String format(String num) {
		BigDecimal bigDecimal= null;
		if(isNumeric(num)){
			bigDecimal = new BigDecimal(num);
		}else{
			bigDecimal = new BigDecimal(0);
		}
		String result = decimalFormat.format(decimalRound(bigDecimal));
		return result;
	}

	/**
	 * 将本类转出的数字字符串转为数字
	 * 
	 * @param num
	 * @return
	 */
	public BigDecimal parse(String num) {
		BigDecimal result = new BigDecimal(0);
		try {
			result = new BigDecimal(decimalFormat.parse(num).doubleValue());
		} catch (Exception e) {
			System.out.println("数字格式错误");
		}
		return result;
	}

	/**
	 * 加法
	 * 
	 * @param bigDecimal
	 * @param bigDecimal2
	 * @return
	 */
	public BigDecimal add(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		BigDecimal result = bigDecimal.add(bigDecimal2);
		return result;
	}

	/**
	 * 加法-直接返回格式化后数字字符串
	 * 
	 * @param bigDecimal
	 * @param bigDecimal2
	 * @return
	 */
	public String addReturnStr(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		return format(add(bigDecimal, bigDecimal2));
	}

	/**
	 * 减法
	 * 
	 * @param bigDecimal
	 *            减数
	 * @param bigDecimal2
	 *            被减数
	 * @return
	 */
	public BigDecimal subtract(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		BigDecimal result = bigDecimal.subtract(bigDecimal2);
		return result;
	}

	/**
	 * 减法-直接返回格式化后的数字字符串
	 * 
	 * @param bigDecimal
	 *            减数
	 * @param bigDecimal2
	 *            被减数
	 * @return
	 */
	public String subtractReturnStr(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		return format(subtract(bigDecimal, bigDecimal2));
	}

	/**
	 * 乘法
	 * 
	 * @param bigDecimal
	 * @param bigDecimal2
	 * @return
	 */
	public BigDecimal multiply(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		BigDecimal result = bigDecimal.multiply(bigDecimal2);
		return result;
	}

	/**
	 * 乘法-直接返回格式化后的字符串
	 * 
	 * @param bigDecimal
	 * @param bigDecimal2
	 * @return
	 */
	public String multiplyReturnString(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		return format(multiply(bigDecimal, bigDecimal2));
	}

	/**
	 * 除法
	 * 
	 * @param bigDecimal
	 *            除数
	 * @param bigDecimal2
	 *            被除数
	 * @return
	 */
	public BigDecimal divide(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		BigDecimal result = bigDecimal.divide(bigDecimal2,scale,scaleMode);
		return result;
	}

	/**
	 * 除法-直接返回格式化后的字符串
	 * 
	 * @param bigDecimal
	 *            除数
	 * @param bigDecimal2
	 *            被除数
	 * @return
	 */
	public String divideReturnStr(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		return format(divide(bigDecimal, bigDecimal2));
	}

	/**
	 * 除法-直接返回格式化后的百分比字符串
	 * 
	 * @param bigDecimal
	 *            除数
	 * @param bigDecimal2
	 *            被除数
	 * @return
	 */
	public String divideReturnStrPercent(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
		return bigNum2Percent(divide(bigDecimal, bigDecimal2));
	}

	/**
	 * 将一个数字转换为百分数
	 * 
	 * @param bigDecimal
	 */
	public String bigNum2Percent(BigDecimal bigDecimal) {
		return format(multiply(bigDecimal, new BigDecimal(100))) + "%";
	}

	/**
	 * 将一个百分数转换为数字
	 * 
	 * @param percentStr
	 */
	public BigDecimal percent2BigNum(String percentStr) {
		BigDecimal result = new BigDecimal(percentStr.replace("%", ""));
		result = divide(result, new BigDecimal(100));
		return result;
	}

	/**
	 * 判断某个字符串是否不为空,若为空,则返回false,若不为空则返回true
	 * 
	 * @param str
	 * @return
	 */
	@SuppressLint("NewApi")
	public Boolean stringNotNull(String str) {
		if (null == str || str.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断一个字符串是否全为数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		if (stringNotNull(str)) {
			Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*");
			return pattern.matcher(str).matches();
		}
		return false;
	}

}
