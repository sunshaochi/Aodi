package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 平均单车延保毛利    升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class AvglastInsuranceGrossComparatorAsc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getAvglastInsuranceGross(), rhs.getAvglastInsuranceGross(),true);

//		return lhs.getAvglastInsuranceGross().compareTo(rhs.getAvglastInsuranceGross());
	}

}