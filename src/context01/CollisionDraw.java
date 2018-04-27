package context01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.threed.jpct.Camera;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.util.KeyState;
import com.threed.jpct.util.Light;

/**
 * @Description context01.3
 *              3D粒子绘制基础类
 * @author Leon
 * @date 2018-04-26 13:20:00
 */
public class CollisionDraw {
    
    private static JFrame frame;
    
    // Graphics
    private Graphics g = null;
    // KeyMapper是一个方便的类来简化来处理键盘访问方式（硬件或软件渲染时）。它提供了统一的方式来访问键盘
    private KeyMapper keyMapper = null;
    // 当JPCT渲染背景时FrameBuffer类提供了一个缓冲,它的结果本质上是一个能显示或者修改甚至能进行更多后处理的图片
    private FrameBuffer fb = null;
    // World类是JPCT中最重要的类，它像“胶”一样把所有粘在一起，它包含了所有对象和光线且在JPCT中定义场景
    private World world = null;
    private Camera cam = null;
    
    // 平面
    private Object3D planeX = null;
    private Object3D planeY = null;
    private Object3D planeZ = null;
    
    private Object3D cube = null;
    
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean ceiling = false;
    private boolean floor = false;
    
    private boolean roleft = false;
    private boolean roright = false;
    private boolean roup = false;
    private boolean rodown = false;
    
    private SimpleVector moveRes = new SimpleVector(0, 0, 0);
    private static final float DAMPING = .5f;
    private static final float SPEED = 3f;
    private static final float MAXSPEED = 5f;
    private static final int RADIANSPEED = 5;
    
    private static CollisionDraw std = new CollisionDraw();
    
    // 默认构造
    public CollisionDraw() {
        // 返回当前处理器数量
        int numberOfProcs = Runtime.getRuntime().availableProcessors();
        // 一系列优化
        Config.useMultipleThreads = numberOfProcs > 1;
        Config.useMultiThreadedBlitting = numberOfProcs > 1;
        Config.loadBalancingStrategy = 1;
        Config.maxNumberOfCores = numberOfProcs;
        Config.lightMul = 1;
        Config.mtDebug = true;
        
        
        if (frame != null) frame.setVisible(false);
        frame = new JFrame();
        // 设置默认退出方式
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 调整此窗口的大小，以适合其子组件的首选大小和布局
        frame.pack();
        // 窗体大小
        frame.setSize(800, 600);
        // 窗体大小是否可调整
        frame.setResizable(false);
        // 为 null，则此窗口将置于屏幕的中央
        frame.setLocationRelativeTo(null);
        // 是否可见
        frame.setVisible(true);
        /*
         * 因为 Graphics 是一个抽象类，所以应用程序不能直接调用此构造方法。 图形上下文从其他图形上下文获取，或者通过在组件上调用
         * getGraphics 来创建。
         */
        g = frame.getGraphics();
        
        initStuff();
    }
    
    private void move() {
        KeyState ks = null;
        while ((ks = keyMapper.poll()) != KeyState.NONE) {
            
            if (ks.getKeyCode() == KeyEvent.VK_UP) {
                up = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_DOWN) {
                down = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_LEFT) {
                left = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                ceiling = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                floor = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_A) {
                roleft = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_D) {
                roright = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_W) {
                roup = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_S) {
                rodown = ks.getState();
            }
        }
        
        // move the cube
        if (up) {
            SimpleVector t = cube.getZAxis();
            t.scalarMul(SPEED);
            moveRes.add(t);
        }
        if (down) {
            SimpleVector t = cube.getZAxis();
            t.scalarMul(-SPEED);
            moveRes.add(t);
        }
        
        if (right) {
            SimpleVector t = cube.getXAxis();
            t.scalarMul(SPEED);
            moveRes.add(t);
        }
        if (left) {
            SimpleVector t = cube.getXAxis();
            t.scalarMul(-SPEED);
            moveRes.add(t);
        }
        
        if (floor) {
            SimpleVector t = cube.getYAxis();
            t.scalarMul(SPEED);
            moveRes.add(t);
        }
        if (ceiling) {
            SimpleVector t = cube.getYAxis();
            t.scalarMul(-SPEED);
            moveRes.add(t);
        }
        if (roleft) {
            cube.rotateY((float) Math.toRadians(-RADIANSPEED));
        }
        if (roright) {
            cube.rotateY((float) Math.toRadians(RADIANSPEED));
        }
        
        if (roup) {
            cube.rotateX((float) Math.toRadians(-RADIANSPEED));
        }
        if (rodown) {
            cube.rotateX((float) Math.toRadians(RADIANSPEED));
        }
        
        if (moveRes.length() > MAXSPEED) 
            moveRes.makeEqualLength(new SimpleVector(0, 0, MAXSPEED));
        cube.translate(moveRes);
        
        // 减震
        if (moveRes.length() > DAMPING) {
            moveRes.makeEqualLength(new SimpleVector(0, 0, DAMPING));
        } else {
            moveRes = new SimpleVector(0, 0, 0);
        }
    }
    
    private void initStuff() {
        // 创建一个窗体大小同样大小的FrameBuffer，正常采样模式（1对1）
        fb = new FrameBuffer(1024, 768, FrameBuffer.SAMPLINGMODE_NORMAL);
        // 实例化World对象
        world = new World();
        cam = world.getCamera();

        // IRenderer.RENDERER_SOFTWARE是JPCT默认的软件渲染方式
        fb.enableRenderer(IRenderer.RENDERER_SOFTWARE);
        // 在AWT/JFRAME窗体上创建一个KeyMapper
        keyMapper = new KeyMapper(frame);
        
        cube = Primitives.getCube(20);
        cube.translate(new SimpleVector(200 , -30, -200));
        cube.rotateY(-(float) Math.PI / 4f);
        
        // 返回平面（quads^ 2）* 2多边形的每个“scale”的大小单位组成。(其中20为quads,10为scale)
        planeX = Primitives.getPlane(10, 10);
        planeY = Primitives.getPlane(10, 10);
        planeZ = Primitives.getPlane(10, 10);
        // 旋转到正常的角度
        planeX.rotateY(-(float) Math.PI / 2f);
        planeY.rotateX((float) Math.PI / 2f);
        
        
        // 对齐
        planeX.translate(new SimpleVector(0, -50, -50));
        planeY.translate(new SimpleVector(50, 0, -50));
        planeZ.translate(new SimpleVector(50, -50, 0));
        
        world.addObject(planeX);
        world.addObject(planeY);
        world.addObject(planeZ);
        world.addObject(cube);
        
        // 光照
        Light light = new Light(world);
        // 光源位置
        light.setPosition(new SimpleVector(100, -100, -100));
        // 设置光照强度
        light.setIntensity(140, 120, 120);
        // -1表示不衰减
        light.setAttenuation(-1);
        // 设置光源的环境光强度
        world.setAmbientLight(20, 20, 20);
    }
    
    public static void addSphere(Particle3D particle) {
        std.world.addObject(particle.getSphere());
    }
    
    public static void build(){
        // 对world里的所有对象调用build()方法
        std.world.buildAllObjects();
    }
    
    public static void clear() {
        std.fb.clear(Color.BLACK);
    }
    
    public static void pause(int time) {
        try{
            Thread.sleep(time);
        } catch(Exception e) {
            
        }
    }
    
    public static void show() {
        std.move();
        std.cam.setPositionToCenter(std.cube);
        std.cam.align(std.cube);
        
        // 渲染
        std.world.renderScene(std.fb);
        // 绘制
        std.world.draw(std.fb);
        // 更新
        std.fb.update();
        // 应用
        std.fb.display(std.g);
    }
}
