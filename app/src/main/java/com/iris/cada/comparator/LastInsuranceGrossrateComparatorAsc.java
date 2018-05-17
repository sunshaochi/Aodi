package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 延保毛利率 升序 比较器
 * @author geng
 *
 */

public class LastInsuranceGrossrateComparatorAsc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getLastInsuranceGrossrate(), rhs.getLastInsuranceGrossrate(),true);
	}

}
