package com.uk.ac.tees.w92214775.fixitexpress.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.uk.ac.tees.w92214775.fixitexpress.R;
import com.uk.ac.tees.w92214775.fixitexpress.bookingConformPage;

import java.util.List;

public class bookingAdapter extends RecyclerView.Adapter<bookingAdapter.booking_VH> {

    private List<bookingModel> bookingModelList;
    private Context context;

    public bookingAdapter(List<bookingModel> bookingModelList, Context context) {
        this.bookingModelList = bookingModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public booking_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_booking_layout,parent,false);
        return new booking_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull booking_VH holder, final int position) {
        holder.gNAME.getEditText().setText(bookingModelList.get(position).getbName());
        holder.gDATE.getEditText().setText(bookingModelList.get(position).getbDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toBookingConform = new Intent(context, bookingConformPage.class);
                toBookingConform.putExtra("BID",bookingModelList.get(position).getbBID());
                context.startActivity(toBookingConform);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingModelList.size();
    }

    public class booking_VH extends RecyclerView.ViewHolder {

        private TextInputLayout gNAME,gDATE;

        public booking_VH(@NonNull View itemView) {
            super(itemView);
            gNAME = (TextInputLayout)itemView.findViewById(R.id.appointmentsFrag_name);
            gDATE = (TextInputLayout)itemView.findViewById(R.id.appointmentsFrag_date);


        }
    }
}
