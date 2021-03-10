package org.homework.twittreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwittReader {


    public static void main(String[] args) {

        long now = System.currentTimeMillis(); // profiling
        List<Twitt> twitts = new ArrayList<>();
        Vocabulary vocabulary = new Vocabulary();
        try {
            vocabulary.init("sentiments.csv");
        } catch (AppException e) {
            System.out.println(e.getMessage());
            throw  new RuntimeException(e);
        }
        long timeAfterCreationVoc = System.currentTimeMillis(); // profiling

        try (FileReader fileReader = new FileReader("family_tweets2014.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().length() > 1) {
                    Twitt twitt = new Twitt(line);
                    TwittAnalizer.analizeTwitt(twitt, vocabulary);
                    twitts.add(twitt);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        long finish = System.currentTimeMillis(); // profiling
        System.out.println(" time ellapsed (ms): " + (finish - now)); // profiling
        System.out.println(" time ellapsed after creation vocabulary (ms): " + (finish - timeAfterCreationVoc)); // profiling

    }
}
