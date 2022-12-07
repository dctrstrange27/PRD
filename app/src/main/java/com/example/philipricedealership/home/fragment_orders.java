package com.example.philipricedealership.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.Order;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;

import java.util.ArrayList;


public class fragment_orders extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        User currentUser = (User) getArguments().getSerializable("currentUser");
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        ArrayList<Order> ords = Order.getAllOrderFrom(currentUser.getUid(), dbHelper);
        for(Order o : ords) System.out.println("My Orders -> "+o.toString());
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }
}