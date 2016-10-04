import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

public  class corgiCore {
    //Note: Java interprets /src/img/ the same regardless of OS. Using \\src\\img\\ for windows introduces weird bugs. Windows and linux also render the fonts differently.
    public static String osPath = System.getProperty("user.dir") + "/src/img/";
    public static int diff = 1; //difficulty
    public static int corgiState; //mental health of your corg, changes on wins/losses. 0 is neutral. Alters what corgi looks like.
    public static double corgiPosX = .1; //dogposition
    public static double corgiPosY = .1;
    public static String dogs;  //dog image path
    public static String dogf;  //flipped dog image path
    private static char facing = 'E'; //used for making corgi face last direction pressed
    public static int health = 10; //dogHP
    public static int[] shipHP = {2,2,2,2}; //N,S,E,W
    public static int points = 0;

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
            if(StdDraw.isKeyPressed(VK_SHIFT)) //hold shift + direction to force corgi movement. kind of a cheat/debug thing
                corgiPosX+=.001;
            if(shield) { //if corgi shield is activated
                hitBox(s, facing); //box collision with direction corgi is facing
                StdDraw.picture(corgiPosX + .1, corgiPosY, osPath + "shield.png");
                if(points >= 10 && StdDraw.isKeyPressed(VK_SPACE)){ //Firing the corgicannon with 10/10 points
                    StdDraw.picture(corgiPosX,corgiPosY, osPath + "dogsmile.png"); //Bark!
                    if(shipHP[2]>0)//reduce shipHP that was hit, if it's still alive
                        shipHP[2]--;
                    for(double i=corgiPosX+.1;i<1;i+=.01) //shows cannon animation
                    {
                        StdDraw.setPenColor(StdDraw.CYAN);
                        StdDraw.filledCircle(i+.02, corgiPosY,.06);
                        corgiScene.show(30);
                        resetBalls(s); //removes balls on screen
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
                    if(shipHP[3]>0)
                        shipHP[3]--;
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
                    if(shipHP[0]>0)
                    shipHP[0]--;
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
                    if(shipHP[1]>0)
                        shipHP[1]--;
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
        switch (facing) { //Shows corgi facing whatever direction last pressed when not holding keyboard direction
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
        if(shield) //hitbox if no direction is pressed
            hitBox(s, '1');
        corgiScene.show(5);
        corgiScene.reDraw();
    }
    public static void setCorgi(){ //used to get an appropriate corgi depending on how it feels from the last game.
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
    private static void hitBox(corgiBall[] s, char c){ //that middle box
        for(int i=0;i<s.length;i++) { //looks at every ball
            if (s[i].dir == c || s[i].dir2 == c) { //checks if key press and ball direction match
                corgiScene.boxColor = StdDraw.GREEN;
                corgiScene.box(); 
                points++;
                corgiScene.show(500); //briefly pauses the game to show the box is green
                s[i].reset();
            }
            else if(s[i].dir != '0' || s[i].dir2 != '0'){ //if the ball doesn't get stopped by the corgi
                corgiScene.boxColor = StdDraw.RED;
                if(health>0)
                    health--;
                if(points>0)
                    points--;
                corgiScene.box();
                corgiScene.show(500);
                s[i].reset();
            }
            else{ //neutral middle box
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
