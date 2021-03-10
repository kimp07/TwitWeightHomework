package org.homework.twittreader;


import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Vocabulary {

    private int minSantimentLength;
    private int maxSantimentLength;
    private String[] storageSantiments;
    private float[] storageWieghts;

    private void addStorageItem(String item, float weight, Map<String, Float> tempStorage) {
        if (!tempStorage.containsKey(item)) {
            int itemLength = item.length();
            if (minSantimentLength == 0 || itemLength < minSantimentLength) {
                minSantimentLength = itemLength;
            }
            if (itemLength > maxSantimentLength) {
                maxSantimentLength = itemLength;
            }
            tempStorage.put(item, weight);
        }
    }

    private void parseCsvString(String[] csvString, int index, Map<String, Float> tempStorage) {
        try {
            String santiment = csvString[0];
            float weight = Float.parseFloat(csvString[1]);
            if (santiment.length() > 0) {
                addStorageItem(santiment, weight, tempStorage);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid line in csv file :" + index);
        }
    }

    public void init(String fileName) throws AppException {
        Map<String, Float> tempStorage = new TreeMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {

            String[] nextLine;
            int index = 0;
            while ((nextLine = reader.readNext()) != null) {
                index++;
                parseCsvString(nextLine, index, tempStorage);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new AppException(e);
        }
        if (tempStorage.isEmpty()) {
            throw new AppException("Storage initilization failed");
        } else {
            storageSantiments = new String[tempStorage.size()];
            storageWieghts = new float[tempStorage.size()];
            int index = 0;
            for (Map.Entry<String, Float> entry : tempStorage.entrySet()) {
                storageSantiments[index] = entry.getKey();
                storageWieghts[index] = entry.getValue();
                index++;
            }
        }
    }

    private int compareSantiment(String santiment, int index) {
        return storageSantiments[index].compareTo(santiment);
    }

    private float getSantimentWeight(String santiment, int up, int bottom) {
        float weight = 0.0f;
        if (bottom - up <= 1) { // exit from recursion
            if (santiment.equals(storageSantiments[up])) {
                weight = storageWieghts[up];
            } else if (santiment.equals(storageSantiments[bottom])) {
                weight = storageWieghts[bottom];
            }
            return weight;
        }
        int currentIndex = up + (bottom - up) / 2;
        int compareResult = compareSantiment(santiment, currentIndex);
        if (compareResult == 0) {
            return storageWieghts[currentIndex];
        } else if (compareResult > 0) {
            weight = getSantimentWeight(santiment, up, currentIndex);
        } else {
            weight = getSantimentWeight(santiment, currentIndex, bottom);
        }
        return weight;
    }

    public float getSantimentWeight(String santiment) {
        int up = 0;
        int bottom = storageSantiments.length - 1;
        return getSantimentWeight(santiment, up, bottom);
    }

    public int getMinSantimentLength() {
        return minSantimentLength;
    }

    public int getMaxSantimentLength() {
        return maxSantimentLength;
    }
}
