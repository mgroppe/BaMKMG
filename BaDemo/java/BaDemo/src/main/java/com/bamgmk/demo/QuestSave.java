package com.bamgmk.demo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by marting on 06.04.2017.
 */

public class QuestSave {
    public int minorQuestNumber;
    public int majorQuestNumber;
    public List<Integer> minorQuestCurrent,majorQuestCurrent;

    public QuestSave(int minorQuestNumber, int majorQuestNumber, List<Integer> minorQuestCurrent, List<Integer> majorQuestCurrent) {
        this.minorQuestNumber = minorQuestNumber;
        this.majorQuestNumber = majorQuestNumber;
        this.minorQuestCurrent = minorQuestCurrent;
        this.majorQuestCurrent = majorQuestCurrent;
    }

    public QuestSave(Quest major, Quest minor) {
        minorQuestNumber = minor.questNumber;
        majorQuestNumber = major.questNumber;
        minorQuestCurrent = minor.currentNumbers;
        majorQuestCurrent = major.currentNumbers;
    }
}
