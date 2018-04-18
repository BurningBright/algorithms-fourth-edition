package class0505;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * @Description 5.5.0
 *          图片输入 转文本、字节
 * @author Leon
 * @date 2018-04-16 15:15:00
 */
public class QPicToBit {
    
    public static void writeChar() throws Exception {
        BufferedImage image = ImageIO.read(new FileInputStream("resource\\5.5\\q128x192.png"));
        FileOutputStream fos = new FileOutputStream("resource\\5.5\\q128x192.txt");
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) 
                fos.write(image.getRGB(j, i) == -1? '0': '1');
            fos.write('\r');
            fos.write('\n');
        }
        fos.close();
    }
    
    public static void writeBit() throws Exception {
        BufferedImage image = ImageIO.read(new FileInputStream("resource\\5.5\\q128x192.png"));
        FileOutputStream fos = new FileOutputStream("resource\\5.5\\q128x192.bin");
        int width = image.getWidth();
        int height = image.getHeight();
        int a = 0;
        for (int i=0, k=1; i<height; i++) 
            for (int j=0; j<width; j++, k++) {
                int color = image.getRGB(j, i);
                a = (a << 1) + ( color == -1? 0: 1);
                if (k%8 == 0) {
                    fos.write(a);
                    a = 0;
                }
            }
        fos.close();
    }
    
    public static void main(String[] args) {
        try {
//            writeChar();
            writeBit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
