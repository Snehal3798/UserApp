package snehal.adhav.userapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import snehal.adhav.userapp.R;
import snehal.adhav.userapp.activities.MainActivity;
import snehal.adhav.userapp.activities.MainActivity2;
import snehal.adhav.userapp.services.MyInterface;


public class ProfileFragment extends Fragment {
TextView name,email;
Button logout;

MyInterface myInterface_profile;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View  view=inflater.inflate(R.layout.fragment_profile, container, false);
          name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        logout=view.findViewById(R.id.logoutBtn);
        String Name="Hi"+ MainActivity2.appPreference.getDisplayName();
        name.setText(Name);
        String Email="Registered Email"+MainActivity2.appPreference.getDisplayEmail()+"\nRegistered_At"+MainActivity2.appPreference.getDisplayDate();
        email.setText(Email);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            myInterface_profile.logout();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_profile= (MyInterface) activity;

    }
}