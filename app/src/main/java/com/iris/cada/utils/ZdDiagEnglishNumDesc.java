package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.ShzdBean;

public class ZdDiagEnglishNumDesc implements Comparator<ShzdBean> {

	/**是顾问模式还是车型模式*/
	private boolean isConsultantMode;
	
	public ZdDiagEnglishNumDesc(boolean isConsultantMode){
		this.isConsultantMode = isConsultantMode;
	}

	
	@Override
	public int compare(ShzdBean lhs, ShzdBean rhs) {
		String str1 = "";
		String str2 = "";
		if(isConsultantMode){//顾问
			str1 = lhs.get服务顾问();
			str2 = rhs.get服务顾问();
		}else{
			str1 = lhs.get车型();
			str2 = rhs.get车型();
		}
		return StringUtils.compare(str1, str2);
	}

}
