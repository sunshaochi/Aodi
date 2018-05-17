package com.iris.cada.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.iris.foundation.utils.CKBigDecimalUtils;
import com.iris.cada.R;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import net.sourceforge.pinyin4j.PinyinHelper;

public class StringUtils {

	public static String FormatString(String string) {
		if (null == string) {
			return "";
		}

		if (string.contains(".")) {
			DecimalFormat format = new DecimalFormat("0.00");
			return format.format(new BigDecimal(string));
		}

		return string;
	}

	public static SpannableString addUnit(Context context, String string, String unitStr) {

		TextAppearanceSpan textAppearanceSpanFirst = new TextAppearanceSpan(context, R.style.textStyle0);
		TextAppearanceSpan textAppearanceSpanSec = new TextAppearanceSpan(context, R.style.textStyle1);

		SpannableString styledText = new SpannableString(FormatString(string) + unitStr);
		styledText.setSpan(textAppearanceSpanFirst, 0, styledText.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		styledText.setSpan(textAppearanceSpanSec, styledText.length() - 1, styledText.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return styledText;
	}

	/**
	 * 比较两个字符串类型的int数字的大小
	 * 
	 * @param lhs
	 * @param rhs
	 * @return  true 升序，false降序
	 * 
	 */
	public static int intStringComparator(String lhs, String rhs, boolean boo) {
		lhs=lhs.replace(",", "");
		rhs=rhs.replace(",", "");
		CKNumFormat ckNumFormat = new CKNumFormat();
		if (ckNumFormat.isNumeric(lhs)) {
			if (boo) {
				Integer lhsInt = Integer.parseInt(lhs);
				Integer rhsInt = Integer.parseInt(rhs);
				return lhsInt.compareTo(rhsInt);
			} else {
				Integer lhsInt = Integer.parseInt(lhs);
				Integer rhsInt = Integer.parseInt(rhs);
				return rhsInt.compareTo(lhsInt);
			}
		} else {
			return 0;
		}

	}
	
	
	
	public static int compare(String ostr1, String ostr2) {
		if (!TextUtils.isEmpty(ostr1) && !TextUtils.isEmpty(ostr2)) {
			for (int i = 0; i < ostr1.length() && i < ostr2.length(); i++) {

				int codePoint1 = ostr1.charAt(i);
				int codePoint2 = ostr2.charAt(i);
				if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
					i++;
				}
				if (codePoint1 != codePoint2) {
					if (Character.isSupplementaryCodePoint(codePoint1)
							|| Character.isSupplementaryCodePoint(codePoint2)) {
						return codePoint1 - codePoint2;
					}
					String pinyin1 = pinyin((char) codePoint1);
					String pinyin2 = pinyin((char) codePoint2);

					if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
						if (!pinyin1.equals(pinyin2)) {
							return pinyin1.compareTo(pinyin2);
						}
					} else {
						return codePoint1 - codePoint2;
					}
				}
			}
			return ostr1.length() - ostr2.length();
		}
		return 0;
	}
	
	
	// 获得汉字拼音的首字符
		private static String pinyin(char c) {
			String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
			if (pinyins == null) {
				return null;
			}
			return pinyins[0];
		}
		
		
		
		
		/**
		 * 数字转千分符
		 * 
		 * @param str
		 * @return
		 */
		public static String intDivTo0(String str) {
			// 如果是null，直接返回""
			if ("null".equals(str) || TextUtils.isEmpty(str)) {
				return "0.00";
			}
			
			String result="0";
			try {
				CKBigDecimalUtils bigDecimalUtils = new CKBigDecimalUtils(0);
				result= bigDecimalUtils.format(new BigDecimal(str));
			} catch (Exception e) {
				result = str;	// 如果转换失败  就把原字符串  返回
				Log.e("StringUtils", "数字添加千分符时,格式异常");
			}
			
			return result;
		}
		
		/** 如果是 null 转换为0 */
		public static String nullTo0(String str) {
			if ("null".equals(str) || "null" == str || str == null || "" == str || "".equals(str)) {
				return "0";
			}
			return str;
		}



}
