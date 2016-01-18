package com.spshop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.spshop.AdRouter.AdRouterListener;
//import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {


    private AdRouter mAdView;
    private ArrayList<String> mImageUrl = null;
    private RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取广告图片
        Map<String, String> map = new HashMap<String, String>();
        map.put("cname", "index_a1");
        map.put("num", "4");
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://spshop.dbdy.net/api/app.asmx/GetAdvsByCname", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        String adJson = response.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(adJson);
                            JSONArray jsonArray = jsonObject.getJSONArray("d");
                            //设置广告图片
                            mImageUrl = new ArrayList<String>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                                mImageUrl.add(jsonObject2.getString("ImgUrl"));
                            }
                            mAdView = (AdRouter) findViewById(R.id.ad_view);
                            mAdView.setImageResources(mImageUrl, mAdCycleViewListener);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);


    }

    private AdRouterListener mAdCycleViewListener = new AdRouterListener() {

        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            //Toast.makeText(MainActivity.this, "position->" + position, 0).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            //ImageLoader imageLoader = new ImageLoader(mQueue,new ImageLoader.ImageCache());
            //ImageLoader imageLoader = ImageLoader.getInstance();
            //imageLoader.init(ImageLoaderConfiguration.createDefault(MainActivity.this));
            //imageLoader.displayImage(imageURL, imageView);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
