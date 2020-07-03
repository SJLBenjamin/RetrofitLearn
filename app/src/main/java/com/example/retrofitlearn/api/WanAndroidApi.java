package com.example.retrofitlearn.api;


import com.example.retrofitlearn.bean.RegisterBean;
import com.example.retrofitlearn.bean.UserBean;
import com.example.retrofitlearn.bean.WXListBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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



        //上传图片,上传图片需要用到Multipart修饰,@part表示会传递part给后台,后台可以通过fileName filePart =request.getPart("file");(此处通过file字段获取对应对象), 然后通过此对象filePart.getFileName()获取fileName的值;
        @Multipart
        @POST("project/upload")
        Call<ResponseBody> upLoad(@Part("file\";filename=\"test.png") RequestBody file);

        //上传图片的第二种写法,上传图片需要用到Multipart修饰,@part表示会传递part给后台,后台可以通过fileName filePart =request.getPart("file");(此处通过file字段获取对应对象), 然后通过此对象filePart.getFileName()获取fileName的值;
        @Multipart
        @POST("project/upload")
        Call<ResponseBody> upLoad2(@Part MultipartBody.Part multipartBody);

        //上传多个图片第一种写法
        @Multipart
        @POST("project/upload")
        Call<ResponseBody> upLoadAll(@PartMap Map<String,RequestBody> partMap);


        //上传多个图片第二种写法
        @Multipart
        @POST("project/upload")
        Call<ResponseBody>  upLoadAll1(@PartMap Map<String,RequestBody> partMap,@Part List<MultipartBody.Part>  mList);

}
