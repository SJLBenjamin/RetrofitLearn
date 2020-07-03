package com.example.retrofitlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;


import com.example.retrofitlearn.api.WanAndroidApi;
import com.example.retrofitlearn.bean.RegisterBean;
import com.example.retrofitlearn.bean.UserBean;
import com.example.retrofitlearn.bean.WXListBean;
import com.example.retrofitlearn.retrofit.RetrofitClient;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.PartMap;

public class MainActivity extends AppCompatActivity {
    MainActivity mContext =this;
    String TAG = "MainActivity";
    private RxPermissions rxPermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final CookieManager cookieManager = CookieManager.getInstance();
        boolean b = cookieManager.acceptCookie();
        Log.d(TAG,"acceptCookie=="+b);

       /*  rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.INTERNET).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(aBoolean){
                    WanAndroidApi wanAndroidApi = new RetrofitClient().createWanAndroidApi();

                    Call<ProjectBean> project = wanAndroidApi.getProject();
                    //异步请求
                    project.enqueue(new Callback<ProjectBean>() {
                        @Override
                        public void onResponse(Call<ProjectBean> call, Response<ProjectBean> response) {
                            Log.d(TAG,"请求成功信息==   "+response.body().getData());
                        }

                        @Override
                        public void onFailure(Call<ProjectBean> call, Throwable t) {
                            Log.d(TAG,"请求失败信息==   "+t.getMessage());
                        }
                    });


                    //同步请求需要开线程,不然会崩溃
//                    try {
//                        Response<ProjectBean> execute = project.execute();
//                        Log.d(TAG,execute.body().toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }

        );*/


       /*
       * 获取公众号列表
       * */
        WanAndroidApi WanAndroidApi =
                new RetrofitClient().createWanAndroidWXListApi();
        Call<WXListBean> project = WanAndroidApi.getProject("408", "1");
        project.enqueue(new Callback<WXListBean>() {
            @Override
            public void onResponse(Call<WXListBean> call, Response<WXListBean> response) {
                Log.d(TAG,"公众号列表=="+response.body());
            }

            @Override
            public void onFailure(Call<WXListBean> call, Throwable t) {
                Log.d(TAG, "公众号列表错误原因==" + t.getMessage());
                
            }
        });


        /*
        * 带条件查询
        * */
        WanAndroidApi WanAndroidQueryApi = new RetrofitClient().createWanAndroidWXListApi();
        Call<WXListBean> java = WanAndroidQueryApi.getProjectForK("408", "1", "Java");
        java.enqueue(new Callback<WXListBean>() {
            @Override
            public void onResponse(Call<WXListBean> call, Response<WXListBean> response) {
                Log.d(TAG,"历史文章=="+response.body());
            }

            @Override
            public void onFailure(Call<WXListBean> call, Throwable t) {
                Log.d(TAG,"历史文章错误原因=="+t.getMessage());
            }
        });


        com.example.retrofitlearn.api.WanAndroidApi wanAndroidLoginApi = new RetrofitClient().createWanAndroidLoginApi();
        Call<UserBean> userBeanCall = wanAndroidLoginApi.CreateUser(new UserBean("宋炯乐", "123456"));
        userBeanCall.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                Log.d(TAG,"登录结果=="+response.body()+"    消息==="+response.message());
                String cookie1 = cookieManager.getCookie("https://www.wanandroid.com/user/login");

                String cookie = response.headers().get("set-cookie");
                Log.d(TAG,"cookie1==="+cookie1+" cookie==="+cookie);
                HttpCookie cookies = new HttpCookie("set-cookie", cookie);
                cookies.setDomain("");               // 设置域名
                cookies.setPath("/");                // 设置path
                cookies.setMaxAge(233);          // 设置过期时间

                Log.d(TAG,"是否有了cookies==="+cookieManager.hasCookies());
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.d(TAG,"登录错误原因=="+t.getMessage());
            }
        });


        /*
        * 注册
        * */
        com.example.retrofitlearn.api.WanAndroidApi wanAndroidRegisterApi = new RetrofitClient().createWanAndroidRegisterApi();
        Call<RegisterBean> registerBeanCall = wanAndroidRegisterApi.registerUser("10086", "123456", "123456");
        registerBeanCall.enqueue(new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                Log.d(TAG,"注册结果=="+response.body()+"    消息==="+response.message());
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                Log.d(TAG,"注册错误原因=="+t.getMessage());
            }
        });

        /*
        * 退出登录,如果没有返回值直接用Object
        * */
        Call<Object> responseCall = wanAndroidRegisterApi.exitLogin();
        responseCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG,"退出结果=="+response.body()+"    消息==="+response.message());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG,"退出错误原因=="+t.getMessage());
            }
        });


        /*
         * 上传单个图片
         * */
        File file = new File("");
        //设置文件类型和File对象
        RequestBody requestBody =  RequestBody.create(MediaType.parse("image/png"),file);
        Call<ResponseBody> responseBodyCall = wanAndroidRegisterApi.upLoad(requestBody);
        responseBodyCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG,"0上传结果=="+response.body()+"    消息==="+response.message());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG,"0上传错误原因=="+t.getMessage());
            }
        });

        /*上传单个图片2*/
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Call<ResponseBody> responseBodyCall1 = wanAndroidLoginApi.upLoad2(part);
        responseBodyCall1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"1上传结果=="+response.body()+"    消息==="+response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,"1上传错误原因=="+t.getMessage());
            }
        });

        /*上传多个图片3*/
        Map<String,RequestBody> map = new HashMap<String,RequestBody>();
        for(int i=0;i<10;i++){
            RequestBody requestBodyAll =  RequestBody.create(MediaType.parse("image/png"),file);
            //此处设置part
            map.put("file\";filename=\"test.png",requestBodyAll);
        }
        Call<ResponseBody> upLoadAll = wanAndroidLoginApi.upLoadAll(map);
        upLoadAll.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"0上传所有图片结果=="+response.body()+"    消息==="+response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,"0上传所有错误原因=="+t.getMessage());
            }
        });

        /*上传多个照片4,老师伪代码报错*/
        File file1 = new File("");
        //设置文件类型和File对象
        RequestBody requestBody1 =  RequestBody.create(MediaType.parse("image/png"),file1);
        MultipartBody.Part partAll = MultipartBody.Part.createFormData("file", file1.getName(), requestBody1);

        Map<String,RequestBody> mapAll = new HashMap<String,RequestBody>();
        for (int i=0;i<10;i++){
            mapAll.put(i+"",requestBody1);
        }
        if(true){
            return;
        }
       // @PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead. (parameter #1),此处报错
        Call<ResponseBody> upLoadAll1 = wanAndroidLoginApi.upLoadAll1(mapAll, new ArrayList<MultipartBody.Part>());

        upLoadAll1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"1上传所有图片结果=="+response.body()+"    消息==="+response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,"1上传所有错误原因=="+t.getMessage());
            }
        });

    }
}
