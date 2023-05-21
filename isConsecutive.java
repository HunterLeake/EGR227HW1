import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class isConsecutive {
    public static boolean isConsecutive(Stack<Integer> s) {
        Queue<Integer> q = new LinkedList<Integer>();
        boolean consecutive = true;
        int size = s.size();
        for (int i = 0; i < size - 1; i++) {
            q.add(s.pop());
        }
        int prev = s.pop();
        while (!q.isEmpty()) {
            int curr = q.remove();
            if (prev - curr != 1) {
                consecutive = false;
            }
            s.push(prev);
            prev = curr;
        }
        s.push(prev);
        while (!q.isEmpty()) {
            s.push(q.remove());
        }
        return consecutive;
    }

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<Integer>();
        s.push(1);
        s.push(2);
        s.push(3);
        System.out.println(isConsecutive(s));
    }
}