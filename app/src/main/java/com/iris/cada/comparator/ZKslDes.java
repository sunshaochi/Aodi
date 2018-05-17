package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.BbkcBean;
import com.iris.cada.utils.StringUtils;

public class ZKslDes implements Comparator<BbkcBean> {

	@Override
	public int compare(BbkcBean lhs, BbkcBean rhs) {
		// TODO Auto-generated method stub
		return StringUtils.intStringComparator(lhs.getZksl()+"", rhs.getZksl()+"", false);
	}

}
