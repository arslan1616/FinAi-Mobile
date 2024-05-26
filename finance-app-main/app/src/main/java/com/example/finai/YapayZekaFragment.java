package com.example.finai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finai.adapter.ExcelDataAdapter;
import com.example.finai.databinding.FragmentYapayzekaBinding;
import com.example.finai.model.ExcelData;
import com.example.finai.viewmodel.ExcelViewModel;

import java.util.List;

public class YapayZekaFragment extends Fragment {

    private ExcelViewModel excelViewModel;
    private FragmentYapayzekaBinding binding;
    private ExcelDataAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentYapayzekaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        excelViewModel = new ViewModelProvider(this).get(ExcelViewModel.class);

        // RecyclerView ve Adapter kurulumu
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExcelDataAdapter();
        recyclerView.setAdapter(adapter);

        // Excel dosyasının adı
        String excelFileName = "guncel_siralama.xlsx";

        // LiveData gözlemi
        excelViewModel.getExcelData().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<List<ExcelData>>() {
            @Override
            public void onChanged(List<ExcelData> data) {
                adapter.setData(data);
            }
        });

        // Hata mesajlarını gözlemleme
        excelViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    // Hata mesajını kullanıcıya göstermek için bir Toast mesajı
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Excel verilerini yükleme
        excelViewModel.loadExcelData(getContext(), excelFileName);
    }
}