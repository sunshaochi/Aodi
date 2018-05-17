package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 车贷渗透率    升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class CarloanpermeaterateComparatorAsc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getCarloanpermeaterate(), rhs.getCarloanpermeaterate(),true);

//		return lhs.getCarloanpermeaterate().compareTo(rhs.getCarloanpermeaterate());
	}

}