package com.example.gorun.NewStory.ui.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gorun.NewStory.adapters.NotificationsAdapter;
import com.example.gorun.NewStory.adapters.UsersAdapter;
import com.example.gorun.NewStory.models.User;
import com.example.gorun.NewStory.ui.NavigationActivity;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    FirebaseListAdapter<User> adapter;
    ListView listView;
    ArrayList<User> userArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        listView = (ListView)view.findViewById(R.id.list_of_all_notifications);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        userArrayList = new ArrayList<>();
            adapter = new NotificationsAdapter((NavigationActivity) getActivity(), User.class,R.layout.item_notification,
                    FirebaseDatabase.getInstance().getReference().child("Notifications").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("new_reuqest"));
        listView.setAdapter(adapter);
    }
}
