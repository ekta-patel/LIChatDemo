package com.example.chatdemo.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.request.login.LoginRequest;
import com.example.chatdemo.data.models.response.login.LoginResponse;
import com.example.chatdemo.domain.UsersRepository;

public class LoginViewModel extends ViewModel {

    private UsersRepository repository = UsersRepository.getInstance();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private MediatorLiveData<LoginResponse> data = new MediatorLiveData<>();


    public void callLogin(LoginRequest login) {
        _isLoading.postValue(true);
        data.addSource(repository.callLogin(login), loginResponse -> {
            _isLoading.postValue(false);
            data.postValue(loginResponse);
        });
    }

    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
}
