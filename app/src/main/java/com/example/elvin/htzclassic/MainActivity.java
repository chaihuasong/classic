package com.example.elvin.htzclassic;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import static com.example.elvin.htzclassic.GridViewAdapter.*;

public class MainActivity extends AppCompatActivity {
    @Titles
    private static final String[] mTitles = {"首页","我的"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.home_pressed,R.drawable.account_pressed};

    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.home, R.drawable.account};

    private JPTabBar jpTabBar;
    private ImageButton imageButtonPlay;
    private ViewPager viewPager;

    private List<Fragment> mlist;
    private Boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //JPTabbar
        mlist = new  ArrayList<Fragment>();
        HomeFragment homeFragment= new HomeFragment();
        MyFragment myFragment  = new MyFragment();
        mlist.add(homeFragment);
        mlist.add(myFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.init(mlist);

        viewPager= (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);

        jpTabBar= (JPTabBar)findViewById(R.id.jptabbar);
        jpTabBar.setContainer(viewPager);

        //JPTabbar中间按钮
        imageButtonPlay = (ImageButton)jpTabBar.getMiddleView().findViewById(R.id.play);
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    imageButtonPlay.setImageResource(R.drawable.play);
                    isPlaying = !isPlaying;
                }else {
                    imageButtonPlay.setImageResource(R.drawable.stop);
                    isPlaying = !isPlaying;
                }
            }
        });
    }
}
