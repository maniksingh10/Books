package com.manik.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by manik on 04-02-2018.
 */

public class CustomAdapter extends ArrayAdapter<BookObject> {


    public CustomAdapter(Context context, List<BookObject> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.one_book, parent, false);
        }

        BookObject currentBook = getItem(position);


        TextView tvBookName = v.findViewById(R.id.tvBookName);
        tvBookName.setText(currentBook.getmBookName());

        TextView tvAuthor = v.findViewById(R.id.tvAuthor);
        tvAuthor.setText(currentBook.getmAuthorName());

        TextView tvDescription = v.findViewById(R.id.tvDescription);
        tvDescription.setText(currentBook.getmDescription());

        TextView tvCategories = v.findViewById(R.id.tvCategories);
        tvCategories.setText(currentBook.getmCategories());

        RatingBar rb = v.findViewById(R.id.ratingBar);
        rb.setRating((float) currentBook.getmRating());

        TextView tvPages = v.findViewById(R.id.tvPages);
        tvPages.setText(String.valueOf(currentBook.getmPages()));


        return v;
    }
}
