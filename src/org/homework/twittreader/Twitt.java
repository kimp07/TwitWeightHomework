package org.homework.twittreader;

public class Twitt {

    private String body;
    private double weight;

    public Twitt(String body) {
        this.body = body;
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
}
