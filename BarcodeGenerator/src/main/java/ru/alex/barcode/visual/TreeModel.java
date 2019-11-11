package ru.alex.barcode.visual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.lang3.StringUtils;

public class TreeModel extends DefaultTreeModel {
    private DataNode root;

    public TreeModel(List<String> lines, String delimiter) {
        super(null);
        init(lines, delimiter);
    }

    @Override
    public DataNode getRoot() {
        return root;
    }

    private void init(List<String> lines, String delimiter) {
        Map<String, List<String>> data = lines.stream()
            .collect(
                Collectors.groupingBy(
                    s -> StringUtils.substringBefore(s, delimiter),
                    Collectors.mapping(n -> StringUtils.trimToNull(StringUtils.substringAfter(n, delimiter)), Collectors.toList())
                )
            );
        root = new DataNode(null, null);
        for (String key : data.keySet()) {
            DataNode node = new DataNode(root, key);
            data.get(key).stream().filter(StringUtils::isNoneBlank).forEach(s -> node.addNode(new DataNode(node, s)));
            root.addNode(node);
        }
    }

    public Map<String, List<String>> getData() {
        HashMap<String, List<String>> result = new HashMap<>();
        for (DataNode node : root.getChildren()) {
            result.put(node.getDescription(), node.getChildren().stream().map(DataNode::getDescription).collect(Collectors.toList()));
        }
        return result;
    }
}
