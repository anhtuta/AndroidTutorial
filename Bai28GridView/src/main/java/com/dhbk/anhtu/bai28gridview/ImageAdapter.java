package com.dhbk.anhtu.bai28gridview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by AnhTu on 07/03/2017.
 */

public class ImageAdapter extends ArrayAdapter<Integer> {

    Activity context;
    int resource;
    List<Integer> objects;

    public ImageAdapter(Activity context, int resource, List<Integer> objects) {
        super(context,resource,objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//vẽ lên giao diện theo ý mình
        View cell=convertView;

        if(cell==null) {
            LayoutInflater inflater=this.context.getLayoutInflater();
            cell = inflater.inflate(this.resource, null);
        }
        ImageView img=(ImageView)cell.findViewById(R.id.ivImage);
        img.setImageResource(this.objects.get(position)); //hiển thị hình ảnh lên cell,position là vị trí của hình đang vẽ

        return cell;
    }
}
