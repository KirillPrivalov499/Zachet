package com.example.gorun.NewStory.ui.Trainers.ChildFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gorun.NewStory.adapters.AdapterAllTrainers;
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


public class AllTrainersFragment extends Fragment {

    FirebaseListAdapter<User> adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_all_trainers, container, false);

        listView = (ListView)view.findViewById(R.id.list_of_all_trainers);
        adapter = new AdapterAllTrainers((NavigationActivity) getActivity(), User.class,R.layout.item_person,
                FirebaseDatabase.getInstance().getReference().child("Trainer"));
        listView.setAdapter(adapter);

        return view;
    }

}
