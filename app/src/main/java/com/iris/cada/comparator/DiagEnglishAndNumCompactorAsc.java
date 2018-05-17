package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.IviewDiagnosisProductBean;
import com.iris.cada.utils.StringUtils;

public class DiagEnglishAndNumCompactorAsc implements Comparator<Check>{
	
	/**是顾问模式还是车型模式*/
	private boolean isConsultantMode;
	
	public DiagEnglishAndNumCompactorAsc(boolean isConsultantMode){
		this.isConsultantMode = isConsultantMode;
	}

	@Override
	public int compare(Check lhs, Check rhs) {
		String str1 = "";
		String str2 = "";
		if(isConsultantMode){//顾问
			str1 = lhs.getSalesConsultant();
			str2 = rhs.getSalesConsultant();
		}else{
			str1 = lhs.getCarType();
			str2 = rhs.getCarType();
		}
		return StringUtils.compare(str2, str1);
	}

}
