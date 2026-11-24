package com.example.trans;


//import static com.example.villagemanagement.Myprofile.isPhoneValid;
//import static com.example.villagemanagement.registration_activity.isEmailValid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Reg_activity extends AppCompatActivity {

    EditText name,email,phone,hospital,password;
    Button register;
    String name1,email1,phone1,hospital1,password1,status,message,spinner;
    String url= config.baseurl+"register.php";
    Spinner type;
    String ty[]={"Select User:","Doctor","Consultent"};

    private RequestQueue rQueue;
    private static ProgressDialog mProgressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        name=findViewById(R.id.edtname);
        email=findViewById(R.id.edtemail);
        type=findViewById(R.id.type);
        phone=findViewById(R.id.edtphone);
        hospital=findViewById(R.id.edthospital);
        password=findViewById(R.id.edtpassword);
        register=findViewById(R.id.REGISTER);
        ArrayAdapter adapter=new ArrayAdapter(Reg_activity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,ty);
        type.setAdapter(adapter);


//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
////                DatePickerDialog datePicker = new DatePickerDialog(Reg_activity.this,
////                        new DatePickerDialog.OnDateSetListener() {
////                            @Override
////                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int day) {
////                                Dob.setText(day + "/" + (monthOfYear + 1) + "/" + year);
////                            }
////                        }, year, month, day);
//                datePicker.show();
//
//            }
//        });



//        ArrayAdapter adapter=new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,scategory);
//        category.setAdapter(adapter);
//
//        ArrayAdapter adapter1=new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spaymentmode);
//        paymentmode.setAdapter(adapter1);

//        final DatePickerDialog.OnDateSetListener ddate = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                mycalendar.set(Calendar.YEAR, year);
//                mycalendar.set(Calendar.MONTH, monthOfYear);
//                mycalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel1();
//
//            }
//        };
//        Dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(caste.this, ddate, mycalendar
//                        .get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
//                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(caste.this,user_home_activity.class);
//                startActivity(intent);
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sub();
            }
        });
    }

//    private void updateLabel1() {
//
//        String myFormat = "dd/MM/yyyy";
//
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
//        sdf.applyPattern("EEE, d MMM yyyy");
//
//        Dob.setText(sdf.format(mycalendar.getTime()));
//    }

    private void sub() {
        name1=name.getText().toString();
        email1=email.getText().toString();
        hospital1=hospital.getText().toString();
        phone1=phone.getText().toString();
        password1=password.getText().toString();
        spinner=type.getSelectedItem().toString();
        if (TextUtils.isEmpty(spinner))
        {

            Toast.makeText(this,"select type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name1))
        {

            name.setError("Required");
            name.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(email1))
        {
            email.setError("Required");
            email.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(hospital1))
        {
            hospital.setError("Required");
            hospital.requestFocus();
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

        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 1);
    }


    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor =getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME));
                        Log.d("name ",displayName);

                        uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(final String pdfname, Uri pdffile) {
        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            showSimpleProgressDialog(getApplicationContext(), null, "Uploading image", false);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest( Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            removeSimpleProgressDialog();
                            Log.d("res",new String(response.data));
                            rQueue.getCache().clear();
                            try {

                                JSONObject jsonObject = new JSONObject(new String(response.data));

                                jsonObject.toString().replace("\\\\","");

                                status = jsonObject.getString("status");
                                message = jsonObject.getString("message");

                                if (status.equals("0"))
                                {
                                    Toast.makeText(Reg_activity.this, "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Reg_activity.this, "REGISTRATION SUCCESSFULL", Toast.LENGTH_SHORT).show();
                                    Intent i= new Intent(Reg_activity.this,login.class);
                                    startActivity(i);
                                    finish();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            removeSimpleProgressDialog();
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //add string parameters
                    params.put("name", name1);
                    params.put("email", email1);
                    params.put("hospital", hospital1);
                    params.put("phone", phone1);
                    params.put("type", spinner);
                    params.put("password", password1);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("filename", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            rQueue = Volley.newRequestQueue(getApplicationContext());
            rQueue.add(volleyMultipartRequest);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public  void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            Log.e("Log", "inside catch IllegalArgumentException");
            ie.printStackTrace();
        } catch (RuntimeException re) {
            Log.e("Log", "inside catch RuntimeException");
            re.printStackTrace();
        } catch (Exception e) {
            Log.e("Log", "Inside catch Exception");
            e.printStackTrace();
        }
    }
    public void showSimpleProgressDialog(Context context, String title,
                                         String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show( context, title, msg );
                mProgressDialog.setCancelable( isCancelable );
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

}

