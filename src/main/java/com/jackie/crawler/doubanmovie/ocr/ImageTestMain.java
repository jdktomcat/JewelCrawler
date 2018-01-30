package com.jackie.crawler.doubanmovie.ocr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * 图片测试类
 * @author Administrator
 */
public class ImageTestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(new File("E:\\document\\crawler\\douban\\jackie.png")));
            ImageIO.write(image, "png", new File("E:\\document\\crawler\\douban\\captcha1.png"));
            String maybe2 = new OCR().recognizeText(new File("E:\\document\\crawler\\douban\\captcha1.png"), "png");
            System.out.println(maybe2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
