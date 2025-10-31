package edu.caltech.cs2.lab03;

import edu.caltech.cs2.libraries.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Stack;


public class Image {
    private Pixel[][] pixels;

    public Image(File imageFile) throws IOException {
        BufferedImage img = ImageIO.read(imageFile);
        this.pixels = new Pixel[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                this.pixels[i][j] = Pixel.fromInt(img.getRGB(i, j));
            }
        }
    }

    private Image(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Image transpose() {
        Pixel[][] d = new Pixel[this.pixels[0].length][this.pixels.length];

        for (int i = 0; i < this.pixels.length; i++) {
            for (int j = 0; j < this.pixels[0].length; j++) d[j][i] = this.pixels[i][j];
        }
        return new Image(d);
    }

    public String decodeText() {
        StringBuilder out = new StringBuilder();
        Stack<Integer> bits = new Stack<>();
        for (Pixel[] pixel : this.pixels) {
            for (int j = 0; j < this.pixels[0].length; j++) {
                bits.push(pixel[j].getLowestBitOfR());

            }
        }
        Collections.reverse(bits);
        while(bits.size() >= 8) {
            StringBuilder r = new StringBuilder();
            int tot;
            for(int p = 8; p > 0; p--){
                r.append(bits.pop());
            }
            tot =Integer.parseInt(r.reverse().toString(),2);
            if(tot != 0) {
                char c = (char) tot;
                out.append(c);
            }
        }
        return out.toString();
    }

    public Image hideText(String text) {
        Pixel[][] pixels2 = this.pixels;
        byte[] bytes = text.getBytes();
        int[] bits = new int[8*text.length()];
        for(int i = 0; i<bytes.length; i++) {
            int charByte = bytes[i];
            for (int j = 7; j >= 0; j--){
                if (Math.pow(2,j) <= charByte){
                    bits[(i*8)+j] = 1;
                    charByte -= Math.pow(2, j);
                }

                else bits[(i*8)+j] = 0;

            }
        }
        int k = 0;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                if(k < bytes.length * 8){
                    pixels2[i][j] = pixels[i][j].fixLowestBitOfR(bits[k]);
                }
                else pixels2[i][j] = pixels[i][j].fixLowestBitOfR(0);
                k++;
            }
            }
        return new Image(pixels2);

    }

    public BufferedImage toBufferedImage() {
        BufferedImage b = new BufferedImage(this.pixels.length, this.pixels[0].length, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < this.pixels.length; i++) {
            for (int j = 0; j < this.pixels[0].length; j++) {
                b.setRGB(i, j, this.pixels[i][j].toInt());
            }
        }
        return b;
    }

    public void save(String filename) {
        File out = new File(filename);
        try {
            ImageIO.write(this.toBufferedImage(), filename.substring(filename.lastIndexOf(".") + 1, filename.length()), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
