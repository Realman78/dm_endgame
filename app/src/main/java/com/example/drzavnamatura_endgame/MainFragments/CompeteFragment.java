package com.example.drzavnamatura_endgame.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.drzavnamatura_endgame.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.drzavnamatura_endgame.MainMenuActivity.currentF;
import static com.example.drzavnamatura_endgame.MainMenuActivity.gradivoPosition;
import static com.example.drzavnamatura_endgame.MainMenuActivity.kojiFragment;

public class CompeteFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> arraylist;
    FloatingActionButton button;


    public CompeteFragment() {
    }

    public static CompeteFragment newInstance(String param1, String param2) {
        CompeteFragment fragment = new CompeteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_compete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kojiFragment = 0;

        listView = view.findViewById(R.id.listViewQueue);
        button = view.findViewById(R.id.floatingActionButton);
        arraylist = new ArrayList<>();
        arraylist.add("Marin");
        arraylist.add("Luka");

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arraylist);
        listView.setAdapter(adapter);

        go_to_leaderboards();

    }

    private void go_to_leaderboards() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaderboardsFragment leaderboardsFragment = new LeaderboardsFragment();
                currentF = leaderboardsFragment;
                assert getFragmentManager() != null;
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, leaderboardsFragment, "compete");
                fragmentTransaction.commit();
                kojiFragment = 3;
            }
        });
    }

}
