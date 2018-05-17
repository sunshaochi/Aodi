package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 上牌总毛利   降序   比较器
 * 
 * geng
 */

public class ShangPaiGrossComparatorDesc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getOnCardTotalProfit(), rhs.getOnCardTotalProfit(),false);
	}

}
