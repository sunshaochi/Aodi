package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 单车上牌毛利    降序   比较器
 * 
 * @author geng
 */

public class AvgShangPaiGrossComparatorDesc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getAvgOnCardGross(), rhs.getAvgOnCardGross(),false);
	}

}
