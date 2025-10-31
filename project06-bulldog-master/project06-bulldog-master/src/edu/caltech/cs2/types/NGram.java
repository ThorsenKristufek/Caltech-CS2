package edu.caltech.cs2.types;

import edu.caltech.cs2.datastructures.LinkedDeque;
import edu.caltech.cs2.interfaces.IDeque;

import java.util.Iterator;

public class NGram implements Iterable<String>, Comparable<NGram> {
    public static final String NO_SPACE_BEFORE = ",?!.-,:'";
    public static final String NO_SPACE_AFTER = "-'><=";
    public static final String REGEX_TO_FILTER = "”|\"|“|\\(|\\)|\\*";
    public static final String DELIMITER = "\\s+|\\s*\\b\\s*";
    private IDeque<String> data;

    public static String normalize(String s) {
        return s.replaceAll(REGEX_TO_FILTER, "").strip();
    }

    public NGram(IDeque<String> x) {
        this.data = new LinkedDeque<>();
        for (String word : x) {
            this.data.add(word);
        }
    }

    public NGram(String data) {
        this(normalize(data).split(DELIMITER));
    }

    public NGram(String[] data) {
        this.data = new LinkedDeque<>();
        for (String s : data) {
            s = normalize(s);
            if (!s.isEmpty()) {
                this.data.addBack(s);
            }
        }
    }

    public NGram next(String word) {
        String[] data = new String[this.data.size()];
        Iterator<String> dataIterator = this.data.iterator();
        dataIterator.next();
        for (int i = 0; i < data.length - 1; i++) {
            data[i] = dataIterator.next();
        }
        data[data.length - 1] = word;
        return new NGram(data);
    }

    public String toString() {
        String result = "";
        String prev = "";
        for (String s : this.data) {
            result += ((NO_SPACE_AFTER.contains(prev) || NO_SPACE_BEFORE.contains(s) || result.isEmpty()) ? "" : " ") + s;
            prev = s;
        }
        return result.strip();
    }

    @Override
    public Iterator<String> iterator() {
        return this.data.iterator();
    }

    /*
    @Override
    public int compareTo(NGram other) {
        if (this.equals(other)) return 0;
        Iterator<String> boop = this.data.iterator();
        Iterator<String> goop = other.iterator();
        while (boop.hasNext()) {
            String boopval = boop.next();
            String goopval;
            if (goop.hasNext()) {
                goopval = goop.next();
            } else return 1;
            if (!boopval.equals(goopval)) {
                if (boopval.length() < goopval.length()) return -1;
                if (boopval.length() > goopval.length()) return 1;
                else return boopval.compareTo(goopval);
            }
        }
        return -1;
    }


     */
    @Override
    public int compareTo(NGram other) {
        return toString().compareTo(other.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NGram)) {
            return false;
        }
        if (data.size() != ((NGram) o).data.size()) {
            return false;
        }
        Iterator<String> boop = this.data.iterator();
        Iterator<String> goop = ((NGram) o).iterator();
        while (boop.hasNext()) {
            if (!boop.next().equals(goop.next())) return false;
        }
        return true;
    }

    //@Override
    //  public int hashCode() {
    // int hashCode = 0;
    // int index = 0;
    // for(String s : data) {
    // int ascii = 0;
    // for(int i = 0; i < s.length(); i++) {
    // ascii += s.charAt(i);
    // }
    // if (hashCode != 0) hashCode += ascii + index * 67 * hashCode;
    // else hashCode += ascii + index * 67;
    // index++;
    // }
    // return hashCode;
//}

    public int hashCode() {
        return (toString().hashCode());
    }
}