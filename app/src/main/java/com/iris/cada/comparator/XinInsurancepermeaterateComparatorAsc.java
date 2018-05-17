package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 新保渗透率    升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class XinInsurancepermeaterateComparatorAsc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		return StringUtils.intStringComparator(lhs.getXinInsurancepermeaterate(), rhs.getXinInsurancepermeaterate(),true);

//		return lhs.getXinInsurancepermeaterate().compareTo(rhs.getXinInsurancepermeaterate());
	}

}