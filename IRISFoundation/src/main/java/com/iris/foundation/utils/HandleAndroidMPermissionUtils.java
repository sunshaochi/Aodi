package com.iris.foundation.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 处理android6.0的权限
 * 
* @ClassName: HandleAndroidMPermissionUtils
* @Description: TODO()
* @author iris-gbl
* @date 2016年12月2日 下午6:33:30
*
 */
public class HandleAndroidMPermissionUtils {
	
	private Activity activity;

	public HandleAndroidMPermissionUtils(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * 调用这个方法即可处理6.0权限问题，需要传入两个参数，然后在调用的地方通过回调方法onRequestPermissionsResult来处理自己的业务逻辑
	 * @param permission	权限
	 * @param requestCode	请求码
	 */
	public void checkAndroidMPermission(String permission, int requestCode){
		if(null!=activity){
			if (!(activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
	             requestPermission(permission,requestCode);
	         }
		}
	}
	
	@TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(String permission,int requestCode){
		activity.requestPermissions(new String[]{permission}, requestCode);
    }	

}
