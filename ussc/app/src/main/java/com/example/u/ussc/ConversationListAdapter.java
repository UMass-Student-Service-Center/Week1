package com.example.u.ussc;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.ArrayList;


public class ConversationListAdapter extends ArrayAdapter<ConversationReferenceItem>{
    private Activity context;
    private int resource;
    private List<ConversationReferenceItem> listItem1;

    public ConversationListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<ConversationReferenceItem> li) {
        super(context, resource, li);
        this.context = context;
        this.resource = resource;
        listItem1 = li;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource,null);

        TextView TextView1 = (TextView) v.findViewById(R.id.members_text_view);
        TextView1.setText(listItem1.get(position).getMuserId1());

        TextView TextView2 = (TextView) v.findViewById(R.id.last_message_text_view);
        TextView2.setText(listItem1.get(position).getLastMessage());

        TextView TextView3 = (TextView) v.findViewById(R.id.last_message_time_text_view);
        TextView3.setText(listItem1.get(position).getLastMessage());

        // image
        de.hdodenhof.circleimageview.CircleImageView imageView = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.profile_image);
        Glide.with(context).load(listItem1.get(position).getMuser1Image()).into(imageView);

        return v;
    }

}
