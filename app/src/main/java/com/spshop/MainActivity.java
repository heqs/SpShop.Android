package com.spshop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.spshop.AdRouter.AdRouterListener;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MainActivity extends Activity {


    private AdRouter mAdView;

    private ArrayList<String> mImageUrl = null;

    private String imageUrl1 = "http://shop.qz12t.cn/template/s01/images/0827f1.jpg";
    private String imageUrl2 = "http://shop.qz12t.cn/template/s01/images/index_a3.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageUrl = new ArrayList<String>();
        mImageUrl.add(imageUrl1);
        mImageUrl.add(imageUrl2);
        mAdView = (AdRouter) findViewById(R.id.ad_view);
        mAdView.setImageResources(mImageUrl, mAdCycleViewListener);
    }

    private AdRouterListener mAdCycleViewListener = new AdRouterListener() {

        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            Toast.makeText(MainActivity.this, "position->" + position, 0).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(MainActivity.this));
            imageLoader.displayImage(imageURL, imageView);
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
