package edu.caltech.cs2.project03;

import edu.caltech.cs2.datastructures.CircularArrayFixedSizeQueue;
import edu.caltech.cs2.interfaces.IFixedSizeQueue;
import edu.caltech.cs2.interfaces.IQueue;

import java.util.Random;


public class CircularArrayFixedSizeQueueGuitarString {
    private static final double samprate = 44100.00;
    private static final double decay = 0.996;
    private int n;
    private IFixedSizeQueue guitarSt;
    private static Random r;
    public CircularArrayFixedSizeQueueGuitarString(double frequency) {
        this.n = (int) (this.samprate / frequency) +1;
        this.guitarSt = new CircularArrayFixedSizeQueue(n);
        this.r = new Random();
        for(int i = 0; i < n; i++) this.guitarSt.enqueue((double)0);
    }

    public int length() {
        return this.guitarSt.size();
    }

    public void pluck() {
        for (int i = 0; i < this.guitarSt.size(); i++) {
            double v = r.nextDouble() - 0.469;
            this.guitarSt.dequeue();
            this.guitarSt.enqueue(v);

        }
    }
    public void tic() {
        double m = (double)this.guitarSt.dequeue();
        double a = (double)this.guitarSt.peek();
        this.guitarSt.enqueue(((m+a)/2) * decay);

    }

    public double sample() {
        return (double)this.guitarSt.peek();
    }
}
