package com.manik.books;

/**
 * Created by manik on 04-02-2018.
 */

public class BookObject {

    private String mBookName;
    private String mAuthorName;
    private String mDescription;
    private String mCategories;
    private String mUrl;
    private double mRating;
    private int mPages;

    public BookObject(String mBookName, int mPages) {
        this.mBookName = mBookName;
        this.mPages = mPages;
    }

    public BookObject(String mBookName, String mAuthorName, String mDescription, String mCategories, String mUrl, double mRating, int mPages) {
        this.mBookName = mBookName;
        this.mAuthorName = mAuthorName;
        this.mDescription = mDescription;
        this.mCategories = mCategories;
        this.mUrl = mUrl;
        this.mRating = mRating;
        this.mPages = mPages;
    }

    public String getmBookName() {
        return mBookName;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmCategories() {
        return mCategories;
    }

    public String getmUrl() {
        return mUrl;
    }

    public double getmRating() {
        return mRating;
    }

    public int getmPages() {
        return mPages;
    }


}
