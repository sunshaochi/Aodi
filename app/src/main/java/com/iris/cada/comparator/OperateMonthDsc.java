package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class OperateMonthDsc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getMonth(), rhs.getMonth(), false);

//		return rhs.getMonth().compareTo(lhs.getMonth());
	}

}