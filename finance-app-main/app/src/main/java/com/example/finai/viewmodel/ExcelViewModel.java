package com.example.finai.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.finai.model.ExcelData;
import com.example.finai.utils.ExcelReader;

import java.util.List;

public class ExcelViewModel extends AndroidViewModel {
    private final MutableLiveData<List<ExcelData>> excelData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public ExcelViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ExcelData>> getExcelData() {
        return excelData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadExcelData(Context context, String fileName) {
        new Thread(() -> {
            List<ExcelData> data = ExcelReader.readExcelFile(context, fileName);
            if (data.isEmpty()) {
                errorMessage.postValue("Dosya yüklenirken bir hata oluştu.");
            } else {
                excelData.postValue(data);
            }
        }).start();
    }
}