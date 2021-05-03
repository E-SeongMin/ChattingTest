package com.example.chattingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ChatItem> items = new ArrayList<ChatItem>();
    Context context;
    View view;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == ChatType.LEFT_MESSAGE) {   // 상대방이 보낸 메시지
            view = inflater.inflate(R.layout.chat_left_item, parent, false);
            return new LeftViewHolder(view);
        } else if (viewType == ChatType.CENTER_MESSAGE) {   // 입장 or 퇴장 메시지
            view = inflater.inflate(R.layout.chat_center_item, parent, false);
            return new CenterViewHolder(view);
        } else {   // 내가 보낸 메시지
            view = inflater.inflate(R.layout.chat_right_item, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LeftViewHolder) {
            ChatItem item = items.get(position);
            ((LeftViewHolder)holder).setItem(item);
        } else if (holder instanceof CenterViewHolder) {
            ChatItem item = items.get(position);
            ((CenterViewHolder)holder).setItem(item);
        } else if (holder instanceof RightViewHolder) {
            ChatItem item = items.get(position);
            ((RightViewHolder)holder).setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ChatItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<ChatItem> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    public ChatItem getItem(int position) {
        return items.get(position);
    }

    public class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, contentText, sendtimeText;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textName);
            contentText = itemView.findViewById(R.id.textMessage);
            sendtimeText = itemView.findViewById(R.id.textSendTime);
        }

        public void setItem(ChatItem item) {
            nameText.setText(item.getUserName());
            contentText.setText(item.getContent());
            sendtimeText.setText(item.getSendTime());
        }
    }

    public class CenterViewHolder extends RecyclerView.ViewHolder {
        TextView contentText;

        public CenterViewHolder(@NonNull View itemView) {
            super(itemView);

            contentText = itemView.findViewById(R.id.textMessage);
        }

        public void setItem(ChatItem item) {
            contentText.setText(item.getContent());
        }
    }

    public class RightViewHolder extends RecyclerView.ViewHolder {
        TextView contentText, sendtimeText;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);

            contentText = itemView.findViewById(R.id.textMessage);
            sendtimeText = itemView.findViewById(R.id.textSendTime);
        }

        public void setItem(ChatItem item) {
            contentText.setText(item.getContent());
            sendtimeText.setText(item.getSendTime());
        }
    }
}
