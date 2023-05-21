import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class mirror {
    public static void mirror(Stack<Integer> s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        Queue<Integer> q = new LinkedList<Integer>();
        int size = s.size();
        for (int i = 0; i < size; i++) {
            int n = s.pop();
            q.add(n);
            s.push(n);
        }
        while (!q.isEmpty()) {
            int n = q.remove();
            s.push(n);
            q.add(n);
        }
    }

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<Integer>();
        s.push(1);
        s.push(2);
        s.push(3);
        mirror(s);
        System.out.println(s);
    }

}
