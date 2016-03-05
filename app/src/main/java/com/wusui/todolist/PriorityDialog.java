package com.wusui.todolist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fg on 2016/2/28.
 */
public class PriorityDialog extends Dialog {


    private Context context;


    public PriorityDialog(Context context) {
        super(context);
        this.context = context;
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.item_dialog);

    }

}
