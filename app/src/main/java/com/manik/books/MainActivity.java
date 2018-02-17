package com.manik.books;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Sends Intent to Display class with the String of book searched
    public void bunSearch(View view) {
        EditText ed1 = findViewById(R.id.etSearch);
        if (ed1.getText().toString().isEmpty()) {
            ed1.setText(R.string.player1);
        }
        String p1 = ed1.getText().toString().trim();

        Intent i = new Intent(MainActivity.this, Display.class);
        i.putExtra("search", p1);
        finish();
        startActivity(i);
    }
}
