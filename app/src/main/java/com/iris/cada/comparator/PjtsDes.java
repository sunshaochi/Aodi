package com.iris.cada.comparator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

import com.iris.cada.entity.BbkcBean;
import com.iris.cada.utils.StringUtils;

import android.util.Log;

public class PjtsDes implements Comparator<BbkcBean> {

	@Override
	public int compare(BbkcBean lhs, BbkcBean rhs) {
		// TODO Auto-generated method stub

		BigDecimal aaa=new BigDecimal(lhs.getPjkcts());
		BigInteger c=aaa.multiply(new BigDecimal(100)).toBigInteger();
		BigDecimal bbb=new BigDecimal(rhs.getPjkcts());
		BigInteger d=bbb.multiply(new BigDecimal(100)).toBigInteger();
		
		return StringUtils.intStringComparator(c.toString(),d.toString(), false);
	    
	}

}
