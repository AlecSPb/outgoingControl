package com.example.jorgegonzalezcabrera.outgoing.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jorgegonzalezcabrera.outgoing.R;
import com.example.jorgegonzalezcabrera.outgoing.adapters.newOutgoingCategoriesAdapter;
import com.example.jorgegonzalezcabrera.outgoing.models.outgoingCategory;
import com.example.jorgegonzalezcabrera.outgoing.models.subcategory;

import io.realm.RealmList;

public class secondPageInitialConfiguration extends Fragment {

    private newOutgoingCategoriesAdapter outgoingCategoriesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment_initial_configuration, container, false);

        final TextView informationTextView = view.findViewById(R.id.outgoingsCategoriesDescription);
        ImageButton informationButton = view.findViewById(R.id.buttonInformation);
        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informationTextView.setVisibility(informationTextView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        RecyclerView recyclerViewOutgoingCategories = view.findViewById(R.id.recyclerViewOutgoingsCategoriesRequest);
        outgoingCategoriesAdapter = new newOutgoingCategoriesAdapter(getContext());
        recyclerViewOutgoingCategories.setAdapter(outgoingCategoriesAdapter);
        final LinearLayoutManager outgoingCategoriesLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewOutgoingCategories.setLayoutManager(outgoingCategoriesLayoutManager);

        Button buttonAddNewOutgoingCategory = view.findViewById(R.id.buttonAddNewOutgoingCategory);
        buttonAddNewOutgoingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outgoingCategoriesAdapter.addOne();
                outgoingCategoriesLayoutManager.scrollToPosition(outgoingCategoriesAdapter.getItemCount() - 1);
            }
        });

        return view;
    }

    public boolean checkData() {
        boolean check = true;
        RealmList<outgoingCategory> outgoingCategories = outgoingCategoriesAdapter.getCategories();
        for (int i = 0; i < outgoingCategories.size(); i++) {
            outgoingCategory category = outgoingCategories.get(i);
            if (!category.checkData()) {
                check = false;
            }
        }
        return check;
    }

    public RealmList<outgoingCategory> getData() {
        RealmList<outgoingCategory> data = outgoingCategoriesAdapter.getCategories();
        for (int i = 0; i < data.size(); i++) {
            outgoingCategory category = data.get(i);
            if (category.getSubcategories().isEmpty()) {
                category.getSubcategories().add(new subcategory(category.getName()));
                outgoingCategoriesAdapter.notifyItemChanged(i);
            }
        }
        return data;
    }
}
