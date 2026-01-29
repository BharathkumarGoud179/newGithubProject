import java.util.*;

public class WaterJug1 {
    public static void main(String[] args) {
        List<int[]> states = new ArrayList<>(List.of(new int[]{0, 0, -1})); // x, y, parentIndex
        for (int i = 0; i < states.size(); i++) {
            int x = states.get(i)[0], y = states.get(i)[1];

            if (x == 2 || y == 2) {
                printPath(states, i);
                return;
            }

            int[] m = {4,y, x,3, 0,y, x,0, x-Math.min(x,3-y),y+Math.min(x,3-y), x+Math.min(y,4-x),y-Math.min(y,4-x)};
            for (int j = 0; j < 12; j += 2) {
                int nx = m[j], ny = m[j+1];
                if (states.stream().noneMatch(s -> s[0] == nx && s[1] == ny))
                    states.add(new int[]{nx, ny, i});
            }
        }
    }

    static void printPath(List<int[]> states, int i) {
        if (i < 0) return;
        printPath(states, states.get(i)[2]);
        System.out.println("(" + states.get(i)[0] + "," + states.get(i)[1] + ")");
    }
}