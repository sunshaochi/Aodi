package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;

/**
 * 首次试驾率 降序 比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class OperatefirstDriverateDesc implements Comparator<IViewOperateReport> {

	@Override
	public int compare(IViewOperateReport lhs, IViewOperateReport rhs) {
		return StringUtils.intStringComparator(lhs.getFirstDriverate(), rhs.getFirstDriverate(),false);
//		return rhs.getFirstDriverate().compareTo(lhs.getFirstDriverate());
	}

}