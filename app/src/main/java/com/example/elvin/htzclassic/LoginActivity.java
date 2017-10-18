package com.example.elvin.htzclassic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.htz.classic.user.HistoriesReq;
import io.grpc.htz.classic.user.HistoriesRes;
import io.grpc.htz.classic.user.userGrpc;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.stub.MetadataUtils;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView respTV;
//    Handler handler  = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login);
        respTV = (TextView)findViewById(R.id.resp_tv);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GrpcTask task = new GrpcTask();
                task.execute();
            }
        });
    }

    public class GrpcTask extends AsyncTask<String,Integer,String> {
        private  String  TAG = "GrpcTask";
        Handler handler;

         ManagedChannel managedChannel;
        @Override
        protected String doInBackground(String... strings) {
            try {
                managedChannel = OkHttpChannelBuilder.forAddress("10.0.0.39",7001).usePlaintext(true).build();

                userGrpc.userBlockingStub stub = userGrpc.newBlockingStub(managedChannel);
                Metadata metadata = new Metadata();
                Metadata.Key<String> customHeadKey = Metadata.Key.of("auth_type", Metadata.ASCII_STRING_MARSHALLER);
                Metadata.Key<String> customHeadKey2 = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
                metadata.put(customHeadKey,"token");
                metadata.put(customHeadKey2,"1234566");
                stub = MetadataUtils.attachHeaders(stub,metadata);

                HistoriesReq historiesReq = HistoriesReq.newBuilder().build();
                HistoriesRes historiesRes = stub.histories(historiesReq);
                Log.d(TAG,historiesRes.toString());
                return historiesRes.toString();
            }catch (StatusRuntimeException e){
                Log.d(TAG,e.getMessage());
                Log.d(TAG,e.getStatus().toString());
                Log.d(TAG,e.getStatus().getDescription());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            respTV.setText(str);
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }
}
