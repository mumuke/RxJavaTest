package image.cui.kejia.rxjavatest;



import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ckj on 2017/7/5.
 */

public interface MovieService {
    @GET("top250")
    Observable<MovieBean> getTopMovie(@Query("start") int start, @Query("count") int count);
}
