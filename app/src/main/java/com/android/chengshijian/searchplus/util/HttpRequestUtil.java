package com.android.chengshijian.searchplus.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.chengshijian.searchplus.listener.OnImageRequestListener;
import com.android.chengshijian.searchplus.listener.OnStringRequestListener;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 *
 * 网络请求工具类
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public class HttpRequestUtil {
    private static RequestQueue mRequestQueue;

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static void stringRequestByPost(String url, final OnStringRequestListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.onResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onErrorResponse(volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                return listener.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                return listener.getParams();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                listener.parseNetworkResponse(response);
                return super.parseNetworkResponse(response);
            }
        };
        mRequestQueue.add(stringRequest);
    }

    public static void stringRequestByGet(String url, final OnStringRequestListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.onResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onErrorResponse(volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                return listener.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                return listener.getParams();
            }
        };
        mRequestQueue.add(stringRequest);
    }


    public static void imageRequest(String url, final OnImageRequestListener listener) {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                listener.onResponse(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onErrorResponse(volleyError);
            }
        }) {
            @Override
            protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
                listener.parseNetworkResponse(response);
                return super.parseNetworkResponse(response);
            }
        };
        mRequestQueue.add(imageRequest);
    }
}
