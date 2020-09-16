package snehal.adhav.userapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import snehal.adhav.userapp.R;
import snehal.adhav.userapp.activities.MainActivity;
import snehal.adhav.userapp.activities.MainActivity2;
import snehal.adhav.userapp.model.User;


public class RegisterFragment extends Fragment {
    CheckBox rshow;
    ImageView rback;
EditText nameInput_reg,emailInput_reg,passwordInput_reg;
Button button_reg;

    public RegisterFragment() {
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
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        nameInput_reg=view.findViewById(R.id.edFisrtPersonName);
        emailInput_reg=view.findViewById(R.id.edEmailIDPerson);

        passwordInput_reg=view.findViewById(R.id.edPassword);
        rshow =  view.findViewById(R.id.rshowPass);
        button_reg=view.findViewById(R.id.signupbutton);
        showPass();
        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

            private void registerUser() {
                final String name=nameInput_reg.getText().toString().trim();
                String email=emailInput_reg.getText().toString().trim();

                String password=passwordInput_reg.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    MainActivity2.appPreference.showToast("Name Required");
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    MainActivity2.appPreference.showToast("Email Required");
                }

                else if(TextUtils.isEmpty(password)){
                    MainActivity2.appPreference.showToast("Password Required ");
                }
                else if(password.length()<6){
                    MainActivity2.appPreference.showToast(" Password To short ");
                }

                else{
                    //Data on server
                    Call<User> userCall = MainActivity2.serviceApi.doRegistration(name,email,password);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            Log.i("My AppResponse",response.body().getResponse());
                           // Toast.makeText(getContext(), "Registeration Done", Toast.LENGTH_SHORT).show();
                            System.out.println("My AppResponse"+response.body().getResponse());
                            if(response.body().getResponse().matches("inserted"))
                            {
                                showMsg("Successfully Registered","Welcome"+name);
                            }
                            else if(response.body().getResponse().matches("exists")){
                                showMsg("Already Registered","Welcome"+name);
                            }
                            else{
                                showMsg("Invalid Data","Insert valid data");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {
                            System.out.println("Error"+t.getMessage());
                            //Toast.makeText(getContext(), "Registeration Failed", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }
        });
        return view;
    }

    public void showPass(){
        rshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    passwordInput_reg.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    passwordInput_reg.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });
    }
    private void showMsg(String title, String msg) {
       final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.show();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cleartext();
                builder.setCancelable(true);


            }
        });
        builder.show();
    }


    public void cleartext(){
        emailInput_reg.setText("");
        nameInput_reg.setText("");
        passwordInput_reg.setText("");

    }


}
