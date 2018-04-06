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

public class profile_item_adapter extends ArrayAdapter<ProfileItem> {
    private Activity context;
    private int resource;
    private List<ProfileItem> list_item_1;
    //private List<item_names> list_item_2;
    //private String mUserId;

    public profile_item_adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ProfileItem> ls) {
        super(context, resource, ls);
        this.context = context;
        this.resource = resource;
        list_item_1 = ls;
        //mUserId = _mUserId;
    }
    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource,null);


        TextView TextView5 = (TextView) v.findViewById(R.id.text_item1);
        TextView5.setText(list_item_1.get(position).getName());

        // image
        ImageView imageView = (ImageView) v.findViewById(R.id.profile_image);
        Glide.with(context).load(list_item_1.get(position).getImages()).into(imageView);
        //imageView.setImageBitmap(currentlist.getImages(b));
        //}
        return v;
    }
}
