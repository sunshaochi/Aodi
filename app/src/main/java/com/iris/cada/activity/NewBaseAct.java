package com.iris.cada.activity;

import com.lidroid.xutils.ViewUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public abstract class NewBaseAct extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		 if (arg0 != null) {
			 arg0.putParcelable("android:support:fragments",null);
	        }
		 super.onCreate(arg0);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setLayout();
		 // 注入控件
	     ViewUtils.inject(this);
		 init(arg0);
	}
	
	

	

	/**
     * 设置布局
     */
	public abstract void setLayout();
	
	
	 /**
     * 填充数据
     */
    public abstract void init(Bundle savedInstanceState);
    
    
    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

}
