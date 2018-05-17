package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.BbfirstAdapter;
import com.iris.cada.adapter.BbkcsInfoAdapter;
import com.iris.cada.adapter.BbkcspjAdapter;
import com.iris.cada.adapter.BbpjzjAdapter;
import com.iris.cada.comparator.JiusAes;
import com.iris.cada.comparator.JiusDes;
import com.iris.cada.comparator.LiusAes;
import com.iris.cada.comparator.LiusDes;
import com.iris.cada.comparator.ModleAes;
import com.iris.cada.comparator.ModleDes;
import com.iris.cada.comparator.ModleSenDes;
import com.iris.cada.comparator.ModleenAes;
import com.iris.cada.comparator.SansAes;
import com.iris.cada.comparator.SansDes;
import com.iris.cada.comparator.YibAes;
import com.iris.cada.comparator.YibDes;
import com.iris.cada.comparator.YibjiaAes;
import com.iris.cada.comparator.YibjiaDes;
import com.iris.cada.comparator.ZtslAes;
import com.iris.cada.comparator.ZtslDes;
import com.iris.cada.entity.BbkcBean;
import com.iris.cada.entity.BbkcsBean;
import com.iris.cada.fragment.NewBaseFrag;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class RepKcsFra extends NewBaseFrag{
	@ViewInject(R.id.iv_cxup)
	private ImageView iv_cxup;
	@ViewInject(R.id.iv_cxdown)
	private ImageView iv_cxdown;
	@ViewInject(R.id.iv_ztslup)
	private ImageView iv_ztslup;
	@ViewInject(R.id.iv_ztsldown)
	private ImageView iv_ztsldown;
	@ViewInject(R.id.iv_zkslup)
	private ImageView iv_zkslup;
	@ViewInject(R.id.iv_zksldown)
	private ImageView iv_zksldown;
	@ViewInject(R.id.iv_kcslup)
	private ImageView iv_kcslup;
	@ViewInject(R.id.iv_kcsldown)
	private ImageView iv_kcsldown;
	@ViewInject(R.id.iv_pjup)
	private ImageView iv_pjup;
	@ViewInject(R.id.iv_pjdown)
	private ImageView iv_pjdown;
	@ViewInject(R.id.iv_jiaup)
	private ImageView iv_jiaup;
	@ViewInject(R.id.iv_jiadown)
	private ImageView iv_jiadown;
	
	@ViewInject(R.id.plv_pjandzj)
	private ListView plv_pjandzj;
	@ViewInject(R.id.plv_info)
	private ListView plv_info;
	
	private boolean b;
	
	private List<BbkcsBean>list=new ArrayList<BbkcsBean>();//总的集合
	private List<BbkcsBean>pjlist=new ArrayList<BbkcsBean>();//平均list
	private List<BbkcsBean>zjlist=new ArrayList<BbkcsBean>();//总计list
	private BbkcspjAdapter adpter;
	private BbkcsInfoAdapter ifadpter;

	private Gson gson;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			
			super.handleMessage(msg);
			switch (msg.what) {
			case ProfitApplication.DATA_SUC://获取更新时间成功
				setData(msg);
				break;		
			case ProfitApplication.DATA_FAILED://获取数据失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;		

			case ProfitApplication.SERVER_FAILED://连接服务器失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			
			}
		}

		
		
	};
	private void setData(Message msg) {
		// TODO Auto-generated method stub
		if(list!=null){
			list.clear();
		}
		if(pjlist!=null){
			pjlist.clear();
		}
		if(zjlist!=null){
			zjlist.clear();
		}
		try {
			String mess=(String) msg.obj;
			
			JSONObject object=new JSONObject(mess);
			JSONArray data=object.getJSONArray("data");
			list=gson.fromJson(data.toString(),new TypeToken<List<BbkcsBean>>(){}.getType());
//			Log.e("第二面测试list", list.toString());
			for(int i=0;i<list.size();i++){
        		        		  
        			if(list.get(i).getCarType().equals("总计")){
        			      zjlist.add(list.get(i));
        			      list.remove(i);
        			}
        			if(list.get(i).getCarType().equals("平均")){
        				  pjlist.add(list.get(i));
        				  list.remove(i);
        			}
        			
        	}
			
			
			 adpter=new BbkcspjAdapter(getActivity(), zjlist, pjlist);	       
			 plv_pjandzj.setAdapter(adpter);
	         
			 ifadpter = new BbkcsInfoAdapter(getActivity(), list);
			 showImage(0);
			 plv_info.setAdapter(ifadpter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("解析错误", e.toString());
		}
	}


	@Override
	public View initView(LayoutInflater inflater) {
		
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fra_repkcs, null);
		return view;
	}


	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		b=true;
		gson=new Gson();
		getDataright(WebUtils.getStart(),WebUtils.getend());//获取数据
	}
	
	
	private void getDataright(String start, String getend) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getreportFormRight(start,getend,handler);
	}

	@OnClick({ R.id.ll_cx, R.id.ll_san, R.id.ll_liu, R.id.ll_jiu, R.id.ll_ybb,R.id.ll_jia })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.ll_cx://车型
            showImage(0);
			break;
		case R.id.ll_san://30天
			showImage(1);
			break;

		case R.id.ll_liu://60天
			showImage(2);
			break;

		case R.id.ll_jiu://90天
			showImage(3);
			break;

		case R.id.ll_ybb://180天
			showImage(4);
			break;
		case R.id.ll_jia://180天+
			showImage(5);
			break;

		
		}
	}

	private void showImage(int i) {
		// TODO Auto-generated method stub
		iv_cxup.setVisibility(View.INVISIBLE);
		iv_cxdown.setVisibility(View.INVISIBLE);
		iv_ztslup.setVisibility(View.INVISIBLE);
		iv_ztsldown.setVisibility(View.INVISIBLE);
		iv_zkslup.setVisibility(View.INVISIBLE);
		iv_zksldown.setVisibility(View.INVISIBLE);
		iv_kcslup.setVisibility(View.INVISIBLE);
		iv_kcsldown.setVisibility(View.INVISIBLE);
		iv_pjup.setVisibility(View.INVISIBLE);
		iv_pjdown.setVisibility(View.INVISIBLE);
		iv_jiaup.setVisibility(View.INVISIBLE);
		iv_jiadown.setVisibility(View.INVISIBLE);
		switch (i) {
		case 0:
			if(b==true){
			iv_cxdown.setVisibility(View.VISIBLE);
			b=false;
			Collections.sort(list, new ModleSenDes());
			ifadpter.notifyDataSetChanged();
		
			}else {			
			iv_cxup.setVisibility(View.VISIBLE);	
			b=true;
			Collections.sort(list, new ModleenAes());
			ifadpter.notifyDataSetChanged();
			}
			break;
		case 1:
			if(b==true){
			iv_ztsldown.setVisibility(View.VISIBLE);
			 b=false;
			 Collections.sort(list,new SansDes());
			 ifadpter.notifyDataSetChanged();
			}else {
			iv_ztslup.setVisibility(View.VISIBLE);
			b=true;
			 Collections.sort(list,new SansAes());
			 ifadpter.notifyDataSetChanged();
			}
			break;

        case 2:  
        	if(b==true){
    		 iv_zksldown.setVisibility(View.VISIBLE);
    		 b=false;
    		 Collections.sort(list,new LiusDes());
			 ifadpter.notifyDataSetChanged();
        	}else {
			 iv_zkslup.setVisibility(View.VISIBLE);
			 b=true;
			 Collections.sort(list,new LiusAes());
			 ifadpter.notifyDataSetChanged();
			}
			break;
			
        case 3:
        	if(b==true){
        	 iv_kcsldown.setVisibility(View.VISIBLE);
       		 b=false;
       		 Collections.sort(list,new JiusDes());
			 ifadpter.notifyDataSetChanged();
           	}else {
   			 iv_kcslup.setVisibility(View.VISIBLE);
   			 b=true;
   			 Collections.sort(list,new JiusAes());
			 ifadpter.notifyDataSetChanged();
   			}
    		
			break;
			
        case 4:
        	if(b==true){
        		 iv_pjdown.setVisibility(View.VISIBLE);
          		 b=false;
          		Collections.sort(list,new YibDes());
   			    ifadpter.notifyDataSetChanged();
              	}else {
      			 iv_pjup.setVisibility(View.VISIBLE);
      			 b=true;
      			 Collections.sort(list,new YibAes());
    			 ifadpter.notifyDataSetChanged();
      			}    		
			break;
		case 5:
			if(b==true){
       		      iv_jiadown.setVisibility(View.VISIBLE);
         		  b=false;
         		 Collections.sort(list,new YibjiaDes());
    			 ifadpter.notifyDataSetChanged();
             	}else {
     			 iv_jiaup.setVisibility(View.VISIBLE);
     			 b=true;
     			 Collections.sort(list,new YibjiaAes());
    			 ifadpter.notifyDataSetChanged();
     			}  
			break;
		}
		
	}


	public void update() {
		// TODO Auto-generated method stub
		getDataright(WebUtils.getStart(),WebUtils.getend());//获取数据
	}
	
	

}
