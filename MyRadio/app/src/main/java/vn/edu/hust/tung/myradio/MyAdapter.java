package vn.edu.hust.tung.myradio;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.tung.myradio.Objects.RadioChannel;
import vn.edu.hust.tung.myradio.R;

/**
 * Created by tung on 3/19/17.
 */

public class MyAdapter extends ArrayAdapter<RadioChannel> {
    private Activity activity;
    ArrayList<RadioChannel> list;


    public MyAdapter(@NonNull Activity activity, ArrayList<RadioChannel> list) {
        super(activity, R.layout.list_radio_template, list);

        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RadioHolder holder = null;

        if (row == null) {
            row = activity.getLayoutInflater().inflate(R.layout.list_radio_template, parent, false);
            holder = new RadioHolder(row, activity);    //nếu row = null thì holder = new Holder...
            row.setTag(holder);

        } else {
            holder = (RadioHolder) row.getTag();        //nếu row != null thì holder = row.getTag()
        }
        holder.setView(list.get(position));

        return row;
    }
}