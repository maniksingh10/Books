package com.manik.books;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by manik on 04-02-2018.
 */

public class CusLoader extends AsyncTaskLoader<List<BookObject>> {

    private String mUrl;

    public CusLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<BookObject> loadInBackground() {
        if (mUrl == null) {

            return null;
        }

        List<BookObject> list = NetworkU.fetchBooks(mUrl);
        return list;
    }
}

