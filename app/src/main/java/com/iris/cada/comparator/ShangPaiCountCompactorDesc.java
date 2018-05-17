package com.iris.cada.comparator;

import java.util.Comparator;


/**
 * 上牌数 降序 比较器
 * @author geng
 *
 */

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

public class ShangPaiCountCompactorDesc implements Comparator<IViewProfitReport>{

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getOnCardSaleNum(), rhs.getOnCardSaleNum(),false);
	}

}
