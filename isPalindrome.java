import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class isPalindrome {
    public static boolean isPalindrome(Queue<Integer> q) {
        Stack<Integer> s = new Stack<Integer>();
        boolean palindrome = true;
        int size = q.size();
        for (int i = 0; i < size; i++) {
            int n = q.remove();
            q.add(n);
            s.push(n);
        }
        for (int i = 0; i < size / 2; i++) {
            int n1 = q.remove();
            int n2 = s.pop();
            if (n1 != n2) {
                palindrome = false;
            }
            q.add(n1);
        }
        return palindrome;
    }

    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(1);
        q.add(2);
        q.add(3);
        System.out.println(isPalindrome(q));
    }
}
