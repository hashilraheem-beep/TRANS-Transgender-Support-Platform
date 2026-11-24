package com.example.trans.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trans.MainActivity;
import com.example.trans.Registration_Activity;
import com.example.trans.SessionManager;
import com.example.trans.config;
import com.example.trans.databinding.FragmentNotificationsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    String name,email,phone,id;
    String name1,email1,phone1,status,message,spass;
    String url= config.baseurl+"update.php";
    TextView edtname,edtmail,edtphone;
    EditText password;
    Button save;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        edtname= binding.name;
        edtmail=binding.email;
        edtphone= binding.phone;
        save= binding.save;
        password=binding.pass;
        name=new SessionManager(getActivity()).getUserDetails().get("name");
        id=new SessionManager(getActivity()).getUserDetails().get("id");
        edtname.setText(name);
        email=new SessionManager(getActivity()).getUserDetails().get("email");
        edtmail.setText(email);
        phone=new SessionManager(getActivity()).getUserDetails().get("phone");
        edtphone.setText(phone);
        spass=new SessionManager(getActivity()).getUserDetails().get("password");
        password.setText(spass);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

       // Toast.makeText(getContext(), "name:"+name, Toast.LENGTH_SHORT).show();

        // TextView textView = binding.textNotification;
      //  notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    private void update() {

        spass=password.getText().toString();
        if (TextUtils.isEmpty(spass))
        {

            password.setError("Required");
            password.requestFocus();
            return;
        }


        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject c = new JSONObject(response);
                    status=c.getString("status");
                    message=c.getString("message");
                    if (status.equals("0"))
                    {
                        Toast.makeText(getActivity(), "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "REGISTRATION SUCCESSFULL", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),String.valueOf(error), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("password",spass);

                params.put("id",id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}