package com.example.mydemoapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mydemoapp.R;

/**
 * thingy for the recycler
 * @author Nat Chelonis
 * @since 20 - Nov - 2025
 */
public class GachaItemViewHolder extends RecyclerView.ViewHolder {
    private final ImageView gachaViewItem;

    private GachaItemViewHolder(View gachaView){
        super(gachaView);
        gachaViewItem = gachaView.findViewById(R.id.gachaItemImageView);
    }

    public void bind(String text){
        //TODO: figure out how to make the image show up with the url
    }

    static GachaItemViewHolder create(ViewGroup parent){
        //parent is a reference to the display object where all the text views will be rendered in
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gacha_recycler_item,parent,false);
        return new GachaItemViewHolder(view);
    }
}
