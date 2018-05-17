package com.iris.cada.comparator;
import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class OperateFirstindentDsc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getFirstindent(), rhs.getFirstindent(), false);

//		return rhs.getFirstindent().compareTo(lhs.getFirstindent());
	}

}