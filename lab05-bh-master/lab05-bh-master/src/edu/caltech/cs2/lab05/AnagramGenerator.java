package edu.caltech.cs2.lab05;

import java.util.ArrayList;
import java.util.List;

public class AnagramGenerator {
    public static void printPhrases(String phrase, List<String> dictionary) {
    List<String> acc = new ArrayList<>();
    LetterBag original = new LetterBag(phrase);
    printPhrasesHelp(original, acc, dictionary);
    }

    public static void printPhrasesHelp(LetterBag buh, List<String> acc, List<String> dictionary) {
    if(buh.isEmpty()){
        String out = "[";
        for(String w : acc)out += w + ", ";
        if(out != "["){
            out = out.substring(0, out.length()-2);
            System.out.println(out + "]");
        }else{ System.out.println("[]");}}
        for(String op : dictionary){
            LetterBag numb = new LetterBag(op);
            if(buh.subtract(numb) != null){
                acc.add(op);
                printPhrasesHelp(buh.subtract(numb), acc, dictionary);
                acc.remove(op);
            }
        }

    }

    public static void printWords(String word, List<String> dictionary) {
    LetterBag dumb = new LetterBag(word);
    for(String m : dictionary){
        LetterBag balls = new LetterBag(m);
        LetterBag nuts = dumb.subtract(balls);
        if(nuts != null && nuts.isEmpty()) System.out.println(m);
    }
    }
}
