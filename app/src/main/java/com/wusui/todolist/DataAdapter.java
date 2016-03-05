package com.wusui.todolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fg on 2016/2/26.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder>{
    private Context mContext;
    private static List<String>mDatas;

    // 没被调用啊
    public DataAdapter(Context context,List<String>mDatas){
        mContext = context;
        this.mDatas = mDatas;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.text_view);
        }
    }

    @Override
    public DataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_edit,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final DataAdapter.MyViewHolder holder, final int position) {
        holder.tv.setText(mDatas.get(position));
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();//所以这里出了空指针
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
