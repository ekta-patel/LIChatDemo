package com.example.chatdemo.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.request.register.RegisterRequestModel;
import com.example.chatdemo.data.models.response.register.RegisterResponse;
import com.example.chatdemo.domain.UsersRepository;

public class RegisterViewModel extends ViewModel {

    private UsersRepository repository = UsersRepository.getInstance();
    private MediatorLiveData<RegisterResponse> data = new MediatorLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public void callRegister(RegisterRequestModel register) {
        _isLoading.postValue(true);
        data.addSource(repository.callRegister(register), registerResponse -> {
            _isLoading.postValue(false);
            data.postValue(registerResponse);
        });
    }

    public LiveData<RegisterResponse> getLoginResponseLiveData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
}
