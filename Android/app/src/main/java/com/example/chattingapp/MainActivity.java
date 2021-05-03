package com.example.chattingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText userName, roomNumber;
    Button btnEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        roomNumber = findViewById(R.id.roomNumber);

        btnEntrance = findViewById(R.id.btnEnter);
        btnEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("userName", userName.getText().toString());
                intent.putExtra("roomNumber", roomNumber.getText().toString());
                startActivity(intent);
            }
        });
    }
}