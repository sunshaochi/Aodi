package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.utils.StringUtils;

public class ZdkcslComparatorDesc implements Comparator<Check> {

	@Override
	public int compare(Check lhs, Check rhs) {
		return StringUtils.intStringComparator(lhs.getReceptionTotal(), rhs.getReceptionTotal(), false);
	}
}
