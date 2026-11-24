package com.example.trans.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trans.VideosActivity;
import com.example.trans.databinding.FragmentHomeBinding;
import com.example.trans.dr_list;
import com.example.trans.feedback;
import com.example.trans.hospital;
import com.example.trans.list_cActivity;
import com.example.trans.taxi_home;
import com.example.trans.taxiservice;

import mumayank.com.airlocationlibrary.AirLocation;

public class HomeFragment extends Fragment {
CardView Taxi,Hospital,Hotel,consult,video,booking,Feedback;
AirLocation airLocation;
String latitude,longitude;
    private FragmentHomeBinding binding;
    static ProgressDialog mProgressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

Taxi=binding.Taxi;
Hospital=binding.Hospital;
Hotel=binding.Hotel;
        consult=binding.counsil;
        booking=binding.consult;
        video=binding.Videos;
        Feedback=binding.feedback;
//fetchLocation();
video.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), VideosActivity.class));
    }
});
booking.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), dr_list.class));

    }
});
        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), list_cActivity.class));
            }
        });
        Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), feedback.class));
            }
        });
Hospital.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fetchLocation();
        Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"?q=HOSPITALS");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
});
Hotel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fetchLocation();
        Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"?q=HOTELS");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
});
Taxi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getActivity(), taxi_home.class);
        startActivity(i);
    }
});
//Hospital.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        Intent i=new Intent(getActivity(), hospital.class);
//        startActivity(i);
//    }
//});

        return root;
    }
    public void fetchLocation() {
//
        showSimpleProgressDialog(getActivity(), "Loading",
                "Fetching current location coordinates", true);

        //AirLocation fetching process...
        airLocation = new AirLocation(getActivity(), true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(@NonNull android.location.Location location) {
               removeSimpleProgressDialog();
                Toast.makeText(getActivity(), "Location fetched successfully", Toast.LENGTH_LONG).show();
                double lat, lng;
                lat = location.getLatitude();
                lng = location.getLongitude();
                latitude=Double.toString(lat);
                longitude =Double.toString(lng);
                Toast.makeText(getActivity(), latitude+longitude, Toast.LENGTH_SHORT).show();

                //checkPermissionSMS();
            }

            @Override
            public void onFailed(@NonNull AirLocation.LocationFailedEnum locationFailedEnum) {
                removeSimpleProgressDialog();
                Toast.makeText(getActivity(), "Location fetching failed. Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public static void removeSimpleProgressDialog() {
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
    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
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


}