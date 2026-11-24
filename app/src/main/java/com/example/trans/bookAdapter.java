package com.example.trans;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<bookingDataModel> dataModelArrayList;
    private Context c;

    public bookAdapter(Context ctx, ArrayList<bookingDataModel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public bookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.booking_list, parent, false);
        bookAdapter.MyViewHolder holder = new bookAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bookAdapter.MyViewHolder holder, final int position) {

        holder.Name.setText(dataModelArrayList.get(position).getName());
        holder.Phone.setText(dataModelArrayList.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Phone;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Phone = itemView.findViewById(R.id.phone);

        }
    }
}

