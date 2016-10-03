import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

public  class corgiCore {

    //Note: Java interprets /src/img/ the same regardless of OS. Using \\src\\img\\ for windows introduces weird bugs. Windows and linux also render the fonts differently.
    public static String osPath = System.getProperty("user.dir") + "/src/img/";
    public static int diff = 1;
    public static int corgiState; //mental health of your corg, changes on wins/losses. 0 is neutral.
    public static double corgiPosX = .1;
    public static double corgiPosY = .1;
    public static String dogs;
    public static String dogf;
    private static char facing = 'E';
    public static int health = 1;
    public static int[] shipHP = {1,1,0,0}; //N,S,E,W
    public static int points = 10;

    public static void control(double m, boolean shield){
        corgiBall[] c = new corgiBall[1];
        c[0] = new corgiBall();
        control(m,shield,c);
    }
    public static void control(double m,boolean shield, corgiBall[] s) {
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) || StdDraw.isKeyPressed(KeyEvent.VK_D)) { //EAST
            facing = 'E';
            if(corgiPosX<=1)
                corgiPosX+=m;
            if(StdDraw.isKeyPressed(VK_SHIFT)) //hold shift + direction to force corgi movement
                corgiPosX+=.001;
            if(shield) {
                hitBox(s, facing);
                StdDraw.picture(corgiPosX + .1, corgiPosY, osPath + "shield.png");
                if(points >= 10 && StdDraw.isKeyPressed(VK_SPACE)){ //Firing the corgicannon with 10/10 points
                    StdDraw.picture(corgiPosX,corgiPosY, osPath + "dogsmile.png");
                    shipHP[2]=0;
                    for(double i=corgiPosX+.1;i<1;i+=.01)
                    {
                        StdDraw.setPenColor(StdDraw.CYAN);
                        StdDraw.filledCircle(i+.02, corgiPosY,.06);
                        corgiScene.show(30);
                        resetBalls(s);
                    }
                }
            }
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) || StdDraw.isKeyPressed(KeyEvent.VK_A)) { //WEST
            facing = 'W';
            if(corgiPosX >=0)
                corgiPosX-=m;
            if(StdDraw.isKeyPressed(VK_SHIFT))
                corgiPosX-=.001;
            if(shield) {
                hitBox(s, facing);
                StdDraw.picture(corgiPosX - .1, corgiPosY, osPath + "shield.png", 180);
                if(points >= 10 && StdDraw.isKeyPressed(VK_SPACE)){
                    StdDraw.picture(corgiPosX,corgiPosY, osPath + corgiImage.flip("dogsmile.png","dogsmilef.png"));
                    shipHP[3]=0;
                    for(double i=corgiPosX-.1;i>0;i-=.01)
                    {
                        StdDraw.setPenColor(StdDraw.CYAN);
                        StdDraw.filledCircle(i-.02,corgiPosY,.06);
                        corgiScene.show(30);
                        resetBalls(s);
                    }
                }
            }
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_UP) || StdDraw.isKeyPressed(KeyEvent.VK_W)) { //NORTH
            facing = 'N';
            if(corgiPosY<=1)
                corgiPosY+=m;
            if(StdDraw.isKeyPressed(VK_SHIFT))
                corgiPosY+=.001;
            if(shield) {
                hitBox(s, facing);
                StdDraw.picture(corgiPosX, corgiPosY + .1, osPath + "shield.png", 90);
                if(points >= 10 && StdDraw.isKeyPressed(VK_SPACE)){
                    StdDraw.picture(corgiPosX,corgiPosY, osPath + "dogsmile.png",90);
                    shipHP[0]=0;
                    for(double i=corgiPosY+.1;i<1;i+=.01)
                    {
                        StdDraw.setPenColor(StdDraw.CYAN);
                        StdDraw.filledCircle(corgiPosX,i+.02,.06);
                        corgiScene.show(30);
                        resetBalls(s);
                    }
                }
            }
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) || StdDraw.isKeyPressed(KeyEvent.VK_S)) {
            facing = 'S';
            if(corgiPosY>=0)
                corgiPosY -= m;
            if(StdDraw.isKeyPressed(VK_SHIFT))
                corgiPosY-=.001;
            if(shield) {
                hitBox(s, facing);
                StdDraw.picture(corgiPosX, corgiPosY - .1, osPath + "shield.png", 270);
                if(points >= 10 && StdDraw.isKeyPressed(VK_SPACE)){
                    StdDraw.picture(corgiPosX,corgiPosY, osPath + corgiImage.flip("dogsmile.png","dogsmilef.png"),90);
                    shipHP[1]=0;
                    for(double i=corgiPosY-.1;i>0;i-=.01)
                    {
                        StdDraw.setPenColor(StdDraw.CYAN);
                        StdDraw.filledCircle(corgiPosX,i-.02,.06);
                        corgiScene.show(30);
                        resetBalls(s);
                    }
                }
            }
        }
        switch (facing) {
            case 'E':
                StdDraw.picture(corgiPosX, corgiPosY, osPath + dogs);
                break;
            case 'N':
                StdDraw.picture(corgiPosX, corgiPosY, osPath + dogs, 90);
                break;
            case 'S':
                StdDraw.picture(corgiPosX, corgiPosY, osPath + dogf, 90);
                break;
            case 'W':
                StdDraw.picture(corgiPosX, corgiPosY, osPath + dogf);
                break;
        }
        if(shield)
            hitBox(s, '1');
        corgiScene.show(5);
        corgiScene.reDraw();
        if(StdDraw.isKeyPressed(VK_1))
            for(int i=0;i<s.length;i++) {
                s[i].mX += .00001;
                s[i].mY += .00001;
            }
        if(StdDraw.isKeyPressed(VK_2))
            for(int i=0;i<s.length;i++) {
                s[i].mX -= .00001;
                s[i].mY -= .00001;
            }
    }
    public static void setCorgi(){
        if(corgiState <=-1) {
            dogs = "dogsad.png";
            dogf = corgiImage.flip("dogsad.png", "dogsadf.png");
            if(corgiState == -1)
                corgiScene.moodText = "\"Please don't make me go through that again...\"";
            else if(corgiState == -2)
                corgiScene.moodText = "Your corgi is trying to float away. He really doesn't want to play again.";
            else
                corgiScene.moodText = "Your corgi is crying on a nearby asteroid.";
        }
        else if(corgiState ==0) {
            dogs = "dog.png";
            dogf = corgiImage.flip("dog.png", "dogf.png");
        }
        else if(corgiState ==1) {
            dogs = "doghappy.png";
            dogf = corgiImage.flip("doghappy.png", "doghappyf.png");
            corgiScene.moodText = "Your corgi really, really wants more treats.";
        }
        else {
            dogs = "dogsuperhappy.png";
            dogf = corgiImage.flip("dogsuperhappy.png", "dogsuperhappyf.png");
            corgiScene.moodText = "Your corgi is now addicted to dog treats. It's almost kind of disgusting.";
        }
    }
    private static void hitBox(corgiBall[] s, char c){
        for(int i=0;i<s.length;i++) {
            if (s[i].dir == c || s[i].dir2 == c) {
                corgiScene.boxColor = StdDraw.GREEN;
                corgiScene.box();
                points++;
                corgiScene.show(500);
                s[i].reset();
            }
            else if(s[i].dir != '0' || s[i].dir2 != '0'){ //if ball is N,E,S,W and doesn't get blocked by dog
                corgiScene.boxColor = StdDraw.RED;
                if(health>0)
                    health--;
                if(points>0)
                    points--;
                corgiScene.box();
                corgiScene.show(500);
                s[i].reset();
            }
            else{
                corgiScene.boxColor = StdDraw.CYAN;
            }
        }
    }
    private static void resetBalls(corgiBall[] a){
        points =0;
        for(int i=0;i<a.length;i++)
            a[i].reset();
    }
    public static void resetAll(){ //doesn't make you go through the loopy ship animation again, resets after game win/loss
        points =0;
        health = 10;
        corgiPosX = corgiPosY = .1;
        for(int i=0;i<shipHP.length;i++)
            shipHP[i]=2;
        corgiGame.start();
    }
}
