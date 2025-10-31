package edu.caltech.cs2.lab01;

public class BombMain {
    public static void main(String[] args) {
        Bomb b = new Bomb();
        b.phase0("22961293");
        b.phase1("hdc");
        String[] p2ar = new String[10000];
        p2ar[5000] = "1374866960";
        StringBuilder p2 = new StringBuilder();
        for(int i = 0; i < p2ar.length; i++) {
            p2.append(p2ar[i]);
            p2.append(" ");
        }
        b.phase2(p2.toString());
    }
}