package org.homework.twittreader;

public class TwittAnalizer {

    private static final char SPACE_CHAR = ' ';
    private static final char TIME_DELIMITER = ':';

    private TwittAnalizer() {}

    private static String normilizeBody(String body) {
        StringBuilder result = new StringBuilder("");
        if (body.length() == 0) {
            return "";
        }
        for (int i = 0; i < body.length(); i++) {
            char substr = body.charAt(i);
            if ((substr >= 'a' && substr <= 'z') || (substr >= 'A' && substr <= 'Z') || (substr >= '0' && substr <= '9') || substr == '\'' || substr == '`') {
                result.append(substr);
            } else {
                result.append(SPACE_CHAR);
            }
        }
        return result.toString().trim();
    }

    private static int analizeSantimentAndReturnFoundedSantimentLength(String body, Vocabulary vocabulary, int pos, int endPos, Twitt twitt) {
        int santimentLength = 0;
        String santiment = body.substring(pos, endPos).trim();
        while (santiment.length() >= vocabulary.getMinSantimentLength()) {
            float santimentWeight = vocabulary.getSantimentWeight(santiment);
            if (santimentWeight != 0.0f) {
                santimentLength = santiment.length();
                twitt.appentFoundedSantiment(santiment);
                twitt.addWeight(santimentWeight);
                break;
            }
            int lastSpace = santiment.lastIndexOf(SPACE_CHAR);
            if (lastSpace > 0) {
                santiment = santiment.substring(0, lastSpace).trim(); // last symbol before space
            } else {
                santiment = "";
            }
        }
        return santimentLength;
    }

    public static void analizeTwitt(Twitt twitt, Vocabulary vocabulary) {

        String body = twitt.getBody();

        int pos = body.indexOf(TIME_DELIMITER);
        if (pos >= 0) { // throw 2nd ':' - twitt time
            pos = body.indexOf(TIME_DELIMITER, pos + 1);
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
        int bodySize = body.length();
        while (pos <= (bodySize - vocabulary.getMinSantimentLength()) && pos >= 0) {
            if (body.charAt(pos) == SPACE_CHAR) {
                pos++;
            } else {
                int endPos = Math.min(pos + vocabulary.getMaxSantimentLength(), bodySize);
                pos += analizeSantimentAndReturnFoundedSantimentLength(body, vocabulary, pos, endPos, twitt);
                pos = body.indexOf(SPACE_CHAR, pos);
            }
        }
    }
}
