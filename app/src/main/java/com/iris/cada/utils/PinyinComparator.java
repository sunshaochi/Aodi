package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.ConsultantInfo;

public class PinyinComparator implements Comparator<ConsultantInfo> {

	public int compare(ConsultantInfo o1, ConsultantInfo o2) {
		if (o1.getLetter().equals("@") || o2.getLetter().equals("#")) {
			return -1;
		} else if (o1.getLetter().equals("#") || o2.getLetter().equals("@")) {
			return 1;
		} else {
			return o1.getLetter().compareTo(o2.getLetter());
		}
	}

}
