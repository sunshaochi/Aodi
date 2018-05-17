package com.iris.cada.view;

import java.util.List;

import com.iris.cada.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class KpiDialog implements OnClickListener {
	private Context context;
    private Dialog dialog;
    private Display display;
    private List<String>list;
    

    public KpiDialog(Context context,List<String>list) {
        this.context = context;
        this.list=list;
    }
    
    public KpiDialog builder(){
    	 // 获取Dialog布局
        View view =LayoutInflater.from(context).inflate(R.layout.act_kpidialog, null);
		ListView lv=(ListView) view.findViewById(R.id.lv);
		TextView tv_biaot=(TextView) view.findViewById(R.id.tv_biaoti);
		tv_biaot.setOnClickListener(this);
		lv.setAdapter(new MyAdapter());
	    dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
	    dialog.setContentView(view);
	    Window dialogWindow = dialog.getWindow();
	    dialogWindow.setGravity(Gravity.CENTER);
	    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
	    lp.x = 0;
	    lp.y = 0;
	    dialogWindow.setAttributes(lp);
	    
       
        return this;
    	 
    }
    
    public KpiDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }
    
    class MyAdapter extends BaseAdapter{
    	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) { 
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=View.inflate(context, R.layout.item_dialog, null);
			TextView kpiname=(TextView) convertView.findViewById(R.id.kpi_name);
			TextView kpivaule=(TextView) convertView.findViewById(R.id.kpi_vaule);
			LinearLayout ll_item=(LinearLayout) convertView.findViewById(R.id.ll_item);			
			if(list!=null&&list.size()!=0){
				String text=list.get(position).toString();
				kpiname.setText(text.split("&")[0].toString());
				kpivaule.setText(text.split("&")[1].toString());
			}
			ll_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
			return convertView;
		}
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
}
