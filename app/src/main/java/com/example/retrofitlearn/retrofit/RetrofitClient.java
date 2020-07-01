package com.example.retrofitlearn.retrofit;




import com.example.retrofitlearn.api.WanAndroidApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*
* 设置BaseUrl,返回接口,接口中封装详细请求
* */
public class RetrofitClient {
    //baseUrl必须以/结束
    String WanAndroidApiURL = "https://www.wanandroid.com/";
    String WanAndroidWXListApiURL = "https://wanandroid.com/";
    String WanAndroidLoginApiURL = "https://www.wanandroid.com/";
    String WanAndroidRegisterApiURL = "https://www.wanandroid.com/";
    public WanAndroidApi createWanAndroidApi(){
        return new Retrofit.Builder().baseUrl(WanAndroidApiURL)
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
                .build().create(WanAndroidApi.class);
    };


    public WanAndroidApi createWanAndroidWXListApi(){
        return new Retrofit.Builder().baseUrl(WanAndroidWXListApiURL)
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
                .build().create(WanAndroidApi.class);
    };

    /*
    * 登录  用cookie,现在有问题,待解决
    * */
    public WanAndroidApi createWanAndroidLoginApi(){
        return new Retrofit.Builder().baseUrl(WanAndroidLoginApiURL)
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
                .build().create(WanAndroidApi.class);
    };

    /*
     * 登录  用cookie,现在有问题,待解决
     * */
    public WanAndroidApi createWanAndroidRegisterApi(){
        return new Retrofit.Builder().baseUrl(WanAndroidRegisterApiURL)
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
                .build().create(WanAndroidApi.class);
    };

}
