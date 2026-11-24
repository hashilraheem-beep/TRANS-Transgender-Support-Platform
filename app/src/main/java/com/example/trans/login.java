package com.example.trans;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText name,password;
    TextView signup;
    Button login;
    String name1,password1,status,message,id,name2,email1,hospital1,phone1,type,password2;
    String url = config.baseurl+"login1.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.edtname);
        password=findViewById(R.id.edtpassword);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,Reg_activity.class));
            }
        });
        login=findViewById(R.id.LOGIN);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();


            }
        });


    }
    public void userlogin(){
        name1=name.getText().toString();
        password1=password.getText().toString();
        if (TextUtils.isEmpty(name1))
        {
            name.setError("Required");
            name.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(password1))
        {
            password.setError("Required");
            password.requestFocus();
            return;
        }
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                try {
                    JSONObject c = new JSONObject(response);
                    status = c.getString("status");
                    message = c.getString("message");
                    id = c.getString("id");
                    name2=c.getString("name");
                    email1=c.getString("email");
                    hospital1=c.getString("hospital");
                    phone1=c.getString("phone");
                    type=c.getString("type");
                    password2=c.getString("password");
                    if (status.equals("0")) {
                        Toast.makeText(login.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(login.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
                        new Session_Manager(login.this).createLoginSession(id,name2,email1,hospital1,phone1,type,password2);
                        Intent i = new Intent(login.this, dr_home.class);
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, String.valueOf(error), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",name1);
                params.put("password",password1);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

