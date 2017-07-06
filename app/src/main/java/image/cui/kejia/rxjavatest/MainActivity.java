package image.cui.kejia.rxjavatest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //    private Disposable mDisposable;
    private int i = 0;


    private Button mBtn;
    private ListView mListView;
    private List<MovieBean.SubjectsBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.click_me_BN);
        mListView = (ListView) findViewById(R.id.result_TV);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovie();
            }
        });
        //创建上游
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.i(TAG, "emitter 1");
//                emitter.onNext(1);
//                Log.i(TAG, "emitter 2");
//                emitter.onNext(2);
//                Log.i(TAG, Thread.currentThread().getName());
//                Log.i(TAG, "emitter 3");
//                emitter.onNext(3);
//                Log.i(TAG, "onComplete");
//                emitter.onComplete();
//            }
//        });

        //创建一个下游
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.i(TAG, "subscribe");
//                mDisposable = d;
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.i(TAG, value + "");
//                Log.i(TAG, Thread.currentThread().getName());
//                i++;
//                if (i == 2) {
//                    //调用dispose()这个方法，下游就不再接收事件，但是上游还是会发送事件
//                    mDisposable.dispose();
//                    Log.i(TAG, mDisposable.isDisposed() + "-------");
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "error");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "complete");
//            }
//        };
        //建立连接
//        observable.subscribe(observer);
        //subscribeOn（）在上游一个新线程运行且只调用的第一次有效，observeOn（）在下游主线程运行然后切换成IO运行，且每次都调用
        //Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
        //Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
        //Schedulers.newThread() 代表一个常规的新线程
        //AndroidSchedulers.mainThread() 代表Android的主线程
//        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io()).subscribe(observer);

//        验证带一个Consumer参数的方法public final Disposable subscribe(Consumer<? super T> onNext) {}
//        Consumer<Integer> observer1 = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.i(TAG, "onNext: " + integer);
//                Log.i(TAG, Thread.currentThread().getName());
//            }
//        };
//        observable.subscribe(observer1);
//        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer1);
    }

    private void getMovie() {
        Subscriber<MovieBean> subscriber = new Subscriber<MovieBean>() {

            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                https:
//api.douban.com/v2/movie/top250?start=0&count=10
                Log.e(TAG, "error");
            }

            @Override
            public void onNext(MovieBean movieBean) {
                list = new ArrayList<MovieBean.SubjectsBean>();
                list = movieBean.getSubjects();
                MovieAdapter mMovieAdapter = new MovieAdapter(MainActivity.this, list);
                mListView.setAdapter(mMovieAdapter);
            }
        };
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }

    class MovieAdapter extends BaseAdapter {
        private Context context;
        private List<MovieBean.SubjectsBean> datalists;

        public MovieAdapter(Context context, List<MovieBean.SubjectsBean> lists) {
            this.context = context;
            this.datalists = lists;
        }

        @Override
        public int getCount() {
            return datalists == null ? 0 : datalists.size();
        }

        @Override
        public Object getItem(int position) {
            return datalists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewholder = null;
            if (convertView == null) {
                viewholder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item, null, false);
                viewholder.mTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewholder.mName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) convertView.getTag();
            }
            viewholder.mTitle.setText(datalists.get(position).getTitle());
            viewholder.mName.setText(datalists.get(position).getCasts().get(0).getName());
            return convertView;
        }
    }

    class ViewHolder {
        TextView mTitle;
        TextView mName;
    }
}
