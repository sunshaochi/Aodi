package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 总交车数  降序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class TurnoverComparatorDesc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		
		// 只比Turnover
		return StringUtils.intStringComparator(lhs.getTurnover(), rhs.getTurnover(),false);
//		return rhs.getTurnover().compareTo(lhs.getTurnover());
	}

}