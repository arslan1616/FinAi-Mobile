package com.example.finai;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.finai.databinding.ActivityMainPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_page extends AppCompatActivity {

    private ActivityMainPageBinding binding;
    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Başlangıçta HomeFragment yüklüyoruz
        if (savedInstanceState == null) {
            currentFragment = new HomeFragment();
            replaceFragment(currentFragment);
        }

        // Bottom Navigation View item seçimi
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.profile) {
                selectedFragment = new ProfileFragment();
            } else if (itemId == R.id.RiskAnalizi) {
                selectedFragment = new RiskAnaliziFragment();
            } else if (itemId == R.id.YapayZeka) {
                selectedFragment = new YapayZekaFragment();
            }

            if (selectedFragment != null && selectedFragment.getClass() != currentFragment.getClass()) {
                replaceFragment(selectedFragment);
                currentFragment = selectedFragment;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}