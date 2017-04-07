package com.bamgmk.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raven on 29.03.2017.
 */
public class ImageAdapter extends BaseAdapter {
    public  List<AdapterItem> mItems = new ArrayList<AdapterItem>();
    private final LayoutInflater mInflater;

    public ImageAdapter(Context context,List<PlayerCharacter> heroTeam) {
        mInflater = LayoutInflater.from(context);

        for (PlayerCharacter pc : heroTeam){
            if(pc.isActive){
                mItems.add(new AdapterItem(pc.name,pc.getImageId(),pc));
            }
        }
        for (PlayerCharacter pc : heroTeam){
            mItems.add(new AdapterItem(pc.name,pc.getImageId(),pc));
        }

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public AdapterItem getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        AdapterItem item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }


}
