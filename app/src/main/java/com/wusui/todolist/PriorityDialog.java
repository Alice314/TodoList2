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

//    public interface CustomDialogEventListener{
//        public void customDialogEvent(int id);
//    }
    private Context context;
//    private CustomDialogEventListener customDialogEventListener;

    public PriorityDialog(Context context) {
        super(context);
        this.context = context;
    }
//    public PriorityDialog(Context context,CustomDialogEventListener listener){
//        super(context);
//        this.context = context;
//        customDialogEventListener = listener;
//    }
//    private void bindTextClickEvent(View layout){
//        TextView four = (TextView)layout.findViewById(R.id.four);
//        TextView three = (TextView)layout.findViewById(R.id.three);
//        TextView two = (TextView)layout.findViewById(R.id.two);
//        TextView one = (TextView)layout.findViewById(R.id.one);
//
//        four.setOnClickListener(this);
//        three.setOnClickListener(this);
//        two.setOnClickListener(this);
//        one.setOnClickListener(this);
//    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.item_dialog);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.item_dialog, null);
//        bindTextClickEvent(layout);
//        this.setContentView(layout);
    }
//    @Override
//      public void onClick(View v) {
//        int id = v.getId();
//        int string = -1;
//        switch (id){
//            case R.id.four:
//                string = R.string.four;
//                break;
//            case R.id.three:
//                string = R.string.three;
//                break;
//            case R.id.two:
//                string = R.string.two;
//                break;
//            case R.id.one:
//                string = R.string.one;
//                break;
//        }
//        if (string != -1){
//            customDialogEventListener.customDialogEvent(string);
//        }
//        dismiss();
//    }
}
