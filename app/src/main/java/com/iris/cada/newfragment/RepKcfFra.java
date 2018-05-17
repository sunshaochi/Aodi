package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.BbfirstAdapter;
import com.iris.cada.adapter.BbpjzjAdapter;
import com.iris.cada.adapter.OperatePopuAdapter;
import com.iris.cada.adapter.ZjandpjAdapter;
import com.iris.cada.comparator.EnglishAndNumCompactorAsc;
import com.iris.cada.comparator.KcslAes;
import com.iris.cada.comparator.KcslDes;
import com.iris.cada.comparator.ModleAes;
import com.iris.cada.comparator.ModleDes;
import com.iris.cada.comparator.OperateTurnoverDsc;
import com.iris.cada.comparator.PjtsAes;
import com.iris.cada.comparator.PjtsDes;
import com.iris.cada.comparator.ZKslAes;
import com.iris.cada.comparator.ZKslDes;
import com.iris.cada.comparator.ZtslAes;
import com.iris.cada.comparator.ZtslDes;
import com.iris.cada.entity.BbkcBean;
import com.iris.cada.fragment.NewBaseFrag;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class RepKcfFra extends NewBaseFrag {
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
	@ViewInject(R.id.plv_pjandzj)
	private ListView plv_pjandzj;
	@ViewInject(R.id.plv_info)
	private ListView plv_info;
	
	private boolean b;
	private List<BbkcBean>list=new ArrayList<BbkcBean>();//总的集合
	private List<BbkcBean>pjlist=new ArrayList<BbkcBean>();//平均list
	private List<BbkcBean>zjlist=new ArrayList<BbkcBean>();//总计list
	private BbpjzjAdapter adpter;//总计和平均的适配器
	private BbfirstAdapter fadpter;//详细数据适配器
	
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
			list=gson.fromJson(data.toString(),new TypeToken<List<BbkcBean>>(){}.getType());
//			Log.e("测试list", list.toString());
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
			
			 adpter=new BbpjzjAdapter(getActivity(), zjlist, pjlist);	       
			 plv_pjandzj.setAdapter(adpter);
	         
			 fadpter = new BbfirstAdapter(getActivity(), list);	
			 showImage(0);
			 plv_info.setAdapter(fadpter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("解析错误", e.toString());
		}
		
	}


	@Override
	public View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fra_repkcf, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		b=true;//默认向下		
		gson=new Gson();
	
		getDataleft(WebUtils.getStart(),WebUtils.getend());//获取数据
	}

	private void getDataleft(String startDate,String endDate) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getreportFormLeft(startDate,endDate,handler);
	}

	@OnClick({ R.id.ll_cx, R.id.ll_ztsl, R.id.ll_zksl, R.id.ll_kcsl, R.id.ll_pj })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.ll_cx://车型
            showImage(0);
			break;
		case R.id.ll_ztsl://在途数量
			showImage(1);
			break;

		case R.id.ll_zksl://在库数量
			showImage(2);
			break;

		case R.id.ll_kcsl://库存数量
			showImage(3);
			break;

		case R.id.ll_pj://平均数量
			showImage(4);
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
		switch (i) {
		case 0:
			if(b==true){
			iv_cxdown.setVisibility(View.VISIBLE);//向下
			b=false;
			Collections.sort(list, new ModleDes());
			fadpter.notifyDataSetChanged();
			}else {			
			iv_cxup.setVisibility(View.VISIBLE);	
			b=true;
			Collections.sort(list, new ModleAes());
			fadpter.notifyDataSetChanged();
			}
			break;
		case 1:
			if(b==true){//向下
			 iv_ztsldown.setVisibility(View.VISIBLE);
			 b=false;
			 Collections.sort(list,new ZtslDes());
			 fadpter.notifyDataSetChanged();
			}else {//向上
			 iv_ztslup.setVisibility(View.VISIBLE);
			 b=true;
			 
			 Collections.sort(list,new ZtslAes());
			 fadpter.notifyDataSetChanged();
			}
			break;

        case 2:  
        	if(b==true){
    		 iv_zksldown.setVisibility(View.VISIBLE);
    		 b=false;
    		 Collections.sort(list,new ZKslDes());
			 fadpter.notifyDataSetChanged();
        	}else {
			 iv_zkslup.setVisibility(View.VISIBLE);
			 b=true;
			 Collections.sort(list,new ZKslAes());
			 fadpter.notifyDataSetChanged();
			}
			break;
			
        case 3:
        	if(b==true){
        	 iv_kcsldown.setVisibility(View.VISIBLE);
       		 b=false;
       		 Collections.sort(list,new KcslDes());
			 fadpter.notifyDataSetChanged();
           	}else {
   			 iv_kcslup.setVisibility(View.VISIBLE);
   			 b=true;
   			 Collections.sort(list,new KcslAes());
			 fadpter.notifyDataSetChanged();
   			}
    		
			break;
			
        case 4:
        	if(b==true){
        		 iv_pjdown.setVisibility(View.VISIBLE);
          		 b=false;
          		 Collections.sort(list,new PjtsDes());
    			 fadpter.notifyDataSetChanged();
              	}else {
      			 iv_pjup.setVisibility(View.VISIBLE);
      			 b=true;
      			 Collections.sort(list,new PjtsAes());
    			 fadpter.notifyDataSetChanged();
      			}    		
			break;
		}
		
	}


	public void update() {
		// TODO Auto-generated method stub
		getDataleft(WebUtils.getStart(),WebUtils.getend());//获取数据	}

}
}
