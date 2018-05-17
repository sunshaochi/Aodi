package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class OprateDormantAsc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getDormant(), rhs.getDormant(), true);

//		return lhs.getDormant().compareTo(rhs.getDormant());
	}

}