package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 精品销售数 升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class BountiqueSaleNumComparatorAsc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getBountiqueSaleNum(), rhs.getBountiqueSaleNum(),true);
//		return lhs.getBountiqueSaleNum().compareTo(rhs.getBountiqueSaleNum());
	}

}