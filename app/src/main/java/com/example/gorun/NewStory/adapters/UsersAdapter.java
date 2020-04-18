package com.example.gorun.NewStory.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gorun.NewStory.CreateOrJoinNewChat;
import com.example.gorun.NewStory.ListPeopleActivity;
import com.example.gorun.NewStory.models.User;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class UsersAdapter extends FirebaseListAdapter<User> {

    private ListPeopleActivity activity;

    public UsersAdapter(ListPeopleActivity activity, Class<User> modelClass, int modelLayout, DatabaseReference ref){
        super(activity,modelClass,modelLayout,ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, User model, int position) {
        ImageView profilePicture = (ImageView) v.findViewById(R.id.imageView_profile_picture);
        TextView displayName = (TextView) v.findViewById(R.id.textView_name);
        TextView activity = (TextView) v.findViewById(R.id.textView_activity);
        Picasso.get()
                .load(R.drawable.ic_account)
                .placeholder(R.drawable.ic_account)
                .into(profilePicture);
        displayName.setText(model.getName());
        activity.setText(model.getActivty());
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        User user = getItem(position);
        view = activity.getLayoutInflater().inflate(R.layout.item_person, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              new  CreateOrJoinNewChat(activity, FirebaseAuth.getInstance().getCurrentUser().getUid(), user.getUid());
           }
       });
        populateView(view, user, position);
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
