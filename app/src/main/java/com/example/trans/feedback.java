package com.example.trans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
import java.util.regex.Pattern;

public class feedback extends AppCompatActivity {
    EditText w_name,w_rating;
    Button w_st;
    RatingBar ratingBar;
    String nid,name,address,mobile,date,time,wuserid,workerid,status,wname,wprofile;
    String Status,message;
    float rating;
    String userid,dname;
    String review,techid,techname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        w_name=findViewById( R.id.w_name );
        w_rating=findViewById( R.id.w_rating );
        w_st=findViewById( R.id.w_st );
        ratingBar=findViewById( R.id.ratingbar );

        HashMap<String,String> data= new SessionManager(feedback.this).getUserDetails();
        userid=data.get( "id" );
//        nid=getIntent().getStringExtra( "id" );
//        name=getIntent().getStringExtra( "email" );
//        Toast.makeText(this, userid+name, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, techname, Toast.LENGTH_SHORT).show();



        w_st.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub();
            }
        } );

    }

    private void sub() {
        dname=w_name.getText().toString();
        rating=ratingBar.getRating();
        review=w_rating.getText().toString();
        if (TextUtils.isEmpty( dname)) {
            w_name.requestFocus();
            w_name.setError( "Required Field" );
            return;
        }
        if (TextUtils.isEmpty( review )) {
            w_rating.requestFocus();
            w_rating.setError( "Required Field" );
            return;
        }


        String url = config.baseurl + "feedback.php";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject c1 = new JSONObject( response );
                            Status = c1.getString( "status" );
                            message = c1.getString( "message" );

                            if (Status.equals( "0" )) {
                                Toast.makeText(feedback.this, "Failed", Toast.LENGTH_SHORT ).show();


                            } else {
                                Toast.makeText(feedback.this, "Rating Added", Toast.LENGTH_SHORT ).show();
                                startActivity(new Intent(getApplicationContext(),Home_activity.class));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(feedback.this, String.valueOf( error ), Toast.LENGTH_SHORT ).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put( "useremail",name );
                params.put( "userid",userid );
                params.put( "review",review );
                params.put( "rating", String.valueOf(rating ));
                params.put( "dname",dname );

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(feedback.this);
        requestQueue.add( stringRequest );



    }


    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();



    }
}