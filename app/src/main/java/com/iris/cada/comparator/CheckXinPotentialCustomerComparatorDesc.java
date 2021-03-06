package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.utils.StringUtils;

/**
 * 新增潜客    降序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class CheckXinPotentialCustomerComparatorDesc implements Comparator<Check> {

	@Override
	public int compare(Check lhs, Check rhs) {
		return StringUtils.intStringComparator(lhs.getXinPotentialCustomer(), rhs.getXinPotentialCustomer(), false);

//		return rhs.getXinPotentialCustomer().compareTo(lhs.getXinPotentialCustomer());
	}

}