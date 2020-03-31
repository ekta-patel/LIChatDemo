package com.example.chatdemo.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatdemo.data.models.request.register.RegisterRequest;
import com.example.chatdemo.data.models.request.register.RegisterRequestModel;
import com.example.chatdemo.data.models.response.register.RegisterResponse;
import com.example.chatdemo.domain.UsersRepository;

public class RegisterViewModel extends ViewModel {

    private UsersRepository repository = UsersRepository.getInstance();
    private MediatorLiveData<RegisterResponse> data = new MediatorLiveData<>();

    public void callRegister(RegisterRequestModel register) {
        data.addSource(repository.callRegister(register), registerResponse -> {
            data.postValue(registerResponse);
        });
    }

    public LiveData<RegisterResponse> getLoginResponseLiveData() {
        return data;
    }
}
