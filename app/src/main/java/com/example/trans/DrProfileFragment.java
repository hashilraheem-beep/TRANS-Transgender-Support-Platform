package com.example.trans;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trans.databinding.FragmentNotificationsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DrProfileFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    String name, email, hospital, phone, id;
    String name1, email1, phone1, hospital1, status, message,password,types;
    String url = config.baseurl + "update1.php";
    TextView edtname, edtmail, edtphone, edthospital,type;
    EditText pass;
    Button update;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dr_profile, container, false);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
//          View root = binding.getRoot();
        edtname =root.findViewById(R.id.name1);
        edtmail = root.findViewById(R.id.email2);
        edthospital=root.findViewById(R.id.hospital2);
        edtphone = root.findViewById(R.id.phone2);
        pass=root.findViewById(R.id.password);
        type=root.findViewById(R.id.type1);
        update = root.findViewById(R.id.save);
        name = new Session_Manager(getActivity()).getUserDetails().get("name");
        id = new Session_Manager(getActivity()).getUserDetails().get("id");
        edtname.setText(name);
        email = new Session_Manager(getActivity()).getUserDetails().get("email");
        edtmail.setText(email);
        hospital= new Session_Manager(getActivity()).getUserDetails().get("hospital");
        edthospital.setText(hospital);
        phone = new Session_Manager(getActivity()).getUserDetails().get("phone");
        edtphone.setText(phone);
        password = new Session_Manager(getActivity()).getUserDetails().get("password");
        pass.setText(password);
        types = new Session_Manager(getActivity()).getUserDetails().get("type");
        type.setText(types);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        return root;
    }

    private void update() {

        password = pass.getText().toString();
        if (TextUtils.isEmpty(password)) {

            pass.setError("Required");
            pass.requestFocus();
            return;

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject c = new JSONObject(response);
                    status = c.getString("status");
                    message = c.getString("message");
                    if (status.equals("0")) {
                        Toast.makeText(getActivity(), "UPDATION FAILED", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "UPDATION SUCCESSFULL", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), login.class);
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
                Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("password", password);
                params.put("id", id);
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