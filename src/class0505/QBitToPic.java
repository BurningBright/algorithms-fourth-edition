package class0505;

import java.awt.Color;

import stdlib.BinaryIn;
import stdlib.Picture;

/**
 * @Description 5.5.0
 *          文本输入、字节输入  转图片
 * @author Leon
 * @date 2018-04-16 13:15:00
 */
public class QBitToPic {
    
    public static void readChars() {
        int width = 32;
        int height = 48;
        Picture picture = new Picture(width, height);
        BinaryCharIn in = new BinaryCharIn("resource\\5.5\\q32x48.txt");
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!in.isEmpty()) {
                    boolean bit = in.readBoolean();
                    if (bit) picture.set(col, row, Color.BLACK);
                    else     picture.set(col, row, Color.WHITE);
                } else {
                    picture.set(col, row, Color.RED);
                }
            }
        }
        picture.show();
    }
    
    public static void readBit() {
        int width = 32;
        int height = 48;
        Picture picture = new Picture(width, height);
        BinaryIn in = new BinaryIn("resource\\5.5\\q32x48.bin");
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!in.isEmpty()) {
                    boolean bit = in.readBoolean();
                    if (bit) picture.set(col, row, Color.BLACK);
                    else     picture.set(col, row, Color.WHITE);
                } else {
                    picture.set(col, row, Color.RED);
                }
            }
        }
        picture.show();
    }
    
    public static void main(String[] args) {
        readBit();
    }

}
