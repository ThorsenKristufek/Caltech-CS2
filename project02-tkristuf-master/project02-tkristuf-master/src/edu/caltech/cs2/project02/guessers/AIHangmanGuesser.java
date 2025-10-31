package edu.caltech.cs2.project02.guessers;

import edu.caltech.cs2.project02.interfaces.IHangmanGuesser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AIHangmanGuesser implements IHangmanGuesser {
  private static String dat = "data/scrabble.txt";
  @Override
  public char getGuess(String pattern, Set<Character> guesses) throws FileNotFoundException {
    Scanner f = new Scanner(new File(dat));
    SortedSet<String> bruh = new TreeSet<>();
    while (f.hasNext()) {
      String thing = f.next();
      if (thing.length() != pattern.length()) continue;
      boolean m = true;
      for (int i = 0; i < pattern.length(); i++) {
        if (thing.charAt(i) != pattern.charAt(i) && pattern.charAt(i) != '-') m = false;
        if (guesses.contains(thing.charAt(i)) && pattern.charAt(i) != thing.charAt(i)) m = false;
      }
      if (m) bruh.add(thing);
    }
    Map<Character, Integer> unguessed = new TreeMap<>();
    for (char c = 'a'; c <= 'z'; c++) {
      if (guesses.contains(c)) continue;
      int count = 0;
      for (String m : bruh) {
        for (int o = 0; o < m.length(); o++) {
          if (m.charAt(o) == c) count++;
        }
      }
      unguessed.put(c, count);
    }
    int max = 0;
    char b = '_';
    for (Map.Entry<Character, Integer> entry : unguessed.entrySet()) {
      if (entry.getValue() > max){
        max = entry.getValue();
        b = entry.getKey();
      }
    }
    return b;
  }
}
