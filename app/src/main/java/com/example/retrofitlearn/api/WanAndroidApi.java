package com.example.retrofitlearn.api;


import com.example.retrofitlearn.bean.RegisterBean;
import com.example.retrofitlearn.bean.UserBean;
import com.example.retrofitlearn.bean.WXListBean;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/*
* 接口
* 创建一系列的请求
*
* */
public interface  WanAndroidApi{
        /*
        * id为公众号id
        * page表示公众号页码
        * 方法中是给id和page赋值
        * */
        @GET("wxarticle/list/{id}/{page}/json")
        Call<WXListBean> getProject(@Path("id") String id, @Path("page") String page);

        //带查询参数的,query代表是查询
        @GET("wxarticle/list/{id}/{page}/json")
        Call<WXListBean> getProjectForK(@Path("id") String id, @Path("page") String page,@Query("k") String k);

        //登录
        @POST("user/login")
        Call<UserBean> CreateUser(@Body UserBean user);

        //注册,表单注册
        @FormUrlEncoded
        @POST("user/register")
        Call<RegisterBean> registerUser(@Field("username") String username,@Field("password") String password,@Field("repassword") String repassword);

        //退出登录
        @GET("user/logout/json")
        Call<Object> exitLogin();


}
