package com.example.ekene.managemeds.ui.medicine.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ekene.managemeds.R;
import com.example.ekene.managemeds.data.model.Medicine;
import com.example.ekene.managemeds.ui.main.adapter.CustomItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyMedicineAdapter extends RecyclerView.Adapter<DailyMedicineAdapter.MedicineHolder> {
    CustomItemClickListener listener;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Context context;
    private List<Medicine> medicineList;

    public DailyMedicineAdapter(Context context, List<Medicine> medicineList, CustomItemClickListener listener) {
        this.context = context;
        this.medicineList = medicineList;
        this.listener = listener;
    }

    @Override
    public MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_daily_medicine, parent, false);
        final MedicineHolder mViewHolder = new MedicineHolder(itemView);
        itemView.setOnClickListener(v -> listener.onItemClick(v, mViewHolder.getPosition()));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MedicineHolder holder, int position) {
        Medicine medicine = medicineList.get(position);

        calendar = Calendar.getInstance();

        if (medicine.getEndDate().after(calendar.getTime())) {
            holder.textViewMedicineName.setText(medicine.getName());
            holder.textViewMedicineDescription.setText(medicine.getDescription());

            if (medicine.getPillsTaken().equals("0")) {
                //No Pill Taken
                holder.textViewPercentage.setText(R.string.full_percentage);
            } else {
                //Someone has taken the pill! Calculate Percentage
                int min = Integer.parseInt(medicine.getPillsTaken());
                int max = Integer.parseInt(medicine.getPills());
                if (min < max) {
                    int takePercentage = (Integer.parseInt(medicine.getPillsTaken()) * 100 / Integer.parseInt(medicine.getPills()));
                    holder.textViewPercentage.setText((100 - takePercentage) + context.getString(R.string.percentage));
                } else {
                    holder.textViewPercentage.setText(R.string.zero_percentage);
                }
            }

            if (!medicine.getPillsTaken().equals("0")) {
                //Show Drop !! pill has been taken
                Context context = holder.iconMedicineLevel.getContext();
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_arrow_drop);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.color_price_drop));
                holder.iconMedicineLevel.setImageDrawable(drawable);
            }
        }
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public class MedicineHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewMedicineName)
        TextView textViewMedicineName;
        @BindView(R.id.textViewMedicineDescription)
        TextView textViewMedicineDescription;
        @BindView(R.id.textViewPercentage)
        TextView textViewPercentage;
        @BindView(R.id.iconMedicineLevel)
        ImageView iconMedicineLevel;
        @BindView(R.id.container)
        LinearLayout container;

        MedicineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
