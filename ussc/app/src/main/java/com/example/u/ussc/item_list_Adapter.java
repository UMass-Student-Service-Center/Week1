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

public class item_list_Adapter extends ArrayAdapter<item_names> {
    private Activity context;
    private int resource;
    private List<item_names> list_item_1;
    //private String mUserId;

    public item_list_Adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<item_names> ls) {
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


        TextView TextView1 = (TextView) v.findViewById(R.id.title_1);
        TextView1.setText(list_item_1.get(position).getTitle());

        // isbn
        //TextView TextView2 = (TextView) v.findViewById(R.id.);
        //TextView2.setText(list_item.get(position).getIsbn());

        // price
        TextView TextView3 = (TextView) v.findViewById(R.id.price_1);
        TextView3.setText(list_item_1.get(position).getPrice());

        // description
        TextView TextView4 = (TextView) v.findViewById(R.id.description_1);
        TextView4.setText(list_item_1.get(position).getDescr());

        TextView TextView5 = (TextView) v.findViewById(R.id.time_1);
        TextView5.setText(list_item_1.get(position).getTime());

        // image
        ImageView imageView = (ImageView) v.findViewById(R.id.image_1);
        Glide.with(context).load(list_item_1.get(position).getImage()).into(imageView);
        //imageView.setImageBitmap(currentlist.getImages(b));
        //}
        return v;
    }
}