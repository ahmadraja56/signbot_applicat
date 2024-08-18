package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LearningActivity extends AppCompatActivity {

    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        listview = findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(LearningActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.letters));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LearningActivity.this, LearningLettersActivity.class);
                intent.putExtra("LetterName", listview.getItemAtPosition(i).toString());
                startActivity(intent);
                finish();
            }
        });

        listview.setAdapter(adapter);


    }
}