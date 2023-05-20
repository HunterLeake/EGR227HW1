package hw1.src;

import java.util.Queue;
import java.util.Stack;

public class HtmlValidator {
    private Queue<HtmlTag> tags;

    public HtmlValidator() {
        tags = new java.util.LinkedList<>();
    }

    public HtmlValidator(Queue<HtmlTag> tags) {
        if (tags == null) {
            throw new IllegalArgumentException();
        }
        this.tags = new java.util.LinkedList<>(tags);
    }

    public void addTag(HtmlTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException();
        }
        tags.add(tag);
    }

    public Queue<HtmlTag> getTags() {
        return tags;
    }

    public void removeAll(String tagName) {
        if (tagName == null) {
            throw new IllegalArgumentException();
        }
        Queue<HtmlTag> tempQueue = new java.util.LinkedList<>();
        while (!tags.isEmpty()) {
            HtmlTag tag = tags.remove();
            if (!tag.getElement().equals(tagName)) {
                tempQueue.add(tag);
            }
        }
        tags = tempQueue;
    }

    public boolean validate() {
        Stack<HtmlTag> stack = new Stack<>();
        for (HtmlTag tag : tags) {
            if (tag.isOpenTag()) {
                stack.push(tag);
            } else if (tag.isOpenTag()) {
                if (stack.isEmpty()) {
                    return false;
                }
                HtmlTag openTag = stack.pop();
                if (!openTag.getElement().equals(tag.getElement())) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}