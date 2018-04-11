package com.example.ekene.managemeds.ui.medicine.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.ekene.managemeds.R;
import com.example.ekene.managemeds.data.model.Medicine;
import com.example.ekene.managemeds.ui.medicine.viewmodel.AddMedicineViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmMedicineActivity extends AppCompatActivity {

    @BindView(R.id.textViewMedName)
    TextView textViewMedName;
    @BindView(R.id.textViewMedDescription)
    TextView textViewMedDescription;
    @BindView(R.id.textViewMedInterval)
    TextView textViewMedInterval;
    @BindView(R.id.textViewMedStartDate)
    TextView textViewMedStartDate;
    @BindView(R.id.textViewMedEndDate)
    TextView textViewMedEndDate;

    @BindView(R.id.buttonEditMedicine)
    Button buttonEditMedicine;
    @BindView(R.id.buttonSaveMedicine)
    Button buttonSaveMedicine;
    String name;
    String description;
    String interval;
    String startDate;
    String endDate;
    String pills;
    int days;
    Bundle bundle;
    private AddMedicineViewModel addMedicineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_medicine);
        ButterKnife.bind(this);

        addMedicineViewModel = ViewModelProviders.of(this).get(AddMedicineViewModel.class);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        assert bundle != null;
        name = bundle.getString("name");
        description = bundle.getString("description");
        interval = bundle.getString("interval");
        startDate = bundle.getString("startDate");
        endDate = bundle.getString("endDate");
        pills = bundle.getString("pills");
        days = bundle.getInt("pills");

        textViewMedName.setText(name);
        textViewMedDescription.setText(description);
        textViewMedInterval.setText(interval);
        textViewMedStartDate.setText(startDate);
        textViewMedEndDate.setText(endDate);

        /**
         * Allow user to edit/update the details
         */
        buttonEditMedicine.setOnClickListener(v -> {
            Intent med = new Intent(getApplicationContext(), AddMedicineActivity.class);
            med.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(med);
        });

        /**
         * Save details
         */
        buttonSaveMedicine.setOnClickListener(v -> saveMedicine());
    }

    private void saveMedicine() {

        Date dateStart = null;
        Date dateEnd = null;

        DateFormat srcDf = new SimpleDateFormat("dd/MM/yyyy");
        try {
             dateStart = srcDf.parse(String.valueOf(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            dateEnd = srcDf.parse(String.valueOf(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        addMedicineViewModel.addMedicine(new Medicine(
                name,
                description,
                interval,
                pills,
                "0",
                true,
                dateStart,
                dateEnd,
                days
        ));

        Intent success = new Intent(getApplicationContext(), MedicineSuccessActivity.class);
        success.putExtras(bundle);
        startActivity(success);
        this.finish();
    }
}
