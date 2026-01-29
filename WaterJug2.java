import java.util.*;

public class WaterJug2 {
    public static void main(String[] args) {
        int A = 7, B = 4, T = 6;
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[2] + Math.min(Math.abs(a[0] - T), Math.abs(a[1] - T))));
        Map<Integer, Integer> p = new HashMap<>();
        
        q.add(new int[]{0, 0, 0});
        p.put(0, -1);

        while (!q.isEmpty()) {
            int[] c = q.poll();
            int x = c[0], y = c[1], g = c[2];

            if (x == T || y == T) {
                draw(p, (x << 8) | y);
                return;
            }

            int[] m = {A, y, x, B, 0, y, x, 0, x - Math.min(x, B - y), y + Math.min(x, B - y), x + Math.min(y, A - x), y - Math.min(y, A - x)};
            for (int i = 0; i < 12; i += 2) {
                int h = (m[i] << 8) | m[i+1];
                if (!p.containsKey(h)) {
                    p.put(h, (x << 8) | y);
                    q.add(new int[]{m[i], m[i+1], g + 1});
                }
            }
        }
    }

    static void draw(Map<Integer, Integer> p, int s) {
        if (s != -1) {
            draw(p, p.getOrDefault(s, -1));
            System.out.println("(" + (s >> 8) + "," + (s & 255) + ")");
        }
    }
}