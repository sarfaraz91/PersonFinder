package com.example.personfinder.ui.information_map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personfinder.databinding.FragmentMapInfoBinding;

import org.jetbrains.annotations.NotNull;

public class MapInformationFragment extends Fragment {

    private static MapInformationFragment instance;
    FragmentMapInfoBinding binding;
    View view;

    public MapInformationFragment() {
        instance = this;
    }

    public static MapInformationFragment getInstance() {
        return instance;
    }

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentMapInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
