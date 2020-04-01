package com.example.chatdemo.ui.one2onechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.response.one2onechat.OneToOneChatResponse;
import com.example.chatdemo.domain.ChatRepository;

import java.util.List;

public class OneToOneViewModel extends ViewModel {

    private ChatRepository repository = ChatRepository.getInstance();
    private MediatorLiveData<List<OneToOneChatResponse>> data = new MediatorLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public void getOneToOneChatRooms() {
        _isLoading.postValue(true);
        data.addSource(repository.getOneToOneChatRooms(), response -> {
            _isLoading.postValue(false);
            data.postValue(response);
        });
    }

    public MediatorLiveData<List<OneToOneChatResponse>> getOneToOneChatResponseLiveData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
}
