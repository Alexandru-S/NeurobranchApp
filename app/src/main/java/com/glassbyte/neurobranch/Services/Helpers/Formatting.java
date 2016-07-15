package com.glassbyte.neurobranch.Services.Helpers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ed on 27/06/2016.
 */
public class Formatting {
    public static final String ELLIPSIS = "[^iIl1\\.,']";
    public static final int MAX_DESC_SIZE = 100;

    public static String formatResearchers(ArrayList<String> researcherNames) {
        if(researcherNames.size() == 1) {
            return researcherNames.get(0);
        } else if (researcherNames.size() == 2){
            return researcherNames.get(0) + " & " + researcherNames.get(1);
        } else {
            String formattedNameGroup = "";
            for(int i=0; i<researcherNames.size(); i++) {
                if(i != researcherNames.size()-1) {
                    formattedNameGroup += researcherNames.get(i) + ", ";
                } else {
                    formattedNameGroup +=  "& " + researcherNames.get(i);
                }
            }
            return formattedNameGroup;
        }
    }


    private static int textWidth(String str) {
        return str.length() - str.replaceAll(ELLIPSIS, "").length() / 2;
    }

    public static String truncateText(String text, int max) {
        if (textWidth(text) <= max)
            return text;

        int end = text.lastIndexOf(' ', max-3);
        if (end == -1)
            return text.substring(0, max-3) + "...";

        int newEnd = end;
        do {
            end = newEnd;
            newEnd = text.indexOf(' ', end + 1);
            if (newEnd == -1)
                newEnd = text.length();

        } while (textWidth(text.substring(0, newEnd) + "...") < max);

        return text.substring(0, end) + "...";
    }

    public static String formatTime(long input) {
        Date date = new Date(input);
        DateFormat formatter = DateFormat.getDateInstance();
        return formatter.format(date);
    }
}
