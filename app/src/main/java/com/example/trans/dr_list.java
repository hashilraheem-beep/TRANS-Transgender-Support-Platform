package com.example.trans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class dr_list extends AppCompatActivity {

    RecyclerView recycle;
    private String url = config.baseurl+"list1.php";
    private ArrayList<drDataModel> dataModelArrayList;
    private drAdapter M ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_list);

        recycle = findViewById(R.id.list1);

        //      p = findViewById(R.id.progress);

        fetchingJSON();
    }
    private void fetchingJSON() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(dr_list.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            Toast.makeText(dr_list.this, response, Toast.LENGTH_SHORT).show();
//                            p.setVisibility(View.GONE);

                            dataModelArrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);

                                dataModelArrayList.add(new drDataModel(
                                        dataobj.getString("id"),
                                        dataobj.getString("name"),
                                        dataobj.getString("email"),
                                        dataobj.getString("hospital"),
                                        dataobj.getString("phone"),
                                        dataobj.getString("type"),
                                        dataobj.getString("password")


                                ));
                            }
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        p.setVisibility(View.GONE);
                        Toast.makeText(dr_list.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>map=new HashMap<>();
//                map.put("type","Doctor");
                return map;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }

            @Override
            public void retry(VolleyError error) {
//                p.setVisibility(View.GONE);
                Toast.makeText(dr_list.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(dr_list.this);
        requestQueue.add(stringRequest);
    }


    private void setupRecycler(){
        M= new drAdapter(dr_list.this,dataModelArrayList);
        recycle.setHasFixedSize(true);
        recycle.setAdapter(M);
        recycle.setLayoutManager(new LinearLayoutManager(dr_list.this, RecyclerView.VERTICAL, false));
    }
}