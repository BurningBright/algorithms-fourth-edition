package class0104;

import java.util.HashMap;
import java.util.Map;

import stdlib.StdRandom;
/**
 * @Description 1.4.25
 *          simulate throwing eggs from a building
 *          1.楼层大于0
 *          2.一定有破碎层
 *          3.站在楼顶丢一定碎
 *          4.丢出不碎的蛋可重复用
 * @author Leon
 * @date 2016-06-03 11:19:04
 */
public class ThrowEggs {
    
    private static class Building {
        private int N;      // all floors
        private int F;      // the broken floor number
        private int lastUnbrokenFloor;
        private int times;
        
        Building(int N) {
            this.N = N;
//            this.F = 11;
            this.F = StdRandom.uniform(1, N + 1);
//            System.out.println(F);
            lastUnbrokenFloor = 0;
            times = 0;
        }
        
        public boolean throwEgg(Egg egg, int floor) {
            if(floor > N || floor < 1) 
                throw new IllegalArgumentException("illegal floor -"+floor);
            if(egg.broken)
                throw new IllegalArgumentException("are you kidding me");
            
            times++;    //出手
            if(floor >= F) 
                egg.broken = true;
            else
                lastUnbrokenFloor = floor;
            
            // 破碎 而且 楼层等于上次没碎+1
            return egg.broken && floor == lastUnbrokenFloor + 1 ? true : false;
            
        }
        
    }
    
    private static class Egg {
        private boolean broken;
        Egg() {
            broken = false;
        }
    }
    
    public static int throwInRoot(Building building) {
        Egg egg_one = new Egg();
        Egg egg_two = new Egg();
        
        int step = (int) Math.sqrt(building.N);
        int current=step;
        
        for(; current<building.N+1; current += step) {
            building.throwEgg(egg_one, current);
            if(egg_one.broken) {
//                System.out.println(building.times+"  1 done");
                break;
            }
            
            // 最后的部分直接到楼顶丢鸡蛋 ps.一定会碎
            if(!egg_one.broken && current+step>building.N) {
                current += step;
                break;
            }
        }
        
        // 再检查一边，在低楼层情况下可能之间找到F
        if(building.F == building.lastUnbrokenFloor + 1) {
            return building.times;
        }
        
        // 回溯最后未碎楼层，开始第二次投蛋
        for(int i=current-step+1; i<current; i++) {
            if(building.throwEgg(egg_two, i)) {
//                System.out.println(building.times+"  2 done");
//                break;
                return building.times;
            }
        }
        
        return 0;
    }
    
    public static int throwInGrow(Building building) {
        Egg egg_one = new Egg();
        Egg egg_two = new Egg();
        
        int before = 0;
        int current = 0;
        int estimate = (int) Math.sqrt(2*building.N);
        
        for(int i=1; i<=estimate; i++) {
            before = current;
            current += i;
            building.throwEgg(egg_one, current);
            
            if(egg_one.broken) {
//                System.out.println(building.times+"  1 done");
                break;
            }
            
            // 如果未触顶则直接到楼顶丢鸡蛋 ps.好像有点多余,一定会碎
            if(!egg_one.broken && current+i+1>building.N) {
                current += i+1;
                building.throwEgg(egg_one, building.N);
//                System.out.println(building.times+"  1.1 done");
                break;
            }
            
        }
        
        // 再检查一边，在低楼层情况下可能之间找到F
        if(building.F == building.lastUnbrokenFloor + 1) {
            return building.times;
        }
        
        // 回溯最后未碎楼层，开始第二次投蛋
        for(int i=before; i<current; i++) {
            building.throwEgg(egg_two, i);
            if(egg_two.broken) {
//                System.out.println(building.times+"  2 done");
                return building.times;
            }
        }
        
        return 0;
    }
    
    public static void main(String[] args) {
        
        System.out.println(throwInRoot(new Building(12)));
        System.out.println(throwInGrow(new Building(12)));
        
        int[] src = {10, 100, 1000, 10000, 100000};
        Map<Integer, Double> result1 = new HashMap<Integer, Double>();
        Map<Integer, Double> result2 = new HashMap<Integer, Double>();
        int T = 100;
        
        for(int i: src) {
            int tSum = 0;
            for(int j=0; j<T; j++) {
                tSum += throwInRoot(new Building(i));
            }
            result1.put(i, 1.0*tSum/T);
        }
        System.out.println(result1);
        
        
        for(int i: src) {
            int tSum = 0;
            for(int j=0; j<T; j++) {
                tSum += throwInGrow(new Building(i));
            }
            result2.put(i, 1.0*tSum/T);
        }
        
        System.out.println(result2);
        
    }

}
