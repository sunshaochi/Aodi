package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 平均单车精品毛利    升序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class CheckLookToBuyRateComparatorAsc implements Comparator<Check> {

	@Override
	public int compare(Check lhs, Check rhs) {
		return StringUtils.intStringComparator(lhs.getLookToBuyRate(), rhs.getLookToBuyRate(), true);

//		return lhs.getLookToBuyRate().compareTo(rhs.getLookToBuyRate());
	}

}