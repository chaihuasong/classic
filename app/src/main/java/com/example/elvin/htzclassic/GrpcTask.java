package com.example.elvin.htzclassic;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.htz.classic.user.HistoriesReq;
import io.grpc.htz.classic.user.HistoriesRes;
import io.grpc.htz.classic.user.userGrpc;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.stub.MetadataUtils;
import io.grpc.StatusRuntimeException;


/**
 * Created by elvin on 17-10-17.
 */

