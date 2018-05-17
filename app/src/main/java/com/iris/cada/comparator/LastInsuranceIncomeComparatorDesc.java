package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 延保销售本店收入    降序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class LastInsuranceIncomeComparatorDesc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getLastInsuranceIncome(), rhs.getLastInsuranceIncome(),false);

//		return rhs.getLastInsuranceIncome().compareTo(lhs.getLastInsuranceIncome());
	}

}