package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class FindBeerActivity extends AppCompatActivity {
    private BeerExpert expert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
    }

    public void onClickFindBeer(View view) {
        // go find the View widget with specification
        // android:id="@+id/brands"
        TextView brands = (TextView) findViewById(R.id.brands);
        brands.setText("Gottle of geer");
        // android:id="@+id/color"
        Spinner colorOfBeer = (Spinner) findViewById(R.id.color);
        String beerType = String.valueOf(colorOfBeer.getSelectedItem());

        List<String> brandList = expert.getBrands(beerType);
        StringBuilder brandsFormatted = new StringBuilder();
        for (String temp_brand : brandList) {
            brandsFormatted.append(temp_brand).append("\n");
        }

        if (brandsFormatted.length()!=0)
            brands.setText(brandsFormatted);
    }
}