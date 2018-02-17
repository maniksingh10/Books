package com.manik.books;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.manik.books.MainActivity.LOG_TAG;

/**
 * Created by manik on 04-02-2018.
 */

public final class NetworkU {

    private NetworkU() {
    }

    public static List<BookObject> extractFromJSON(String bookJSON) {

        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<BookObject> bookData = new ArrayList<>();

        try {
            JSONObject base = new JSONObject(bookJSON);

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

                BookObject book = new BookObject(jTitle, jAuthor, jDispriction, jCategories, jUrl, jRating, jPages);
                bookData.add(book);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        return bookData;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream ins = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                ins = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(ins);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<BookObject> fetchBooks(String requestUrl) {


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<BookObject> earthquakes = extractFromJSON(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }


}
