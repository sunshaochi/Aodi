package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 上牌 毛利率 降序 比较器
 * @author geng
 *
 */

public class ShangPaiGrossrateComparatorDesc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getOnCardGrossrate(), rhs.getOnCardGrossrate(),false);
	}

}
