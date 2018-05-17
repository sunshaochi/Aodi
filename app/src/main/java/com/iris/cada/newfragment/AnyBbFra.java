package com.iris.cada.newfragment;

import java.io.File;

import com.iris.cada.JavaScriptinterface;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.activity.ShmainAct;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.CloseWebUtil;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract.Colors;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AnyBbFra extends Fragment implements OnClickListener{
	private ImageView iv_setting,iv_yunyin;
	private WebView wb_viewll;
	private boolean bool;
	private ProgressBar pb;
	private LinearLayout ll_show;
	private TextView tv_zy,tv_ys;
	private View view_zy,view_ys;	
	private String url;
	private String stime;
	private String etime;
	private String mode;//true是顾问模式 false是车型模式
	private SharedPreferencesUtils sh;
	private LinearLayout ll_webll;
	
	private String premission; 
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_bb, container, false);	   
		iv_setting=(ImageView) view.findViewById(R.id.iv_setting);
		iv_setting.setOnClickListener(this);
		iv_yunyin=(ImageView) view.findViewById(R.id.iv_yunyin);
		iv_yunyin.setOnClickListener(this);
//	    wb_view=(WebView) view.findViewById(R.id.wb_view);
	    pb = (ProgressBar)view.findViewById(R.id.pb);  
	    tv_zy=(TextView) view.findViewById(R.id.tv_zy);
	    tv_ys=(TextView) view.findViewById(R.id.tv_zy);
	    tv_zy.setOnClickListener(this);
	    tv_ys.setOnClickListener(this);
	    ll_show=(LinearLayout) view.findViewById(R.id.ll_show);
	    view_zy=view.findViewById(R.id.view_zy);
	    view_ys=view.findViewById(R.id.view_zy);
	    ll_webll=(LinearLayout) view.findViewById(R.id.ll_webll);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		bool=false;//默认是用户
		premission = ProfitApplication.loginBackInfo.getPermission();
		if(TextUtils.isEmpty(premission)){
			premission="总经理";
		}
		sh=new SharedPreferencesUtils(getActivity());
		stime=WebUtils.getStart();
		etime=WebUtils.getend();
		mode=(sh.getMode()==true?"0":"1");//顾问模式是0,1是车型模式
		buildVbview();
		url=ProfitApplication.H5_BB_YH+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//		url=ProfitApplication.BAIDUH5;
		WebUtils.WebSet(url, wb_viewll, pb);
		wb_viewll.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
	}
	


    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_setting:
			Intent intent=new Intent(getActivity(),NewSettingAct.class);
			startActivity(intent);
			break;
			
        case R.id.iv_yunyin:
        	stime=WebUtils.getStart();
			etime=WebUtils.getend();
			mode=(sh.getMode()==true?"0":"1");//顾问模式是0,1是车型模式;		
			if(!bool){
				if("售后半权限".equals(premission)){
					Toast.makeText(getActivity(), "暂无权限", Toast.LENGTH_SHORT).show();
				}else {
					bool=true;
					iv_yunyin.setImageResource(R.drawable.btn_yl);//盈利
					ll_show.setVisibility(View.GONE);
					close();
					buildVbview();
					url=ProfitApplication.H5_BB_ZY+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//					url=ProfitApplication.BAIDUH5;
					WebUtils.WebSet(url, wb_viewll, pb);
					wb_viewll.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
				}
				
			}else {
				bool=false;
				iv_yunyin.setImageResource(R.drawable.btn_user);//用户
				ll_show.setVisibility(View.GONE);
				close();
				buildVbview();
				url=ProfitApplication.H5_BB_YH+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//				url=ProfitApplication.BAIDUH5;
				WebUtils.WebSet(url, wb_viewll, pb);
				wb_viewll.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
			}
			break;
			


		
		}
	}

	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if(hidden){
//			WebUtils.clearViewCache(wb_view, getActivity());
			close();
		}else {
			reflush();		
			
		}
	}
	
	
    public void reflush() {
			stime=WebUtils.getStart();
			etime=WebUtils.getend();
			mode=(sh.getMode()==true?"0":"1");//顾问模式是0,1是车型模式			
			if(bool==true){
				
				buildVbview();
				url=ProfitApplication.H5_BB_ZY+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//				url=ProfitApplication.BAIDUH5;
				WebUtils.WebSet(url, wb_viewll, pb);
				wb_viewll.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
			}else {
				
				buildVbview();
				url=ProfitApplication.H5_BB_YH+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//				url=ProfitApplication.BAIDUH5;
				WebUtils.WebSet(url, wb_viewll, pb);
				wb_viewll.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
			}
		}


	/**清除缓存**/
	public void ClearCache() {
		wb_viewll.loadDataWithBaseURL(null, "","text/html", "utf-8",null);//清除缓存	
	}
	
/**动态添加webview**/
	
	public void buildVbview(){
		wb_viewll=new WebView(ProfitApplication.getApplication());
		wb_viewll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
		ll_webll.addView(wb_viewll);
	}

	
	/**彻底关闭webview**/
	public void close(){
		
		CloseWebUtil.clearWebViewResource(wb_viewll);
	}


}
