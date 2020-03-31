package com.example.chatdemo.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.request.login.LoginRequest;
import com.example.chatdemo.data.models.response.login.LoginResponse;
import com.example.chatdemo.domain.UsersRepository;

public class LoginViewModel extends ViewModel {

    private UsersRepository repository = UsersRepository.getInstance();
    private MediatorLiveData<LoginResponse> data = new MediatorLiveData<>();

    public void callLogin(LoginRequest login) {
        data.addSource(repository.callLogin(login), loginResponse -> {
            data.postValue(loginResponse);
        });
    }

    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return data;
    }
}
