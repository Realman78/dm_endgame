package com.example.drzavnamatura_endgame.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drzavnamatura_endgame.R;
import com.example.drzavnamatura_endgame.RecyclerViewHelper.CjelinaItem;
import com.example.drzavnamatura_endgame.RecyclerViewHelper.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.drzavnamatura_endgame.MainMenuActivity.gradivoPosition;

public class CjelineFragment extends Fragment implements RecyclerAdapter.OnItemListener {

    private List<CjelinaItem> listCjelina;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sve_cjeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerCjeline);
        TextView textView = view.findViewById(R.id.bigNaslovCjeline);
        textView.setText(gradivoPosition);
        listCjelina = new ArrayList<>();
        listCjelina.add(new CjelinaItem("WELL YES, BUT ACTUALLY NO"));
        listCjelina.add(new CjelinaItem("Gubi se"));
        listCjelina.add(new CjelinaItem("Ha, haha, ha"));

        final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), listCjelina, 1, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), listCjelina.get(position).getNaslovCjelina(), Toast.LENGTH_SHORT).show();
    }
}
