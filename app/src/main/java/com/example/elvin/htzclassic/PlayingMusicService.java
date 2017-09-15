package com.example.elvin.htzclassic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;

public class PlayingMusicService extends Service {
    private MediaPlayer mediaPlayer;
    private Boolean isStop = true;

    @Override
    public void onCreate() {
        super.onCreate();
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
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
