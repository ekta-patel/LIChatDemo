package com.example.chatdemo.ui.one2onechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.response.one2onechat.OneToOneChatResponse;
import com.example.chatdemo.domain.ChatRepository;

import java.util.List;

public class OneToOneViewModel extends ViewModel {

    private ChatRepository repository = ChatRepository.getInstance();
    private MediatorLiveData<List<OneToOneChatResponse>> data = new MediatorLiveData<>();

    public void getOneToOneChatRooms() {
        data.addSource(repository.getOneToOneChatRooms(), response -> {
            data.postValue(response);
        });
    }

    public MediatorLiveData<List<OneToOneChatResponse>> getOneToOneChatResponseLiveData() {
        return data;
    }
}
