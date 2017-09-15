package com.example.elvin.htzclassic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by elvin on 17-9-11.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<Data> mlist;
    private Context mContext;

    GridViewAdapter(List<Data> data,Context ctx){
        this.mlist = data;
        this.mContext = ctx;
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

        viewHolder.imageView.setImageResource(mlist.get(i).getImageID());
        viewHolder.textView.setText(mlist.get(i).getTitle());

        return view;
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
