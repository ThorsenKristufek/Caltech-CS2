package edu.caltech.cs2.lab08;

import edu.caltech.cs2.helpers.Inspection;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Problem2Tests {
    Problem2 tester = new Problem2();
    private static final String PROBLEM_2_SOURCE = "src/edu/caltech/cs2/lab08/Problem2.java";

    @Order(0)
    @Tag("A")
    @DisplayName("Import & Usage Tests")
    @Test
    public void testForInvalidClassesAndDataStructures() {
        List<String> regexps = List.of("java\\.lang\\.reflect", "java\\.io", "javax\\.swing", "java\\.util","\\s*.*\\s*\\[\\]\\s*.*\\s*=\\s*new .*\\[.*\\]\\s*;", ".*\\s*=\\s*new .*\\[.*\\]\\s*;");
        Inspection.assertNoImportsOf(PROBLEM_2_SOURCE, regexps);
        Inspection.assertNoUsageOf(PROBLEM_2_SOURCE, regexps);
    }

    @Order(1)
    @Tag("A")
    @DisplayName("Test Problem2: 1")
    @Test
    public void testProblem2A() {
        int [] arr1 = {1,2,3,0,0,0};
        int [] arr2 = {2,5,6};
        int m = 3;
        int n = 3;
        tester.merge(arr1,m,arr2,n);
        int [] expected = {1,2,2,3,5,6};
        for(int i = 0; i < expected.length;i++){
            assertEquals(expected[i], arr1[i]);
        }
    }

    @Order(2)
    @Tag("A")
    @DisplayName("Test Problem2: 2")
    @Test
    public void testProblem2B() {
        int [] arr1 = {1};
        int [] arr2 = {};
        int m = 1;
        int n = 0;
        tester.merge(arr1,m,arr2,n);
        int [] expected = {1};
        for(int i = 0; i < expected.length;i++){
            assertEquals(expected[i], arr1[i]);
        }
    }

    @Order(3)
    @Tag("A")
    @DisplayName("Test Problem2: 3")
    @Test
    public void testProblem2C() {
        int [] arr1 = {0};
        int [] arr2 = {1};
        int m = 0;
        int n = 1;
        tester.merge(arr1,m,arr2,n);
        int [] expected = {1};
        for(int i = 0; i < expected.length;i++){
            assertEquals(expected[i], arr1[i]);
        }
    }

    @Order(4)
    @Tag("A")
    @DisplayName("Test Problem2: 4")
    @Test
    public void testProblem2D() {
        int [] arr1 = {-23, -4, 12, 15, 0, 0, 0, 0};
        int [] arr2 = {-17, -5, -1, 20};
        int m = 4;
        int n = 4;
        tester.merge(arr1,m,arr2,n);
        int [] expected = {-23,-17,-5,-4,-1,12,15,20};
        for(int i = 0; i < expected.length;i++){
            assertEquals(expected[i], arr1[i]);
        }
    }

    @Order(5)
    @Tag("A")
    @DisplayName("Random Tests 1")
    @Test
    public void randomTests1(){
        SortedSet<Integer> randomNums = new TreeSet<Integer>();
        int size = ThreadLocalRandom.current().nextInt(5, 50);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(0, 100));
        }
        ArrayList<Integer> intRandomNums = new ArrayList<>(randomNums);
        for(int i = 1; i < size; i++){
            int [] arr1 = new int[size];
            int [] arr2 = new int[size - i];
            for(int j = 0; j < i; j++){
                arr1[j] = intRandomNums.get(j);
            }
            for(int j = i; j < size; j++){
                arr2[j - i] = intRandomNums.get(j);
            }
            tester.merge(arr1, i, arr2,size - i);
            for(int j = 0; j < size; j++){
                assertEquals(intRandomNums.get(j), arr1[j]);
            }
        }
    }

    @Order(6)
    @Tag("A")
    @DisplayName("Random Tests 2")
    @Test
    public void randomTests2(){
        SortedSet<Integer> randomNums = new TreeSet<Integer>();
        int size = ThreadLocalRandom.current().nextInt(5, 100);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(0, 1000));
        }
        ArrayList<Integer> intRandomNums = new ArrayList<>(randomNums);
        for(int i = 1; i < size; i++){
            int [] arr1 = new int[size];
            int [] arr2 = new int[size - i];
            for(int j = 0; j < i; j++){
                arr1[j] = intRandomNums.get(j);
            }
            for(int j = i; j < size; j++){
                arr2[j - i] = intRandomNums.get(j);
            }
            tester.merge(arr1, i, arr2,size - i);
            for(int j = 0; j < size; j++){
                assertEquals(intRandomNums.get(j), arr1[j]);
            }
        }
    }

    @Order(7)
    @Tag("A")
    @DisplayName("Random Tests 3")
    @Test
    public void randomTests3(){
        SortedSet<Integer> randomNums = new TreeSet<Integer>();
        int size = ThreadLocalRandom.current().nextInt(0, 10000);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(1, 10000));
        }
        ArrayList<Integer> intRandomNums = new ArrayList<>(randomNums);
        for(int i = 1; i < size; i++){
            int [] arr1 = new int[size];
            int [] arr2 = new int[size - i];
            for(int j = 0; j < i; j++){
                arr1[j] = intRandomNums.get(j);
            }
            for(int j = i; j < size; j++){
                arr2[j - i] = intRandomNums.get(j);
            }
            tester.merge(arr1, i, arr2,size - i);
            for(int j = 0; j < size; j++){
                assertEquals(intRandomNums.get(j), arr1[j]);
            }
        }
    }

    @Order(8)
    @Tag("A")
    @DisplayName("Random Tests 4")
    @Test
    public void randomTests4(){
        SortedSet<Integer> randomNums = new TreeSet<Integer>();
        int size = ThreadLocalRandom.current().nextInt(0, 10000);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(1, 10000));
        }
        ArrayList<Integer> intRandomNums = new ArrayList<>(randomNums);
        for(int i = 1; i < size; i++){
            int [] arr1 = new int[size];
            int [] arr2 = new int[size - i];
            for(int j = 0; j < i; j++){
                arr1[j] = intRandomNums.get(j);
            }
            for(int j = i; j < size; j++){
                arr2[j - i] = intRandomNums.get(j);
            }
            tester.merge(arr1, i, arr2,size - i);
            for(int j = 0; j < size; j++){
                assertEquals(intRandomNums.get(j), arr1[j]);
            }
        }
    }

    @Order(9)
    @Tag("A")
    @DisplayName("Random Tests 5")
    @Test
    public void randomTests5(){
        SortedSet<Integer> randomNums = new TreeSet<Integer>();
        int size = ThreadLocalRandom.current().nextInt(0, 10000);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(1, 10000));
        }
        ArrayList<Integer> intRandomNums = new ArrayList<>(randomNums);
        for(int i = 1; i < size; i++){
            int [] arr1 = new int[size];
            int [] arr2 = new int[size - i];
            for(int j = 0; j < i; j++){
                arr1[j] = intRandomNums.get(j);
            }
            for(int j = i; j < size; j++){
                arr2[j - i] = intRandomNums.get(j);
            }
            tester.merge(arr1, i, arr2,size - i);
            for(int j = 0; j < size; j++){
                assertEquals(intRandomNums.get(j), arr1[j]);
            }
        }
    }


}
