package com.example.elvin.htzclassic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private GridViewWithHeaderAndFooter gridView;
    private List<GridViewAdapter.Data> gridData;
    private GridViewAdapter gridViewAdapter;

    private AppCompatActivity mAppCompatActivity;
    private RollPagerView mRollPagerView;
    private LinearLayout mSearchBarLayout;
    private ImageButton mHistoryBtn;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAppCompatActivity = (AppCompatActivity)getActivity();
        Toolbar toolbar = (Toolbar)mAppCompatActivity.findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);


        //轮番转动的推荐Banner
        mRollPagerView = (RollPagerView)mAppCompatActivity.findViewById(R.id.rollpagerview);
        mRollPagerView.setAnimationDurtion(500);
        mRollPagerView.setAdapter(new rollPagerViewAdapter());
        mRollPagerView.setHintView(new ColorPointHintView(mAppCompatActivity, Color.YELLOW,Color.WHITE));

        mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mAppCompatActivity,"你点击了"+position+"张Banner",Toast.LENGTH_SHORT).show();
            }
        });

        mSearchBarLayout = (LinearLayout)mAppCompatActivity.findViewById(R.id.search_bar_layout);
        mSearchBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mAppCompatActivity,SearchActivity.class);
                startActivity(intent);
            }
        });

        mHistoryBtn = (ImageButton)mAppCompatActivity.findViewById(R.id.history_btn);
        mHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mAppCompatActivity,HistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private  void initGridData(){
        gridData = new ArrayList<GridViewAdapter.Data>();
        GridViewAdapter.Data data1 = new GridViewAdapter.Data(R.drawable.chuan_xi_lu,"传习录","《传习录》是哲学著作，作者是中国明代哲学家、宋明理学中心学一派的代表人物王守仁（字伯安），世称阳明先生。此书记载了他的语录和论学书信。传习一词源出自《论语》中的传不习乎一语。");
        GridViewAdapter.Data data2 = new GridViewAdapter.Data(R.drawable.chun_qiu,"春秋","《春秋》，即《春秋经》，又称《麟经》或《麟史》，中国古代儒家典籍“六经”之一。也是周朝时期鲁国的国史，现存版本由孔子修订而成。《春秋》用于记事的语言极为简练，然而几乎每个句子都暗含褒贬之意，被后人称为“春秋笔法”、“微言大义”。后来出现了很多对《春秋》所记载的历史进行补充、解释、阐发的书，被称为“传”。代表作品是称为“春秋三传”的《左传》、《公羊传》、《谷梁传》。");
        GridViewAdapter.Data data3 = new GridViewAdapter.Data(R.drawable.dao_de_jing,"道德经","《道德经》是春秋时期老子（李耳）的哲学作品，又称《道德真经》、《老子》、《五千言》、《老子五千文》，是中国古代先秦诸子分家前的一部著作，为其时诸子所共仰，是道家哲学思想的重要来源。道德经分上下两篇，原文上篇《德经》、下篇《道经》，不分章，后改为《道经》37章在前，第38章之后为《德经》，并分为81章。");
        GridViewAdapter.Data data4 = new GridViewAdapter.Data(R.drawable.li_ji,"礼记","《礼记》又名《小戴礼记》、《小戴记》，据传为西汉礼学家戴圣所编，是中国古代一部重要的典章制度选集，共二十卷四十九篇。");
        GridViewAdapter.Data data5 = new GridViewAdapter.Data(R.drawable.liu_zu_tan_jing,"六祖坛经","《六祖坛经》，全称《南宗顿教最上大乘摩诃般若波罗蜜经六祖惠能大师于韶州大梵寺施法坛经》，是佛教禅宗祖师惠能说，[1]  弟子法海等集录的一部经典。");
        GridViewAdapter.Data data6 = new GridViewAdapter.Data(R.drawable.meng_zi,"孟子","《孟子》“四书 ”之一。战国中期孟子及其弟子万章、公孙丑等著。为孟子、孟子弟子所作最早见于赵岐《孟子题辞》：“此书，孟子之所作也，故总谓之《孟子》”。《汉书·艺文志》著录《孟子》十一篇，现存七篇十四卷。总字数三万五千余字，286章。相传另有《孟子外书》四篇，已佚（今本《孟子外书》系明姚士粦伪作）。书中记载有孟子及其弟子的政治、教育、哲学、伦理等思想观点和政治活动。古代考试主要考四书五经。");
        GridViewAdapter.Data data7 = new GridViewAdapter.Data(R.drawable.shang_shu,"尚书","《尚书》，最早书名为《书》，约成书于前五世纪，传统《尚书》由伏生传下来。考证为上古文化《三坟五典》遗留著作。");
        GridViewAdapter.Data data8 = new GridViewAdapter.Data(R.drawable.wu_chang,"五常","“仁义礼智信”为儒家“五常”，孔子提出“仁、义、礼”，孟子延伸为“仁、义、礼、智”，董仲舒扩充为“仁、义、礼、智、信”，后称“五常”。这“五常”贯穿于中华伦理的发展中，他与五行说“金木水火土”，古人创作的“梅花篆字”梅报五福（平安、健康、幸福、快乐、长寿）成为中国价值体系中的最核心因素。");
        GridViewAdapter.Data data9 = new GridViewAdapter.Data(R.drawable.zhong_jing,"忠经","《忠经》是系统总结忠德的专门经典，马融因为有《孝经》而无《忠经》，故作此书来补阙，全篇共十八章。《天地神明章》把忠说成是天地间的至理至德，是评价人们行为的最高准则。");
        GridViewAdapter.Data data10 = new GridViewAdapter.Data(R.drawable.zhong_yong,"中庸","《中庸》是一篇论述儒家人性修养的散文，原是《礼记》第三十一篇，相传为子思所作，是一部儒家学说经典论著。经北宋程颢、程颐极力尊崇，南宋朱熹作《中庸集注》，最终和《大学》、《论语》、《孟子》并称为“四书”。宋、元以后，《中庸》成为学校官定的教科书和科举考试的必读书，对中国古代教育产生了极大的影响。");
        gridData.add(data1);
        gridData.add(data2);
        gridData.add(data3);
        gridData.add(data4);
        gridData.add(data5);
        gridData.add(data6);
        gridData.add(data7);
        gridData.add(data8);
        gridData.add(data9);
        gridData.add(data10);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //首页中的GridView
        initGridData();
        gridViewAdapter = new GridViewAdapter(gridData,view.getContext());
        gridView = (GridViewWithHeaderAndFooter)view.findViewById(R.id.gridview);
        View headerView = inflater.inflate(R.layout.grid_view_header, container, false);
        gridView.addHeaderView(headerView);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),AlbumActivity.class);
                intent.putExtra(AlbumActivity.albumTitle,gridData.get(i).getTitle());
                intent.putExtra(AlbumActivity.albumDesc,gridData.get(i).getDesc());
                intent.putExtra(AlbumActivity.albumImage,gridData.get(i).getImageID());
                startActivity(intent);
            }
        });


        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ImageTask extends AsyncTask<String,Integer,Drawable>{
        ImageView imageView;

        ImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Drawable doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                Drawable drawable = Drawable.createFromStream(url.openStream(),"src");
                return  drawable;
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            imageView.setImageDrawable(drawable);
        }
    }

    private  class rollPagerViewAdapter extends StaticPagerAdapter {
        private int[] imgs = new int[]{
                R.drawable.banner_zhu_zi_wan_nian_ding_lun,
                R.drawable.banner_dian_zi_bao,
        };

        String[] urls = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508998833&di=449b89b6d459b976402ddbcd115525d6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201509%2F16%2F20150916173323_FUN2E.jpeg"
        ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508393156265&di=31b9b7edfc3820e8789e30608abbaed4&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Ff31fbe096b63f6240100df6e8d44ebf81a4ca37a.jpg"};

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
//            view.setImageResource(imgs[position]);
//            try {
//                URL url = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508393156265&di=31b9b7edfc3820e8789e30608abbaed4&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Ff31fbe096b63f6240100df6e8d44ebf81a4ca37a.jpg");
//                Drawable drawable = Drawable.createFromStream(url.openStream(),"src");
//                view.setImageDrawable(drawable);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            ImageTask imageTask = new  ImageTask(view);
            imageTask.execute(urls[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
