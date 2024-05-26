package com.example.finai;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private EditText etFirstName, etLastName, etPhone;
    private TextView tvRiskStatus;
    private Button btnSave;
    private UserRepository userRepository;
    private int userId;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etPhone = view.findViewById(R.id.etPhone);
        tvRiskStatus = view.findViewById(R.id.tvRiskStatus);
        btnSave = view.findViewById(R.id.btnSave);

        // Initialize repository
        userRepository = new UserRepository(getActivity());
        userRepository.open();

        // Get userId from Main_page activity
        if (getActivity().getIntent() != null) {
            userId = getActivity().getIntent().getIntExtra("USER_ID", -1);
        }

        // Load user info
        loadUserInfo();

        // Set save button click listener
        btnSave.setOnClickListener(v -> saveUserInfo());

        return view;
    }

    private void loadUserInfo() {
        Cursor cursor = userRepository.getUserById(userId);
        if (cursor != null && cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_LAST_NAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_PHONE));
            String riskStatus = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_RISK_STATUS));

            etFirstName.setText(firstName);
            etLastName.setText(lastName);
            etPhone.setText(phone);
            tvRiskStatus.setText("Risk Statünüz: " + riskStatus);
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void saveUserInfo() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        // Risk status ve admin durumu güncellenmeyecekse, mevcut değerlerini yükleyin
        user.setRiskStatus(tvRiskStatus.getText().toString().replace("Risk Statünüz: ", ""));
        // Admin durumu kullanıcı tarafından güncellenmeyeceği için veritabanındaki değeri yükleyelim
        user.setAdmin(userRepository.isAdmin(userId));

        userRepository.updateUser(user);
        Toast.makeText(getActivity(), "User info updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userRepository.close();
    }
}