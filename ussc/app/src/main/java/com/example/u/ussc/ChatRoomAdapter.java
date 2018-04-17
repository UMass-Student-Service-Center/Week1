package com.example.u.ussc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomAdapter extends BaseAdapter {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUserId;
    public static ArrayList<MessageItem> messageList;
    private Context context;
    private LayoutInflater inflater;

    public ChatRoomAdapter(ArrayList<MessageItem> messageList, Context context) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        this.messageList = messageList;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        MessageItem mi = messageList.get(position);

        if(mi.getSenderId().equals(mUserId)){
            //v = inflater.inflate(R.layout.activity_message_sent, null);
            v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.activity_message_sent, viewGroup, false);
        }else if (!mi.getSenderId().equals(mUserId)){
            //v = inflater.inflate(R.layout.activity_message_received, null);
            v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.activity_message_received, viewGroup, false);
        }

        TextView senderName = (TextView)v.findViewById(R.id.senderName);
        BubbleTextView bubbleTextView = (BubbleTextView)v.findViewById(R.id.bubblechat);
        if(messageList.get(position).getSenderId().equals(mUserId)){
            senderName.setText(ConversationsActivity.sendUser);
        }else {
            senderName.setText(ConversationsActivity.receiveUser);
        }
        bubbleTextView.setText(messageList.get(position).getMessage());

        return v;
    }
}
