package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 总交车数  升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class TurnoverComparatorAsc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		// 先比Turnover，后比bountiqueSaleNum
//		int result = rhs.getTurnover().compareTo(lhs.getTurnover());
//		if (result == 0) {
//			return rhs.getBountiqueSaleNum().compareTo(lhs.getBountiqueSaleNum());
//		} else {
//			return result;
//		}
		
		// 只比Turnover
		return StringUtils.intStringComparator(lhs.getTurnover(), rhs.getTurnover(),true);
//		return lhs.getTurnover().compareTo(rhs.getTurnover());
	}

}