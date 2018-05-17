package com.iris.cada.comparator;
import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class OperateQuotationAsc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getQuotation(), rhs.getQuotation(), true);

//		return lhs.getQuotation().compareTo(rhs.getQuotation());
	}
}