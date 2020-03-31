package com.example.chatdemo.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseFragment<T extends ViewDataBinding, V extends ViewModel> extends Fragment {

    protected T binding;
    protected V viewModel;
    protected View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, inflateView(), container, false);
        viewModel = new ViewModelProvider(this).get(initViewModel());
        view = binding.getRoot();
        initFragment();
        return view;
    }

    protected abstract Class<V> initViewModel();

    protected abstract void initFragment();

    protected abstract int inflateView();
}
