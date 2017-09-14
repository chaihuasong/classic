package com.example.elvin.htzclassic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private AppCompatActivity mAppCompatActivity;
    private List<Info> mInfoList;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAppCompatActivity = (AppCompatActivity)getActivity();
        initInfos();

        mListView = (ListView)mAppCompatActivity.findViewById(R.id.my_listivew);
        View listViewHeader = mAppCompatActivity.getLayoutInflater().inflate(R.layout.my_listview_header,mListView,false);
        mListView.addHeaderView(listViewHeader);
        mListView.setAdapter(new ListViewAdapter(mInfoList));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    private void initInfos(){
        mInfoList = new ArrayList<Info>();
        Info infoFavorite  = new Info();
        infoFavorite.setEnter(R.drawable.enter);
        infoFavorite.setIcon(R.drawable.favorite);
        infoFavorite.setTitle("我的收藏");
        mInfoList.add(infoFavorite);

        Info infoHistory  = new Info();
        infoHistory.setEnter(R.drawable.enter);
        infoHistory.setIcon(R.drawable.history);
        infoHistory.setTitle("播放历史");
        mInfoList.add(infoHistory);
    }

    public class ListViewAdapter extends BaseAdapter{
        private List<View> mList;

        public  ListViewAdapter(List<Info> list){
            mList = new ArrayList<View>();
            for(int i = 0;i< list.size();i++){
                Info info = list.get(i);
                View view = makeView(info.icon,info.title,info.enter);
                mList.add(view);
            }
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (null == view){
                return mList.get(i);
            }

            return view;
        }

        private View makeView(int icon,String title,int enter){
            View view = mAppCompatActivity.getLayoutInflater().inflate(R.layout.my_listview_item,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.listview_item_icon);
            imageView.setImageResource(icon);

            TextView textView = (TextView)view.findViewById(R.id.listview_item_title);
            textView.setText(title);
            ImageView imageView2 = (ImageView)view.findViewById(R.id.listview_item_enter);
            imageView2.setImageResource(enter);

            return  view;
        }
    }

    public class  Info{
        private int icon;

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getEnter() {
            return enter;
        }

        public void setEnter(int enter) {
            this.enter = enter;
        }

        private String title;
        private int enter;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
}
