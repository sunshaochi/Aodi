package com.iris.cada.utils;

import com.iris.cada.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ChuanJing
 * @date 2016年7月27日 上午11:46:13
 */
public class MyToast extends Toast {

	private final Context context;
	
	public MyToast(Context context) {
		super(context);
		
		this.context=context;
	}
	
	public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast result = new MyToast(context);

        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.my_toast_layout, null);
        
        // 换字体，和逻辑无关
     	//TypefaceHelper.typeface(layout);
     	
        TextView tv_smg =  (TextView) layout.findViewById(R.id.tv_smg);

        tv_smg.setText(text);

        result.setView(layout);
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);

        return result;
    }
//    public void setNewText(String s) {
//        makeText(context,s,Toast.LENGTH_SHORT);
//    }

    @Override
    public void setView(View view) {
        super.setView(view);
    }

    public void setText(CharSequence s) {
       View view = getView();
       /* TextView textView =  (TextView) view.findViewById(R.id.tv_smg);
        textView.setText(s);*/
        if (view == null) {
            throw new RuntimeException("Toast没有创建 Toast.makeText()！");
        }
        TextView tv = (TextView) view.findViewById(R.id.tv_smg);
        if (tv == null) {
            throw new RuntimeException("Toast没有创建 Toast.makeText()！");
        }
        tv.setText(s);
    }

}
