package pl.czaki.reactivejavaobserver.observer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Downloader() {

    }


   private String[] url = {"http://bykowski.pl/spring-boot/",
            "http://bykowski.pl/mikroserwisy-wprowadzenie-i-praktyczny-przyklad/",
           "http://bykowski.pl/jenkins-zautomatyzuj-swoja-prace/","http://bykowski.pl/jenkins-zautomatyzuj-swoja-prace/"};

    public Downloader(MyObservable myObservable){

        for (int i = 0; i < url.length; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    readWebsite(url[finalI], finalI + ".html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        myObservable.onFinish();
    }



    public static void readWebsite(String link, String fileName) throws IOException {
        URL oto = new URL(link);
        BufferedReader in = new BufferedReader(new InputStreamReader(oto.openStream()));

        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        in.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
        bw.write(stringBuilder.toString());
        bw.close();
    }
}
