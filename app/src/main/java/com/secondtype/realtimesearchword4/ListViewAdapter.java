package com.secondtype.realtimesearchword4;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by appaaaa on 2017-04-21.
 */

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<Reply> mList;
    private Activity activity;

    //생성자
    ListViewAdapter(Activity activity, ArrayList<Reply> mList){
        this.mList = new ArrayList<Reply>();
        this.activity = activity;
        this.mList.addAll(mList);

        Log.v("listview test", "listview setmList");
        Log.v("listview test", Integer.toString(this.mList.size()));
    }


    @Override
    public int getCount() {
        Log.v("listview test", "getCount()");
        Log.v("listview test", Integer.toString(mList.size()));
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("listview test", "start listview test");
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reply_list_item, parent, false);
        }



        TextView word = (TextView)convertView.findViewById(R.id.textview_reply_list_word);
        TextView text = (TextView)convertView.findViewById(R.id.textview_reply_list_text);
        TextView time = (TextView)convertView.findViewById(R.id.textview_reply_list_time);
        TextView count1 = (TextView)convertView.findViewById(R.id.textview_reply_list_count1);
        TextView count2 = (TextView)convertView.findViewById(R.id.textview_reply_list_count2);
        TextView count3 = (TextView)convertView.findViewById(R.id.textview_reply_list_count3);

        Log.v("listview test", mList.get(position).name);
        word.setText(mList.get(position).name);
        text.setText(mList.get(position).text);
        time.setText(mList.get(position).time);
        count1.setText(mList.get(position).count1);
        count2.setText(mList.get(position).count2);
        count3.setText(mList.get(position).count3);


        return convertView;
    }
}
