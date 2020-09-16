package snehal.adhav.userapp.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import snehal.adhav.userapp.model.User;


public interface ServiceApi {
    @GET("register.php")
    Call<User> doRegistration(
            @Query("name") String name,
            @Query("email") String email,

            @Query("password") String password

    );
    @GET("login.php")
    Call<User> doLogin(
            @Query("email") String email,
            @Query("password") String password
    );
}
