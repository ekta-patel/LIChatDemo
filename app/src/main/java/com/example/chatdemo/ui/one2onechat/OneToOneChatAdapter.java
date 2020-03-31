package com.example.chatdemo.ui.one2onechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatdemo.R;
import com.example.chatdemo.data.models.response.one2onechat.OneToOneChatResponse;
import com.example.chatdemo.databinding.ItemOnetooneChatBinding;
import com.example.chatdemo.ui.base.RecyclerViewItemClickListener;

import java.util.List;

public class OneToOneChatAdapter extends RecyclerView.Adapter<OneToOneChatAdapter.ViewHolder> {

    private List<OneToOneChatResponse> responseList;
    private RecyclerViewItemClickListener listener;

    OneToOneChatAdapter(List<OneToOneChatResponse> list, RecyclerViewItemClickListener listener) {
        this.responseList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOnetooneChatBinding oneToOneChatBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_onetoone_chat, parent, false);
        return new ViewHolder(oneToOneChatBinding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(responseList.get(position));
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemOnetooneChatBinding chatBinding;
        private RecyclerViewItemClickListener listener;

        ViewHolder(ItemOnetooneChatBinding oneToOneChatBinding, RecyclerViewItemClickListener listener) {
            super(oneToOneChatBinding.getRoot());
            this.chatBinding = oneToOneChatBinding;
            this.listener = listener;
            View view = oneToOneChatBinding.getRoot();
            view.setOnClickListener(this);
        }

        void bind(OneToOneChatResponse chatResponse) {
            chatBinding.setOneToOne(chatResponse);
            chatBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, chatBinding.getOneToOne(), getAdapterPosition());
        }
    }
}
