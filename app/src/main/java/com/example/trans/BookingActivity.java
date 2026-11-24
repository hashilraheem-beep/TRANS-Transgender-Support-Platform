package com.example.trans;

import static okhttp3.internal.http.HttpDate.format;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8;
    EditText edittext1, editText2;
    Calendar mycalendar, mcurrenttime;
    Button dbutton;
    String dayOfTheWeek;
    String url = config.baseurl+"consult.php";
    String patientName,drid,doctorusername,drphone, userid, department, dremail, drhospital,username, userphone,bookingtime,
            bookingdate, status,drname, error,age,disease,phone,working_time,avday,DoctorPhone,key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        textView1=findViewById(R.id.duser);
        textView2=findViewById(R.id.ddpt);
        textView3=findViewById(R.id.dqu);
        textView4=findViewById(R.id.dex);
        textView5=findViewById(R.id.dhos);
//        textView6=findViewById(R.id.dphn);
//        textView7=findViewById(R.id.dwrt);
//        textView8=findViewById(R.id.davd);
        edittext1 = findViewById(R.id.dedittext);
        editText2 = findViewById(R.id.tedittext);
        mycalendar = Calendar.getInstance();
        mcurrenttime = Calendar.getInstance();
        dbutton = findViewById(R.id.dpay);



        HashMap<String, String> user = new SessionManager(BookingActivity.this).getUserDetails();
        age=user.get("age");
        //Toast.makeText(DlistActivity.this, patientName+age+phone+disease+key, Toast.LENGTH_SHORT).show();

        Intent in=getIntent();

        drid = in.getStringExtra("drid");
        drname = in.getStringExtra("drname");
        dremail = in.getStringExtra("dremail");
        drhospital = in.getStringExtra("drhospital");
        drphone = in.getStringExtra("drphone");
        userid=in.getStringExtra("userid");
        username = in.getStringExtra("username");
        userphone= in.getStringExtra("userphone");

        textView1.setText("Doctor ID : "+drid);
        textView2.setText("Doctor Name : "+drname);
        textView3.setText("Email : "+dremail);
        textView4.setText("Hospital Name: "+drhospital);
        textView5.setText("DoctorPhone : "+drphone);






        dbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR, year);
                mycalendar.set(Calendar.MONTH, monthOfYear);
                mycalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel1();

            }
        };
        edittext1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookingActivity.this, date, mycalendar
                        .get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = mcurrenttime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrenttime.get(Calendar.MINUTE);
                TimePickerDialog mTimepicker;
                mTimepicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        editText2.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                mTimepicker.setTitle("select time");
                mTimepicker.show();
            }
        });
    }

    private void Booking() {
//        Toast.makeText(this, textView8.getText().toString(), Toast.LENGTH_SHORT).show();
        bookingdate = edittext1.getText().toString();
        bookingtime = editText2.getText().toString();
        if (TextUtils.isEmpty(bookingdate.trim())) {
            edittext1.setError("Please enter username");
            edittext1.requestFocus();
            return;
        }

        else if (TextUtils.isEmpty(bookingtime.trim())) {
            editText2.setError("Please enter password");
            editText2.requestFocus();
            return;
        }
//        if(textView8.getText().toString().contains( dayOfTheWeek)){
//
//            Toast.makeText(this, "Date : " +bookingdate, Toast.LENGTH_SHORT).show();
//
//        }
//         else {
//             edittext1.setError("Invalid day");
//             edittext1.requestFocus();
//             return;
//         }
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BookingActivity.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("Status");
                            error = c.getString("Error");
                            if (status.equals("0")) {
                                Toast.makeText(BookingActivity.this, error, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(BookingActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(BookingActivity.this, PayActivity.class);
//                                startActivity(i);
//                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("drid", drid);
                map.put("drname", drname);
                map.put("dremail", dremail);
                map.put("drhospital", drhospital);
                map.put("drphone",drphone);
                map.put("userid",userid);
                map.put("username",username);
                map.put("userphone",userphone);
                map.put("age",age);
                map.put("booking_date",bookingdate);
                map.put("booking_time",bookingtime);

                return map;

            }

        };
        Volley.newRequestQueue(BookingActivity.this).add(request);
    }
    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        sdf.applyPattern("EEE, d MMM yyyy");

        edittext1.setText(sdf.format(mycalendar.getTime()));

        sdf = new SimpleDateFormat("EEE");
        Date d_name = new Date(format(mycalendar.getTime()));
        dayOfTheWeek = sdf.format(d_name);

        // Toast.makeText(this, dayOfTheWeek, Toast.LENGTH_SHORT).show();

    }

}
