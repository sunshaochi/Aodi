package com.iris.cada.newfragment;

import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.fragment.NewBaseFrag;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SxbbFragment extends NewBaseFrag{
	
	@ViewInject(R.id.tv_yunyin)
	private TextView tv_yunyin;
	@ViewInject(R.id.tv_kc)
	private TextView tv_kc;
	@ViewInject(R.id.tv_yinli)
	private TextView tv_yinli;
	
	private FragmentManager fragmentManager;
	
	private ReportOperateFragment yyfra;//运营
	private ReportProfitFragment ylfra;//盈利
	private ReprotKcFrag kcfra;//库存
	private int type;
	private String premission;
	

	@Override
	public View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
//		View view = inflater.inflate(R.layout.fragment_overview,null);
		View view=inflater.inflate(R.layout.fra_sxbb, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 premission = ProfitApplication.loginBackInfo.getPermission();		
		if(TextUtils.isEmpty(premission)){
				premission="总经理";
		}
		 fragmentManager=getActivity().getSupportFragmentManager();
		 slectbtncolor(0);
		 slectfra(0);
		
	}
	
	
	@OnClick({R.id.overview_title_setting,R.id.tv_yunyin,R.id.tv_kc,R.id.tv_yinli})
	private void OnClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.overview_title_setting:
			openActivity(NewSettingAct.class);
			break;
		case R.id.tv_yunyin:
			 slectbtncolor(0);
			 slectfra(0);
			break;	
		case R.id.tv_kc:
			 slectbtncolor(1);
			 slectfra(1);
			break;
		case R.id.tv_yinli:
//			String premission = ProfitApplication.loginBackInfo.getPermission();
			if("仅运营权限".equals(premission)||"销售半权限".equals(premission)){
				Toast.makeText(getActivity(), "没有足够权限", Toast.LENGTH_LONG).show();			
			}else{
			 slectbtncolor(2);
			 slectfra(2);
			}
			break;	

		
		}

	}
	
	
	private void slectfra(int i) {
		// TODO Auto-generated method stub
		    FragmentTransaction transaction=fragmentManager.beginTransaction();
	        hideFragment(transaction);
	        switch (i){
            case 0://运营
            	type=0;
                if(yyfra==null){
                	yyfra=new ReportOperateFragment ();
                    transaction.add(R.id.fra_xsbb,yyfra);                                     
                }else {
                    transaction.show(yyfra);
                }
                break;
            case 1://库存
            	type=1;
                if(kcfra==null){
                	kcfra=new ReprotKcFrag();
                    transaction.add(R.id.fra_xsbb,kcfra);
                   
                }else {
                    transaction.show(kcfra);
                }

                break;
            case 2://盈利
            	type=2;
                if(ylfra==null){
                	ylfra=new ReportProfitFragment();
                    transaction.add(R.id.fra_xsbb,ylfra);
                   
                }else {
                    transaction.show(ylfra);
                }

                break;
        }
        transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if(yyfra!=null){
            transaction.hide(yyfra);
        }
        if(kcfra!=null){
            transaction.hide(kcfra);
        }
        if(ylfra!=null){
            transaction.hide(ylfra);
        }
		
	}

	private void slectbtncolor(int i) {
		// TODO Auto-generated method stub
		    tv_yunyin.setBackgroundResource(R.drawable.left_yuan);
	        tv_yunyin.setTextColor(Color.parseColor("#ffffffff"));
	        tv_kc.setBackgroundColor(Color.parseColor("#00000000"));
	        tv_kc.setTextColor(Color.parseColor("#ffffffff"));
	        tv_yinli.setBackgroundResource(R.drawable.right_yuan);
	        tv_yinli.setTextColor(Color.parseColor("#ffffffff"));
	        
	        if(i==0){
	        	   tv_yunyin.setBackgroundResource(R.drawable.left_black_yuan);
	   	           tv_yunyin.setTextColor(Color.parseColor("#0d70c0"));
	        }
	        if(i==1){
	        	 tv_kc.setBackgroundColor(Color.parseColor("#ffffffff"));
	 	         tv_kc.setTextColor(Color.parseColor("#0d70c0"));
	        }
	        if(i==2){
	        	 tv_yinli.setBackgroundResource(R.drawable.right_black_yuan);
	        	 tv_yinli.setTextColor(Color.parseColor("#0d70c0"));
	        }
	}
	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if(hidden){
			
		}else{
			if(type==1){
				kcfra.upData();
			}
		}
	}


}
