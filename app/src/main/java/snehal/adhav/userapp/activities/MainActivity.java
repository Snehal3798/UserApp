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

public class MainActivity extends AppCompatActivity  {

    Thread timer;
ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = findViewById(R.id.giffy);
        Glide.with(this).load(R.drawable.mrdude).into(imageView);

        timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent_my = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent_my);
                }
            }
        };
        timer.start();


    }
}