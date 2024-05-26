package com.example.finai.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.finai.utils.ExcelReader;
import com.example.finai.model.ExcelData;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ExcelUpdateWorker extends Worker {

    private static final String TAG = "ExcelUpdateWorker";

    public ExcelUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        String excelFileName = "guncel_siralama.xlsx";

        // Dosya indirme işlemini gerçekleştirin
        if (!downloadExcelFile(applicationContext, excelFileName)) {
            return Result.failure();
        }

        // Excel dosyasını okuyun
        List<ExcelData> data = ExcelReader.readExcelFile(applicationContext, excelFileName);

        if (data == null || data.isEmpty()) {
            // Hata olduğunda Result.failure() döndür
            return Result.failure();
        } else {
            // Başarılı olduğunda Result.success() döndür
            return Result.success();
        }
    }

    private boolean downloadExcelFile(Context context, String fileName) {
        try {
            URL url = new URL("http://yourserver.com/" + fileName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Dosya yolu olarak getExternalFilesDir kullanımı
            File file = new File(context.getExternalFilesDir(null), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] data = new byte[1024];
            int count;
            while ((count = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, count);
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();

            Log.d(TAG, "Excel file downloaded successfully.");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error downloading Excel file", e);
            return false;
        }
    }
}