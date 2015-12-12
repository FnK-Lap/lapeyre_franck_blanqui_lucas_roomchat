package com.example.roomchat.adapter;

import java.util.List;

import com.example.roomchat.R;
import com.example.roomchat.model.entity.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChatListAdapter extends ArrayAdapter<Message> {	
	
	public ChatListAdapter(Context context, List<Message> messages) {
	        super(context, 0, messages);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).
				    inflate(R.layout.chat_item, parent, false);
			final ViewHolder holder = new ViewHolder();
			holder.body = (TextView)convertView.findViewById(R.id.tvBody);
			convertView.setTag(holder);
		}
		final Message message = (Message)getItem(position);
		final ViewHolder holder = (ViewHolder)convertView.getTag();
	
		
		holder.body.setText(message.getMessage());
		return convertView;
	}
	
	
	final class ViewHolder {
		public TextView body;
	}

}