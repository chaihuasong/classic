package com.example.elvin.htzclassic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


//TODO 有过一个bug,listview多刷新一下就会乱
public class AlbumActivity extends AppCompatActivity {
    public final  static String albumImage = "album_image";
    public final  static String albumTitle = "album_title";
    public final  static String albumDesc = "album_desc";

    private ImageView mAlbumImage;
    private TextView mAlbumTitle;
    private TextView mAlbumDesc;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = (Toolbar)findViewById(R.id.album_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        setAllListener();
    }

    private void setAllListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AlbumActivity.this,ListeningActivity.class);
                startActivity(intent);
            }
        });
    }

    private  void init(){
        Intent intent = getIntent();

        mAlbumDesc = (TextView)findViewById(R.id.album_desc);
        mAlbumDesc.setText(intent.getStringExtra(albumDesc));
        mAlbumImage = (ImageView)findViewById(R.id.album_image);
        int icon = intent.getIntExtra(albumImage,0);
        mAlbumImage.setImageResource(icon);
        mAlbumTitle = (TextView)findViewById(R.id.album_title);
        mAlbumTitle.setText(intent.getStringExtra(albumTitle));

        List<AlbumActivity.Info> list = new ArrayList<AlbumActivity.Info>();
        Info info = new Info();
        info.setIcon(icon);
        info.setPlayCount(9857438);
        info.setTime("29:44");
        info.setTitle("001.阳明心学的缘起");
        list.add(info);

        Info info2 = new Info();
        info2.setIcon(icon);
        info2.setPlayCount(899788);
        info2.setTime("26:44");
        info2.setTitle("002.门人眼中的阳明先生");
        list.add(info2);

        Info info3 = new Info();
        info3.setIcon(icon);
        info3.setPlayCount(62438);
        info3.setTime("23:14");
        info3.setTitle("003.“亲民”与“新民”之辨？");
        list.add(info3);

        Info info4 = new Info();
        info4.setIcon(icon);
        info4.setPlayCount(65338);
        info4.setTime("22:44");
        info4.setTitle("004.至善求于心还是外在事物之理？");
        list.add(info4);

        Info info5 = new Info();
        info5.setIcon(icon);
        info5.setPlayCount(3338);
        info5.setTime("15:24");
        info5.setTitle("005.天下事理皆源于心吗？");
        list.add(info5);

        Info info6 = new Info();
        info6.setIcon(icon);
        info6.setPlayCount(83338);
        info6.setTime("21:11");
        info6.setTitle("006.事理更好，需从内心来改造");
        list.add(info6);

        Info info7 = new Info();
        info7.setIcon(icon);
        info7.setPlayCount(38838);
        info7.setTime("16:59");
        info7.setTitle("007.至善是心纯乎天理之极");
        list.add(info7);

        Info info8 = new Info();
        info8.setIcon(icon);
        info8.setPlayCount(3338);
        info8.setTime("19:55");
        info8.setTitle("008.被误解的“知行合一”  ");
        list.add(info8);

        Info info9 = new Info();
        info9.setIcon(icon);
        info9.setPlayCount(13338);
        info9.setTime("19:34");
        info9.setTitle("009.知行的本体在于内心的感受");
        list.add(info9);

        ListViewAdapter listViewAdapter = new ListViewAdapter(list);
        mListView = (ListView)findViewById(R.id.album_listview);

        View listViewHeader = getLayoutInflater().inflate(R.layout.album_listview_header,mListView,false);
        mListView.addHeaderView(listViewHeader);
        mListView.setAdapter(listViewAdapter);
    }

    public class ListViewAdapter extends BaseAdapter {
        private List<View> mList;

        public  ListViewAdapter(List<AlbumActivity.Info> list){
            mList = new ArrayList<View>();
            for(int i = 0;i< list.size();i++){
                AlbumActivity.Info info = list.get(i);
                View view = makeView(info.getIcon(),info.getTitle(),info.getPlayCount(),info.getTime());
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

        private View makeView(int icon,String title,int count,String time){
            View view = getLayoutInflater().inflate(R.layout.album_listview_item,null);

            ImageView imageView = (ImageView)view.findViewById(R.id.album_listview_item_icon);
            imageView.setImageResource(icon);



            TextView tvTitle = (TextView)view.findViewById(R.id.album_listview_item_title);
            tvTitle.setText(title);

            TextView tvPlayCount = (TextView)view.findViewById(R.id.album_listview_item_play_count);
            tvPlayCount.setText(""+count);

            TextView tvTime = (TextView)view.findViewById(R.id.album_listview_item_time);
            tvTime.setText(time);

            return  view;
        }
    }

    public class Info{
        private int icon;
        private String title;
        private int playCount;
        private String time;

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

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
}
