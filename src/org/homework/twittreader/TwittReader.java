package org.homework.twittreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TwittReader {


    public static void main(String[] args) {

        long now = System.currentTimeMillis();

        Vocabulary vocabulary = new Vocabulary();
        try {
            vocabulary.init("voc.csv");
        } catch (AppException e) {
            System.out.println(e.getMessage());
            throw  new RuntimeException(e);
        }
        long timeAfterCreationVoc = System.currentTimeMillis();
        try (FileReader fileReader = new FileReader("1984.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bodyBuilder.append(line);
            }
            Twitt twitt = new Twitt(bodyBuilder.toString());
            TwittAnalizer.analizeTwitt(twitt, vocabulary);
            System.out.println("Twitter weight: " + twitt.getWeight());
        } catch (IOException e) {
            System.out.println(e);
        }
        long finish = System.currentTimeMillis();
        System.out.println(" time ellapsed (ms): " + (finish - now));
        System.out.println(" time ellapsed after creation vocabulary (ms): " + (finish - timeAfterCreationVoc));
    }
}
