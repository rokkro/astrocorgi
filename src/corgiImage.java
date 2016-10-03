import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class corgiImage{
    //calling either method in a loop is likely to cause issues.
    //flips your dog, or whatever image for facing the right direction.
    public static String flip(String original, String newImage){
        if(!new File(corgiCore.osPath + newImage).exists()) {
            BufferedImage normal = null;
            try {
                normal = ImageIO.read(new File(corgiCore.osPath + original));
            } catch (IOException e) {}
            BufferedImage flipped = new BufferedImage(normal.getWidth(), normal.getHeight(), TYPE_INT_ARGB);
            for (int x = 0; x < normal.getWidth(); x++) {
                for (int y = 0; y < normal.getHeight(); y++) {
                    flipped.setRGB(normal.getWidth() - x - 1, y, (new Color(normal.getRGB(x, y), true)).getRGB());
                }
            }
            try {
                ImageIO.write(flipped, "png", new File(corgiCore.osPath+newImage));
            } catch (IOException e) {}
        }
        return newImage;
    }

    //tints whatever image you specify and creates a new one. Useful for distinguishing different balls.
    public static String reColor(String newImage, Color c){
        return reColor("ballR.png", newImage, c, false);
    }
    public static String reColor(String original, String newImage, Color c, boolean forceOverwrite){
        if(!new File(corgiCore.osPath + newImage).exists() || forceOverwrite) {
            BufferedImage ballNormal = null;
            try {
                ballNormal = ImageIO.read(new File(corgiCore.osPath + original));
            } catch (IOException e) {}
            BufferedImage ballColored = new BufferedImage(ballNormal.getWidth(), ballNormal.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < ballNormal.getWidth(); x++) {
                for (int y = 0; y < ballNormal.getHeight(); y++) {
                    Color pixelColor = new Color(ballNormal.getRGB(x,y),true);
                    //checks for the dark edges of the ball and doesn't tint that
                    if(pixelColor.getRed()<=100 && pixelColor.getGreen()<=100 && pixelColor.getBlue()<=100 && pixelColor.getAlpha()>0){
                        ballColored.setRGB(x,y,ballNormal.getRGB(x,y));
                        continue;
                    } //Tinting code below obtained from http://stackoverflow.com/a/36744345
                    int r = (pixelColor.getRed() + c.getRed()) / 2;
                    int g = (pixelColor.getGreen() + c.getGreen()) / 2;
                    int b = (pixelColor.getBlue() + c.getBlue()) / 2;
                    int a = pixelColor.getAlpha();
                    int rgba = (a << 24) | (r << 16) | (g << 8) | b;
                    ballColored.setRGB(x, y, rgba);
                }
            }
            try {
                ImageIO.write(ballColored, "png", new File(corgiCore.osPath+newImage));
            } catch (IOException e) {System.err.print("caught it");}
        }
        return newImage;
    }
}
