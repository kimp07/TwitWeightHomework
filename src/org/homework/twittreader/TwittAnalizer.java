package org.homework.twittreader;

public class TwittAnalizer {

    private static float analizeSantiment(String body, Vocabulary vocabulary, int pos, int endPos) {
        float weight = 0;
        for (int santimentSize = endPos; santimentSize >= vocabulary.getMinSantimentLength(); santimentSize--) {
            String santiment = body.substring(pos, pos + santimentSize);
            float santimentWeight = vocabulary.getSantimentWeight(santiment);
            if (santimentWeight != 0.0f) {
                weight = santimentWeight;
                System.out.println("founded santiment: " + santiment);
                break;
            }
        }
        return weight;
    }

    public static void analizeTwitt(Twitt twitt, Vocabulary vocabulary) {

        double weight = 0;

        char space = ' ';

        int bodySize = twitt.getBody().length() - 1;
        int pos = 0;
        while (pos < (bodySize - vocabulary.getMinSantimentLength()) && pos >= 0) {
            if (twitt.getBody().charAt(pos) == space) {
                pos++;
            } else {
                int endPos = vocabulary.getMaxSantimentLength();
                if (pos + vocabulary.getMaxSantimentLength() > (bodySize + 1)) {
                    endPos = bodySize - pos + 1;
                }
                weight += analizeSantiment(twitt.getBody(), vocabulary, pos, endPos);
                pos = twitt.getBody().indexOf(space, pos);
            }

        }
        twitt.setWeight(weight);
    }
}
