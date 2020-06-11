package com.example.gorun.NewStory.adapters;

import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gorun.NewStory.ui.ListMessages2Activity;
import com.example.gorun.NewStory.models.Message;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

public class MessageAdapter extends FirebaseListAdapter<Message> {

 private ListMessages2Activity activity;

 public MessageAdapter(ListMessages2Activity activity, Class<Message> modelClass, int modelLayout, DatabaseReference ref){
     super(activity,modelClass,modelLayout,ref);
     this.activity = activity;
 }

    @Override
    protected void populateView(View v, Message model, int position) {
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Message chatMessage = getItem(position);
        if (chatMessage.getMessageUserId().equals(activity.getLoggedInUserName()))
            view = activity.getLayoutInflater().inflate(R.layout.list_item_left, viewGroup, false);
        else
            view = activity.getLayoutInflater().inflate(R.layout.list_item_right, viewGroup, false);


        populateView(view, chatMessage, position);

        return view;
    }

    @Override
    public int getItemViewType(int position) {
       return position % 2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
