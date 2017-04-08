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
                    currentNumbers.add(0);
                    requiredNumbers.add(5);
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
                    requiredNumbers.add(5);
                    increases.put(completeQuests,0);
                    questText = generateText(type,questNumber,currentNumbers,requiredNumbers);
                    break;
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
        isFinished = true;
        for (int k = 0; k<currentNumbers.size();k++){
            if (currentNumbers.get(k)< requiredNumbers.get(k)){
                isFinished = false;
                break;
            }
        }


    }

    public String generateText(int questType,int i, List<Integer> currentNumbers, List<Integer> requiredNumbers) {
        switch (questType) {
            case 0:{
                switch (i) {

                    case killMorningStar:
                        return "Essling und seine Freunde wurden mit einer Horde plündernder Wikinger verwechselt. Keine Ahnung, wie das passieren konnte, aber das kann man nicht so stehen lassen.\n" +
                                "\nZeig zehn Wikingern, dass sie woanders plündern gehen sollen." +
                                "\n\nBesiegte Wikinger " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                    case killBerserker:
                        return "Die Berserker haben mal wieder nur Unfug im Sinn. Neulich haben sie dem Schmied Brokkr eine Waffe geklaut. Brokkr hat gesagt, dass du die Waffe behalten kannst, wenn du denen eine Lehre erteilst ist er glücklich." +
                                "\nBesiege zehn Berserker, damit sie sich von der Waffe trennen."+
                                "\n\nBesiegte Berserker " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                    case killAnubis:
                        return "Essling und seine Freunde brauchen dringend Holz für ihre Pläne. Praktisch, dass die Schakalskrieger Schilde aus Holz haben! Bring sie dazu, Essling ihre Schilde zu überlassen!" +
                                "\nÜberzeuge zehn Schakalskrieger, dass ihre Schilde nutzlos sind." +
                                "\n\nBesiegte Schakalskrieger " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                    case killPriestess:
                        return "Die Priesterinnen des Ra haben festgestellt, dass sie für deutsche Wetterverhältnisse zu dünn bekleidet sind. Leider war ihre Lösung für das Problem, ein Kleidungsgeschäft auszurauben. Mach ihnen klar, dass sie dafür bezahlen müssen!"+
                                "\nErteile 10 Priesterinnen des Ra eine Lektion."+
                                "\n\nBesiegte Priesterinnen " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                    case killSolo:
                        return "Einige Leute in der Umgebung haben im Wald einen unbekannten Pilz gegessen. Dadurch sind sie riesig und verrückt geworden. Sie müssen gestoppt werden!" +
                                "\nBesiege zwei Bosse"+
                                "\n\nBesiegte Bosse " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                    case completeQuests:
                        return "Zeig den Koblenzern, dass Essling sich um ihr Wohlbefinden sorgt. Vielleicht lassen sie ihn dann ihre Werft mitbenutzen."+
                                "\nErfülle acht Quests."+
                                "\n\nErfüllte Quests " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);
                }
            }
           case 1:{
            switch (i) {
                case killMorningStar:
                    return "Einige Wikinger sind in Koblenz auf Kneipentour gegangen und haben sich stark betrunken. Leider haben sie anschließend in ihrem Rausch alles kurz und klein gehauen und die Koblenzer sind jetzt sauer auf alle Wikinger." +
                            "\nVerprügel 4 Wikinger, damit die sich das nächste mal etwas am Riemen reißen!" +
                            "\n\nBesiegte Wikinger " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                case killBerserker:
                    return "Die Berserker sind total verrückt geworden und haben angefangen, in der Umgebung lauter Wälder mit ihren Äxten kurz und klein zu schlagen. Essling braucht aber das Holz. Werf sie aus den Wäldern raus! " +
                            "\nBesiege 4 Berserker" +
                            "\n\nBesiegte Berserker " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                case killAnubis:
                    return "Die Schakale heulen momentan jede Nacht in Koblenz den Mond an. Die schlaflosen Einwohner haben einen Preis ausgesetzt für denjenigen, der es schafft, sie zu verjagen." +
                            "\nJage 4 Schakalskrieger in den Wald." +
                            "\n\nBesiegte Schakalskrieger " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);

                case killPriestess:
                    return "Die Priesterinnen des Ra haben so viel gebetet, dass Ra den Rhein austrocknen lassen hat :( \\n So kann Essling niemals zurücksegeln. Mach ihnen klar, dass man bei der Ausübung seiner Religion auch auf andere Rücksicht nehmen sollte." +
                            "\nBesiege 4 Priesterinnen des Ra, damit Essling bald wieder segeln kann." +
                            "\n\nBesiegte Priesterinnen " + currentNumbers.get(0) + " von " + requiredNumbers.get(0);
            }
           }
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
