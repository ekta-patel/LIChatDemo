package com.example.chatdemo.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.request.logout.LogoutRequest;
import com.example.chatdemo.domain.UsersRepository;

public class HomeViewModel extends ViewModel {

    private UsersRepository repository = UsersRepository.getInstance();
    private MediatorLiveData<String> data = new MediatorLiveData<>();

    public void logout(LogoutRequest request) {
        data.addSource(repository.callLogout(request), loginResponse -> {
            data.postValue(loginResponse);
        });
    }

    public LiveData<String> getLogoutResponseLiveData() {
        return data;
    }
}
