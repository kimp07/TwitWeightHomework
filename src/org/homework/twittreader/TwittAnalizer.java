package org.homework.twittreader;

public class TwittAnalizer {

    private static char space = ' ';

    private static String normilizeBody(String body) {
        String result = "";
        if (body.length() == 0) {
            return result;
        }
        for (int i = 0; i < body.length(); i++) {
            char substr = body.charAt(i);
            if ((substr >= 'a' && substr <= 'z') || (substr >= 'A' && substr <= 'Z') || (substr >= '0' && substr <= '9') || substr == '\'' || substr == '`') {
                result += substr;
            } else {
                result += " ";
            }
        }
        return result.trim();
    }

    private static float analizeSantiment(String body, Vocabulary vocabulary, int pos, int endPos, Twitt twitt) {
        float weight = 0;
        String santiment = body.substring(pos, endPos - 1).trim();
        while (santiment.length() >= vocabulary.getMinSantimentLength()) {
            float santimentWeight = vocabulary.getSantimentWeight(santiment);
            if (santimentWeight != 0.0f) {
                weight = santimentWeight;
                twitt.appentFoundedSantiment(santiment);
                break;
            }
            int lastSpace = santiment.lastIndexOf(space);
            if (lastSpace > 0) {
                santiment = santiment.substring(0, lastSpace); // last symbol before space
            } else {
                santiment = "";
            }
        }
        return weight;
    }

    public static void analizeTwitt(Twitt twitt, Vocabulary vocabulary) {

        double weight = 0;
        String body = twitt.getBody();

        int pos = body.indexOf(':');
        if (pos >= 0) { // throw 2nd ':' - twitt time
            pos = body.indexOf(":", pos + 1);
            if (pos > 0) {
                pos += 3;
            }
        }
        if (pos < 0) {
            pos = 0;
        }
        try {
            body = normilizeBody(body.substring(pos, body.length()));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(body);
        }
        pos = 0;
        int bodySize = body.length() - 1;
        while (pos <= (bodySize - vocabulary.getMinSantimentLength()) && pos >= 0) {
            if (body.charAt(pos) == space) {
                pos++;
            } else {
                int endPos = Math.min(pos + vocabulary.getMaxSantimentLength(), bodySize);
                weight += analizeSantiment(body, vocabulary, pos, endPos, twitt);
                pos = body.indexOf(space, pos);
            }

        }
        twitt.setWeight(weight);
    }
}
