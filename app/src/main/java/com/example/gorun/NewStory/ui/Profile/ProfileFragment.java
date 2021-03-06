package com.example.gorun.NewStory.ui.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.gorun.NewStory.adapters.ViewPagerAdapter;
import com.example.gorun.NewStory.ui.NavigationActivity;
import com.example.gorun.NewStory.ui.Profile.ChildFragments.ManageActivitiesFragment;
import com.example.gorun.NewStory.ui.Profile.ChildFragments.MyProfileFragment;
import com.example.gorun.NewStory.ui.Trainers.ChildFragments.AllTrainersFragment;
import com.example.gorun.NewStory.ui.Trainers.ChildFragments.MySportsmensFragment;
import com.example.gorun.NewStory.ui.Trainers.ChildFragments.MyTrainersFragment;
import com.example.gorun.NewStory.ui.Trainers.TrainersFragment;
import com.example.gorun.R;
import com.google.android.material.tabs.TabLayout;


public class ProfileFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final ViewPager viewPager = root.findViewById(R.id.profile_viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = root.findViewById(R.id.profile_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(getContext(),"One",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(),"Two",Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Toast.makeText(getContext(), NavigationActivity.getMyActivity(), Toast.LENGTH_SHORT).show();
        adapter.addFrag(new MyProfileFragment(),"My Profile");
        adapter.addFrag(new ManageActivitiesFragment(), "Trainings");

        viewPager.setAdapter(adapter);
    }
}
