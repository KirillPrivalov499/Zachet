package com.example.gorun.NewStory.adapters;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gorun.NewStory.Logic.AcceptRequestNotifications;
import com.example.gorun.NewStory.models.User;
import com.example.gorun.NewStory.ui.NavigationActivity;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class NotificationsAdapter extends FirebaseListAdapter<User> {

    private NavigationActivity activity;

    public NotificationsAdapter(NavigationActivity activity, Class<User> modelClass, int modelLayout, DatabaseReference ref){
        super(activity,modelClass,modelLayout,ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, User model, int position) {
        ImageView profilePicture = (ImageView) v.findViewById(R.id.not_imageView_profile_picture);
        TextView displayName = (TextView) v.findViewById(R.id.not_textView_name);
        displayName.setText(model.getName());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReference();

        Picasso.get()
                .load(storage.getReference().child("images/" + FirebaseAuth.getInstance().getCurrentUser()+"/").getPath())
                .placeholder(R.drawable.ic_account)
                .into(profilePicture);


        rootRef.child("images/" + model.getUid() + "/").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Toast.makeText(activity,uri.toString(),Toast.LENGTH_LONG).show();
                Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.ic_account)
                        .into(profilePicture);
            }

        });
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        User user = getItem(position);
        view = activity.getLayoutInflater().inflate(R.layout.item_notification, viewGroup, false);

       view.findViewById(R.id.image_clear).setOnClickListener(new View.OnClickListener() {

           @Override
       public void onClick(View v) {
          DatabaseReference databaseReference = getRef(position);
           databaseReference.removeValue();
           Toast.makeText(activity, "Удалено "+getRef(position), Toast.LENGTH_SHORT).show();
       }
          });

        view.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AcceptRequestNotifications(user, FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });

        view.findViewById(R.id.btn_no_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Понял", Toast.LENGTH_SHORT).show();
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
