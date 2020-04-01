package com.example.chatdemo.ui.login;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatdemo.R;
import com.example.chatdemo.data.local.AppSharedPrefManager;
import com.example.chatdemo.data.models.request.login.LoginRequest;
import com.example.chatdemo.databinding.FragmentLoginBinding;
import com.example.chatdemo.ui.base.BaseFragment;
import com.example.chatdemo.utils.Constants;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    private NavController navController;

    @Override
    protected Class<LoginViewModel> initViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(LoginFragment.this);
        binding.setLogin(new LoginRequest());
        binding.btnLogin.setOnClickListener((v) -> {
            if (isValid()) viewModel.callLogin(binding.getLogin());
        });
        binding.btnRegister.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));

        observeData();
    }

    private void observeData() {

        viewModel.getLoginResponseLiveData().observe(getViewLifecycleOwner(), loginResponse -> {
            if (loginResponse != null) {
                AppSharedPrefManager.setLoginData(Constants.SharedPrefKeys.LOGIN_RESPONSE, loginResponse);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build();
                navController.navigate(R.id.homeFragment, null, navOptions);
            }
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
        String email = binding.getLogin().getEmail().trim();
        String password = binding.getLogin().getPassword().trim();
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            binding.etEmail.setError("Enter valid email");
        }
        if (TextUtils.isEmpty(password)) {
            isValid = false;
            binding.etPassword.setError("Enter valid password");
        }

        return isValid;
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_login;
    }

}
