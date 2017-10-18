package com.example.elvin.htzclassic;

import android.os.AsyncTask;
import android.util.Log;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.htzclassic.user.HistoriesReq;
import io.grpc.htzclassic.user.HistoriesRes;
import io.grpc.htzclassic.user.userGrpc;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.stub.MetadataUtils;
import io.grpc.StatusRuntimeException;


/**
 * Created by elvin on 17-10-17.
 */

public class GrpcTask extends AsyncTask {
    private  static String  TAG = "GrpcTask";
    ManagedChannel managedChannel;
    @Override
    protected String doInBackground(Object[] objects) {
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
}
