package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.BbkcBean;
import com.iris.cada.utils.StringUtils;

public class ModleAes implements Comparator<BbkcBean> {

	@Override
	public int compare(BbkcBean lhs, BbkcBean rhs) {
		// TODO Auto-generated method stub
		return StringUtils.compare(rhs.getCarType(),lhs.getCarType());
	}

}
