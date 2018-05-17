package com.iris.cada.utils;

import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;

public class CloseWebUtil {
	
	
	public static void clearWebViewResource(WebView view) {
	    if (view != null) {
	      
	        view.removeAllViews();
	        // in android 5.1(sdk:21) we should invoke this to avoid memory leak
	        // see (https://coolpers.github.io/webview/memory/leak/2015/07/16/
	        // android-5.1-webview-memory-leak.html)
	        ((ViewGroup) view.getParent()).removeView(view);
	        view.setTag(null);
	        view.clearHistory();
	        view.destroy();
	        view = null;
	    }
	}
	
	

}
