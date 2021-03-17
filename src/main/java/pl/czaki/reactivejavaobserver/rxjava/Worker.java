package pl.czaki.reactivejavaobserver.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.io.IOException;
import java.util.Random;

import static pl.czaki.reactivejavaobserver.observer.Downloader.readWebsite;

public class Worker {

    private static String[] url = {"http://bykowski.pl/spring-boot/",
            "http://bykowski.pl/mikroserwisy-wprowadzenie-i-praktyczny-przyklad/",
            "http://bykowski.pl/jenkins-zautomatyzuj-swoja-prace/","http://bykowski.pl/jenkins-zautomatyzuj-swoja-prace/"};


    public static void main(String[] args) {

        Observer<String> observer = new Observer<String>() {

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("finish!!!!");
            }

            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("start work");
            }

            @Override
            public void onNext(String s) {
                Thread thread = new Thread(() -> {
                    try {
                        readWebsite(s, new Random().nextInt() + ".html");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                System.out.println("finish one worker");
            }
        };
        Observable.fromArray(url).subscribe(observer);

    }
}
