package com.getbase.floatingactionbutton.sample;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Yeshy on 3/6/2016.
 */
class yourAdapter extends BaseAdapter {

    Context context;
    String[] data;
    int[] count;
    private static LayoutInflater inflater = null;

    public yourAdapter(Context context, String[] data, int[] count) {

        this.context = context;
        this.data = data;
        this.count = count;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public yourAdapter(Context context, String[] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.layout, null);
        }
        TextView text = (TextView) vi.findViewById(R.id.text);
        LinearLayout layout = (LinearLayout) vi.findViewById(R.id.linearLayout111);
        text.setText(data[position]);
        if(count != null) {
            if(count.length == data.length) {
                if(count[position] == 1) {
                    layout.setBackgroundColor(Color.parseColor("#a6d785"));
                }
            }

        }
        return vi;
    }
}
