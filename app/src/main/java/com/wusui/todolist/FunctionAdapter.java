package com.wusui.todolist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fg on 2016/2/26.
 */
public class FunctionAdapter extends ArrayAdapter<Function>{

    private int resourceId;

    public FunctionAdapter(Context context, int textViewResourceId, List<Function> objects) {
        super(context,  textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        Function fun = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView funName = (TextView)view.findViewById(R.id.fun_name);
        ImageView funImage = (ImageView)view.findViewById(R.id.fun_image);

        funName.setText(fun.getName());
        funImage.setImageResource(fun.getImageId());

        return view;

    }
}
