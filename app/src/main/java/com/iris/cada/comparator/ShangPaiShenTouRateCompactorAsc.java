package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 上牌渗透率 升序 比较器
 * @author geng
 *
 */

public class ShangPaiShenTouRateCompactorAsc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getOnCardPermeaterate(), rhs.getOnCardPermeaterate(),true);
	}

}