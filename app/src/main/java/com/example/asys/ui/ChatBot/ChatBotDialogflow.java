package com.example.asys.ui.ChatBot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.asys.Models.Message;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.asys.Adapters.ChatAdapter;
import com.example.asys.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatBotDialogflow extends AppCompatActivity {

    RecyclerView chatView;
    ChatAdapter chatAdapter;
    List<Message> messageList = new ArrayList<>();
    EditText editMessage;
    ImageButton btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_dialogflow);

        chatView = (RecyclerView) findViewById(R.id.chatView);
        editMessage = (EditText) findViewById(R.id.editMessage);
        btnSend = (ImageButton) findViewById(R.id.btnSend);

        messageList.add(new Message("hello", false));
        messageList.add(new Message("hey buddy", true));
        messageList.add(new Message("how are you", false));
        messageList.add(new Message("I am fine. wbu?", true));
        messageList.add(new Message("Good. How is health?", false));
        messageList.add(new Message("Nice yaar.", true));
        messageList.add(new Message("Good to hear from you", false));
        messageList.add(new Message("Good to hear from you", true));
        messageList.add(new Message("Good to hear from you", false));
        messageList.add(new Message("Good to hear from you", true));
        messageList.add(new Message("Good to hear from you", false));
        messageList.add(new Message("Good to hear from you", true));
        messageList.add(new Message("Good to hear from you", false));
        messageList.add(new Message("Good to hear from you", true));
        messageList.add(new Message("Good to hear from you", false));

        chatAdapter = new ChatAdapter(messageList, this);
        chatView.setAdapter(chatAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editMessage.getText().toString();
                if (message != null && !message.isEmpty()) {
                    messageList .add(new Message(message, false));
                    editMessage.setText("");
                    sendMessageBot();
                    Objects.requireNonNull(chatView.getAdapter()).notifyDataSetChanged();
                    Objects.requireNonNull(chatView.getLayoutManager())
                            .scrollToPosition(messageList.size()-1);
                }
            }
        });
    }

    private void sendMessageBot() {

    }
}