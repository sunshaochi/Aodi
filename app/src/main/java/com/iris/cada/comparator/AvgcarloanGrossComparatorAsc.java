package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 平均单车车贷毛利    升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class AvgcarloanGrossComparatorAsc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getAvgcarloanGross(), rhs.getAvgcarloanGross(),true);

//		return lhs.getAvgcarloanGross().compareTo(rhs.getAvgcarloanGross());
	}

}