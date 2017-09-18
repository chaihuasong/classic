package com.example.elvin.htzclassic;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.elvin.htzclassic.view.ILrcBuilder;
import com.example.elvin.htzclassic.view.ILrcView;
import com.example.elvin.htzclassic.view.impl.DefaultLrcBuilder;
import com.example.elvin.htzclassic.view.impl.LrcRow;
import com.example.elvin.htzclassic.view.impl.LrcView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class ListeningActivity extends AppCompatActivity {
    private ImageButton mImageButtonPlay;
    private Boolean isPlaying;

    public final  static  int PLAY = 1;
    public final  static  int PAUSE= 2;
    public final  static  int STOP = 3;

    private ILrcView mLrcView;
    private MsgReceiver msgReceiver;

    public final  static String   SERVICE_PLAYING_ACTION = "com.example.elvin.htzclassic.service.playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        Toolbar toolbar = (Toolbar)findViewById(R.id.listening_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        //从assets目录下读取歌词文件内容
        String lrc = getFromAssets("nan_er_dang_zi_qiang.lrc");
        mLrcView=(ILrcView)findViewById(R.id.lrcView);

        //解析歌词构造器
        ILrcBuilder builder = new DefaultLrcBuilder();
        //解析歌词返回LrcRow集合
        List<LrcRow> rows = builder.getLrcRows(lrc);
        //将得到的歌词集合传给mLrcView用来展示
        mLrcView.setLrc(rows);

        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SERVICE_PLAYING_ACTION);
        registerReceiver(msgReceiver,intentFilter);

    }

    private  void playMusic(int type){
        Intent intent = new Intent(this,PlayingMusicService.class);
        intent.putExtra("type",type);
        startService(intent);
    }

    private  void init(){
        isPlaying = false;
        mImageButtonPlay = (ImageButton)findViewById(R.id.listening_play);
        mImageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    mImageButtonPlay.setBackgroundResource(R.drawable.play);
                    isPlaying = !isPlaying;
                    playMusic(STOP);
                }else {
                    mImageButtonPlay.setBackgroundResource(R.drawable.stop);
                    isPlaying = !isPlaying;
                    playMusic(PLAY);
                }
            }
        });
    }

    public String getFromAssets(String fileName){
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null){
                if(line.trim().equals(""))
                    continue;

                result += line + "\r\n";
            }

            return  result;
        }catch (Exception e){
            e.printStackTrace();
        }

        return  "";
    }

   public class  MsgReceiver extends BroadcastReceiver{
       @Override
       public void onReceive(Context context, Intent intent) {
           int position = intent.getIntExtra("position",0);
           mLrcView.seekLrcToTime(position);
       }
   }
}
