package com.example.gorun.NewStory.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gorun.NewStory.models.User;
import com.example.gorun.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {


    TextView nameET;
    TextView yearsOldET;
    TextView emailTV;
    TextView activityTV;
    ImageView profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nameET = (TextView) findViewById(R.id.tvNumber11);
        emailTV = (TextView) findViewById(R.id.tvNumber22);
        yearsOldET = (TextView) findViewById(R.id.tvNumber33);
        activityTV = (TextView) findViewById(R.id.tvNumber44);
        profilePicture = (ImageView) findViewById(R.id.image_profile1);

        getSupportActionBar().setTitle("Detail"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action ba

        Bundle arguments = getIntent().getExtras();
        String Uid = arguments.get("Uid").toString();
        FirebaseDatabase.getInstance().getReference().child("Users").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nameET.setText(dataSnapshot.getValue(User.class).getName());
                emailTV.setText(dataSnapshot.getValue(User.class).getEmail());
                yearsOldET.setText(dataSnapshot.getValue(User.class).getYearsOld());
                activityTV.setText(dataSnapshot.getValue(User.class).getActivty());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReference();

        rootRef.child("images/" + Uid + "/").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.ic_account)
                        .into(profilePicture);
            }
    } );
    }
}
