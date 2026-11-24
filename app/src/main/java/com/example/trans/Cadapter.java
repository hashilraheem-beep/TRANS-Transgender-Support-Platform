package com.example.trans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cadapter extends RecyclerView.Adapter<Cadapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Cmodel> dataModelArrayList;
    private Context c;

    public Cadapter(Context ctx,ArrayList<Cmodel> dataModelArrayList){
        c=ctx;
        inflater=LayoutInflater.from(c);
        this.dataModelArrayList=dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_c,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.Name.setText(dataModelArrayList.get(position).getName());
        holder.Email.setText(dataModelArrayList.get(position).getEmail());
        holder.Phone.setText(dataModelArrayList.get(position).getPhone());
        holder.Consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Booking(position);
            }
        });
    }

    private void Booking(int position) {
        String id=dataModelArrayList.get(position).getId();
        String cname=dataModelArrayList.get(position).getName();
        String name=new SessionManager(c).getUserDetails().get("name");
        String Id=new SessionManager(c).getUserDetails().get("id");
        String phone=new SessionManager(c).getUserDetails().get("phone");
        String url= config.baseurl+"consult.php";

        StringRequest stringRequest= new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res>>",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("message");
                    if (status.equals("0"))
                    {
                        Toast.makeText(c, "BOOKING FAILED", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(c, "BOOKING SUCCESSFULL", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(c,MainActivity.class);
                        c.startActivity(i);
                        ((Activity)c).finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(c,String.valueOf(error), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("Id",Id);
                params.put("drname",cname);
                params.put("name",name);
                params.put("phone",phone);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        requestQueue.add(stringRequest);
    }


    @Override
    public int getItemCount() { return dataModelArrayList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Email,Phone;
        Button Consult;

        public MyViewHolder(View itemView){
            super(itemView);
            Name = itemView.findViewById(R.id.namec);
            Email = itemView.findViewById(R.id.typec);
            Phone = itemView.findViewById(R.id.phonec);
            Consult = itemView.findViewById(R.id.consult1c);

        }
    }
}
