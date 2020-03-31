package com.example.chatdemo.ui.chatrooms.joined;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatdemo.R;
import com.example.chatdemo.data.models.request.createchatroom.Chatroom;
import com.example.chatdemo.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.chatdemo.data.models.response.joinedgroups.JoinedChatRoomResponse;
import com.example.chatdemo.databinding.FragmentJoinedGroupChatBinding;
import com.example.chatdemo.databinding.LayoutDialogCreateroomBinding;
import com.example.chatdemo.ui.base.BaseFragment;
import com.example.chatdemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatdemo.ui.chatrooms.GroupChatViewModel;
import com.example.chatdemo.ui.home.HomeFragmentDirections;
import com.example.chatdemo.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JoinedChatRoomsFragment extends BaseFragment<FragmentJoinedGroupChatBinding, GroupChatViewModel> implements RecyclerViewItemClickListener<JoinedChatRoomResponse> {

    private List<JoinedChatRoomResponse> responseList;
    private JoinedGroupAdapter adapter;
    private NavController navController;

    @Override
    protected Class<GroupChatViewModel> initViewModel() {
        return GroupChatViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(JoinedChatRoomsFragment.this);
        binding.btnCreateChatRoom.setOnClickListener((v) -> openCreateChatDialog().show());
        binding.btnViewChatRoom.setOnClickListener((v) -> {
            navController.navigate(R.id.groupChatRoomsFragment);
        });
        initAdapter();

        observeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getJoinedChatRooms();
    }

    private AlertDialog openCreateChatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        LayoutDialogCreateroomBinding dialogCreateroomBinding = DataBindingUtil.inflate(inflater, R.layout.layout_dialog_createroom, null, false);
        dialogCreateroomBinding.setCreateChatroom(new Chatroom());
        builder.setView(dialogCreateroomBinding.getRoot())
                .setPositiveButton("Create", (dialog, id) -> {
                    String chatroomName = dialogCreateroomBinding.getCreateChatroom().getName();
                    if (!chatroomName.isEmpty()) {
                        CreateChatRoomRequest request = new CreateChatRoomRequest();
                        request.setChatroom(dialogCreateroomBinding.getCreateChatroom());
                        viewModel.createChatRoom(request);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        return builder.create();
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new JoinedGroupAdapter(responseList, this);
        binding.rvJoinedGroupChat.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        binding.rvJoinedGroupChat.setAdapter(adapter);
    }

    private void observeData() {
        viewModel.getJoinedChatRoomResponseLiveData().observe(this, resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.getCreateChatRoomResponseLiveData().observe(this, createChatroomResponse -> {
            if (createChatroomResponse != null) {
                if (createChatroomResponse.getStatus().equalsIgnoreCase("ok")) {
                    Toast.makeText(getContext(), "Chatroom created successfully", Toast.LENGTH_LONG).show();
                    viewModel.getJoinedChatRooms();
                }
            }
        });
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_joined_group_chat;
    }

    @Override
    public void onItemClick(View v, JoinedChatRoomResponse data, int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.BundleKeys.IS_GROUP, true);
        bundle.putInt(Constants.BundleKeys.CHATROOM_ID, data.getId());
        bundle.putString(Constants.BundleKeys.CHATROOM_NAME, data.getName());
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToMainChatFragment(bundle);
        navController.navigate(directions);
    }
}
