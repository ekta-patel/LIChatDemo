package com.example.chatdemo.ui.chatrooms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.chatdemo.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.chatdemo.data.models.response.groupchat.GroupChatResponse;
import com.example.chatdemo.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.chatdemo.data.models.response.joinedgroups.JoinedChatRoomResponse;
import com.example.chatdemo.domain.ChatRepository;

import java.util.List;

public class GroupChatViewModel extends ViewModel {

    private ChatRepository repository = ChatRepository.getInstance();
    private MediatorLiveData<List<GroupChatResponse>> data = new MediatorLiveData<>();
    private MediatorLiveData<CreateChatroomResponse> createChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<JoinedChatRoomResponse>> joinedChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<JoinChatRoomResponse> joinChatroomResponseMediatorLiveData = new MediatorLiveData<>();

    public void getJoinedChatRooms() {
        joinedChatroomResponseMediatorLiveData.addSource(repository.getJoinedChatRooms(), response -> {
            joinedChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public void getChatRooms() {
        data.addSource(repository.getGroupChatRooms(), response -> {
            data.postValue(response);
        });
    }

    public void createChatRoom(CreateChatRoomRequest request) {
        createChatroomResponseMediatorLiveData.addSource(repository.createChatRoom(request), response -> {
            createChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public void joinChatRoom(int chatroomId) {
        joinChatroomResponseMediatorLiveData.addSource(repository.joinChatRoom(chatroomId), response -> {
            joinChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public LiveData<List<GroupChatResponse>> getGroupChatResponseLiveData() {
        return data;
    }

    public LiveData<CreateChatroomResponse> getCreateChatRoomResponseLiveData() {
        return createChatroomResponseMediatorLiveData;
    }

    public LiveData<List<JoinedChatRoomResponse>> getJoinedChatRoomResponseLiveData() {
        return joinedChatroomResponseMediatorLiveData;
    }

    public LiveData<JoinChatRoomResponse> getJoinChatRoomResponseLiveData() {
        return joinChatroomResponseMediatorLiveData;
    }

}
