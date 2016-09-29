package class0204;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description 2.4.37
 *      对优先队列在一秒内所能做的最大弹压次数做测试
 *      生成平均次数分布图表
 * @author Leon
 * @date 2016-09-29 15:43:49
 */
public class PerformanceDriverII {
    
    static public class CountTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("运行了！时间为：" + new Date());
        }
    }
    
    public static void main(String[] args) {
        CountTask task = new CountTask();
        Timer timer = new Timer();
        System.out.println("当前时间："+new Date());
        timer.schedule(task, 3000,1000);
        try {
            Thread.sleep(60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
    }

}
