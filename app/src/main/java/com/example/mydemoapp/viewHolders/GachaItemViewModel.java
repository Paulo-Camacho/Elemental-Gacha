package com.example.mydemoapp.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.GachaItem;

import java.util.List;

/**
 * Thingy for the recycler view
 * @author Nat Chelonis
 * @since 20 - Nov - 2025
 */
public class GachaItemViewModel extends AndroidViewModel {
    private final GachaRepository repository;

    public GachaItemViewModel(Application application){
        super(application);
        repository = GachaRepository.getRepository(application);
    }

    public LiveData<List<GachaItem>> getAllItemsByUserID(int userID){
        return repository.getAllPullsLiveData();
    }

}
