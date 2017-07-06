package image.cui.kejia.rxjavatest;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ckj on 2017/7/5.
 */

public class HttpMethods {
    public static final String BASIC_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit mRetrofit;
    private MovieService mMovieService;
    private static HttpMethods mHttpMethods;

    private HttpMethods() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder().
                client(builder.build()).
                //增加返回值是gson的支持
                        addConverterFactory(GsonConverterFactory.create()).
                //增加返回Observable的支持
                        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        baseUrl(BASIC_URL).
                        build();
        //java的动态代理模式
        mMovieService = mRetrofit.create(MovieService.class);
    }

    public static HttpMethods getInstance() {
        if (mHttpMethods == null) {
            mHttpMethods = new HttpMethods();
        }
        return mHttpMethods;
    }

    public void getTopMovie(Subscriber<MovieBean> subscriber, int start, int count) {
        Observable<MovieBean> observable = mMovieService.getTopMovie(start, count);
        observable.
                subscribeOn(Schedulers.io()).
                unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(subscriber);
    }
}
