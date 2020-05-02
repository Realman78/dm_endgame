package com.example.drzavnamatura_endgame.MainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.drzavnamatura_endgame.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.drzavnamatura_endgame.MainMenuActivity.currentF;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    ImageButton back_button;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;
    private CollectionReference usersCollectionRef = db.collection("users");


    public LeaderboardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderboardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardsFragment newInstance(String param1, String param2) {
        LeaderboardsFragment fragment = new LeaderboardsFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrayList = new ArrayList<>();


        listView = view.findViewById(R.id.leaderboardsList);
        back_button = view.findViewById(R.id.backButton_compete);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        final Boolean OrderSet = false;


        final ArrayList<String> user_names = new ArrayList<>();
        final ArrayList<String> users_scores = new ArrayList<String>();
        usersCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < documentSnapshots.size(); i++) {
                    DocumentSnapshot documentSnapshot = documentSnapshots.get(i);
                    String score = Objects.requireNonNull(documentSnapshot.get("score")).toString();
                    String name = Objects.requireNonNull(documentSnapshot.get("username")).toString();
                    Log.d(TAG, "onSuccess: " + user_names + " " + users_scores);
                    users_scores.add(score);
                    user_names.add(name);
                    arrayList.add((i + 1) + ". " + name + "               " + score);
                    arrayAdapter.notifyDataSetChanged();
                }

                System.out.println("List Before Sorting: " + users_scores);

                Boolean not_sorted = true;

                while (not_sorted) {
                    not_sorted = false;
                    for (int i = 1; i < users_scores.size(); i++) {
                        if (Integer.parseInt(users_scores.get(i)) > Integer.parseInt(users_scores.get(i - 1))) {
                            int temp_score = Integer.parseInt(users_scores.get(i));
                            String temp_name = user_names.get(i);
                            users_scores.set(i, users_scores.get(i - 1));
                            user_names.set(i, user_names.get(i-1));
                            users_scores.set(i - 1, temp_score + "");
                            user_names.set(i-1, temp_name);
                            not_sorted = true;
                        }
                    }
                    if (!not_sorted) {
                        break;
                    }
                }
                System.out.println("List After Sorting: " + users_scores.toString());
                arrayList.clear();
                arrayAdapter.notifyDataSetChanged();
                for(int i = 0; i< user_names.size(); i++) {

                    arrayList.add((i + 1) + ". " + user_names.get(i) + "               " + users_scores.get(i));
                }

            }
        });




        return_to_compete();


    }

    private void return_to_compete() {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompeteFragment competeFragment = new CompeteFragment();
                currentF = competeFragment;
                assert getFragmentManager() != null;
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, competeFragment, "compete");
                fragmentTransaction.commit();
            }
        });
    }

}
