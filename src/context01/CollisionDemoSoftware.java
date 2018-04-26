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
 * 碰撞检测Demo
 * jpct demo
 * @author Simdanfeg
 */
public class CollisionDemoSoftware extends JFrame {
    // SerialID
    private static final long serialVersionUID = 1L;

    private static final float DAMPING = 0.1f;

    private static final float SPEED = 1f;

    private static final float MAXSPEED = 1f;
    // Graphics
    private Graphics g = null;
    // KeyMapper是一个方便的类来简化来处理键盘访问方式（硬件或软件渲染时）。它提供了统一的方式来访问键盘
    private KeyMapper keyMapper = null;
    // 当JPCT渲染背景时FrameBuffer类提供了一个缓冲,它的结果本质上是一个能显示或者修改甚至能进行更多后处理的图片
    private FrameBuffer fb = null;
    // World类是JPCT中最重要的类，它像“胶”一样把所有粘在一起，它包含了所有对象和光线且在JPCT中定义场景
    private World world = null;
    /*
     * Object3D类是一个三维对象,千万不要傻呼呼的认为它与java.lang.Object类似。
     * 一个Object3D对象作为一个实例被添加到在渲染的World对象中。Object3D在World 中一次添加一个实例
     * ，他们可能被联系起作为孩子/父母来在他们中建立一个制度. 人体模型当然也能应用在以上的规则中。他们常常不加到一个World实例中，而是
     * 绑定到其它对象中(人体模型或非人体模型)。有些方法 在这个类中需要一个实例
     * 添加到一个World实例中(用World.addObject()方法可以实现)。
     */
    // 平面
    private Object3D plane = null;
    // 斜道
    private Object3D ramp = null;
    // 立方体
    private Object3D cube = null;
    // 立方体2
    private Object3D cube2 = null;
    // 球体
    private Object3D sphere = null;
    // 上
    private boolean up = false;
    // 下
    private boolean down = false;
    // 左
    private boolean left = false;
    // 右
    private boolean right = false;
    // 是否循环
    private boolean doloop = true;
    
    private boolean xleft = false;
    private boolean xright = false;
    private boolean zleft = false;
    private boolean zright = false;

    /*
     * SimpleVector是一个代表三维矢量的基础类，几乎每一个矢量都
     * 是用SimpleVector或者至少是一个SimpleVector变体构成的(有时由于 某些原因比如性能可能会用(float x,float
     * y,float z)之类)。
     */
    private SimpleVector moveRes = new SimpleVector(0, 0, 0);

    private SimpleVector ellipsoid = new SimpleVector(2, 2, 2);

    // 默认构造
    public CollisionDemoSoftware() {
        // 返回当前处理器数量
        int numberOfProcs = Runtime.getRuntime().availableProcessors();
        // 一系列优化
        Config.useMultipleThreads = numberOfProcs > 1;
        Config.useMultiThreadedBlitting = numberOfProcs > 1;
        Config.loadBalancingStrategy = 1;
        Config.maxNumberOfCores = numberOfProcs;
        Config.lightMul = 1;
        Config.mtDebug = true;
        // 设置默认退出方式
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 调整此窗口的大小，以适合其子组件的首选大小和布局
        pack();
        // 窗体大小
        setSize(800, 600);
        // 窗体大小是否可调整
        setResizable(false);
        // 为 null，则此窗口将置于屏幕的中央
        setLocationRelativeTo(null);
        // 是否可见
        setVisible(true);
        /*
         * 因为 Graphics 是一个抽象类，所以应用程序不能直接调用此构造方法。 图形上下文从其他图形上下文获取，或者通过在组件上调用
         * getGraphics 来创建。
         */
        g = getGraphics();
    }

    private void initStuff() {
        // 创建一个窗体大小同样大小的FrameBuffer，正常采样模式（1对1）
        fb = new FrameBuffer(1024, 768, FrameBuffer.SAMPLINGMODE_NORMAL);
        // 实例化World对象
        world = new World();

        // Enables a renderer (OpenGL or software) in OpenGL-lighting mode.
        // OpenGL-lighting mode is the recommended mode for the software as well
        // as for the hardware renderer. However, jPCT constructs every new
        // FrameBuffer as software/legacy for compatibility reasons. It is
        // possible to use the software renderer as well as the OpenGL renderer
        // on a single framebuffer at the same time, but it's not advised to do
        // so, because this will disable some optimizations that are else made
        // in case OpenGL is used (like T&L optimizations and triangle strips).
        // To enable OpenGL without supporting the software renderer, the
        // software renderer has to be disabled after enabling OpenGL.

        // Important: NEVER change the renderer from outside the thread that is
        // doing the rendering if you intend to use the OpenGL renderer, because
        // this will cause your application to crash. This is a "feature" of
        // LWJGL, so there's nothing jPCT can do about it.

        // IRenderer.RENDERER_SOFTWARE是JPCT默认的软件渲染方式
        fb.enableRenderer(IRenderer.RENDERER_SOFTWARE);
        // 在AWT/JFRAME窗体上创建一个KeyMapper
        keyMapper = new KeyMapper(this);
        // 返回平面（quads^ 2）* 2多边形的每个“scale”的大小单位组成。(其中20为quads,10为scale)
        plane = Primitives.getPlane(20, 10);
        // 旋转到正常的角度
        plane.rotateX((float) Math.PI / 2f);
        // 精灵(操作对象)
        ramp = Primitives.getCube(20);
//        ramp.rotateX((float) Math.PI / 2f);
        // 球体
        sphere = Primitives.getSphere(30);
        sphere.translate(-50, 10, 50);
        // 正方体1
        cube2 = Primitives.getCube(20);
        cube2.translate(60, -20, 60);
        // 正方体2
        cube = Primitives.getCube(2);
        cube.translate(-50, -10, -50);
        // Set CollisionMode(设置碰撞模式)
        plane.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
        ramp.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
        sphere.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
        cube2.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
        cube.setCollisionMode(Object3D.COLLISION_CHECK_SELF);
        // 将Object3D对象添加到world中
        world.addObject(plane);
        world.addObject(ramp);
        world.addObject(cube);
        world.addObject(sphere);
        world.addObject(cube2);
        // 光照
        Light light = new Light(world);
        // 光源位置
        light.setPosition(new SimpleVector(0, -80, 0));
        // 设置光照强度
        light.setIntensity(140, 120, 120);
        // -1表示不衰减
        light.setAttenuation(-1);
        // 设置光源的环境光强度
        world.setAmbientLight(20, 20, 20);
        // 对world里的所有对象调用build()方法
        world.buildAllObjects();
    }

    private void move() {
        KeyState ks = null;
        while ((ks = keyMapper.poll()) != KeyState.NONE) {
            // 如果按下上键
            if (ks.getKeyCode() == KeyEvent.VK_UP) {
                // ks.getState()表示键盘按下或释放
                up = ks.getState();
            }
            // 如果按下下键
            if (ks.getKeyCode() == KeyEvent.VK_DOWN) {
                // ks.getState()表示键盘按下或释放
                down = ks.getState();
            }
            // 如果按下左键
            if (ks.getKeyCode() == KeyEvent.VK_LEFT) {
                // ks.getState()表示键盘按下或释放
                left = ks.getState();
            }
            // 如果按下右键
            if (ks.getKeyCode() == KeyEvent.VK_RIGHT) {
                // ks.getState()表示键盘按下或释放
                right = ks.getState();
            }
            // 如果按下Escape键
            if (ks.getKeyCode() == KeyEvent.VK_ESCAPE) {
                // ks.getState()表示键盘按下或释放
                doloop = false;
            }
            if (ks.getKeyCode() == KeyEvent.VK_A) {
                xleft = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_D) {
                xright = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_W) {
                zleft = ks.getState();
            }
            if (ks.getKeyCode() == KeyEvent.VK_S) {
                zright = ks.getState();
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
        // 沿Y轴旋转1度
        if (left) {
            cube.rotateY((float) Math.toRadians(-1));
        }
        // 同上，沿Y轴反方向旋转1度
        if (right) {
            cube.rotateY((float) Math.toRadians(1));
        }

        if (xleft) {
            cube.rotateX((float) Math.toRadians(-1));
        }
        // 同上，沿Y轴反方向旋转1度
        if (xright) {
            cube.rotateX((float) Math.toRadians(1));
        }
        if (zleft) {
            cube.rotateZ((float) Math.toRadians(-1));
        }
        // 同上，沿Y轴反方向旋转1度
        if (zright) {
            cube.rotateZ((float) Math.toRadians(1));
        }
        
        // avoid high speeds
        // SimpleVector长度大于MAXSPEED时，取最大值为MAXSPEED
        if (moveRes.length() > MAXSPEED) {
            moveRes.makeEqualLength(new SimpleVector(0, 0, MAXSPEED));
        }
        /*
         * 检查的东西，如果当前对象碰撞时到特定的方向前进。这只是一个检查，
         * 所以没有翻译正在执行。一个碰撞只能检测到被设置为COLLISION_CHECK_OTHERS对象。
         * 此方法使用（扫描）椭球体，多边形碰撞检测。
         */

        // 第1个参数为cube的移动(translation)
        // 第2个参数为碰撞的区域大小(ellips)
        // 此处的第3个参数为:递归深度的碰撞检测，较高的值将提高碰撞检测精度，而且降低性能。合理的值介于1和5。
        // trsn包含当前的坐标信息
        moveRes = cube.checkForCollisionEllipsoid(moveRes, ellipsoid, 5);
        // cube旋转
        cube.translate(moveRes);

        // finally apply the gravity:
        // 同上，考虑重力的影响
        SimpleVector t = new SimpleVector(0, 1, 0);
        t = cube.checkForCollisionEllipsoid(t, ellipsoid, 1);
        cube.translate(t);

        // damping
        // 减震
        if (moveRes.length() > DAMPING) {
            moveRes.makeEqualLength(new SimpleVector(0, 0, DAMPING));
        } else {
            moveRes = new SimpleVector(0, 0, 0);
        }
    }

    // 运行
    private void doIt() throws Exception {
        // Camera
        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEOUT, 100);
        cam.moveCamera(Camera.CAMERA_MOVEUP, 100);
        cam.lookAt(ramp.getTransformedCenter());
        // FPS
        long start = System.currentTimeMillis();
        @SuppressWarnings("unused")
        long fps = 0;

        while (doloop) {
            move();
            cam.setPositionToCenter(cube);
            cam.align(cube);
            cam.rotateCameraX((float) Math.toRadians(30));
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 100);
            // 用红色清屏
            fb.clear(Color.RED);
            // 渲染
            world.renderScene(fb);
            // 绘制
            world.draw(fb);
            // 更新
            fb.update();
            // 应用
            fb.display(g);
            // 计算FPS
            fps++;
            if (System.currentTimeMillis() - start >= 1000) {
                start = System.currentTimeMillis();
//                System.out.println(fps);
                fps = 0;
            }
            Thread.sleep(100);
        }
        fb.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        System.exit(0);
    }

    // 很难解释
    public static void main(String[] args) throws Exception {
        CollisionDemoSoftware cd = new CollisionDemoSoftware();
        // 初始化场景
        cd.initStuff();
        // 绘制场景
        cd.doIt();
    }
}