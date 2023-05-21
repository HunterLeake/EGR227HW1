import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class isSorted {
    public static boolean isSorted(Stack<Integer> s) {
        Queue<Integer> q = new LinkedList<Integer>();
        boolean sorted = true;
        int size = s.size();
        for (int i = 0; i < size; i++) {
            int n = s.pop();
            q.add(n);
            s.push(n);
        }
        int prev = q.remove();
        for (int i = 1; i < size; i++) {
            int curr = q.remove();
            if (prev > curr) {
                sorted = false;
            }
            s.push(prev);
            prev = curr;
        }
        s.push(prev);
        while (!q.isEmpty()) {
            s.push(q.remove());
        }
        return sorted;
    }

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<Integer>();
        s.push(1);
        s.push(2);
        s.push(3);
        System.out.println(isSorted(s));
    }

}
