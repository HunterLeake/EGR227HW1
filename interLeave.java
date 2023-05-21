import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class interLeave {
    public static void interleave(Queue<Integer> q) {
        if (q.size() % 2 == 1) {
            throw new IllegalArgumentException();
        }
        Stack<Integer> s = new Stack<Integer>();
        int halfSize = q.size() / 2;
        for (int i = 0; i < halfSize; i++) {
            s.push(q.remove());
        }
        while (!s.empty()) {
            q.add(s.pop());
        }
        for (int i = 0; i < halfSize; i++) {
            q.add(q.remove());
        }
        for (int i = 0; i < halfSize; i++) {
            s.push(q.remove());
        }
        while (!s.empty()) {
            q.add(s.pop());
            q.add(q.remove());
        }
    }

    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        interleave(q);
        System.out.println(q);
    }
}