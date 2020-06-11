package com.example.gorun.NewStory.ui.Trainers.ChildFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gorun.NewStory.adapters.ForSportsmensAdapter;
import com.example.gorun.NewStory.adapters.UsersAdapter;
import com.example.gorun.NewStory.models.User;
import com.example.gorun.NewStory.ui.NavigationActivity;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class MySportsmensFragment extends Fragment {

    FirebaseListAdapter<User> adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_sportsmens, container, false);
        listView = (ListView)view.findViewById(R.id.list_of_my_sportsmens);
        adapter = new ForSportsmensAdapter((NavigationActivity) getActivity(), User.class,R.layout.sportsmens_item,
                FirebaseDatabase.getInstance().getReference().child("RunnersOfTrainer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()));

        listView.setAdapter(adapter);
        return view;
    }
}
