package com.bamgmk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by marting on 03.04.2017.
 */

public class Quest {
    public List<Integer> requiredNumbers;
    public List<Integer> currentNumbers;
    public int type;
    public int questNumber;
    public static final int killMorningStar = 0, killBerserker = 1, killAnubis=2,killPriestess = 3, killSolo =4 , completeQuests = 5, typeMajor =0, typeMinor = 1;
    //public GameItem reward;
    public String questText;
    // Bedingungen -> counter
    public HashMap<Integer,Integer> increases;

    public boolean isFinished;

    public Quest (int type){
        Random rnd = new Random();
        this.type = type;
        increases = new HashMap<>();
        requiredNumbers = new ArrayList<>();
        currentNumbers = new ArrayList<>();
        //reward = GameItem.createItem(rnd.nextInt(3),type,lvl,rnd.nextInt(4));

        if (type== typeMajor ){
            int i =rnd.nextInt(6);
            questNumber = i;
            switch (i){
                case killMorningStar:
                    currentNumbers.add(0);
                    requiredNumbers.add(10);
                    increases.put(killMorningStar,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case killBerserker:
                    currentNumbers.add(0);
                    requiredNumbers.add(10);
                    increases.put(killBerserker,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case killAnubis:
                    currentNumbers.add(0);
                    requiredNumbers.add(10);
                    increases.put(killAnubis,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case killPriestess:
                    currentNumbers.add(0);
                    requiredNumbers.add(10);
                    increases.put(killPriestess,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case killSolo:
                    currentNumbers.add(0);
                    requiredNumbers.add(2);
                    increases.put(killSolo,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case completeQuests:
                    currentNumbers.add(3);
                    requiredNumbers.add(8);
                    increases.put(completeQuests,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
            }

        }

        if (type == typeMinor){
            increases = new HashMap<>();
            requiredNumbers = new ArrayList<>();
            currentNumbers = new ArrayList<>();
            int i = rnd.nextInt(4);
            questNumber = i;
            switch (i){
                case killMorningStar:
                    currentNumbers.add(0);
                    requiredNumbers.add(4);
                    increases.put(killMorningStar,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case killBerserker:
                    currentNumbers.add(0);
                    requiredNumbers.add(4);
                    increases.put(killBerserker,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case killAnubis:
                    currentNumbers.add(0);
                    requiredNumbers.add(4);
                    increases.put(killAnubis,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
                case killPriestess:
                    currentNumbers.add(0);
                    requiredNumbers.add(4);
                    increases.put(killPriestess,0);
                    questText = generateText(type,i,currentNumbers,requiredNumbers);
                    break;
            }
        }
        isFinished = false;
    }

    public Quest(int type, int questNumber, List<Integer> questCurrent) {
        this.type = type;
        this.questNumber = questNumber;
        increases = new HashMap<>();
        requiredNumbers = new ArrayList<>();
        currentNumbers = new ArrayList<>();
        currentNumbers.addAll(questCurrent);
        if (type == typeMajor){
            switch (questNumber){
                case killMorningStar:
                    requiredNumbers.add(10);
                    increases.put(killMorningStar,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case killBerserker:
                    requiredNumbers.add(10);
                    increases.put(killBerserker,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case killAnubis:
                    requiredNumbers.add(10);
                    increases.put(killAnubis,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case killPriestess:
                    requiredNumbers.add(10);
                    increases.put(killPriestess,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case killSolo:
                    requiredNumbers.add(2);
                    increases.put(killSolo,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case completeQuests:
                    requiredNumbers.add(8);
                    increases.put(completeQuests,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
            }
            isFinished = true;
            for (int k = 0; k<currentNumbers.size();k++){
                if (currentNumbers.get(k)< requiredNumbers.get(k)){
                    isFinished = false;
                    break;
                }
            }
        }
        else {
            switch (questNumber){
                case killMorningStar:
                    requiredNumbers.add(4);
                    increases.put(killMorningStar,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case killBerserker:
                    requiredNumbers.add(4);
                    increases.put(killBerserker,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case killAnubis:
                    requiredNumbers.add(4);
                    increases.put(killAnubis,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
                case killPriestess:
                    requiredNumbers.add(4);
                    increases.put(killPriestess,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
            }
        }


    }

    public String generateText(int questType,int i, List<Integer> currentNumbers, List<Integer> requiredNumbers) {
        switch (i){
            case killMorningStar:
                return "Besiege Wikinger "+ currentNumbers.get(0)+" von "+requiredNumbers.get(0);

            case killBerserker:
                return "Besiege Berserker "+ currentNumbers.get(0)+" von "+requiredNumbers.get(0);

            case killAnubis:
                return "Besiege Schakalskrieger "+ currentNumbers.get(0)+" von "+requiredNumbers.get(0);

            case killPriestess:
                return "Besiege Priesterinnen "+ currentNumbers.get(0)+" von "+requiredNumbers.get(0);

            case killSolo:
                return "Besiege Solos "+ currentNumbers.get(0)+" von "+requiredNumbers.get(0);

            case completeQuests:
                return "Erfülle Quests "+ currentNumbers.get(0)+" von "+requiredNumbers.get(0);

        }
        return "Error";
    }

    public void increase (int i){
        if (!isFinished){
            if (increases.containsKey(i)){
                int j = currentNumbers.get(increases.get(i));
                currentNumbers.remove((int)increases.get(i));
                currentNumbers.add(increases.get(i),j+1);
                questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                isFinished = true;
                for (int k = 0; k<currentNumbers.size();k++){
                    if (currentNumbers.get(k)< requiredNumbers.get(k)){
                        isFinished = false;
                        break;
                    }
                }

            }
        }
    }


}
