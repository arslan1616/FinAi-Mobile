package com.example.finai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Switch notificationSwitch;
    private Switch darkModeSwitch;
    private Spinner languageSpinner;
    private Button logoutButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Bildirim ayarı
        notificationSwitch = view.findViewById(R.id.notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Bildirim ayarını kaydedin veya güncelleyin
        });

        // Karanlık mod ayarı
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Karanlık mod ayarını kaydedin veya güncelleyin
        });

        // Dil ayarı
        languageSpinner = view.findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Dil ayarını kaydedin veya güncelleyin
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Hiçbir şey seçilmediğinde yapılacak işlemler
            }
        });

        // Çıkış yap butonu
        logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Kullanıcıyı çıkış yaptırın ve giriş ekranına yönlendirin
            Intent intent = new Intent(getActivity(), LogintoContact.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}
