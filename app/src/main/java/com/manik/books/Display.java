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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manik on 04-02-2018.
 */

public class Display extends MainActivity  {

    public static final String TAG ="HEREEEEEEEEEEEEEEE" ;
    private TextView mEmptyStateTextView;
    RecyclerView recyclerView;
    List<BookObject> list;

    RecAdap recAdap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allbooks);


        recyclerView = findViewById(R.id.all_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        for(int i =1; i<200;i++){
            BookObject bookObject = new BookObject("hello", i);
            list.add(bookObject);
        }


        recAdap = new RecAdap(list, getApplicationContext());
        recyclerView.setAdapter(recAdap);




//        loadData();

//        allBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                BookObject currentBook = mCustomAdapter.getItem(i);
//
//                Uri url = Uri.parse(currentBook.getmUrl());
//
//                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
//                intentBuilder.setToolbarColor(ContextCompat.getColor(Display.this, R.color.colorPrimary));
//                intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(Display.this, R.color.colorPrimaryDark));
//                intentBuilder.setStartAnimations(Display.this, R.anim.slide_in_right, R.anim.slide_out_left);
//                intentBuilder.setExitAnimations(Display.this, android.R.anim.slide_in_left,
//                        android.R.anim.slide_out_right);
//                CustomTabsIntent customTabsIntent = intentBuilder.build();
//
//                customTabsIntent.launchUrl(Display.this, url);
//            }
//        });


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.


            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible


            // Update empty state with no connection error message
            mEmptyStateTextView.setText("No Internet Connection");
        }
    }


//    @Override
//    public Loader<List<BookObject>> onCreateLoader(int i, Bundle bundle) {
//        return new CusLoader(this, addSearch());
//    }
//
//    @Override
//    public void onLoadFinished(Loader<List<BookObject>> loader, List<BookObject> bookObjects) {
//        mCustomAdapter.clear();
//        View loadingIndicator = findViewById(R.id.loading_spinner);
//        loadingIndicator.setVisibility(View.GONE);
//
//        mEmptyStateTextView.setText(R.string.nothing);
//
//        if (bookObjects != null && !bookObjects.isEmpty()) {
//            mCustomAdapter.addAll(bookObjects);
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<List<BookObject>> loader) {
//        mCustomAdapter.clear();
//    }


    private String addSearch() {
        Bundle bu = getIntent().getExtras();
        String a = bu.getString("search");
        setTitle(a);
        String b = "https://www.googleapis.com/books/v1/volumes?q=" + a + "&maxResults=10";
        return b;
    }

    public void onBackPressed() {
        Intent intent = new Intent(Display.this, MainActivity.class);
        finish();
        startActivity(intent);
    }





    private void loadData() {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, addSearch(),
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject base = new JSONObject(response);

                            JSONArray items = base.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {

                                JSONObject curJ = items.getJSONObject(i);


                                JSONObject volumeInfo = curJ.getJSONObject("volumeInfo");
                                String jTitle = volumeInfo.getString("title");

                                String jDispriction = "";
                                if (volumeInfo.has("description")) {
                                    jDispriction = volumeInfo.getString("description");
                                }

                                int jPages = 0;
                                if (volumeInfo.has("pageCount")) {
                                    jPages = volumeInfo.getInt("pageCount");
                                }

                                double jRating = 0.0;
                                if (volumeInfo.has("averageRating")) {
                                    jRating = volumeInfo.getDouble("averageRating");
                                }
                                String jUrl = "";
                                if (volumeInfo.has("previewLink")) {
                                    jUrl = volumeInfo.getString("previewLink");
                                }

                                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                                String jAuthor = authorArray.getString(0);

                                JSONArray catArray = volumeInfo.getJSONArray("categories");

                                String jCategories = "";
                                if (catArray.length() != 0) {
                                    jCategories = catArray.getString(0);
                                }
                                Log.d(TAG, "onResponse: "+jTitle);
                                BookObject book = new BookObject(jTitle, jAuthor, jDispriction, jCategories, jUrl, jRating, jPages);
                                list.add(book);
                            }

                            recAdap = new RecAdap(list, getApplicationContext());
                            recyclerView.setAdapter(recAdap);

                        } catch (JSONException e) {
                            Log.e("QueryUtils", "Problem parsing the JSON results", e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }






}
