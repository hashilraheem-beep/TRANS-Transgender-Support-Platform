package com.example.trans.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trans.R;
import com.example.trans.SessionManager;
import com.example.trans.Session_Manager;
import com.example.trans.bookingAdapter;
import com.example.trans.bookingmodel;
import com.example.trans.config;
import com.example.trans.databinding.FragmentDashboardBinding;
import com.example.trans.drAdapter;
import com.example.trans.drDataModel;
import com.example.trans.dr_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {
    RecyclerView recycle;
    private String url = config.baseurl+"bookinglist.php",uid;
    private ArrayList<bookingmodel> dataModelArrayList;
    private bookingAdapter M ;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycle = root.findViewById(R.id.notifi_list);
        HashMap<String,String>map=new SessionManager(getActivity()).getUserDetails();
        uid=map.get("id");
//        Toast.makeText(getActivity(), uid, Toast.LENGTH_SHORT).show();
        //      p = findViewById(R.id.progress);

        fetchingJSON();
        return root;
    }
    private void fetchingJSON() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        try {
//                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//                            p.setVisibility(View.GONE);

                            dataModelArrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);

                                dataModelArrayList.add(new bookingmodel(
                                        dataobj.getString("id"),
                                        dataobj.getString("user_id"),
                                        dataobj.getString("drname"),
                                        dataobj.getString("name")



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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("id",uid);
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
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void setupRecycler(){
        M= new bookingAdapter(getActivity(),dataModelArrayList);
        recycle.setHasFixedSize(true);
        recycle.setAdapter(M);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}