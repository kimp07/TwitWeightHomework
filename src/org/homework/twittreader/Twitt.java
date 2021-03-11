package org.homework.twittreader;

public class Twitt {

    private String body;
    private double weight;
    private String foundedSantiments;

    public Twitt(String body) {
        this.body = body;
        this.foundedSantiments = "";
    }

    public String getBody() {
        return body;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void appentFoundedSantiment(String santiment) {
        foundedSantiments += santiment + ";";
    }

    public void addWeight(float weight) {
        this.weight += weight;
    }
}
