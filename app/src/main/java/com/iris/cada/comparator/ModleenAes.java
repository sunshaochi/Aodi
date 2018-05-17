package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.BbkcsBean;
import com.iris.cada.utils.StringUtils;

public class ModleenAes implements Comparator<BbkcsBean> {

	@Override
	public int compare(BbkcsBean lhs, BbkcsBean rhs) {
		// TODO Auto-generated method stub
		return StringUtils.compare(rhs.getCarType(),lhs.getCarType());
	}

}
