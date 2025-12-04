package com.example.mydemoapp.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.GachaItem;

/**
 * Thingy for the recycler
 * @author Nat Chelonis
 * @since 20 - Nov - 2025
 */
public class GachaAdapter extends ListAdapter<GachaItem,GachaItemViewHolder> {

    public GachaAdapter(@NonNull DiffUtil.ItemCallback<GachaItem> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public GachaItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return GachaItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GachaItemViewHolder holder, int position){
        GachaItem current = getItem(position);
        //this isn't going to work right
        //TODO: make sure the images are all added
        holder.bind(current.toString());
    }

    public static class GachaItemDiff extends DiffUtil.ItemCallback<GachaItem>{
        @Override
        public boolean areItemsTheSame(@NonNull GachaItem oldItem, @NonNull GachaItem newItem){
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GachaItem oldItem, @NonNull GachaItem newItem){
            return oldItem.equals(newItem);
        }
    }



}
