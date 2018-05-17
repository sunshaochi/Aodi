package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class OperateNewpotentialAsc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getXinpotential(), rhs.getXinpotential(), true);

//		return lhs.getXinpotential().compareTo(rhs.getXinpotential());
	}

}
