package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 新保销售数    降序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class XinInsuranceSaleNumComparatorDesc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getXinInsuranceSaleNum(), rhs.getXinInsuranceSaleNum(),false);

//		return rhs.getXinInsuranceSaleNum().compareTo(lhs.getXinInsuranceSaleNum());
	}

}