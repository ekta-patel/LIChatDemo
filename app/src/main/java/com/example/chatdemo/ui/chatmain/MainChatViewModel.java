package com.example.chatdemo.ui.chatmain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.chatdemo.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.chatdemo.data.models.response.messages.MessagesResponseModel;
import com.example.chatdemo.domain.ChatRepository;

public class MainChatViewModel extends ViewModel {

    private ChatRepository repository = ChatRepository.getInstance();
    private MediatorLiveData<DeleteChatRoomResponse> data = new MediatorLiveData<>();
    private MediatorLiveData<LeaveChatroomResponse> leaveChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<MessagesResponseModel> messagesResponseModelMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public void deleteChatroom(int chatRoomId) {
        _isLoading.postValue(true);
        data.addSource(repository.deleteChatRoom(chatRoomId), response -> {
            data.postValue(response);
        });
    }

    public void leaveChatroom(int chatRoomId) {
        _isLoading.postValue(true);
        leaveChatroomResponseMediatorLiveData.addSource(repository.leaveChatRoom(chatRoomId), leaveChatroomResponse -> {
            _isLoading.postValue(false);
            leaveChatroomResponseMediatorLiveData.postValue(leaveChatroomResponse);
        });
    }

    public LiveData<DeleteChatRoomResponse> getDeleteChatroomLiveData() {
        return data;
    }

    public LiveData<LeaveChatroomResponse> getLeaveChatroomLiveData() {
        return leaveChatroomResponseMediatorLiveData;
    }

    public LiveData<MessagesResponseModel> getMessageResponseLiveData() {
        return messagesResponseModelMediatorLiveData;
    }

    public void getChatRoomMessages(int chatRoomId) {
        _isLoading.postValue(true);
        messagesResponseModelMediatorLiveData.addSource(repository.getChatRoomMessages(chatRoomId), messagesResponseModel -> {
            _isLoading.postValue(false);
            messagesResponseModelMediatorLiveData.postValue(messagesResponseModel);
        });
    }

    public void getUserMessages(int userId) {
        _isLoading.postValue(true);
        messagesResponseModelMediatorLiveData.addSource(repository.getUserMessages(userId), messagesResponseModel -> {
            _isLoading.postValue(false);
            messagesResponseModelMediatorLiveData.postValue(messagesResponseModel);
        });
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
}
