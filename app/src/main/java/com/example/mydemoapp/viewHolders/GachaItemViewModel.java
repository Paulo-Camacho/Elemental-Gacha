package com.example.mydemoapp.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.GachaItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Thingy for the recycler view
 * @author Nat Chelonis
 * @since 20 - Nov - 2025
 */
public class GachaItemViewModel extends AndroidViewModel {
    private final GachaRepository repository;

    private MutableLiveData<List<Integer>> collectionIDs = new MutableLiveData<>();
    private MediatorLiveData<List<GachaItem>> collectionItems = new MediatorLiveData<>();

    public GachaItemViewModel(Application application){
        super(application);
        repository = GachaRepository.getRepository(application);

    }

    private GachaItem getGachaItemById(Integer id) {
        return repository.getAllPulls().get(id-1);
    }

    public LiveData<List<GachaItem>> getAllItemsByUserID(int userID){
        LiveData<List<Integer>> tempIDs = repository.getConnectionsByUser(userID);
        tempIDs.observeForever(newValue ->{
            collectionIDs.setValue(newValue);
        });

        collectionItems.addSource(collectionIDs, gachaIDs -> {
            List<GachaItem> tempGachaList = new ArrayList<>();
            if(gachaIDs != null){
                for(Integer id : gachaIDs){
                    tempGachaList.add(getGachaItemById(id));
                }
            }
            collectionItems.setValue(tempGachaList);
        });
        return collectionItems;
    }

}
