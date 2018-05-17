package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 车贷毛利率 降序 比较器
 * @author geng
 *
 */

public class CarloanGrossrateComparatorDesc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getCarloanGrossrate(), rhs.getCarloanGrossrate(),false);
	}

}
