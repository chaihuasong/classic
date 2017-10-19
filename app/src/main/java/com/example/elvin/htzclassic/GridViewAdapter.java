package com.example.elvin.htzclassic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.protobuf.ByteString;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Inflater;

import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.htz.classic.storage.DownloadFileReq;
import io.grpc.htz.classic.storage.DownloadFileRes;
import io.grpc.htz.classic.storage.Storage;
import io.grpc.htz.classic.storage.storageGrpc;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;

/**
 * Created by elvin on 17-9-11.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<Data> mlist;
    private Context mContext;
    Handler handler;

    GridViewAdapter(List<Data> data,Context ctx){
        this.mlist = data;
        this.mContext = ctx;
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder= null;
        if (null == view){
            view = LayoutInflater.from(this.mContext).inflate(R.layout.grid_item,viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.img_icon);
            viewHolder.textView = (TextView)view.findViewById(R.id.txt_icon);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

//        viewHolder.imageView.setImageResource(mlist.get(i).getImageID());
        ImageTask task = new ImageTask(viewHolder.imageView);
        task.execute("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508998833&di=449b89b6d459b976402ddbcd115525d6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201509%2F16%2F20150916173323_FUN2E.jpeg");
        viewHolder.textView.setText(mlist.get(i).getTitle());

        return view;
    }

    class ImageTask extends AsyncTask<String,Integer,Bitmap>{
        ImageView imageView;
        ManagedChannel managedChannel;

        ImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                managedChannel = OkHttpChannelBuilder.forAddress("10.0.0.39",6001).usePlaintext(true).build();
                storageGrpc.storageBlockingStub storageBlockingStub = storageGrpc.newBlockingStub(managedChannel);
                Metadata metadata = new Metadata();
                Metadata.Key<String> customHeadKey = Metadata.Key.of("auth_type", Metadata.ASCII_STRING_MARSHALLER);
                Metadata.Key<String> customHeadKey2 = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
                metadata.put(customHeadKey,"token");
                metadata.put(customHeadKey2,"1234566");
                storageBlockingStub = MetadataUtils.attachHeaders(storageBlockingStub,metadata);

                DownloadFileReq req = DownloadFileReq.newBuilder().setFileId("123").build();
                Iterator<DownloadFileRes> resp = storageBlockingStub.downloadFile(req);
                ByteString byteString =null;
                while (resp.hasNext()){
                    DownloadFileRes res = resp.next();
                    if(null == byteString){
                        byteString = res.getContent();
                    }else {
                        byteString.concat(res.getContent());
                    }
                }

                return  BitmapFactory.decodeByteArray(byteString.toByteArray(),0,byteString.size());
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    public class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    public static class Data{
        private   int imageID;
        private  String title;
        private  String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setImageID(int imageID) {
            this.imageID = imageID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImageID() {
            return imageID;
        }

        public  Data(){

        }
        
        public  Data(int imageID,String title,String desc){
            this.imageID = imageID;
            this.title = title;
            this.desc  = desc;
        }
    }
}
