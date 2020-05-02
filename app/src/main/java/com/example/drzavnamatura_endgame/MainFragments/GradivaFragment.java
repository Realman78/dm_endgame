package com.example.drzavnamatura_endgame.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drzavnamatura_endgame.RecyclerViewHelper.RecyclerAdapter;
import com.example.drzavnamatura_endgame.RecyclerViewHelper.GradivoItem;
import com.example.drzavnamatura_endgame.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.drzavnamatura_endgame.MainMenuActivity.currentF;
import static com.example.drzavnamatura_endgame.MainMenuActivity.gradivoPosition;
import static com.example.drzavnamatura_endgame.MainMenuActivity.kojiFragment;

public class GradivaFragment extends Fragment implements RecyclerAdapter.OnItemListener{

    private List<GradivoItem> listGradiva;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sva_gradiva, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerGradiva = view.findViewById(R.id.recyclerViewGradiva);


        listGradiva = new ArrayList<>();
        listGradiva.add(new GradivoItem(R.drawable.slika_za_iteme, "Brojevi"));
        listGradiva.add(new GradivoItem("Funkcije"));
        listGradiva.add(new GradivoItem("Kurac"));
        listGradiva.add(new GradivoItem("Geometrija"));
        listGradiva.add(new GradivoItem("Neznan"));
        listGradiva.add(new GradivoItem("Derivacije"));
        listGradiva.add(new GradivoItem("Drugi Stupanj"));
        listGradiva.add(new GradivoItem("Naslov"));

        final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);


        //adapter
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), listGradiva, this);
        recyclerGradiva.setLayoutManager(layoutManager);
        recyclerGradiva.setAdapter(adapter);


    }



    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), listGradiva.get(position).getNaslov(),Toast.LENGTH_SHORT).show();
        gradivoPosition = listGradiva.get(position).getNaslov();
        CjelineFragment cjelineFragment = new CjelineFragment();
        currentF = cjelineFragment;
        assert getFragmentManager() != null;
        FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, cjelineFragment, "cjeline");
        fragmentTransaction.commit();
        kojiFragment = 2;

    }
}
