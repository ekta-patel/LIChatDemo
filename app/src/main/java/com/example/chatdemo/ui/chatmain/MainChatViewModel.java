package com.example.chatdemo.ui.chatmain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.chatdemo.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.chatdemo.domain.ChatRepository;

public class MainChatViewModel extends ViewModel {

    private ChatRepository repository = ChatRepository.getInstance();
    private MediatorLiveData<DeleteChatRoomResponse> data = new MediatorLiveData<>();
    private MediatorLiveData<LeaveChatroomResponse> leaveChatroomResponseMediatorLiveData = new MediatorLiveData<>();

    public void deleteChatroom(int chatRoomId) {
        data.addSource(repository.deleteChatRoom(chatRoomId), response -> {
            data.postValue(response);
        });
    }

    public void leaveChatroom(int chatRoomId) {
        leaveChatroomResponseMediatorLiveData.addSource(repository.leaveChatRoom(chatRoomId), leaveChatroomResponse -> leaveChatroomResponseMediatorLiveData.postValue(leaveChatroomResponse));
    }

    public LiveData<DeleteChatRoomResponse> getDeleteChatroomLiveData() {
        return data;
    }

    public LiveData<LeaveChatroomResponse> getLeaveChatroomLiveData() {
        return leaveChatroomResponseMediatorLiveData;
    }

}
