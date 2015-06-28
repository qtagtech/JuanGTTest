package com.juandavidarroyave.android.juangttest;


import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.juandavidarroyave.android.juangttest.adapters.ViewPagerAdapter;
import com.juandavidarroyave.android.juangttest.ui.views.Popup;
import com.juandavidarroyave.android.juangttest.ui.views.Tab;

import java.lang.ref.WeakReference;
import java.util.Random;

import hugo.weaving.DebugLog;


public class MainActivity extends AppCompatActivity implements Tab.OnImageSelectedListener {



    private static String TAG = "MainActivity.class";

    View decorView;
    Context mContext;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    Button btnPlay;
    TextView tvBalance;
    CharSequence titles[] = {"First Deck", "second Deck", "third Deck" };
    int numTabs;
    int  numImages = 20;
    int imagesPerPage = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        decorView = getWindow().getDecorView();
        btnPlay = (Button) findViewById(R.id.button_play);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        btnPlay.setOnClickListener(playListener);
        tvBalance.setText(String.format(mContext.getString(R.string.title_balance),1));
        // Creating The Toolbar and setting it as the Toolbar for the activity

//        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = mContext.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int defaultImagesPerPage = getResources().getInteger(R.integer.saved_images_per_page);
        int defaultImagesTotal = getResources().getInteger(R.integer.saved_total_images);
        imagesPerPage = sharedPref.getInt(getString(R.string.images_per_page), 0);
        numImages = sharedPref.getInt(getString(R.string.total_images), 0);
        if(defaultImagesPerPage != imagesPerPage) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.images_per_page), defaultImagesPerPage);
            editor.commit();
            imagesPerPage = defaultImagesPerPage;
        }
        if(defaultImagesTotal != numImages) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.total_images), defaultImagesTotal);
            editor.commit();
            numImages = defaultImagesTotal;
        }


        numTabs = (int)  Math.ceil(numImages / (double) imagesPerPage);

        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),titles,numTabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setPageTransformer(true, new CubeOutTransformer());
        pager.addOnPageChangeListener(pageChanger);
    }

    @Override
    public void onStart(){
        super.onStart();
        animateBackground();
    }

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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }



    //Methods to determine the page in which the user currently is at in order to set the grid with the new images

    private ViewPager.OnPageChangeListener pageChanger = new ViewPager.OnPageChangeListener() {
        @Override
        @DebugLog
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        @DebugLog
        public void onPageSelected(int position) {
            Tab cPage = getPage();
            cPage.updateGrid(position);
        }

        @Override
        @DebugLog
        public void onPageScrollStateChanged(int state) {

        }
    };

    @DebugLog
    protected Tab getPage(){
        FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) pager.getAdapter();
        return (Tab) a.instantiateItem(pager, pager.getCurrentItem());
    }


    @Override
    public void onImageSelected(int position) {
        showEditDialog(position);
    }

    private void showEditDialog(int position) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Popup popUpWindow = Popup.newInstance(position,width,height);
        popUpWindow.show(getSupportFragmentManager(), "pop_up_window");
    }

    private OnClickListener playListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Random r = new Random();
            int i1 = r.nextInt(100000 - 2) + 1 ;
            tvBalance.setText(String.format(getString(R.string.title_balance),i1));
            tvBalance.animate().translationX(11).setInterpolator(new CycleInterpolator(2)).setDuration(450);
//            tvBalance.animate().rotationYBy(720);
        }
    };

    private void animateBackground(){
        final FrameLayout bg = (FrameLayout) findViewById(R.id.bg_layout);
        final Runnable endAction = new Runnable() {
            public void run() {
                bg.animate().scaleX(1).scaleY(1);
            }
        };

        bg.animate().scaleX(2).scaleY(2).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                endAction.run();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
