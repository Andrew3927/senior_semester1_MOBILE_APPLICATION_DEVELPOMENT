package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    public BeerExpert() {
    }

    List<String> getBrands(String color) {
        List<String> bands = new ArrayList<>();
        if (color.equals("amber")) {
            bands.add("Jack Amber");
            bands.add("Red Moose");
        } else if (color.equals("light")) {
            ;
        } else if (color.equals("brown")) {
            ;
        } else if (color.equals("darks")) {
            ;
        } else {
            // error
        }

        return bands;

    }
}
