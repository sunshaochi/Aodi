package com.iris.cada.comparator;
import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class OperateReceivesumDsc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getReceivesum(), rhs.getReceivesum(), false);

//		return rhs.getReceivesum().compareTo(lhs.getReceivesum());
	}

}