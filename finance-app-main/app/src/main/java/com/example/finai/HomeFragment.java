package com.example.finai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private ImageView imgSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Fragment'inizin layout'unu inflate edin
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // imgSettings butonunu tanımlayın
        imgSettings = view.findViewById(R.id.imgSettings);

        // Tıklama olayını ayarlayın
        imgSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        return view;
    }
}