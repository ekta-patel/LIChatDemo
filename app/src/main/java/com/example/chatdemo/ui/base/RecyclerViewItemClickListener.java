package com.example.chatdemo.ui.base;

import android.view.View;

public interface RecyclerViewItemClickListener<T> {

    void onItemClick(View v, T data, int position);
}
