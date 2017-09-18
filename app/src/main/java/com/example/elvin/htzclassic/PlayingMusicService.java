package com.example.elvin.htzclassic;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingMusicService extends Service {
    private  final  static  String TAG = "PlayingMusicService";
    private MediaPlayer mediaPlayer;
    private Boolean isStop = true;

    private Timer timer;
    private LrcTask task;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();

        if (null == mediaPlayer){
            mediaPlayer = new MediaPlayer();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mp){
                    Intent intent = new Intent();
                    intent.setAction("com.complete");
                    sendBroadcast(intent);
                }
            });

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Log.d("dd","dd");
//                    mediaPlayer.start();
                    timer.schedule(task,0,10000);
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.e(TAG,""+i +""+i1);
                    return false;
                }
            });
        }
    }

    class LrcTask extends  TimerTask{
        @Override
        public void run() {
            Log.d("dd","ddfsafas");
//            if(mediaPlayer.isPlaying()){
//                Intent intent = new Intent(ListeningActivity.SERVICE_PLAYING_ACTION);
//                int position = mediaPlayer.getCurrentPosition();
//                intent.putExtra("position",position);
//                sendBroadcast(intent);
//            }

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getIntExtra("type",-1)){
            case ListeningActivity.PLAY:
                if(isStop){
                    mediaPlayer.reset();
                    mediaPlayer = MediaPlayer.create(this,R.raw.music);
                    mediaPlayer.start();
                    Log.e(TAG,""+mediaPlayer.getCurrentPosition());
                    mediaPlayer.setLooping(false);
                    isStop = false;
                }else if(!isStop && mediaPlayer != null && !mediaPlayer.isPlaying() ){
                    mediaPlayer.start();
                }
                break;
            case ListeningActivity.PAUSE:
                if (mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case ListeningActivity.STOP:
                if (mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    isStop = true;
                }
                break;
        }

        return  START_NOT_STICKY;
    }

    public PlayingMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  null;
    }
}
