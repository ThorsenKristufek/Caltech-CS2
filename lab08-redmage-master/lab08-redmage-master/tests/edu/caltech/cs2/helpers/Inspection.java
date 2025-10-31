package edu.caltech.cs2.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Inspection {

    private static String arrayRegex1 = "\\s*.*\\s*\\[\\]\\s*.*\\s*=\\s*new .*\\[.*\\]\\s*;";
    private static String arrayRegex2 = ".*\\s*=\\s*new .*\\[.*\\]\\s*;";

    private static String getUsageOf(List<String> regexps, List<? extends Node> codeObjects) {
        for (Node d : codeObjects) {
            for (String regex : regexps) {
                if (d.toString().replaceAll("\\R", "").matches(".*" + regex + ".*")) {
                    return regex;
                }
            }
        }
        return null;
    }

    public static void assertNoImportsOf(String filePath, List<String> regexps) {
        try {
            CompilationUnit cu = JavaParser.parse(new File(filePath));
            String usage = getUsageOf(regexps, cu.getImports());
            if (usage != null) {
                fail("You may not import " + usage + " in " + Paths.get(filePath).getFileName() + ".");
            }
        } catch (FileNotFoundException e) {
            fail("Missing Java file: " + Paths.get(filePath).getFileName());
        }
    }

    private static class ConstructorCollector extends VoidVisitorAdapter<List<ConstructorDeclaration>> {
        @Override
        public void visit(ConstructorDeclaration md, List<ConstructorDeclaration> collector) {
            super.visit(md, collector);
            collector.add(md);
        }
    }

    private static class MethodCollector extends VoidVisitorAdapter<List<MethodDeclaration>> {
        @Override
        public void visit(MethodDeclaration md, List<MethodDeclaration> collector) {
            super.visit(md, collector);
            collector.add(md);
        }
    }

    private static MethodCollector METHOD_COLLECTOR = new MethodCollector();
    private static ConstructorCollector CONSTRUCTOR_COLLECTOR = new ConstructorCollector();

    public static void assertNoUsageOf(String filePath, List<String> regexps) {
        try {
            CompilationUnit cu = JavaParser.parse(new File(filePath));

            List<ConstructorDeclaration> constructors = new ArrayList<>();
            CONSTRUCTOR_COLLECTOR.visit(cu, constructors);
            String usage = getUsageOf(regexps, constructors);
            if (usage != null) {
                fail("You may not use " + usage + " in " + Paths.get(filePath).getFileName() + ".");
            }

            List<MethodDeclaration> methods = new ArrayList<>();
            METHOD_COLLECTOR.visit(cu, methods);
            usage = getUsageOf(regexps, methods);
            if (usage != null) {
                if(usage.equals(arrayRegex1) || usage.equals(arrayRegex2)){
                    fail("You may not use arrays in " + Paths.get(filePath).getFileName() + ".");
                }
                else {
                    fail("You may not use " + usage + " in " + Paths.get(filePath).getFileName() + ".");
                }
            }
        } catch (FileNotFoundException e) {
            fail("Missing Java file: " + Paths.get(filePath).getFileName());
        }
    }

}
