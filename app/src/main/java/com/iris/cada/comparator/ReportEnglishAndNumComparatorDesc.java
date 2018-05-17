package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

public class ReportEnglishAndNumComparatorDesc   implements Comparator<IViewProfitReport>{

	/**是顾问模式还是车型模式*/
	private boolean isConsultantMode;
	
	public ReportEnglishAndNumComparatorDesc(boolean isConsultantMode){
		this.isConsultantMode = isConsultantMode;
	}
	
	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		String str1 = "";
		String str2 = "";
		if(isConsultantMode){//顾问
			str1 = lhs.getSalesconsultant();
			str2 = rhs.getSalesconsultant();
		}else{
			str1 = lhs.getModels();
			str2 = rhs.getModels();
		}
		
		return StringUtils.compare(str1, str2);
	}

}
