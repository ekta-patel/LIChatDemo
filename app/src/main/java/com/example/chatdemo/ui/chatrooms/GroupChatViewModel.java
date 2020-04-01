package com.example.chatdemo.ui.chatrooms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.chatdemo.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.chatdemo.data.models.response.groupchat.GroupChatResponse;
import com.example.chatdemo.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.chatdemo.data.models.response.joinedgroups.JoinedChatRoomResponse;
import com.example.chatdemo.domain.ChatRepository;
import com.example.chatdemo.utils.Event;

import java.util.List;

public class GroupChatViewModel extends ViewModel {

    private ChatRepository repository = ChatRepository.getInstance();
    private MediatorLiveData<List<GroupChatResponse>> data = new MediatorLiveData<>();
    private MediatorLiveData<Event<CreateChatroomResponse>> createChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<JoinedChatRoomResponse>> joinedChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<JoinChatRoomResponse> joinChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public void getJoinedChatRooms() {
        _isLoading.postValue(true);
        joinedChatroomResponseMediatorLiveData.addSource(repository.getJoinedChatRooms(), response -> {
            _isLoading.postValue(false);
            joinedChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public void getChatRooms() {
        _isLoading.postValue(true);
        data.addSource(repository.getGroupChatRooms(), response -> {
            _isLoading.postValue(false);
            data.postValue(response);
        });
    }

    public void createChatRoom(CreateChatRoomRequest request) {
        _isLoading.postValue(true);
        createChatroomResponseMediatorLiveData.addSource(repository.createChatRoom(request), response -> {
            _isLoading.postValue(false);
            createChatroomResponseMediatorLiveData.postValue(new Event<CreateChatroomResponse>(response));
        });
    }

    public void joinChatRoom(int chatroomId) {
        _isLoading.postValue(true);
        joinChatroomResponseMediatorLiveData.addSource(repository.joinChatRoom(chatroomId), response -> {
            _isLoading.postValue(false);
            joinChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public LiveData<List<GroupChatResponse>> getGroupChatResponseLiveData() {
        return data;
    }

    public LiveData<Event<CreateChatroomResponse>> getCreateChatRoomResponseLiveData() {
        return createChatroomResponseMediatorLiveData;
    }

    public LiveData<List<JoinedChatRoomResponse>> getJoinedChatRoomResponseLiveData() {
        return joinedChatroomResponseMediatorLiveData;
    }

    public LiveData<JoinChatRoomResponse> getJoinChatRoomResponseLiveData() {
        return joinChatroomResponseMediatorLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

}
