package com.example.chatdemo.domain;

import androidx.lifecycle.MutableLiveData;

import com.example.chatdemo.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.chatdemo.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.chatdemo.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.chatdemo.data.models.response.groupchat.GroupChatResponse;
import com.example.chatdemo.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.chatdemo.data.models.response.joinedgroups.JoinedChatRoomResponse;
import com.example.chatdemo.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.chatdemo.data.models.response.one2onechat.OneToOneChatResponse;
import com.example.chatdemo.data.remote.ApiClient;
import com.example.chatdemo.data.remote.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {

    private static ChatRepository INSTANCE;
    private ApiService apiService = ApiClient.getApiService();

    public static ChatRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatRepository();
        }
        return INSTANCE;
    }

    public MutableLiveData<List<OneToOneChatResponse>> getOneToOneChatRooms() {
        MutableLiveData<List<OneToOneChatResponse>> data = new MutableLiveData<>();
        apiService.getOneToOneChatRooms().enqueue(new Callback<List<OneToOneChatResponse>>() {
            @Override
            public void onResponse(Call<List<OneToOneChatResponse>> call, Response<List<OneToOneChatResponse>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<OneToOneChatResponse>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<GroupChatResponse>> getGroupChatRooms() {
        MutableLiveData<List<GroupChatResponse>> data = new MutableLiveData<>();
        apiService.getGroupChatRooms().enqueue(new Callback<List<GroupChatResponse>>() {
            @Override
            public void onResponse(Call<List<GroupChatResponse>> call, Response<List<GroupChatResponse>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GroupChatResponse>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<CreateChatroomResponse> createChatRoom(CreateChatRoomRequest request) {
        MutableLiveData<CreateChatroomResponse> data = new MutableLiveData<>();
        apiService.createChatRoom(request).enqueue(new Callback<CreateChatroomResponse>() {
            @Override
            public void onResponse(Call<CreateChatroomResponse> call, Response<CreateChatroomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<CreateChatroomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<DeleteChatRoomResponse> deleteChatRoom(int chatRoomId) {
        MutableLiveData<DeleteChatRoomResponse> data = new MutableLiveData<>();
        apiService.deleteChatRoom(chatRoomId).enqueue(new Callback<DeleteChatRoomResponse>() {
            @Override
            public void onResponse(Call<DeleteChatRoomResponse> call, Response<DeleteChatRoomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<DeleteChatRoomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<JoinedChatRoomResponse>> getJoinedChatRooms() {
        MutableLiveData<List<JoinedChatRoomResponse>> data = new MutableLiveData<>();
        apiService.getJoinedChatRooms().enqueue(new Callback<List<JoinedChatRoomResponse>>() {
            @Override
            public void onResponse(Call<List<JoinedChatRoomResponse>> call, Response<List<JoinedChatRoomResponse>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<JoinedChatRoomResponse>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<JoinChatRoomResponse> joinChatRoom(int chatroomId) {
        MutableLiveData<JoinChatRoomResponse> data = new MutableLiveData<>();
        apiService.joinChatRoom(chatroomId).enqueue(new Callback<JoinChatRoomResponse>() {
            @Override
            public void onResponse(Call<JoinChatRoomResponse> call, Response<JoinChatRoomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<JoinChatRoomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<LeaveChatroomResponse> leaveChatRoom(int chatRoomId) {
        MutableLiveData<LeaveChatroomResponse> data = new MutableLiveData<>();
        apiService.leaveChatRoom(chatRoomId).enqueue(new Callback<LeaveChatroomResponse>() {
            @Override
            public void onResponse(Call<LeaveChatroomResponse> call, Response<LeaveChatroomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LeaveChatroomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

}
