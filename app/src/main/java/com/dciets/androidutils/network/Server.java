package com.dciets.androidutils.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dciets.androidutils.R;
import com.dciets.androidutils.database.PreferencesController;
import com.dciets.androidutils.listener.Listener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marc-antoinehinse on 2015-10-10.
 */
public class Server {

    private static final String TAG = "Server";
    private static String BASE_URL = "http://put.your-base-url.here/";

    public static void postRequestWithHeadersAndParams(final Context context, final Listener listener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "api/stuff/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i(TAG, response);
                        if(listener != null) {
                            listener.onDoStuff();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
                if(listener != null) {
                    listener.onErrorStuff();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("X-Auth-Token", PreferencesController.getStringPreference(context, PreferencesController.TOKEN));
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Only works with POST request
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("firstPostParam", "firstPostValue");
                return headers;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getRequestWithParams(final Context context, final Listener listener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("firstParam", "firstValue");
        params.put("secondParam", "secondValue");

        final String url = BASE_URL + getParams(params);
        Log.i(TAG, url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            if (listener != null) {
                                listener.onDoStuff();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (listener != null) {
                                listener.onErrorStuff();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
                if (listener != null) {
                    listener.onErrorStuff();
                }
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private String getParams(final HashMap<String, String> params) {
        StringBuilder builder = new StringBuilder();
        for (String key : params.keySet())
        {
            Object value = params.get(key);
            if (value != null)
            {
                try
                {
                    value = URLEncoder.encode(String.valueOf(value), "utf-8");


                    if (builder.length() > 0)
                        builder.append("&");
                    builder.append(key).append("=").append(value);
                }
                catch (UnsupportedEncodingException e)
                {
                }
            }
        }

        return "?" + builder.toString();
    }
}
