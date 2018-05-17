package com.iris.cada.comparator;
import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class OperateActiveAsc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getActive(), rhs.getActive(), true);

//		return lhs.getActive().compareTo(rhs.getActive());
	}

}