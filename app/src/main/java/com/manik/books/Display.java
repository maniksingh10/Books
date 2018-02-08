package com.manik.books;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manik on 04-02-2018.
 */

public class Display extends MainActivity implements LoaderCallbacks<List<BookObject>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    CustomAdapter mCustomAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allbooks);


        ListView allBooks = findViewById(R.id.all_books);

        mCustomAdapter = new CustomAdapter(this, new ArrayList<BookObject>());

        allBooks.setAdapter(mCustomAdapter);

        allBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookObject currentBook = mCustomAdapter.getItem(i);

                Uri url = Uri.parse(currentBook.getmUrl());

                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                intentBuilder.setToolbarColor(ContextCompat.getColor(Display.this, R.color.colorPrimary));
                intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(Display.this, R.color.colorPrimaryDark));
                intentBuilder.setStartAnimations(Display.this, R.anim.slide_in_right, R.anim.slide_out_left);
                intentBuilder.setExitAnimations(Display.this, android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                CustomTabsIntent customTabsIntent = intentBuilder.build();

                customTabsIntent.launchUrl(Display.this, url);
            }
        });


        mEmptyStateTextView = findViewById(R.id.empty_view);
        allBooks.setEmptyView(mEmptyStateTextView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText("No Internet Connection");
        }
    }


    @Override
    public Loader<List<BookObject>> onCreateLoader(int i, Bundle bundle) {
        return new CusLoader(this, addSearch());
    }

    @Override
    public void onLoadFinished(Loader<List<BookObject>> loader, List<BookObject> bookObjects) {
        mCustomAdapter.clear();
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.nothing);

        if (bookObjects != null && !bookObjects.isEmpty()) {
            mCustomAdapter.addAll(bookObjects);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookObject>> loader) {
        mCustomAdapter.clear();
    }


    private String addSearch() {
        Bundle bu = getIntent().getExtras();
        String a = bu.getString("search");
        setTitle(a);
        String b = "https://www.googleapis.com/books/v1/volumes?q=" + a + "&maxResults=40";
        return b;
    }

    public void onBackPressed() {
        Intent intent = new Intent(Display.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
