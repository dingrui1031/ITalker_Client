package cn.dingrui.factory.net;

import cn.dingrui.common.common.Common;
import cn.dingrui.factory.Factory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求的封装
 * Created by dingrui
 */

public class NetWork {

    //构建一个Retrofit
    public static Retrofit getRetrofit(){

        //创建一个okhttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder();

        Retrofit retrofit = builder.baseUrl(Common.Constant.API_URL)
                // 设置client
                .client(okHttpClient)
                // 设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();

        return retrofit;
    }

    /**
     * 返回一个请求代理
     * @return
     */
    public static RemoteService remote(){
        return NetWork.getRetrofit().create(RemoteService.class);
    }
}
