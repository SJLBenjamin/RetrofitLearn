package com.example.retrofitlearn.retrofit;




import android.util.Log;

import com.example.retrofitlearn.api.WanAndroidApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
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
    private Retrofit mRetrofit;

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
    String TAG = "MainActivity";
  static   Map<HttpUrl,List<Cookie>> httpUrlCookieMap = new HashMap<HttpUrl,List<Cookie>>();
    /*
    * 登录  用cookie,现在有问题,待解决
    * */
    public WanAndroidApi createWanAndroidLoginApi(){

        OkHttpClient okHttpClient = new OkHttpClient();

        CookieJar cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                Log.d(TAG,"cookies响应信息来了=="+url);
                if(httpUrlCookieMap.containsKey(url)){
                   httpUrlCookieMap.remove(url);
                   httpUrlCookieMap.put(url,cookies);
                    Log.d(TAG,"1响应对应的url=="+url+"  cookies长度=="+cookies.size());
                }else {
                    httpUrlCookieMap.put(url,cookies);
                    Log.d(TAG,"2响应对应的url=="+url+"  cookies长度=="+cookies.size());
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                Log.d(TAG,"cookies请求对应的url=="+url);
                List<Cookie> cookies = httpUrlCookieMap.get(url);
                 return cookies;
            }
        };
        okHttpClient.newBuilder().cookieJar(cookieJar);

        return new Retrofit.Builder().baseUrl(WanAndroidLoginApiURL)
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
              .client(okHttpClient).build().create(WanAndroidApi.class);
    };

    /*
     * 登录  用cookie,现在有问题,待解决
     * */
    public WanAndroidApi createWanAndroidRegisterApi(){
        return new Retrofit.Builder().baseUrl(WanAndroidRegisterApiURL)
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
               .build().create(WanAndroidApi.class);
    };

    //上传图片
    public WanAndroidApi createWanAndroidUploadApi(){
        return new Retrofit.Builder().baseUrl(WanAndroidRegisterApiURL)
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
               .build().create(WanAndroidApi.class);
    };

    //接口封装
    public void CreateRetrofit(String url){
        mRetrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
    }



    public <T>  T createWanAndroidUploadApi(Class<T> service){
        return mRetrofit.create(service);
    };


    //下载图片
    public WanAndroidApi downloadApi(){
        return new Retrofit.Builder().baseUrl("https://timgsa.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())//将数据转换为Bean类的工具
                .build().create(WanAndroidApi.class);
    };


}
