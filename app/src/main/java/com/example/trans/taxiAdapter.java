package com.example.trans;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class taxiAdapter extends RecyclerView.Adapter<taxiAdapter.MyViewHolder>{

private LayoutInflater inflater;
private ArrayList<taxiDataModel> dataModelArrayList;
private Context c;

public taxiAdapter(Context ctx, ArrayList<taxiDataModel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(c);
        this.dataModelArrayList = dataModelArrayList;
        }



@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_taxi_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
        }



@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

    holder.Vname.setText(dataModelArrayList.get(position).getVname());
    holder.Vtype.setText(dataModelArrayList.get(position).getVehicletype());
    holder.Dname.setText(dataModelArrayList.get(position).getDname());
    holder.Phone.setText(dataModelArrayList.get(position).getPhone());
    holder.Stand.setText(dataModelArrayList.get(position).getStand());


    Picasso.get().load(config.imgurl+dataModelArrayList.get(position).getImage()).into(holder.img);
    Toast.makeText(c, dataModelArrayList.get(position).getImage(), Toast.LENGTH_SHORT).show();

}

@Override
public int getItemCount() {
        return dataModelArrayList.size();
        }




        class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Vname,Dname,Vtype,Phone,Stand;
        ImageView img;


    public MyViewHolder(View itemView) {
        super(itemView);
        Vname = itemView.findViewById(R.id.name);
        Vtype= itemView.findViewById(R.id.type);
        Dname= itemView.findViewById(R.id.dname);
        Phone= itemView.findViewById(R.id.phone);
        Stand= itemView.findViewById(R.id.loc);
        img=itemView.findViewById(R.id.imagestaxi);
    }

}

}


