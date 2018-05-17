package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class EnglishAndNumCompactorDesc  implements Comparator<IViewOperateReport>{
	
	/**是顾问模式还是车型模式*/
	private boolean isConsultantMode;
	
	public EnglishAndNumCompactorDesc(boolean isConsultantMode){
		this.isConsultantMode = isConsultantMode;
	}

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		
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
