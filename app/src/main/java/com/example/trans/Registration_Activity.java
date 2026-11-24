package com.example.trans;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class Registration_Activity extends AppCompatActivity {
    EditText name,age,email,phone,password;
    Button register;
    String name1,age1,email1,phone1,password1,status,message;
    String url= config.baseurl+"reg.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name=findViewById(R.id.edtname);
        age=findViewById(R.id.edtage);
        email=findViewById(R.id.edtemail);
        phone=findViewById(R.id.edtphone);
        password=findViewById(R.id.edtpassword);
        register=findViewById(R.id.REGISTER);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { userregistration();

            }
        });

    }
    private void  userregistration(){
        name1=name.getText().toString();
        age1=age.getText().toString();
        email1=email.getText().toString();
        phone1=phone.getText().toString();
        password1=password.getText().toString();
        if (TextUtils.isEmpty(name1))
        {

            name.setError("Required");
            name.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(age1))
        {
            age.setError("Required");
            age.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(email1))
        {
            email.setError("Required");
            email.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(phone1))
        {
            phone.setError("Required");
            phone.requestFocus();
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
                try {
                    JSONObject c = new JSONObject(response);
                    status=c.getString("status");
                    message=c.getString("message");
                    if (status.equals("0"))
                    {
                        Toast.makeText(Registration_Activity.this, "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Registration_Activity.this, "REGISTRATION SUCCESSFULL", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(Registration_Activity.this,MainActivity.class);
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
                Toast.makeText(Registration_Activity.this,String.valueOf(error), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name1);
                params.put("age",age1);
                params.put("email",email1);
                params.put("phone",phone1);
                params.put("password",password1);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }
}

