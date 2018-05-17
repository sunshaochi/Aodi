package com.iris.cada.newfragment;

import com.iris.cada.JavaScriptinterface;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.LoginActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.activity.ShmainAct;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.CloseWebUtil;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AnyGlFra extends Fragment implements OnClickListener {
	private ImageView iv_setting, iv_yunyin;
//	private WebView wb_view;
	private boolean bool ;
	private ProgressBar pb;
	
	private String url;
	private String stime;
	private String etime;
	private String mode;//true是顾问模式 false是车型模式
	private SharedPreferencesUtils sh;
	private LinearLayout ll_web;
	private WebView wb_view;
	
	private String premission; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);		
		View view = inflater.inflate(R.layout.fragment_gl, container, false);
		iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
		iv_yunyin = (ImageView) view.findViewById(R.id.iv_yunyin);
		iv_yunyin.setOnClickListener(this);
		iv_setting.setOnClickListener(this);
//		wb_view = (WebView) view.findViewById(R.id.wb_view);
		pb = (ProgressBar) view.findViewById(R.id.pb);	
		ll_web=(LinearLayout) view.findViewById(R.id.ll_web);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		bool=false;
		premission = ProfitApplication.loginBackInfo.getPermission();
		if(TextUtils.isEmpty(premission)){
			premission="总经理";
		}
		sh=new SharedPreferencesUtils(getActivity());
		stime=WebUtils.getStart();
		etime=WebUtils.getend();
		mode=(sh.getMode()==true?"0":"1");//顾问模式是0,1是车型模式//默认get出来的是false	
		url=ProfitApplication.H5_GL_YY+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//		url=ProfitApplication.BAIDUH5;
		buildVbview();//动态添加webview;
		WebUtils.WebSet(url, wb_view, pb);
		wb_view.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
		
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_setting:
//			 ((ShmainAct) getActivity()).getSlidingMenu().toggle();显示侧滑菜单
			Intent intent=new Intent(getActivity(),NewSettingAct.class);
			startActivity(intent);
			break;

		case R.id.iv_yunyin:
			stime=WebUtils.getStart();
			etime=WebUtils.getend();
			mode=(sh.getMode()==true?"0":"1");//顾问模式是0,1是车型模式	
			if (!bool) {
				if("售后半权限".equals(premission)){
					Toast.makeText(getActivity(), "暂无权限", Toast.LENGTH_SHORT).show();
				}else {
					bool = true;
					iv_yunyin.setImageResource(R.drawable.btn_yl);// 盈利
					close();
					buildVbview();
					url=ProfitApplication.H5_GL_YL+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();				
//					url=ProfitApplication.BAIDUH5;
					WebUtils.WebSet(url, wb_view, pb);
					wb_view.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
				}
				
			} else {
				bool = false;
				iv_yunyin.setImageResource(R.drawable.btn_user);// 用户
				
				close();
				buildVbview();
				
				url=ProfitApplication.H5_GL_YY+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//				url=ProfitApplication.BAIDUH5;
				WebUtils.WebSet(url, wb_view, pb);
				wb_view.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
			}
			break;
		}
	}
	
	

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		
		if(hidden){//影藏
//			WebUtils.clearViewCache(wb_view, getActivity());
			
			CloseWebUtil.clearWebViewResource(wb_view);
			
		}else {//可见
			reflush();			
		}
		
		
		
	}

	public void reflush() {
		// TODO Auto-generated method stub
		
			stime=WebUtils.getStart();
			etime=WebUtils.getend();
			mode=(sh.getMode()==true?"0":"1");//顾问模式是0,1是车型模式			
			if(bool ==true){
				
				buildVbview();;//关闭webview
				url=ProfitApplication.H5_GL_YL+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();				
//				url=ProfitApplication.BAIDUH5;
				WebUtils.WebSet(url, wb_view, pb);
				wb_view.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
			}else {
				
				
				buildVbview();;//关闭webview
				
				
				url=ProfitApplication.H5_GL_YY+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//				url=ProfitApplication.BAIDUH5;
				Log.e("概览用户", url);				
				WebUtils.WebSet(url, wb_view, pb);
				wb_view.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
			}
		}
	

	public void ClearCache() {
		wb_view.loadDataWithBaseURL(null, "","text/html", "utf-8",null);//清除缓存	
	}
	
	/**动态添加webview**/
	
	public void buildVbview(){
		wb_view=new WebView(ProfitApplication.getApplication());
		wb_view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
		ll_web.addView(wb_view);
	}

	/**彻底关闭webview**/
	public void close(){
		CloseWebUtil.clearWebViewResource(wb_view);
	}
	
}
