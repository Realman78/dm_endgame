package com.example.drzavnamatura_endgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.drzavnamatura_endgame.MainFragments.GradivaFragment;
import com.example.drzavnamatura_endgame.RecyclerViewHelper.GradivoItem;
import com.example.drzavnamatura_endgame.RecyclerViewHelper.RecyclerAdapter;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements RecyclerAdapter.OnItemListener{
    Button goBackButton_shop;
    RecyclerAdapter recyclerAdapter;
    List <GradivoItem> listIkona;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        recyclerView = findViewById(R.id.recyclerViewIkone);
        listIkona = new ArrayList<>();
        listIkona.add(new GradivoItem(R.drawable.ic_profile_buy, "Nesto"));
        listIkona.add(new GradivoItem(R.drawable.ic_profile_buy, "Drugo"));
        listIkona.add(new GradivoItem(R.drawable.ic_profile_buy, "Trece"));
        listIkona.add(new GradivoItem(R.drawable.ic_profile_buy, "Cetvrto"));
        recyclerAdapter = new RecyclerAdapter(this, listIkona, this);
        recyclerView.setAdapter(recyclerAdapter);



        goBackButton_shop = findViewById(R.id.backButton_shop);
        goBackButton_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, listIkona.get(position).getNaslov(), Toast.LENGTH_SHORT).show();
    }
}
