package com.example.elvin.htzclassic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //首页中的GridView
        gridData = new ArrayList<GridViewAdapter.Data>();
        GridViewAdapter.Data data1 = new GridViewAdapter.Data(R.drawable.chuan_xi_lu,"传习录");
        GridViewAdapter.Data data2 = new GridViewAdapter.Data(R.drawable.chun_qiu,"春秋");
        GridViewAdapter.Data data3 = new GridViewAdapter.Data(R.drawable.dao_de_jing,"道德经");
        GridViewAdapter.Data data4 = new GridViewAdapter.Data(R.drawable.li_ji,"礼记");
        GridViewAdapter.Data data5 = new GridViewAdapter.Data(R.drawable.liu_zu_tan_jing,"六祖坛经");
        GridViewAdapter.Data data6 = new GridViewAdapter.Data(R.drawable.meng_zi,"孟子");
        GridViewAdapter.Data data7 = new GridViewAdapter.Data(R.drawable.shang_shu,"尚书");
        GridViewAdapter.Data data8 = new GridViewAdapter.Data(R.drawable.wu_chang,"无常");
        GridViewAdapter.Data data9 = new GridViewAdapter.Data(R.drawable.zhong_jing,"忠经");
        GridViewAdapter.Data data10 = new GridViewAdapter.Data(R.drawable.zhong_yong,"中庸");
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
        gridViewAdapter = new GridViewAdapter(gridData,view.getContext());
        gridView = (GridViewWithHeaderAndFooter)view.findViewById(R.id.gridview);
        View headerView = inflater.inflate(R.layout.grid_view_header, container, false);
        gridView.addHeaderView(headerView);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),AlbumActivity.class);
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

    private  class rollPagerViewAdapter extends StaticPagerAdapter {
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
}
