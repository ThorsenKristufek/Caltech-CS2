package edu.caltech.cs2.project02.choosers;

import edu.caltech.cs2.project02.interfaces.IHangmanChooser;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;
import java.io.*;
public class RandomHangmanChooser implements IHangmanChooser {
    private static final Random r = new Random();
    private final String word;
    private SortedSet<Character> guessed;
    private int nguess;

  public RandomHangmanChooser(int wordLength, int maxGuesses) throws FileNotFoundException {
    Scanner f = new Scanner(new File("data/scrabble.txt"));
    SortedSet<String> bruh = new TreeSet<>();
    if (wordLength < 1) throw new IllegalArgumentException();
    if (maxGuesses < 1) throw new IllegalArgumentException();
    while (f.hasNext()) {
      String thing = f.next();
      if (thing.length() == wordLength) {
        bruh.add(thing);
      }
    }
    if (bruh.size() == 0) throw new IllegalStateException();
    int idx = r.nextInt(bruh.size());
    this.nguess = maxGuesses;
    this.guessed = new TreeSet<>();
    Iterator go = bruh.iterator();
    for (int i = 0; i < idx; i++) {
      go.next();}
    this.word = (String)go.next();


  }
  @Override
  public int makeGuess(char letter) {
    int cnt = 0;
    if(getGuessesRemaining() == 0) throw new IllegalStateException();
    if(!(Character.isLowerCase(letter))) throw new IllegalArgumentException();
    if(guessed.contains(letter)) throw new IllegalArgumentException();
    for(int i = 0; i < this.word.length(); i++){
      if(this.word.charAt(i)==letter) {
        cnt++;
      }
    }
    if(cnt == 0) this.nguess--;
    this.guessed.add(letter);
    return cnt;
  }

  @Override
  public boolean isGameOver() {
    if(getGuessesRemaining()==0 || getPattern().equals(this.word)) return true;
    return false;
  }

  @Override
  public String getPattern() {
    String out = "";
    for(int i = 0; i < word.length(); i++){
      if(guessed.contains(word.charAt(i))) out += word.charAt(i);
      else out += "-";
    }
    return out;
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