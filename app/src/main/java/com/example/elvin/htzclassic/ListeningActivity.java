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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.elvin.htzclassic.view.ILrcBuilder;
import com.example.elvin.htzclassic.view.ILrcView;
import com.example.elvin.htzclassic.view.ILrcViewListener;
import com.example.elvin.htzclassic.view.impl.DefaultLrcBuilder;
import com.example.elvin.htzclassic.view.impl.LrcRow;
import com.example.elvin.htzclassic.view.impl.LrcView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.util.List;


public class ListeningActivity extends AppCompatActivity {
    final  static String TAG ="ListeningActivity";
    private ImageButton mImageButtonPlay;

    public final  static  int PLAY = 1;
    public final  static  int PAUSE= 2;
    public final  static  int STOP = 3;
    public final  static  int SEEK = 4;

    private ILrcView mLrcView;
    private MsgReceiver msgReceiver;
    private CompletionReceiver completionReceiver;

    public final  static String   SERVICE_PLAYING_ACTION = "com.example.elvin.htzclassic.service.playing";
    public final  static String   SERVICE_PLAYING_COMPLETION_ACTION = "com.example.elvin.htzclassic.service.playing.COMPLETION";

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

        mLrcView.setListener(new ILrcViewListener() {
            @Override
            public void onLrcSeeked(int newPosition, LrcRow row) {
                Intent intent = new Intent(ListeningActivity.this,PlayingMusicService.class);
                intent.putExtra("type",SEEK);
                intent.putExtra("position",row.time);
                startService(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SERVICE_PLAYING_ACTION);
        registerReceiver(msgReceiver,intentFilter);

        completionReceiver = new CompletionReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(SERVICE_PLAYING_COMPLETION_ACTION);
        registerReceiver(completionReceiver,intentFilter2);

        if(PlayingMusicService.PLAYER_STATE == PlayingMusicService.PLAYER_STATE_PLAYING){
            mImageButtonPlay.setBackgroundResource(R.drawable.stop);
        }else {
            mImageButtonPlay.setBackgroundResource(R.drawable.play);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(msgReceiver);
        unregisterReceiver(completionReceiver);
    }

    private  void playMusic(int type){
        Intent intent = new Intent(this,PlayingMusicService.class);
        intent.putExtra("type",type);
        startService(intent);
    }

    private  void init(){
        mImageButtonPlay = (ImageButton)findViewById(R.id.listening_play);
        mImageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PlayingMusicService.PLAYER_STATE == PlayingMusicService.PLAYER_STATE_PLAYING){
                    mImageButtonPlay.setBackgroundResource(R.drawable.play);
                    playMusic(PAUSE);
                }else {
                    mImageButtonPlay.setBackgroundResource(R.drawable.stop);
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

   public  class  CompletionReceiver extends  BroadcastReceiver{
       @Override
       public void onReceive(Context context, Intent intent) {
           Log.d(TAG,"CompletionReceiver:onReceive");
           mImageButtonPlay.setBackgroundResource(R.drawable.play);
       }
   }
}
