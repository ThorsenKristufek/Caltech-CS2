package edu.caltech.cs2.textgenerator;

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

    @Override
    public int compareTo(NGram other) {
        int res = 0;
        Iterator<String> bob = this.data.iterator();
        Iterator<String> two = other.data.iterator();
        while(bob.hasNext() && two.hasNext()){
            res = bob.next().compareTo(two.next());
            if(res != 0) return res;
        }
        res = this.data.size() - other.data.size();
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof NGram)) {
            return false;
        }
        NGram nuts = (NGram)o;
        if(this.data.size() != nuts.data.size()){
            return false;
        }
        Iterator bt = this.data.iterator();
        Iterator lbjenkins = nuts.iterator();
        while(bt.hasNext()){
            if(!bt.next().equals(lbjenkins.next())) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int tot = 0;
        Iterator<String> bruh = this.data.iterator();
        while(bruh.hasNext()){
            String op = bruh.next();
            tot = tot * 31 + op.hashCode();
        }
        return tot;
    }
}