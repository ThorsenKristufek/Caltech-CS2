package edu.caltech.cs2.lab04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DecisionTree {
    private final DecisionTreeNode root;

    public DecisionTree(DecisionTreeNode root) {
        this.root = root;
    }

    public String predict(Dataset.Datapoint point) {
        return ((OutcomeNode) predictDad(root, point)).outcome;
    }

    public DecisionTreeNode predictDad(DecisionTreeNode curPoint, Dataset.Datapoint point) {
        System.out.println(curPoint);
        if(curPoint.isLeaf()) return curPoint;
        AttributeNode nownode = (AttributeNode) curPoint;
        String label = point.attributes.get(nownode.attribute);
        return predictDad(nownode.children.get(label), point);
    }

    public static DecisionTreeNode id3helper(Dataset data, List<String> attributes) {
        if(!Objects.equals(data.pointsHaveSameOutcome(), "")) {
            return new OutcomeNode(data.pointsHaveSameOutcome());
        }
        if(attributes.isEmpty()) {
            return new OutcomeNode(data.getMostCommonOutcome());
        }
        String minEnt = data.getAttributeWithMinEntropy(attributes);
        List<String> feats = data.getFeaturesForAttribute(minEnt);
        HashMap<String, DecisionTreeNode> children = new HashMap<>();
        for(String feat : feats) {
            Dataset points = data.getPointsWithFeature(feat);
            if(points.isEmpty()) {
                children.put(feat, new OutcomeNode(data.getMostCommonOutcome()));
            }
            else {
                List<String> newattr = new ArrayList<>(attributes);
                newattr.remove(minEnt);
                children.put(feat, id3helper(points, newattr));
            }
        }
        return new AttributeNode(minEnt, children);
    }

    public static DecisionTree id3(Dataset dataset, List<String> attributes) {
        String alphaMale = dataset.getMostCommonOutcome();
        return new DecisionTree(id3helper(dataset, attributes));
    }
}
