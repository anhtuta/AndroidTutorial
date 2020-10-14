package com.bkhn.anhtu.fragmentsdemo6;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by anhtu on 18/03/2017.
 */

public class Setting_ListFragment extends ListFragment {
    String [] listSetting = new String[] {"type", "size", "color"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting_list_fragment, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listSetting);
        setListAdapter(adapter);

        return v;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "shit!", Toast.LENGTH_SHORT).show();
    }
}
