package com.example.chatdemo.ui.one2onechat;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatdemo.R;
import com.example.chatdemo.data.models.response.one2onechat.OneToOneChatResponse;
import com.example.chatdemo.databinding.FragmentOnetooneChatBinding;
import com.example.chatdemo.ui.base.BaseFragment;
import com.example.chatdemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatdemo.ui.home.HomeFragmentDirections;
import com.example.chatdemo.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OneToOneChatFragment extends BaseFragment<FragmentOnetooneChatBinding, OneToOneViewModel> implements RecyclerViewItemClickListener<OneToOneChatResponse> {

    private List<OneToOneChatResponse> responseList;
    private OneToOneChatAdapter adapter;
    private NavController navController;

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(OneToOneChatFragment.this);

        initAdapter();

        observeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getOneToOneChatRooms();
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new OneToOneChatAdapter(responseList, this);
        binding.rvOneToOneChat.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        binding.rvOneToOneChat.setAdapter(adapter);
    }

    private void observeData() {
        viewModel.getOneToOneChatResponseLiveData().observe(this, resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_onetoone_chat;
    }

    @Override
    protected Class<OneToOneViewModel> initViewModel() {
        return OneToOneViewModel.class;
    }

    @Override
    public void onItemClick(View v, OneToOneChatResponse data, int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.BundleKeys.IS_GROUP, false);
        bundle.putString(Constants.BundleKeys.CHATROOM_NAME, data.getUsername());
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToMainChatFragment(bundle);
        navController.navigate(directions);
    }
}
