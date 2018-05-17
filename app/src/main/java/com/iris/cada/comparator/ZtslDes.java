package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.BbkcBean;
import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.StringUtils;

public class ZtslDes implements Comparator<BbkcBean>{

	@Override
	public int compare(BbkcBean lhs, BbkcBean rhs) {
		// TODO Auto-generated method stub
		return StringUtils.intStringComparator(lhs.getZtsl()+"", rhs.getZtsl()+"", false);
	}

}
