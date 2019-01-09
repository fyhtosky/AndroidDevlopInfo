package com.example.fanyuanhua.netpower.net;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        printRequestMessage(request);
        Response response = chain.proceed(request);
        printResponseMessage(response);
        return response;
    }


    /**
     * 打印请求的参数
     * @param request
     */
    private void printRequestMessage(Request request) {
        try {
            if (request == null) {
                return;
            }
            Logger.i("Url   : " + request.url().url().toString());
            RequestBody requestBody = request.body();
            if (requestBody == null) {
                return;
            }
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = requestBody.contentType().charset();
            charset = charset == null ? Charset.forName("utf-8") : charset;
            Logger.i("Params: " + bufferedSink.readString(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 打印返回数据的参数
     * @param response
     */
    private void printResponseMessage(Response response) {
        try {
            if (response == null || !response.isSuccessful()) {
                return;
            }
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            try {
                source.request(Long.MAX_VALUE); // Buffer the entire body.
            } catch (IOException e) {
                e.printStackTrace();
            }
            Buffer buffer = source.buffer();
            Charset charset = UTF_8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset();
            }
            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                Logger.json( result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
