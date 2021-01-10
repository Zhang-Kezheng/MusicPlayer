package com.example.musicplayer.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @author 章可政
 * @date 2020/10/19 11:59
 */
public class HttpUtil {
    /**
     * 发送一个get请求 用于请求网络上的数据
     * @param url 网络地址
     * @return 响应体
     */
    public static String sendGetRequest(URL url){
        StringBuffer buffer=new StringBuffer();
     Thread t=  new Thread(() -> {
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Cookie","kg_mid=9cc69b78794df8550f3c29b2d142ac46");
                conn.setConnectTimeout(6 * 1000);
                conn.setReadTimeout(6 * 1000);
                InputStream in = conn.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                BufferedInputStream bufferedInputStream=new BufferedInputStream(in);
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    buffer.append(new String(bytes,0,len));
                }
                bufferedInputStream.close();
                in.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
     });
     t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void  sendGetRequest(URL url, Handler handler,int what){
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Cookie", "kg_mid=9cc69b78794df8550f3c29b2d142ac46")
                .addHeader("clienttime","1610265088")
                .addHeader("dfid","4WErRt1NrF6t1yms5H4bfByV")
                .addHeader("mid","222509261258459411814045547501909370639")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String  responseBody=response.body().string();
                Message message = handler.obtainMessage();
                message.what=what;
                Bundle bundle = new Bundle();
                bundle.putString("data",responseBody);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
}
