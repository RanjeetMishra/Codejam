import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class EruptingVolcanoes {

    static class EffectData {
        int x;
        int y;
        int w;

        EffectData(int x,int y,int w) {
            this.x=x;
            this.y = y;
            this.w = w;
        }
    }

    private static int getFullEffect(int x, int y, List<EffectData> volcanoList) {

        int resultEffect = 0;
        for(EffectData e:volcanoList) {
            resultEffect += Math.max(e.w- Math.max(Math.abs(e.x-x), Math.abs(e.y-y)), 0);
        }
        return resultEffect;
    }

    public static int maxVolcanoEffect(int n, List<EffectData> volcanoList) {

        int maxEffect = Integer.MIN_VALUE;

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                int resultEffect = getFullEffect(i,j,volcanoList);
                if(resultEffect>maxEffect)
                    maxEffect = resultEffect;
            }
        }
        return maxEffect;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();

        List<EffectData> volcanoList = new ArrayList<EffectData>();

        for(int a0 = 0; a0 < m; a0++){
            int x = in.nextInt();
            int y = in.nextInt();
            int w = in.nextInt();
            volcanoList.add(new EffectData(x,y,w));
            // Write Your Code Here
        }
        System.out.println(maxVolcanoEffect(n,volcanoList));

        in.close();
    }
}
