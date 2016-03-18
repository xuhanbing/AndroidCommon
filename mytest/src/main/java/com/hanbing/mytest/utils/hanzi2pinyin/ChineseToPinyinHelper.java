package com.hanbing.mytest.utils.hanzi2pinyin;



import java.util.Set;

public class ChineseToPinyinHelper {
    public static final char CHARACTER_SEPARATOR = ' ';
    public static final String CHARACTER_SEPARATOR_STR = String
            .valueOf(CHARACTER_SEPARATOR);
    public static final char POLYPHONIC_SEPARATOR = 'þ';
    public static final String POLYPHONIC_SEPARATOR_STR = String
            .valueOf(POLYPHONIC_SEPARATOR);
    private static Set<Character> mLegalCharactSet;

    private ChineseToPinyinHelper() {

    }

    public static String[] translateMulti(String input) {
        if (null == input) {
            return null;
        }

        ChineseToPinyinResource chineseToPinyinResource = ChineseToPinyinResource
                .getInstance();
        final int inputLength = input.length();
        final StringBuilder sb = new StringBuilder();
        int duoyinRowCount = 1;

        for (int i = 0; i < inputLength; i++) {
            final char character = input.charAt(i);
            if (character < 256) {
                if (character == CHARACTER_SEPARATOR) {
                    sb.append(CHARACTER_SEPARATOR);
                } else if (mLegalCharactSet.contains(character)) {
                    sb.append(character);
                } else {
                    sb.append(CHARACTER_SEPARATOR);
                }
            } else {
                String[] array = chineseToPinyinResource
                        .getHanyuPinyinStringArray(character);
                if (null != array && null != array[0]) {
                    duoyinRowCount *= array.length;

                    sb.append(CHARACTER_SEPARATOR);
                    for (int a = 0; a < array.length; ++a) {
                        sb.append(array[a]);
                        sb.append(POLYPHONIC_SEPARATOR);
                    }
                    sb.setLength(sb.length() - 1);
                    sb.append(CHARACTER_SEPARATOR);
                } else {
                    if (character == CHARACTER_SEPARATOR) {
                        sb.append(CHARACTER_SEPARATOR);
                    } else if (mLegalCharactSet.contains(character)) {
                        sb.append(character);
                    } else {
                        sb.append(CHARACTER_SEPARATOR);
                    }
                }
            }
        }

        String retString = sb.toString().trim();
        if (retString.length() == 0) {
            retString = "*";
        } else {
            sb.setLength(0);
            for (char character : retString.toCharArray()) {
                if (character != CHARACTER_SEPARATOR) {
                    sb.append(character);
                } else {
                    int len = sb.length();
                    if (len > 0 && sb.charAt(len - 1) != CHARACTER_SEPARATOR) {
                        sb.append(CHARACTER_SEPARATOR);
                    }
                }
            }
            retString = sb.toString();
        }

        String[] retStringSplit = retString.split(CHARACTER_SEPARATOR_STR);
        String[][] result = new String[duoyinRowCount][retStringSplit.length];

        if (retString.indexOf(POLYPHONIC_SEPARATOR) != -1) {
            int perRepeat = duoyinRowCount;
            for (int col = 0; col < retStringSplit.length; col++) {
                String[] columnSplit = retStringSplit[col]
                        .split(POLYPHONIC_SEPARATOR_STR);
                perRepeat = perRepeat / columnSplit.length;

                for (int row = 0; row < duoyinRowCount;) {
                    for (int cs = 0; cs < columnSplit.length; ++cs) {
                        for (int re = 0; re < perRepeat; ++re, ++row) {
                            result[row][col] = columnSplit[cs];
                        }
                    }
                }
            }
        } else {
            result[0] = retStringSplit;
        }
        String [] retValues = new String[duoyinRowCount];
        for (int i = 0; i < duoyinRowCount; i++) {
            retValues[i] = "";
            for (int j = 0; j < result[i].length; j++) {
                retValues[i] += result[i][j];
            }
        }
        return retValues;
    }

    public static String hanziToPinyin(String input) {
        if (null == input) {
            return null;
        }

        ChineseToPinyinResource chineseToPinyinResource = ChineseToPinyinResource
                .getInstance();
        final int inputLength = input.length();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inputLength; i++) {
            final char character = input.charAt(i);
            if (character < 256) {
                if (mLegalCharactSet.contains(character)) {
                    sb.append(character);
                }
            } else {
                String[] array = chineseToPinyinResource
                        .getHanyuPinyinStringArray(character);
                if (null != array && null != array[0]) {
                    for (int a = 0; a < array.length; ++a) {
                        sb.append(array[a]);
                    }
                } else {
                    if (mLegalCharactSet.contains(character)) {
                        sb.append(character);
                    }
                }
            }
        }
        return sb.toString().trim().toLowerCase();
    }

    public static void setLegalCharactSet(Set<Character> legalCharactSet) {
        mLegalCharactSet = legalCharactSet;
    }
    
    

}
