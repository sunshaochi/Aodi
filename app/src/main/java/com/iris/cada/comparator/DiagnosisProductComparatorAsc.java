package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.entity.IviewDiagnosisProductBean;

/**
 * 诊断新增潜客    升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class DiagnosisProductComparatorAsc implements Comparator<IviewDiagnosisProductBean> {

	@Override
	public int compare(IviewDiagnosisProductBean lhs, IviewDiagnosisProductBean rhs) {
		
		return lhs.getXinPotentialCustomer().compareTo(rhs.getXinPotentialCustomer());
	}

}