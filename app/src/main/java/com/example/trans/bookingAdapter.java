package com.example.trans;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


    public class bookingAdapter extends RecyclerView.Adapter<bookingAdapter.MyViewHolder>{

        private LayoutInflater inflater;
        private ArrayList<bookingmodel> dataModelArrayList;
        private Context c;

        public bookingAdapter(Context ctx, ArrayList<bookingmodel> dataModelArrayList){
            c = ctx;
            inflater = LayoutInflater.from(c);
            this.dataModelArrayList = dataModelArrayList;
        }



        @NonNull
        @Override
        public com.example.trans.bookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.bookinglist_notifi, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }



        @Override
        public void onBindViewHolder(@NonNull com.example.trans.bookingAdapter.MyViewHolder holder, final int position) {
            holder.drname.setText("DR Name:"+dataModelArrayList.get(position).getDr_name());
            holder.id.setText(dataModelArrayList.get(position).getU_id());
            holder.name.setText(dataModelArrayList.get(position).getDName());
            holder.bkid.setText(dataModelArrayList.get(position).getId());



        }

        @Override
        public int getItemCount() {
            return dataModelArrayList.size();
        }




        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView id,name,bkid,drname;



            public MyViewHolder(View itemView) {
                super(itemView);
                drname= itemView.findViewById(R.id.drname);
                id= itemView.findViewById(R.id.id_not);
                name= itemView.findViewById(R.id.nam_not);
                bkid= itemView.findViewById(R.id.book_not);

            }

        }

    }




