package com.example.tinhnguyen.chat;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by phamm on 9/11/2017.
 */

public class CustomAdapterFriends extends BaseAdapter {
    Activity context;
    ArrayList<Friends> list;
    int layout;
    public CustomAdapterFriends(Activity context, ArrayList<Friends> list,int layout) {
        this.context= context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
