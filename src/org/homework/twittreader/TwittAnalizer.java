package org.homework.twittreader;

public class TwittAnalizer {

    static void analizeTwitt(Twitt twitt, Vocabulary vocabulary) {

        double weight = 0;

        char space = ' ';

        int bodySize = twitt.getBody().length() - 1;
        for (int pos = 0; pos < (bodySize - vocabulary.getMinSantimentLength()); pos++) {
            if (twitt.getBody().charAt(pos) == space) {
                continue;
            }
            int endPos = vocabulary.getMaxSantimentLength();
            if (pos + vocabulary.getMaxSantimentLength() > (bodySize + 1)) {
                endPos = bodySize - pos + 1;
            }
            for (int santimentSize = endPos; santimentSize >= vocabulary.getMinSantimentLength(); santimentSize--) {
                String santiment = twitt.getBody().substring(pos, pos + santimentSize);
                float santimentWeight = vocabulary.getSantimentWeight(santiment);
                if (santimentWeight != 0.0f) {
                    weight += santimentWeight;
                    System.out.println("founded santiment: " + santiment);
                    break;
                }
            }
            pos = twitt.getBody().indexOf(space, pos);
            if (pos < 0) {
                break;
            }

        }
        twitt.setWeight(weight);
    }
}
