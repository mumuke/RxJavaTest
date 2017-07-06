package image.cui.kejia.rxjavatest;


import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ckj on 2017/7/6.
 */

public interface LoginService {
    @POST("LoginAction")
    Observable<UserBean> login(@Query("username") String username, @Query("password") String password);
}
