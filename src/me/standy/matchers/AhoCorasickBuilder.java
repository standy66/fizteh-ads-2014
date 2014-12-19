package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.*;

//TODO: fix file template
/**
 * This class is package-private and is not exposed as a library class. Thus, it doesn't need testing.
 * @author andrew
 *         Created by andrew on 05.12.14.
 */

class AhoNode {
    AhoNode parent;
    char character;
    Map<Character, AhoNode> go = new HashMap<>();
    Map<Character, AhoNode> next = new HashMap<>();
    AhoNode suflink = null;
    AhoNode hardlink = null;
    boolean hardLinkSet = false;
    int stringId;
    boolean terminal;

    public AhoNode(char character, AhoNode parent, boolean terminal, int stringId) {
        this.character = character;
        this.parent = parent;
        this.terminal = terminal;
        this.stringId = stringId;
    }

    AhoNode go(char c) {
        if (go.get(c) == null) {
            if (parent != null) {
                go.put(c, link().go(c));
            } else {
                go.put(c, this);
            }
        }
        return go.get(c);
    }

    AhoNode link() {
        if (suflink == null) {
            if (parent == null)
                return suflink = null;
            //if our parent is root node
            if (parent.parent == null) {
                return suflink = parent;
            } else {
                return suflink = parent.link().go(character);
            }
        }
        return suflink;
    }

    AhoNode hardlink() {
        if (!hardLinkSet) {
            hardLinkSet = true;
            if (link() == null) {
                return hardlink = null;
            } else {
                if (link().terminal) {
                    return hardlink = link();
                } else {
                    return hardlink = link().hardlink();
                }
            }
        }
        return hardlink;
    }
}


class AhoCorasickBuilder {

    public AhoNode build(List<String> strings, List<Integer> ids) {
        AhoNode root = new AhoNode((char) 0, null, false, 0);
        for (int i = 0; i < strings.size(); i++) {
            addString(root, strings.get(i), ids.get(i));
        }
        return root;
    }

    private void addString(AhoNode root, String s, int stringId) {
        AhoNode current = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (current.go.get(c) == null) {
                AhoNode newNode = new AhoNode(c, current, false, stringId);
                current.go.put(c, newNode);
                current.next.put(c, newNode);
            }
            current = current.go.get(c);
        }
        current.terminal = true;
        current.stringId = stringId;
    }

    private void clear(AhoNode node) {
        if (node != null) {
            node.hardLinkSet = false;
            node.suflink = node.hardlink = null;
            node.go.clear();
            node.go.putAll(node.next);
        }
    }

    public AhoNode merge(AhoNode first, AhoNode second, List<String> templatesList) {
        List<Integer> templateIds = new ArrayList<>();
        if (first != null)
            dfs(first, templateIds);
        if (second != null)
            dfs(second, templateIds);
        List<String> templates = new ArrayList<>();
        for (Integer id : templateIds) {
            templates.add(templatesList.get(id));
        }
        return build(templates, templateIds);
    }

    @Deprecated
    public AhoNode mergeRecursive(AhoNode first, AhoNode second) {
        if (first == null) {
            clear(second);
            return second;
        }
        if (second == null) {
            clear(first);
            return first;
        }
        Set<Character> processed = new HashSet<>();
        Map<Character, AhoNode> firstEdgesCopy = new HashMap<>(first.next);
        for (Map.Entry<Character, AhoNode> edge : firstEdgesCopy.entrySet()) {
            char ch = edge.getKey();
            processed.add(ch);
            AhoNode firstChild = edge.getValue();
            AhoNode secondChild = second.next.get(ch);
            first.next.put(ch, mergeRecursive(firstChild, secondChild));
            first.next.get(ch).parent = first;
        }
        for (Map.Entry<Character, AhoNode> edge : second.next.entrySet()) {
            AhoNode node = edge.getValue();
            char ch = edge.getKey();
            if (!processed.contains(ch)) {
                node.parent = first;
                clear(node);
                first.next.put(ch, node);
            }
        }
        clear(first);
        first.terminal |= second.terminal;
        if (second.terminal) {
            first.stringId = second.stringId;
        }
        return first;
    }

    private void dfs(AhoNode node, List<Integer> templateIds) {
        if (node.terminal) {
            templateIds.add(node.stringId);
        }
        for (AhoNode next : node.next.values()) {
            if (next != null) {
                dfs(next, templateIds);
            }
        }
    }
}