//package com.iris.cada.http.Manage;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.text.TextUtils;
//
//import com.google.gson.Gson;
//import com.iris.cada.ProfitApplication;
//import com.iris.cada.http.Parma.Param;
//import com.iris.cada.http.ResultCallback;
//import com.iris.cada.utils.GsonUtils;
//import com.iris.cada.utils.MyLogUtils;
//import com.iris.cada.utils.SpUtils;
//import com.squareup.okhttp.OkHttpClient;
//import com.zhy.http.okhttp.OkHttpUtils;
//
//
//import org.json.JSONObject;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import okhttp3.Call;
//import okhttp3.MediaType;
//import okhttp3.Response;
//
//
///**
// * Created by bitch-1 on 2017/3/13.
// */
//
//public class OkHttpManager {
//    private static OkHttpClient mOkHttpClient;
//    private static OkHttpManager mInstance;
//    private Gson mGson;
//    private Handler mDelivery;
//
//    public OkHttpManager() {
//        mGson = new Gson();
//        //        OkHttpClient.Builder builder = new OkHttpClient.Builder();
////        mOkHttpClient = builder.build();
//        //cookie enabled
//        mDelivery = new Handler(Looper.getMainLooper());
//    }
//
//    public static OkHttpManager getInstance() {
//        if (mInstance == null) {
//            if (mInstance == null) {
//                mInstance = new OkHttpManager();
//            }
//        }
//        return mInstance;
//    }
//
//    private Param[] map2Params(Map<String, String> params) {
//        if (params == null) return new Param[0];
//        int size = params.size();
//        Param[] res = new Param[size];
//        Set<Map.Entry<String, String>> entries = params.entrySet();
//        int i = 0;
//        for (Map.Entry<String, String> entry : entries) {
//            res[i++] = new Param(entry.getKey(), entry.getValue());
//        }
//        return res;
//    }
//
//    /**
//     * post请求  键值对
//     *
//     * @param url
//     * @param paramMap
//     * @param callback
//     */
//    public void doPost(String url, Map<String, String> paramMap, final ResultCallback callback) {
//        MyLogUtils.info("请求地址:" + url);
//        MyLogUtils.info("请求参数:" + GsonUtils.bean2Json(paramMap));
//        OkHttpUtils.post().url(url).params(paramMap).headers(getHeaders()).build().execute(new com.zhy.http.okhttp.callback.Callback<String>() {
//            @Override
//            public String parseNetworkResponse(Response response, int id) throws Exception {
//                String header = response.header("Set-Cookie");
//                MyLogUtils.info("获取到cookie:" + header);
//                if (!TextUtils.isEmpty(header))
//                    SpUtils.setCooike(ProfitApplication.getApplication(), header);
//                return response.body().string();
//            }
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                sendFailedStringCallback(-1, "请求失败，请检查网络！", callback);
//            }
//
//            @Override
//            public void onResponse(String result, int id) {
//                MyLogUtils.info("获取result：" + result);
//                if (callback.mType == String.class) {
//                    sendSuccessResultCallback(result, callback);
//                } else {
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        String code = jsonObject.getString("code");
//                        if (code.equals("success")) {//成功
//                            Object o = mGson.fromJson(result, callback.mType);
//                            sendSuccessResultCallback(o, callback);
//                        }else {//失败
//                            sendFailedStringCallback(-1, jsonObject.getString("message"), callback);
//                        }
//                    } catch (Exception e) {
//                        sendFailedStringCallback(-1, "解析异常", callback);
//                    }
//                }
//
//            }
//        });
//    }
//
//
//
//
//
//    /**
//     * post请求只是参数全部转成json
//     *
//     * @param url
//     * @param paramMap
//     * @param callback
//     */
//    public void doPostForjson(String url, Map<String, String> paramMap, final ResultCallback callback) {
//        MyLogUtils.info("请求地址:" + url);
//        MyLogUtils.info("请求参数:" + GsonUtils.bean2Json(paramMap));
//        OkHttpUtils.postString().
//                url(url).
//                mediaType(MediaType.parse(("application/json; charset=utf-8;"))
//                ).headers(getHeaders()).
//                content(GsonUtils.bean2Json(paramMap)).build().
//                execute(new com.zhy.http.okhttp.callback.Callback<String>() {
//                    @Override
//                    public String parseNetworkResponse(Response response, int id) throws Exception {
//                        String header = response.header("Set-Cookie");
//                        MyLogUtils.info("获取到cookie:" + header);
//                        if (!TextUtils.isEmpty(header))
//                            SpUtils.setCooike(ProfitApplication.getApplication(), header);
//                        return response.body().string();
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        sendFailedStringCallback(-1, "请求失败，请检查网络！", callback);
//                    }
//
//                    @Override
//                    public void onResponse(String result, int id) {
//                        MyLogUtils.info("获取result：" + result);
//                        if (callback.mType == String.class) {
//                            sendSuccessResultCallback(result, callback);
//                        } else {
//                            try {
//                                JSONObject jsonObject = new JSONObject(result);
//                                String succ = jsonObject.getString("code");
//                                if (succ.equals("success")) {//成功
//                                    Object o = mGson.fromJson(result, callback.mType);
//                                    sendSuccessResultCallback(o, callback);
//                                }
////                        else if (status == 601) {
////                            sendFailedStringCallback(status, result, callback);
////                        }
//                                else {//失败
//                                    sendFailedStringCallback(400, jsonObject.getString("message"), callback);
//                                }
//                            } catch (Exception e) {
//                                sendFailedStringCallback(-1, "解析异常", callback);
//                            }
//                        }
//                    }
//                });
//    }
//
//
//
//    private void sendFailedStringCallback(final int status, final String e, final ResultCallback callback) {
//        mDelivery.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callback != null)
//                    callback.onError(status, e);
//            }
//        });
//    }
//
//    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
//        mDelivery.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callback != null) {
//                    callback.onResponse(object);
//                }
//            }
//        });
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//    /**
//     * 获取请求头
//     *
//     * @param
//     * @return //
//     */
//    public static Map<String, String> getHeaders() {
//        HashMap<String, String> map = new HashMap<>();
//        if (!TextUtils.isEmpty(SpUtils.getCookie(ProfitApplication.getApplication()))) {
//            map.put("Cookie", SpUtils.getCookie(ProfitApplication.getApplication()));
//        }
////        if (EchinfoUtils.getCurrentUser() != null) {
////            if (!TextUtils.isEmpty(EchinfoUtils.getCurrentUser().getId())) {
////                map.put("keyUserId", EchinfoUtils.getCurrentUser().getId());
////            }
////        }
////        map.put("Content-Type","application/x-www-form-urlencoded");//注册当中的请求头
//        return map;
//    }
//
//
//}
