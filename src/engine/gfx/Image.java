package engine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;

import static engine.GameSettings.USE_ROTATION_IMAGES_BUFFER;

public class Image {
    public static final float SCALE_DEFAULT = 1f;
    private int width,height;
    private int scaledWidth;
    private String imagePath;
    private int scaledHeight;
    private int[] pixels;
    private int[] scalePixels;
    private float scaleW = SCALE_DEFAULT;
    private float scaleH = SCALE_DEFAULT;
    private HashMap<String, int[]> rotatedPixelsBuffer;

    public Image(String path) {
        imagePath = path;
        BufferedImage image = null;
        try {
            if(System.getProperty("os.name").contains("Windows")){
                image = ImageIO.read(new File(System.getProperty("user.dir") +  File.separator + "res"  + path));

            }else {
                image = ImageIO.read(getClass().getResourceAsStream(path));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        width = image.getWidth();
        height = image.getHeight();
        scaledWidth = image.getWidth();
        scaledHeight = image.getHeight();
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
        scalePixels = pixels.clone();
        image.flush();
        rotatedPixelsBuffer = new HashMap<>();
    }

    private void scale(float scaleW,float scaleH) {
        int w1 = this.width;
        int h1 = this.height;
        int w2 = (int)(w1*scaleW);
        int h2 = (int)(h1*scaleH);
        scalePixels = new int[w2*h2] ;
        double x_ratio = w1/(double)w2 ;
        double y_ratio = h1/(double)h2 ;
        double px, py ;
        for (int i=0;i<h2;i++) {
            for (int j=0;j<w2;j++) {
                px = Math.floor(j*x_ratio) ;
                py = Math.floor(i*y_ratio) ;
                scalePixels[(i*w2)+j] = pixels[(int)((py*w1)+px)] ;
            }
        }
        scaledWidth = w2;
        scaledHeight = h2;
    }

    public int[] rotate(int angle, float center_x, float center_y, float targetScaleW, float targetScaleH){

        if(USE_ROTATION_IMAGES_BUFFER){
            if(rotatedPixelsBuffer.containsKey(""+angle+targetScaleW+targetScaleW))
                return  rotatedPixelsBuffer.get(""+angle+targetScaleW+targetScaleW);
        }

        final double rads = Math.toRadians(-angle);
        final double sin = Math.sin(rads);
        final double cos = Math.cos(rads);
        boolean scaled;

        int[] temp;
        int newWidth;
        int newHeight;
        if(targetScaleW == SCALE_DEFAULT && targetScaleH == SCALE_DEFAULT){
            scaled = false;
            temp = new int[width*height];
            newWidth = width;
            newHeight = height;
        }else{
            scaled = true;
            if(targetScaleW != this.scaleW || targetScaleH != this.scaleH){

                scale(targetScaleW, targetScaleH);
            }
            this.scaleW = targetScaleW;
            this.scaleH = targetScaleH;
            newWidth  = scaledWidth;
            newHeight = scaledHeight;
            temp = new int[scaledWidth*scaledHeight];
        }

        for(int y=0; y < newHeight; y++) {
            for(int x = 0; x < newWidth; x++) {
                int newX = (int)(center_x + (x-center_x)*cos - (y-center_y)*sin);
                int newY = (int)(center_y + (x-center_x)*sin + (y-center_y)*cos);
                int color = 0;
                if(newX >= 0 && newX < newWidth && newY >=0 && newY < newHeight) {
                    if(!scaled){
                        color = pixels[newX + newY * newWidth];
                    }else {
                        color = scalePixels[newX + newY * newWidth];
                    }
                }else{
                    color = 0;
                }

                temp[x + y * newWidth] = color;
            }
        }
        if(USE_ROTATION_IMAGES_BUFFER){
            rotatedPixelsBuffer.put(""+angle+targetScaleW+targetScaleW, temp);
        }
        return temp;
    }

    public String getImagePath() {
        return imagePath;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getScaledWidth() {
        return scaledWidth;
    }
    public int getScaledHeight() {
        return scaledHeight;
    }
    public int[] getPixelsCopy() {
        return pixels.clone();
    }
    public int[] getPixels() {
        return pixels;
    }
    public int[] getScalePixelsCopy() {
        return scalePixels.clone();
    }
    public int[] getScalePixels() {
        return scalePixels;
    }
    public Image getScaled(float scaleW, float scaleH){
        if(scaleW == this.scaleW && scaleH == this.scaleH) return this;
        this.scaleH = scaleH;
        this.scaleW = scaleW;
        scale(scaleW, scaleH);
        return this;
    }
}
