package com.example.chatdemo.ui.chatrooms.all;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatdemo.R;
import com.example.chatdemo.data.models.response.groupchat.GroupChatResponse;
import com.example.chatdemo.databinding.FragmentGroupChatBinding;
import com.example.chatdemo.ui.base.BaseFragment;
import com.example.chatdemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatdemo.ui.chatrooms.GroupChatViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupChatRoomsFragment extends BaseFragment<FragmentGroupChatBinding, GroupChatViewModel> implements RecyclerViewItemClickListener<GroupChatResponse> {

    private List<GroupChatResponse> responseList;
    private GroupChatAdapter adapter;
    private NavController navController;

    @Override
    protected Class<GroupChatViewModel> initViewModel() {
        return GroupChatViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(GroupChatRoomsFragment.this);
        viewModel.getChatRooms();
        initAdapter();
        observeData();
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new GroupChatAdapter(responseList, this);
        binding.rvAllGroupChat.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        binding.rvAllGroupChat.setAdapter(adapter);
    }

    private void observeData() {
        viewModel.getGroupChatResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.getJoinChatRoomResponseLiveData().observe(getViewLifecycleOwner(), joinChatRoomResponse -> {
            if (joinChatRoomResponse != null) {
                Toast.makeText(getContext(), "Successfully joined!", Toast.LENGTH_LONG).show();
                navController.navigateUp();
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

    @Override
    protected int inflateView() {
        return R.layout.fragment_group_chat;
    }

    @Override
    public void onItemClick(View v, GroupChatResponse data, int position) {
        viewModel.joinChatRoom(data.getId());
    }
}
