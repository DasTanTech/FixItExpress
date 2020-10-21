package com.uk.ac.tees.w92214775.fixitexpress.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uk.ac.tees.w92214775.fixitexpress.R;
import com.uk.ac.tees.w92214775.fixitexpress.servicePage;

import java.util.List;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.home_VH> {

    private List<homeModel> homeModelList;
    private Context context;



    public homeAdapter(List<homeModel> homeModelList, Context context) {
        this.homeModelList = homeModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public home_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_home_layout,parent,false);
        return new home_VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull home_VH holder, final int position) {
        Glide.with(context).load(homeModelList.get(position).getS_PICURL()).into(holder.VHpic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toService = new Intent(context, servicePage.class);
                toService.putExtra("SID",homeModelList.get(position).getS_ID());
                context.startActivity(toService);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }

    public class home_VH extends RecyclerView.ViewHolder {
        private ImageView VHpic;
        public home_VH(@NonNull View itemView) {
            super(itemView);
            VHpic = (ImageView)itemView.findViewById(R.id.singleHomeLayout_imageView);
        }
    }
}
