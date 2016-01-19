package com.spshop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.spshop.AdRouter.AdRouterListener;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends Activity {


    private AdRouter mAdView;
    private ArrayList<String> mImageUrl = null;
    private RequestQueue mQueue;
    private ImageView homeAd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(MainActivity.this);
        homeAd1 = (ImageView)findViewById(R.id.home_ad1);
        //头部轮播广告图片
        loadTopAd();
        loadAdv("index_a2",homeAd1);
    }

    private void loadTopAd()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cname", "index_a1");
        map.put("num", "4");
        map.put("version",getResources().getString(R.string.version));
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,getResources().getString(R.string.server_api_url), params,
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

    private void loadAdv(String cname, ImageView imageView) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("cname",cname);
        map.put("version",getResources().getString(R.string.version));
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,getResources().getString(R.string.server_api_url),params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        String adJson = response.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(adJson);
                            JSONObject jsonObject2 = jsonObject.getJSONObject("d");
                            String imageURL = jsonObject2.getString("ImgUrl");
                            //设置广告图片
                            ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
                                @Override
                                public void putBitmap(String url, Bitmap bitmap) {
                                }

                                @Override
                                public Bitmap getBitmap(String url) {
                                    return null;
                                }
                            });
                            ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.loading, R.drawable.nophoto);
                            imageLoader.get(imageURL, listener);

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
    }

    private AdRouterListener mAdCycleViewListener = new AdRouterListener() {

        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            //Toast.makeText(MainActivity.this, "position->" + position, 0).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                }

                @Override
                public Bitmap getBitmap(String url) {
                    return null;
                }
            });
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.loading, R.drawable.nophoto);
            imageLoader.get(imageURL, listener);
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
