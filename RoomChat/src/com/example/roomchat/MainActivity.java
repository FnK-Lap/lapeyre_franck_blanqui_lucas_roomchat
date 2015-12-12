package com.example.roomchat;

import android.app.Activity;

import java.util.ArrayList;

import com.example.roomchat.adapter.ChatListAdapter;
import com.example.roomchat.model.entity.Message;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private EditText etMessage;
	private Button btSend;
	private ListView lvChat;
	private ArrayList<Message> mMessages;
	private ChatListAdapter mAdapter;
	// Keep track of initial load to scroll to the bottom of the ListView
	private boolean mFirstLoad;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Firebase.setAndroidContext(this);
		Firebase rootRef = new Firebase("https://supfirechat.firebaseio.com/message");
		setContentView(R.layout.activity_main);
		setupMessagePosting();
		
	
		
		rootRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onCancelled(FirebaseError firebaseError) {
				System.out.println("The read failed: " + firebaseError.getMessage());
			}

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				mMessages.clear();
				for (DataSnapshot postSnapshot: snapshot.getChildren()) {
		            Message message = new Message(postSnapshot.getValue().toString());
		            mMessages.add(message);
		            mAdapter.notifyDataSetChanged();
		            if(mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
		          }
			}
		});
	}
	
	
	
	private void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        lvChat = (ListView) findViewById(R.id.lvChat);
        mMessages = new ArrayList<Message>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
    	mAdapter = new ChatListAdapter(MainActivity.this, mMessages);
    	lvChat.setAdapter(mAdapter);

        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                
                etMessage.setText("Data + "+data);
            }
        });
}

}
