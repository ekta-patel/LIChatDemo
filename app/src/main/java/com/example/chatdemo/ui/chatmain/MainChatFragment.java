package com.example.chatdemo.ui.chatmain;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatdemo.R;
import com.example.chatdemo.data.models.response.messages.Message;
import com.example.chatdemo.databinding.FragmentMainChatBinding;
import com.example.chatdemo.ui.base.BaseFragment;
import com.example.chatdemo.utils.Constants;
import com.example.chatdemo.utils.MarginItemDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainChatFragment extends BaseFragment<FragmentMainChatBinding, MainChatViewModel> {

    private int mChatRoomId;
    private NavController navController;
    private ChatMessagesAdapter adapter;
    private List<Message> messageList;

    @Override
    protected Class<MainChatViewModel> initViewModel() {
        return MainChatViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(MainChatFragment.this);
        if (getArguments() != null) {
            Bundle b = getArguments().getBundle("my_args");
            if (b != null) {
                if (b.getBoolean(Constants.BundleKeys.IS_GROUP)) {
                    mChatRoomId = b.getInt(Constants.BundleKeys.CHATROOM_ID);
                    setHasOptionsMenu(true);
                    viewModel.getChatRoomMessages(mChatRoomId);
                } else {
                    int mOtherUserId = b.getInt(Constants.BundleKeys.OTHER_USER_ID);
                    viewModel.getUserMessages(mOtherUserId);
                }
                Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(b.containsKey(Constants.BundleKeys.CHATROOM_NAME) ? b.getString(Constants.BundleKeys.CHATROOM_NAME) : getString(R.string.app_name));
            }
        }

        initAdapter();

        observeData();
    }

    private void initAdapter() {
        messageList = new ArrayList<>();
        adapter = new ChatMessagesAdapter(messageList);
        binding.rvMessages.addItemDecoration(new MarginItemDecorator(16, 16, 16, 16));
        binding.rvMessages.setAdapter(adapter);
    }

    private void observeData() {
        viewModel.getDeleteChatroomLiveData().observe(getViewLifecycleOwner(), deleteChatRoomResponse ->
                {
                    if (deleteChatRoomResponse.getStatus().equalsIgnoreCase("ok"))
                        navController.navigateUp();
                }
        );
        viewModel.getLeaveChatroomLiveData().observe(getViewLifecycleOwner(), leaveChatroomResponse -> {
            if (leaveChatroomResponse.getSuccess().toLowerCase().contains("leave")) {
                navController.navigateUp();
            }
        });
        viewModel.getMessageResponseLiveData().observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                this.messageList.clear();
                this.messageList.addAll(res.getMessages());
                adapter.notifyDataSetChanged();
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
        return R.layout.fragment_main_chat;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteChatRoom();
                return true;
            case R.id.action_leave:
                leaveChatRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void leaveChatRoom() {
        viewModel.leaveChatroom(mChatRoomId);
    }

    private void deleteChatRoom() {
        viewModel.deleteChatroom(mChatRoomId);
    }

}
