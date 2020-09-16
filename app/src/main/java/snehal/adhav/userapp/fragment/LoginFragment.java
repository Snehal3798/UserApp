package snehal.adhav.userapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import snehal.adhav.userapp.R;
import snehal.adhav.userapp.activities.MainActivity;
import snehal.adhav.userapp.activities.MainActivity2;
import snehal.adhav.userapp.model.User;
import snehal.adhav.userapp.services.MyInterface;


public class LoginFragment extends Fragment {
  Button loginBtn;
  EditText emailInput,passwordInput;
  MyInterface myInterface_login;
  Button register;
    CheckBox show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailInput=view.findViewById(R.id.inputEmail);
        passwordInput=view.findViewById(R.id.inputPassword);
        loginBtn=view.findViewById(R.id.btnlogin);
        show = view.findViewById(R.id.showPass);
        loginBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              loginUser();
            //Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
          }
        });
        showPass();
        //Register
      register=view.findViewById(R.id.btnsignup);
      register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
            myInterface_login.register();
        }
      });
        return view;
    }

    public void showPass(){
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString();
        String password=passwordInput.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            MainActivity2.appPreference.showToast("Enter your emailId");
        }else if (TextUtils.isEmpty(password))
        {
            MainActivity2.appPreference.showToast("Enter your password");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            MainActivity2.appPreference.showToast("Email id is invalid");
        }else if (password.length()<6)
        {
            MainActivity2.appPreference.showToast("Password too short!");
        }else
        {
            Call<User> userCall = MainActivity2.serviceApi.doLogin(email,password);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                    if(response.body().getResponse().equals("data"))
                    {
                        MainActivity2.appPreference.setLoginStatus(true);
                        myInterface_login.login(response.body().getName(),response.body().getEmail(),response.body().getCreatedAt());

                        //Toast.makeText(getActivity(), "Login Successfull!!", Toast.LENGTH_SHORT).show();
                    }
                    else  if(response.body().getResponse().equals("login_failed")){
                        Toast.makeText(getActivity(), "Login Failed!!", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {


                }
            });


        }
    }






    public LoginFragment() {
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_login= (MyInterface) activity;
    }
}