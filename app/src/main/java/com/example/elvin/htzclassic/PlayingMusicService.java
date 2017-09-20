package com.example.elvin.htzclassic;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ProviderInfo;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.nfc.Tag;
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
    public MediaPlayer mediaPlayer;
    private Boolean isStop = true;

    private Timer timer;
    private LrcTask task;

    public static  int PLAYER_STATE_PLAYING =1;
    public static  int PLAYER_STATE_PAUSE =2;
    public static  int PLAYER_STATE_STOP =3 ;
    public static  int PLAYER_STATE = PLAYER_STATE_STOP;
    private HeadsetDetectReceiver headsetDetectReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        task = new LrcTask();

        if (null == mediaPlayer){
            mediaPlayer = MediaPlayer.create(this,R.raw.music);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mp){
                    Log.d(TAG,"onCompletion");
                    Intent intent = new Intent();
                    intent.setAction(ListeningActivity.SERVICE_PLAYING_COMPLETION_ACTION);
                    sendBroadcast(intent);
                    PLAYER_STATE = PLAYER_STATE_STOP;
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

        headsetDetectReceiver = new HeadsetDetectReceiver();
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        intentFilter3.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetDetectReceiver,intentFilter3);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(headsetDetectReceiver);
    }

    class LrcTask extends  TimerTask{
        @Override
        public void run() {
            try{
                Intent intent = new Intent(ListeningActivity.SERVICE_PLAYING_ACTION);
                int position = mediaPlayer.getCurrentPosition();
                intent.putExtra("position",position);
                sendBroadcast(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getIntExtra("type",-1)){
            case ListeningActivity.PLAY:
                PLAYER_STATE = PLAYER_STATE_PLAYING;
                if(isStop){
                    try{
                        mediaPlayer.start();
                        timer.schedule(task,0,100);
                        mediaPlayer.setLooping(false);
                        isStop = false;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else if(!isStop && mediaPlayer != null && !mediaPlayer.isPlaying() ){
                    mediaPlayer.start();
                }
                break;
            case ListeningActivity.PAUSE:
                PLAYER_STATE = PLAYER_STATE_PAUSE;
                if (mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case ListeningActivity.STOP:
                PLAYER_STATE = PLAYER_STATE_STOP;
                if (mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    isStop = true;
                }
                break;
            case ListeningActivity.SEEK:
                long position = intent.getLongExtra("position",0);
                if (mediaPlayer != null){
                    mediaPlayer.seekTo((int) position);
                }
        }

        return  START_NOT_STICKY;
    }

    public PlayingMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  new localBinder();
    }

    public class localBinder extends Binder{
        PlayingMusicService getService(){
            return PlayingMusicService.this;
        }
    }

    public  class  HeadsetDetectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"HeadsetDetectReceiver:onReceive:action:"+intent.getAction());
//            if(intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)){
            if(intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY) || intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)){
                if(intent.hasExtra("state")){
                    int state = intent.getIntExtra("state",0);
                    Log.d(TAG,"HeadsetDetectReceiver:onReceive:state:"+state);
                    if(1==state){

                    }else if(0==state){//拔出耳机
                        PLAYER_STATE = PLAYER_STATE_PAUSE;
                        if (mediaPlayer != null && mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                        }
                    }
                }
            }
        }
    }
}
