package com.example.elvin.htzclassic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class ListeningActivity extends AppCompatActivity {
    private ImageButton mImageButtonPlay;
    private Boolean isPlaying;

    public final  static  int PLAY = 1;
    public final  static  int PAUSE= 2;
    public final  static  int STOP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        Toolbar toolbar = (Toolbar)findViewById(R.id.listening_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
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
}
