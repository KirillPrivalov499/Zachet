package com.example.gorun.NewStory.adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gorun.NewStory.Logic.CreateOrJoinNewChat;
import com.example.gorun.NewStory.models.User;
import com.example.gorun.NewStory.ui.DetailActivity;
import com.example.gorun.NewStory.ui.NavigationActivity;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdapterAllTrainers extends FirebaseListAdapter<User> {
    private NavigationActivity activity;

    public AdapterAllTrainers(NavigationActivity activity, Class<User> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, User model, int position) {

        ImageView profilePicture = (ImageView) v.findViewById(R.id.imageView_profile_picture);
        TextView displayName = (TextView) v.findViewById(R.id.textView_name);
        TextView activity = (TextView) v.findViewById(R.id.textView_activity);
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
            view = activity.getLayoutInflater().inflate(R.layout.item_person, viewGroup, false);
            final User[] runner = new User[1];
            view.setOnClickListener(v -> {
                Intent intent = new Intent(activity,DetailActivity.class);
                intent.putExtra("Uid",user.getUid());
                activity.startActivity(intent);
            });
            view.findViewById(R.id.btn_send_request).setOnClickListener(v -> {
                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String email = dataSnapshot.getValue(User.class).getEmail();
                                Log.e("efefeg", email);
                                String activity = dataSnapshot.getValue(User.class).getActivty();
                                String name = dataSnapshot.getValue(User.class).getName();
                                String picture = dataSnapshot.getValue(User.class).getPicture();
                                String uid = dataSnapshot.getValue(User.class).getUid();
                                String yearsOld = dataSnapshot.getValue(User.class).getYearsOld();
                                runner[0] = new User(name, email, uid, picture, yearsOld, activity);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                FirebaseDatabase.getInstance().getReference().child("Notifications").child(user.getUid()).child("new_reuqest").push().setValue(runner[0]);
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
