package edu.caltech.cs2.lab08;

import edu.caltech.cs2.lab08.Problem1.TreeNode;
import org.junit.jupiter.api.*;
import edu.caltech.cs2.helpers.Inspection;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Problem1Tests {
    Problem1 tester = new Problem1();
    private static final String PROBLEM_1_SOURCE = "src/edu/caltech/cs2/lab08/Problem1.java";

    private TreeNode put(TreeNode currNode, int val){
        if(currNode == null){
            return new TreeNode(val);
        }
        if(val == currNode.val){
            return currNode;
        }
        if(val < currNode.val){
            currNode.left = put(currNode.left, val);
            return currNode;
        }
        currNode.right = put(currNode.right,val);
        return currNode;
    }

    @Order(0)
    @Tag("B")
    @DisplayName("Import Tests")
    @Test
    public void testForInvalidClasses() {
        List<String> regexps = List.of("java\\.lang\\.reflect", "java\\.io", "javax\\.swing", "java\\.util","\\s*.*\\s*\\[\\]\\s*.*\\s*=\\s*new .*\\[.*\\]\\s*;", ".*\\s*=\\s*new .*\\[.*\\]\\s*;");
        Inspection.assertNoImportsOf(PROBLEM_1_SOURCE, regexps);
        Inspection.assertNoUsageOf(PROBLEM_1_SOURCE, regexps);
    }

    @Order(1)
    @Tag("B")
    @DisplayName("Test Problem1: Tree1")
    @Test
    public void testProblem1A() {
        PrintStream systemErr = System.err;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        System.setErr(systemErr);

        String output = outputStream.toString();
        System.out.println(output);


        // Test 1:
        TreeNode root = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(3), new TreeNode(7)),
                new TreeNode(15, null,
                        new TreeNode(18)));

        int actualAns = tester.rangeSumBST(root, 7, 15);
        assertEquals(32, actualAns);
    }

    @Order(2)
    @Tag("B")
    @DisplayName("Test Problem1: Tree2")
    @Test
    public void testProblem1B(){
        PrintStream systemErr = System.err;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        System.setErr(systemErr);

        String output = outputStream.toString();
        System.out.println(output);

        TreeNode root = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(3, new TreeNode(1), null),
                        new TreeNode(7, new TreeNode(6), null)),
                new TreeNode(15, null,
                        new TreeNode(18)));

        int actualAns = tester.rangeSumBST(root, 6, 10);
        assertEquals(23, actualAns);
    }

    @Order(3)
    @Tag("B")
    @DisplayName("Test Problem1: Tree3")
    @Test
    public void testProblem1C(){
        PrintStream systemErr = System.err;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        System.setErr(systemErr);

        String output = outputStream.toString();
        System.out.println(output);

        TreeNode root = new TreeNode(10,
                new TreeNode(5),
                new TreeNode(15));

        int actualAns = tester.rangeSumBST(root, 6, 7);
        assertEquals(0, actualAns);

    }

    @Order(4)
    @Tag("B")
    @DisplayName("Test Problem1: Same Min/Max")
    @Test
    public void testProblem1extra(){
        PrintStream systemErr = System.err;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        System.setErr(systemErr);

        String output = outputStream.toString();
        System.out.println(output);

        TreeNode root = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(3, new TreeNode(1), null),
                        new TreeNode(7, new TreeNode(6), null)),
                new TreeNode(15, null,
                        new TreeNode(18)));

        int actualAns = tester.rangeSumBST(root, 5, 5);
        assertEquals(5, actualAns);

    }

    @Order(4)
    @Tag("B")
    @DisplayName("Test Problem1: Empty Tree")
    @Test
    public void testProblem1D(){
        PrintStream systemErr = System.err;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        System.setErr(systemErr);

        String output = outputStream.toString();
        System.out.println(output);

        TreeNode root = null;

        int actualAns = tester.rangeSumBST(root, 3, 15);
        assertEquals(0, actualAns);
    }

    @Order(5)
    @Tag("B")
    @DisplayName("Random Tests 1")
    @Test
    public void randomTests1(){
        Set<Integer> randomNums = new HashSet<>() ;
        int size = ThreadLocalRandom.current().nextInt(5, 50);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(0, 100));
        }
        int rangelow = ThreadLocalRandom.current().nextInt(0, 100);
        int rangehigh = ThreadLocalRandom.current().nextInt(0, 100);
        int target = 0;
        for(int item : randomNums){
            if(item >= rangelow && item <= rangehigh){
                target += item;
            }
        }

        TreeNode root = null;
        for(int item : randomNums){
            if(root == null){
                root = new TreeNode(item);
            }
            else{
                root = put(root, item);
            }
        }

        int actual = tester.rangeSumBST(root, rangelow, rangehigh);
        assertEquals(target,actual);
    }

    @Order(6)
    @Tag("B")
    @DisplayName("Random Tests 2")
    @Test
    public void randomTests2(){
        Set<Integer> randomNums = new HashSet<>() ;
        int size = ThreadLocalRandom.current().nextInt(5, 100);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(0, 1000));
        }
        int rangelow = ThreadLocalRandom.current().nextInt(0, 1000);
        int rangehigh = ThreadLocalRandom.current().nextInt(0, 1000);
        int target = 0;
        for(int item : randomNums){
            if(item >= rangelow && item <= rangehigh){
                target += item;
            }
        }

        TreeNode root = null;
        for(int item : randomNums){
            if(root == null){
                root = new TreeNode(item);
            }
            else{
                root = put(root, item);
            }
        }

        int actual = tester.rangeSumBST(root, rangelow, rangehigh);
        assertEquals(target,actual);
    }

    @Order(7)
    @Tag("B")
    @DisplayName("Random Tests 3")
    @Test
    public void randomTests3(){
        Set<Integer> randomNums = new HashSet<>() ;
        int size = ThreadLocalRandom.current().nextInt(0, 10000);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(1, 10000));
        }
        int rangelow = ThreadLocalRandom.current().nextInt(0, 10000);
        int rangehigh = ThreadLocalRandom.current().nextInt(0, 10000);
        int target = 0;
        for(int item : randomNums){
            if(item >= rangelow && item <= rangehigh){
                target += item;
            }
        }

        TreeNode root = null;
        for(int item : randomNums){
            if(root == null){
                root = new TreeNode(item);
            }
            else{
                root = put(root, item);
            }
        }

        int actual = tester.rangeSumBST(root, rangelow, rangehigh);
        assertEquals(target,actual);
    }

    @Order(8)
    @Tag("B")
    @DisplayName("Random Tests 4")
    @Test
    public void randomTests4(){
        Set<Integer> randomNums = new HashSet<>() ;
        int size = ThreadLocalRandom.current().nextInt(0, 10000);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(1, 10000));
        }
        int rangelow = ThreadLocalRandom.current().nextInt(0, 10000);
        int rangehigh = ThreadLocalRandom.current().nextInt(0, 10000);
        int target = 0;
        for(int item : randomNums){
            if(item >= rangelow && item <= rangehigh){
                target += item;
            }
        }

        TreeNode root = null;
        for(int item : randomNums){
            if(root == null){
                root = new TreeNode(item);
            }
            else{
                root = put(root, item);
            }
        }

        int actual = tester.rangeSumBST(root, rangelow, rangehigh);
        assertEquals(target,actual);
    }

    @Order(9)
    @Tag("B")
    @DisplayName("Random Tests 5")
    @Test
    public void randomTests5(){
        Set<Integer> randomNums = new HashSet<>() ;
        int size = ThreadLocalRandom.current().nextInt(0, 10000);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(1, 10000));
        }
        int rangelow = ThreadLocalRandom.current().nextInt(0, 10000);
        int rangehigh = ThreadLocalRandom.current().nextInt(0, 10000);
        int target = 0;
        for(int item : randomNums){
            if(item >= rangelow && item <= rangehigh){
                target += item;
            }
        }

        TreeNode root = null;
        for(int item : randomNums){
            if(root == null){
                root = new TreeNode(item);
            }
            else{
                root = put(root, item);
            }
        }

        int actual = tester.rangeSumBST(root, rangelow, rangehigh);
        assertEquals(target,actual);
    }

    @Order(10)
    @Tag("B")
    @DisplayName("Random Tests 6")
    @Test
    public void randomTests6(){
        Set<Integer> randomNums = new HashSet<>() ;
        int size = ThreadLocalRandom.current().nextInt(0, 10000);
        while(randomNums.size() != size){
            randomNums.add(ThreadLocalRandom.current().nextInt(1, 10000));
        }
        int rangelow = ThreadLocalRandom.current().nextInt(0, 10000);
        int rangehigh = ThreadLocalRandom.current().nextInt(0, 10000);
        int target = 0;
        for(int item : randomNums){
            if(item >= rangelow && item <= rangehigh){
                target += item;
            }
        }

        TreeNode root = null;
        for(int item : randomNums){
            if(root == null){
                root = new TreeNode(item);
            }
            else{
                root = put(root, item);
            }
        }

        int actual = tester.rangeSumBST(root, rangelow, rangehigh);
        assertEquals(target,actual);
    }





}