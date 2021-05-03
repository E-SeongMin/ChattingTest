package com.example.chattingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {
    Socket mSocket;
    String userName, roomNumber;
    EditText editMessage;
    RecyclerView recyclerView;
    String ServerIP = "http://192.168.200.176:80";
    ChatAdapter adapter;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setConnection();
    }

    public void setConnection() {
        try {
            mSocket = IO.socket(ServerIP);
            Log.d("Socket", "Connection Success : " + mSocket.id());
        } catch(URISyntaxException e) {
            Log.d("Socket", "Connection Fail");
        }

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        roomNumber = intent.getStringExtra("roomNumber");

        adapter = new ChatAdapter(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Button send = findViewById(R.id.btnSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        mSocket.connect();

        mSocket.on(Socket.EVENT_CONNECT, args -> {
           mSocket.emit("enter", gson.toJson(new RoomData(userName, roomNumber)));
        });

        mSocket.on("update", args -> {
           MessageData data = gson.fromJson(args[0].toString(), MessageData.class);
           runOnUiThread(() -> {
               if(data.getType().equals("ENTER") || data.getType().equals("LEFT")) {
                   adapter.addItem(new ChatItem(data.getFrom(), data.getContent(), toDate(data.getSendTime()), ChatType.CENTER_MESSAGE));
                   recyclerView.scrollToPosition(adapter.getItemCount() - 1);
               } else {
                   adapter.addItem(new ChatItem(data.getFrom(), data.getContent(), toDate(data.getSendTime()), ChatType.LEFT_MESSAGE));
                   recyclerView.scrollToPosition(adapter.getItemCount() - 1);
               }
           });
        });
    }

    public void sendMessage() {
        editMessage = findViewById(R.id.editMessage);
        mSocket.emit("newMessage", gson.toJson(new MessageData("MESSAGE", userName, roomNumber, editMessage.getText().toString(), System.currentTimeMillis())));
        adapter.addItem(new ChatItem(userName, editMessage.getText().toString(), toDate(System.currentTimeMillis()), ChatType.RIGHT_MESSAGE));
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        editMessage.setText("");
    }

    public String toDate(long currentMiliis) {
        return new SimpleDateFormat("hh:mm a").format(new Date(currentMiliis));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.emit("left", gson.toJson(new RoomData(userName, roomNumber)));
        mSocket.disconnect();
    }
}

