package com.iris.cada.comparator;

import java.text.Collator;
import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**报表--运营界面第一列比较器*/

public class EnglishAndNumCompactorAsc implements Comparator<IViewOperateReport>{

	/**是顾问模式还是车型模式*/
	private boolean isConsultantMode;
	
	public EnglishAndNumCompactorAsc(boolean isConsultantMode){
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
//		char[] c={str1.toLowerCase().charAt(0),str2.toLowerCase().charAt(0)};//首字母  
//        String[] str={str1.substring(0, 1),str2.substring(0, 1)};  
//        int type[]={1,1};  
//        for(int i=0;i<2;i++)  
//        {  
//            if(str[i].matches("[\\u4e00-\\u9fbb]+"))//中文字符  
//                      type[i]=1;  
//            else if(c[i]>='a' && c[i]<='z')  
//                type[i]=2;  
//            else if(c[i]>='1' && c[i]<='9')  
//                type[i]=3;  
//            else   
//                type[i]=4;  
//        }  
//        if(type[0]==1 && type[1]==1){  
//            return Collator.getInstance(java.util.Locale.CHINESE).compare(str1, str2);   
//        }
//            if(type[0]==type[1]){ //同一类  
//                return str1.compareTo(str2);  
//            }
//        return type[0]-type[1]; 
		
		return StringUtils.compare(str2, str1);
	}


}
