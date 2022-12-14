package com.mockable.parserap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NextActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL_description = "http://demo7877231.mockable.io/api/v1/post/"; // + id записи
    private static final String URL_ = "http://demo7877231.mockable.io/api/v1/hot";

    String key1 = "271", key2 = "811", key3 = "1924";
    TextView text, text1, text2, text3;
    WebView webView;
    ArrayList<JSONObject> list;

    private static int i;
    private static ArrayList<HashMap<String, Object>> list2;
    private static final String _text = "text";
    private static final String _url1 = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        text = (TextView) findViewById(R.id.text);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        webView = findViewById(R.id.webView);

        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);

        loadID(URL_);

        //loadFromURL(URL_description);

        list2 = new ArrayList<HashMap<String, Object>>();
    }

    public void loadFromURL(String Url_) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url_,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("payload");
                            for (int b = 0; b < jsonArray.length(); b++) {
                                HashMap<String, Object> hm;
                                hm = new HashMap<String, Object>();

                                if(i == 0){
                                    hm.put(_text, jsonArray.getJSONObject(b).getString("text").toString());
                                    TextView text1 = findViewById(R.id.text1);
                                    text1.setText((CharSequence) hm.get("text"));
                                }else if(i == 1){
                                    hm.put(_url1, jsonArray.getJSONObject(b).getString("url").toString());
                                    TextView text2 = findViewById(R.id.text2);
                                    text2.setText((CharSequence) hm.get("url"));
                                }else{
                                    hm.put(_url1, jsonArray.getJSONObject(b).getString("url").toString());
                                    TextView text3 = findViewById(R.id.text3);
                                    text3.setText((CharSequence) hm.get("url"));
                                }
                                list2.add(hm);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(NextActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(NextActivity.this);
        requestQueue.add(stringRequest);
    }

    private void loadID(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("");
                            ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                            list = listItems;
                            for (i = 0; i <= 2; i++) {
                                loadFromURL(URL_description+listItems.get(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(NextActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(NextActivity.this);
        requestQueue.add(stringRequest);
    }

    public static ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray) {
        ArrayList<JSONObject> aList = new ArrayList<>();
        try {
            if (jsonArray != null) {
                for (int a = 0; a < jsonArray.length(); a++) {
                    aList.add(jsonArray.getJSONObject(a));
                }
            }
        } catch (JSONException js) {
            js.printStackTrace();
        }
        return aList;
    }

    public void runWeView() {
        getSupportActionBar().hide();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(_url1);
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(NextActivity.this, R.anim.alpha);
        switch (v.getId()) {
            case R.id.text1:
                text1.startAnimation(Animation);
                text.setText(_text);
                break;
            case R.id.text2:
                text2.startAnimation(Animation);
                runWeView();
                break;
            case R.id.text3:
                text3.startAnimation(Animation);
                runWeView();
                break;
        }
    }

    public static class WebViewClient extends android.webkit.WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        // Для старых устройств
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
