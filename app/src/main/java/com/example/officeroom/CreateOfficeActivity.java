package com.example.officeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class CreateOfficeActivity extends AppCompatActivity {

    String[] items = {"Institute","Software Farm","Factory"};

    AutoCompleteTextView autoCompleteText;
    ArrayAdapter <String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_office);
        getWindow().setStatusBarColor(ContextCompat.getColor(CreateOfficeActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(CreateOfficeActivity.this,R.color.backgroundColor));
        findAllId();

        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(CreateOfficeActivity.this, "selected "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findAllId() {
        autoCompleteText = findViewById(R.id.createOfficeTypeId);
        arrayAdapter = new ArrayAdapter<>(this,R.layout.list_item,items);
        autoCompleteText.setAdapter(arrayAdapter);
    }
}