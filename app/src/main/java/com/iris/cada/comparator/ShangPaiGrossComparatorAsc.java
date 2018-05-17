package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 上牌总毛利   升序   比较器
 * 
 * geng
 */

public class ShangPaiGrossComparatorAsc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getOnCardTotalProfit(), rhs.getOnCardTotalProfit(),true);
	}

}
