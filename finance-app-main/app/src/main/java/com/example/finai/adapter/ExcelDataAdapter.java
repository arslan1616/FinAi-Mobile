package com.example.finai.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finai.databinding.ItemExcelDataBinding;
import com.example.finai.model.ExcelData;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataAdapter extends RecyclerView.Adapter<ExcelDataAdapter.ExcelDataViewHolder> {

    private List<ExcelData> data = new ArrayList<>();

    @NonNull
    @Override
    public ExcelDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemExcelDataBinding itemBinding = ItemExcelDataBinding.inflate(layoutInflater, parent, false);
        return new ExcelDataViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcelDataViewHolder holder, int position) {
        ExcelData excelData = data.get(position);
        holder.bind(excelData);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ExcelData> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    static class ExcelDataViewHolder extends RecyclerView.ViewHolder {
        private final ItemExcelDataBinding binding;

        public ExcelDataViewHolder(@NonNull ItemExcelDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ExcelData excelData) {
            binding.setData(excelData);
            binding.executePendingBindings();
        }
    }
}