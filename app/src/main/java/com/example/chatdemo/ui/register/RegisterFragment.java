package com.example.chatdemo.ui.register;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatdemo.R;
import com.example.chatdemo.data.models.request.register.RegisterRequest;
import com.example.chatdemo.data.models.request.register.RegisterRequestModel;
import com.example.chatdemo.databinding.FragmentRegisterBinding;
import com.example.chatdemo.ui.base.BaseFragment;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> {

    private NavController navController;

    @Override
    protected Class<RegisterViewModel> initViewModel() {
        return RegisterViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(RegisterFragment.this);
        binding.setRegister(new RegisterRequest());
        binding.btnRegister.setOnClickListener((v) -> {
            if (isValid()) {
                RegisterRequestModel requestModel = new RegisterRequestModel();
                requestModel.setRegisterRequest(binding.getRegister());
                viewModel.callRegister(requestModel);
            }
        });

        observeData();
    }

    private void observeData() {
        viewModel.getLoginResponseLiveData().observe(getViewLifecycleOwner(), registerResponse -> {
            if (registerResponse != null)
                navController.popBackStack();
        });
        viewModel.isLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoader();
            } else {
                dismissLoader();
            }
        });
    }

    private boolean isValid() {
        boolean isValid = true;
        String username = binding.getRegister().getUsername().trim();
        String email = binding.getRegister().getEmail().trim();
        String password = binding.getRegister().getPassword().trim();
        String confirmPassword = binding.getRegister().getPasswordConfirmation().trim();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            binding.etEmail.setError("Enter valid email");
        }
        if (TextUtils.isEmpty(password)) {
            isValid = false;
            binding.etPassword.setError("Enter valid password");
        }
        if (TextUtils.isEmpty(username)) {
            isValid = false;
            binding.etUsername.setError("Enter valid username");
        }
        if (TextUtils.isEmpty(confirmPassword) || !confirmPassword.equals(password)) {
            isValid = false;
            binding.etConfirmPassword.setError("Enter valid confirm password");
        }

        return isValid;

    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_register;
    }
}
