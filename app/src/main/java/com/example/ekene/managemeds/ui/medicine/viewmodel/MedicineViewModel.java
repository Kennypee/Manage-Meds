package com.example.ekene.managemeds.ui.medicine.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.example.ekene.managemeds.DB.AppDatabase;
import com.example.ekene.managemeds.data.model.Medicine;

import java.util.List;


public class MedicineViewModel extends AndroidViewModel {

    private final LiveData<List<Medicine>> medicineList;
    private AppDatabase appDatabase;

    public MedicineViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        medicineList = appDatabase.medicineDao().getAllMedicine();
    }

    public LiveData<List<Medicine>> getMedicineList() {
        return medicineList;
    }

    public void deleteItem(Medicine medicine) {
        new deleteAsyncTask(appDatabase).execute(medicine);
    }

    private static class deleteAsyncTask extends AsyncTask<Medicine, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Medicine... params) {
            db.medicineDao().deleteMedicine(params[0]);
            return null;
        }

    }
}
