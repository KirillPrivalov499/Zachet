package com.example.gorun.NewStory.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gorun.NewStory.Logic.CreateOrJoinNewChat;
import com.example.gorun.NewStory.models.User;
import com.example.gorun.NewStory.ui.AddTaskForSportsmenActivity;
import com.example.gorun.NewStory.ui.NavigationActivity;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ForTrainersAdapter extends FirebaseListAdapter<User> {
    private NavigationActivity activity;

    public ForTrainersAdapter(NavigationActivity activity, Class<User> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, User model, int position) {
        ImageView profilePicture = (ImageView) v.findViewById(R.id.imageView_profile_picture11);
        TextView displayName = (TextView) v.findViewById(R.id.textView_name11);
        TextView activity = (TextView) v.findViewById(R.id.textView_activity11);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReference();

        Picasso.get()
                .load(storage.getReference().child("images/" + FirebaseAuth.getInstance().getCurrentUser()+"/").getPath())
                .placeholder(R.drawable.ic_account)
                .into(profilePicture);

        rootRef.child("images/" + model.getUid() + "/").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.ic_account)
                        .into(profilePicture);
            }

        });



        displayName.setText(model.getName());
        activity.setText(model.getActivty());
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        User user = getItem(position);
        view = activity.getLayoutInflater().inflate(R.layout.my_trainers_item, viewGroup, false);
        view.setOnClickListener(v -> new CreateOrJoinNewChat(activity, FirebaseAuth.getInstance().getCurrentUser().getUid(), user.getUid()));
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

