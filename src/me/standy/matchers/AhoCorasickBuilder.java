package me.standy.matchers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public AhoNode build(List<String> strings) {
        AhoNode root = new AhoNode((char) 0, null, false, 0);
        for (int i = 0; i < strings.size(); i++) {
            addString(root, strings.get(i), i);
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
            }
            current = current.go.get(c);
        }
        current.terminal = true;
        current.stringId = stringId;
    }


}