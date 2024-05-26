package com.example.finai;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RiskAnaliziFragment extends Fragment {
    private UserRepository userRepository;
    private int userId;

    private RadioGroup rgAge, rgExperience, rgFinancial, rgGoals, rgTolerance;
    private Button btnCalculateRisk, btnSaveRisk;
    private TextView tvRiskProfile;

    public RiskAnaliziFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riskanalizi, container, false);

        // Initialize repository
        userRepository = new UserRepository(getActivity());
        userRepository.open();

        // Get userId from Main_page activity
        if (getActivity().getIntent() != null) {
            userId = getActivity().getIntent().getIntExtra("USER_ID", -1);
        }

        // Initialize views
        rgAge = view.findViewById(R.id.rgAge);
        rgExperience = view.findViewById(R.id.rgExperience);
        rgFinancial = view.findViewById(R.id.rgFinancial);
        rgGoals = view.findViewById(R.id.rgGoals);
        rgTolerance = view.findViewById(R.id.rgTolerance);
        btnCalculateRisk = view.findViewById(R.id.btnCalculateRisk);
        btnSaveRisk = view.findViewById(R.id.btnSaveRisk);
        tvRiskProfile = view.findViewById(R.id.tvRiskProfile);

        // Set calculate button click listener
        btnCalculateRisk.setOnClickListener(v -> calculateRiskProfile());

        // Set save button click listener
        btnSaveRisk.setOnClickListener(v -> saveRiskProfile());

        return view;
    }

    private void calculateRiskProfile() {
        int[] scores = new int[5];
        scores[0] = getSelectedScore(rgAge);
        scores[1] = getSelectedScore(rgExperience);
        scores[2] = getSelectedScore(rgFinancial);
        scores[3] = getSelectedScore(rgGoals);
        scores[4] = getSelectedScore(rgTolerance);

        String riskProfile = calculateRiskProfile(scores);
        tvRiskProfile.setText("Risk Profiliniz: " + riskProfile);
    }

    private void saveRiskProfile() {
        String riskProfile = tvRiskProfile.getText().toString().replace("Risk Profiliniz: ", "");

        boolean isUpdated = userRepository.updateUserRiskStatus(userId, riskProfile);
        if (isUpdated) {
            Toast.makeText(getActivity(), "Risk profili başarıyla güncellendi", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Risk profili güncellenemedi", Toast.LENGTH_SHORT).show();
        }
    }

    private int getSelectedScore(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return 0;
        }
        if (selectedId == R.id.option1 || selectedId == R.id.experience_option1 ||
                selectedId == R.id.financial_option1 || selectedId == R.id.goals_option1 ||
                selectedId == R.id.tolerance_option1) {
            return 3;
        } else if (selectedId == R.id.option2 || selectedId == R.id.experience_option2 ||
                selectedId == R.id.financial_option2 || selectedId == R.id.goals_option2 ||
                selectedId == R.id.tolerance_option2) {
            return 2;
        } else if (selectedId == R.id.option3 || selectedId == R.id.experience_option3 ||
                selectedId == R.id.financial_option3 || selectedId == R.id.goals_option3 ||
                selectedId == R.id.tolerance_option3) {
            return 1;
        }
        return 0;
    }

    private String calculateRiskProfile(int[] scores) {
        int totalScore = 0;
        for (int score : scores) {
            totalScore += score;
        }

        if (totalScore <= 7) {
            return "Düşük Risk";
        } else if (totalScore <= 12) {
            return "Orta Risk";
        } else {
            return "Yüksek Risk";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userRepository.close();
    }
}