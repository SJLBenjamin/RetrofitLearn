package com.example.retrofitlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.retrofitlearn.api.WanAndroidApi;
import com.example.retrofitlearn.bean.RegisterBean;
import com.example.retrofitlearn.bean.UserBean;
import com.example.retrofitlearn.bean.WXListBean;
import com.example.retrofitlearn.retrofit.RetrofitClient;
import com.tbruyelle.rxpermissions2.RxPermissions;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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
                Log.d(TAG,"公众号列表错误原因=="+t.getMessage());
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
    }
}
