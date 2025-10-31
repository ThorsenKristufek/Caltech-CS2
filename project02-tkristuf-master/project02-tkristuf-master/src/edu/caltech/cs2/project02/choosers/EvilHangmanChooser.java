package edu.caltech.cs2.project02.choosers;

import edu.caltech.cs2.project02.interfaces.IHangmanChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

public class EvilHangmanChooser implements IHangmanChooser {
  private String word;
  private int nguess;
  private SortedSet<Character> guessed = new TreeSet<Character>();
  private SortedSet<String> pwords = new TreeSet<String>();
  public EvilHangmanChooser(int wordLength, int maxGuesses) throws FileNotFoundException {
      Scanner f = new Scanner(new File("data/scrabble.txt"));
      SortedSet<String> posswords = new TreeSet<String>();
      while(f.hasNext()){
        String thing = f.next();
        if(thing.length() == wordLength){
          posswords.add(thing);
        }
      }
      if(wordLength < 1 || maxGuesses < 1) throw new IllegalArgumentException();
      if(posswords.size()==0) throw new IllegalStateException();
      this.nguess = maxGuesses;
      this.pwords = posswords;
  }

  @Override
  public int makeGuess(char letter) {
    if(this.nguess < 1) throw new IllegalStateException();
    if(!(Character.isLowerCase(letter)) || this.guessed.contains(letter)) throw new IllegalArgumentException();
    guessed.add(letter);
    Map<String, SortedSet<String>> mappings = new TreeMap<>();
    for(String w: this.pwords) {
      String pattern = "";
      for (int j = 0; j < w.length(); j++) {
        if (w.charAt(j) == letter) pattern += letter;
        else pattern += "-";
      }
      if (mappings.keySet().contains(pattern)){
        SortedSet<String> vals = mappings.get(pattern);
        vals.add(w);
        mappings.put(pattern, vals);
      }
      else{
        SortedSet<String> values = new TreeSet<String>();
        values.add(w);
        mappings.put(pattern, values);

      }
    }
    int max = 0;
    SortedSet<String> pwords = new TreeSet<>();
    for(SortedSet<String> w: mappings.values()) {
      if (w.size() > max) {
        max = w.size();
        pwords = w;
      }
    }
    this.pwords = pwords;
    if(this.pwords.size() == 1) this.word = this.pwords.first();
    int count = 0;
    for(int i = 0; i<this.pwords.first().length(); i++){
      if(this.pwords.first().charAt(i) == letter) count++;
    }
    if(count == 0) this.nguess--;
    return count;
  }

  @Override
  public boolean isGameOver() {
    char[] lets = this.pwords.first().toCharArray();
    int correct = 0;
    for(char i: lets){
      if(this.guessed.contains(i)) correct++;
    }
    if(this.pwords.first().length() == correct || this.nguess == 0) return true;
    return false;
  }

  @Override
  public String getPattern() {
    String pat = "";
    char[] lets = this.pwords.first().toCharArray();
    for(char c: lets){
      if(this.guessed.contains(c)) pat += c;
      else pat+= "-";
    }
    return pat;
  }

  @Override
  public SortedSet<Character> getGuesses() {
    return this.guessed;
  }

  @Override
  public int getGuessesRemaining() {
    return this.nguess;
  }

  @Override
  public String getWord() {
    this.nguess = 0;
    return this.word;
  }
}