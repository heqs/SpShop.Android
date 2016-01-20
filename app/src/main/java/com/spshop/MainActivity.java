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
    public JSONArray jsonArrayAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(MainActivity.this);
        //头部轮播广告图片
        loadTopAd();
        loadAdvs("index_a2");
        loadAdvs("index_a3");
        loadAdvs("index_a4");
        loadAdvs("index_a5");
    }

    private void loadTopAd() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cname", "index_a1");
        map.put("num", "4");
        map.put("version", getResources().getString(R.string.version));
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server_api_url) + "GetAdvsByCname", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                            mAdView = (AdRouter) findViewById(R.id.index_a1);
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

    public void loadAdvs(String cname) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cname", cname);
        map.put("version", getResources().getString(R.string.version));
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.server_api_url) + "GetAdvByCname", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        String adJson = response.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(adJson);
                            JSONObject jsonObject2 = jsonObject.getJSONObject("d");
                            String cname = jsonObject2.getString("Cname");
                            ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
                                @Override
                                public void putBitmap(String url, Bitmap bitmap) {
                                }

                                @Override
                                public Bitmap getBitmap(String url) {
                                    return null;
                                }
                            });
                            switch (cname) {
                                case "index_a2":
                                    ImageView imageViewA2 = (ImageView)findViewById(R.id.index_a2);
                                    ImageLoader.ImageListener listener2 = ImageLoader.getImageListener(imageViewA2, R.drawable.loading, R.drawable.nophoto);
                                    imageLoader.get(jsonObject2.getString("ImgUrl"), listener2);
                                    break;
                                case "index_a3":
                                    ImageView imageViewA3 = (ImageView)findViewById(R.id.index_a3);
                                    ImageLoader.ImageListener listener3 = ImageLoader.getImageListener(imageViewA3, R.drawable.loading, R.drawable.nophoto);
                                    imageLoader.get(jsonObject2.getString("ImgUrl"), listener3);
                                    break;
                                case "index_a4":
                                    ImageView imageViewA4 = (ImageView)findViewById(R.id.index_a4);
                                    ImageLoader.ImageListener listener4 = ImageLoader.getImageListener(imageViewA4, R.drawable.loading, R.drawable.nophoto);
                                    imageLoader.get(jsonObject2.getString("ImgUrl"), listener4);
                                    break;
                                case "index_a5":
                                    ImageView imageViewA5 = (ImageView)findViewById(R.id.index_a5);
                                    ImageLoader.ImageListener listener5 = ImageLoader.getImageListener(imageViewA5, R.drawable.loading, R.drawable.nophoto);
                                    imageLoader.get(jsonObject2.getString("ImgUrl"), listener5);
                                    TextView textViewA5 = (TextView)findViewById(R.id.index_a5_txt);
                                    textViewA5.setText(jsonObject2.getString("Title"));
                                    break;
                            }
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
