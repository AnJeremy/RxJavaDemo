package cn.syxg.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RXJAVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建上游  发送时间
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

                emitter.onComplete();

            }

        });

        //创建下游 接受事件

        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe");
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG,"onNext"+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete");
            }
        };

        //创建链接
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        });

        Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {


            }
        };

        stringObservable.subscribe(stringObserver);
    }
}
