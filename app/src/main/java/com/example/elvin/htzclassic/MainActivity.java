package com.example.elvin.htzclassic;

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

    private SearchView searchView;

    private RollPagerView mRollPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //轮番转动的推荐Banner
        mRollPagerView = (RollPagerView)findViewById(R.id.rollpagerview);
        mRollPagerView.setAnimationDurtion(500);
        mRollPagerView.setAdapter(new rollPagerViewAdapter());
        mRollPagerView.setHintView(new ColorPointHintView(this, Color.YELLOW,Color.WHITE));

        mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"你点击了"+position+"张Banner",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  class rollPagerViewAdapter extends StaticPagerAdapter{
        private int[] imgs = new int[]{
                R.drawable.banner_zhu_zi_wan_nian_ding_lun,
                R.drawable.banner_dian_zi_bao,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view,menu);

//        MenuItem item =  menu.findItem(R.id.searchview);
//        SearchView searchview = (SearchView) item.getActionView();
//        searchView.setIconifiedByDefault(true);
//        searchView.setQueryHint("搜索");
//        searchView.setSubmitButtonEnabled(true);

        return  true;
    }
}
