package snehal.adhav.userapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import snehal.adhav.userapp.R;
import snehal.adhav.userapp.constants.Constant;
import snehal.adhav.userapp.extras.AppPreference;
import snehal.adhav.userapp.fragment.LoginFragment;
import snehal.adhav.userapp.fragment.ProfileFragment;
import snehal.adhav.userapp.fragment.RegisterFragment;
import snehal.adhav.userapp.services.MyInterface;
import snehal.adhav.userapp.services.RetrofitClient;
import snehal.adhav.userapp.services.ServiceApi;

public class MainActivity2 extends AppCompatActivity implements MyInterface {
    FrameLayout container_fragment;
    public static AppPreference appPreference;

    public static ServiceApi serviceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        appPreference =new AppPreference(this);

        serviceApi= RetrofitClient.getApiClient(Constant.baseUrl.BASE_URl).create(ServiceApi.class);
        container_fragment=findViewById(R.id.fragment_container);
        if(container_fragment!=null)
        {
            if(savedInstanceState!=null){
                return;
            }
            if (appPreference.getLoginStatus()){
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new ProfileFragment()).commit();
            }
            else{
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new LoginFragment()).commit();

            }
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,new LoginFragment())
                .commit();
    }

    @Override
    public void register() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new RegisterFragment())

                .commit();
    }

    @Override
    public void login(String name, String email, String created_at) {
        appPreference.setDisplayEmail(email);
        appPreference.setDisplayName(name);
        appPreference.setDisplayDate(created_at);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new ProfileFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void logout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new LoginFragment())
                .addToBackStack(null)
                .commit();

    }
}