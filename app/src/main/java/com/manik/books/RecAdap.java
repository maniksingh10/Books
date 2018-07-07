package com.manik.books;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecAdap extends RecyclerView.Adapter<RecAdap.ViewHolder>  {

    private List<BookObject> list;
    private Context context;

    public RecAdap(List<BookObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_book,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       BookObject bookObject =list.get(position);
       holder.tvBookName.setText(bookObject.getmBookName());
    }

    @Override
    public int getItemCount() {


        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBookName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBookName=itemView.findViewById(R.id.tvBookName);
        }
    }
}
