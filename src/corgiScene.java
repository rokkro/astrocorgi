import java.awt.*;
import java.awt.event.KeyEvent;

public class corgiScene {

    public static double mid = 0;
    /*After originally using this ^, I found it seems to be a bad idea to use this to define the middle,
     as StdDraw.picture(.5 - -) recognizes the current center while StdDraw.picture(mid - -) does not
     Now it can be used for shifting the background in the main game with alt+(left or right)*/
    public static final double midBox = .06;
    public static double m1 = 0; //ship and treat positions
    public static double m2 = 1;
    private static double bgSpeed = .0001; //bg movement
    public static Color boxColor = StdDraw.CYAN;
    public static String moodText = "";

    public static Font main = new Font("Gill Sans MT", Font.PLAIN, 25);
    public static Font intro = new Font("Gill Sans MT", Font.BOLD, 25);
    public static Font disp = new Font("Gill Sans MT", Font.BOLD, 12);
    public static Font end = new Font("Gill Sans MT", Font.BOLD, 30);
    public static Font bigNum = new Font("Gill Sans MT", Font.BOLD, 50);

    public static void preSet(){
        StdDraw.setCanvasSize(800, 800);
        StdDraw.enableDoubleBuffering();
    }
    public static void reDraw() {
        StdDraw.clear();
        StdDraw.picture(mid, .5, corgiCore.osPath + "bg.jpg");
        mid+= bgSpeed;
        if(mid>=1 || mid<= 0)
            bgSpeed *=-1;
    }
    public static void show(int i) {
        StdDraw.show();
        StdDraw.pause(i);
    }
    public static void mainScreen(){
        while(true) {
            reDraw();
            StdDraw.picture(.5, .8, corgiCore.osPath + "logo.png");
            StdDraw.picture(.5, .5, corgiCore.osPath + "dogbig.png");
            StdDraw.setFont(intro);
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(.5, .2, "PRESS ENTER");
            StdDraw.setPenRadius(.005);
            StdDraw.setPenColor(StdDraw.CYAN);
            StdDraw.line(.35, .18, .65, .18);
            show(5);
            if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER)){
                StdDraw.setFont(main);
                break;
            }
        }
    }
    public static void levelScreen(){
        while(true) {
            reDraw();
            if(corgiCore.corgiState > -3)
                StdDraw.picture(mid, .7, corgiCore.osPath + corgiCore.dogs);
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(.5, .54, "ENTER A DIFFICULTY TO BEGIN!");
            StdDraw.setPenColor(StdDraw.CYAN);
            StdDraw.line(.25, .52, .75, .52);
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(.4,.48, "[ 1 ]");
            StdDraw.text(.4, .45, "[ 2 ]");
            StdDraw.text(.4, .42, "[ 3 ]");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(.48, .48, " - EASY");
            StdDraw.text(.51, .45, " - NORMAL");
            StdDraw.text(.49, .42, " - HARD");
            StdDraw.setFont(disp);
            StdDraw.text(.5, .05, "HOLD W,A,S,D OR THE ARROW KEYS TO BLOCK INCOMING BULLETS!");
            StdDraw.text(.5, .02, "BLOCK BULLETS TO GAIN BARK POINTS, GET 10 POINTS AND USE SPACE BAR TO FIRE AT A SHIP.");
            StdDraw.setFont(main);
            StdDraw.text(.5,.78,moodText);

            show(5);
            if(StdDraw.isKeyPressed(KeyEvent.VK_1))
            {
                corgiCore.diff=1;
                break;
            }
            else if(StdDraw.isKeyPressed(KeyEvent.VK_2))
            {
                corgiCore.diff=2;
                break;
            }
            else if(StdDraw.isKeyPressed(KeyEvent.VK_3))
            {
                corgiCore.diff=3;
                break;
            }
        }
    }
    public static void countDown(int cd){
        while(cd > 0) {
            box();
            shipAndTreats();
            StdDraw.setFont(main);
            StdDraw.setPenColor(StdDraw.CYAN);
            switch (corgiCore.diff) {
                case (1):
                    StdDraw.text(.5, .9, "EASY MODE");
                    break;
                case (2):
                    StdDraw.text(.5, .9, "NORMAL MODE");
                    break;
                case (3):
                    StdDraw.text(.5, .9, "HARD MODE");
                    break;
            }
            StdDraw.setFont(bigNum);
            StdDraw.setPenColor(StdDraw.YELLOW);
            show(900); //this part worked weirdly, had to try a lot of things till everything showed up right
            StdDraw.text(.5, .8, Integer.toString(cd));
            corgiCore.control(0,false);
            show(0);
            cd--;
            countDown(cd);
            break;
        }
    }
    public static void box()
    {
        StdDraw.setPenRadius(.007);
        StdDraw.setPenColor(boxColor);
        StdDraw.square(corgiCore.corgiPosX,corgiCore.corgiPosY, midBox);
    }
    public static int shipAndTreats(double m) {
        if (m1 <= 1 && m2 >= 0) {
            StdDraw.picture(corgiCore.corgiPosX, m1, corgiCore.osPath + "ship.png", 180);
            StdDraw.picture(corgiCore.corgiPosX, m2, corgiCore.osPath + "ship.png"); //S
            StdDraw.picture(m1, corgiCore.corgiPosY, corgiCore.osPath + "ship.png", 90);
            StdDraw.picture(m2, corgiCore.corgiPosY, corgiCore.osPath + "ship.png", 270);
            StdDraw.setPenColor(StdDraw.CYAN);
            StdDraw.circle(corgiCore.corgiPosX,corgiCore.corgiPosY, m2);
            m1 += m;
            m2 -= m;
            return 0;
        }
        else{
            countDown(3);
            return 1;
        }
    }
    public static void shipAndTreats(){
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(main);
        for(int i=0;i<corgiCore.shipHP.length;i++) {
            switch (i) {
                case 0:
                    if (corgiCore.shipHP[i] == 2 || corgiCore.shipHP[i] == 1)
                        StdDraw.picture(corgiCore.corgiPosX, m1, corgiCore.osPath + "ship.png", 180); //north
                    if(corgiCore.shipHP[i] == 1)
                        StdDraw.text(corgiCore.corgiPosX + .1, m1 - .015, "50%");
                    if(corgiCore.shipHP[i] == 0)
                        StdDraw.picture(.5, m1 - .035, corgiCore.osPath + "treat.png");
                    break;
                case 1:
                    if (corgiCore.shipHP[i] == 2 || corgiCore.shipHP[i] == 1)
                        StdDraw.picture(corgiCore.corgiPosX, m2, corgiCore.osPath + "ship.png"); //South
                    if (corgiCore.shipHP[i] == 1)
                        StdDraw.text(corgiCore.corgiPosX + .1, m2 + .015, "50%");
                    if(corgiCore.shipHP[i] == 0)
                        StdDraw.picture(.5, m2 + .035, corgiCore.osPath + "treat.png");
                    break;
                case 2:
                    if (corgiCore.shipHP[i] == 2 || corgiCore.shipHP[i] == 1)
                        StdDraw.picture(m1, corgiCore.corgiPosY, corgiCore.osPath + "ship.png", 90); //east
                    if (corgiCore.shipHP[i] == 1)
                        StdDraw.text(m1 - .026, corgiCore.corgiPosY + .1, "50%");
                    if(corgiCore.shipHP[i] == 0)
                        StdDraw.picture(m1 - .035, .5, corgiCore.osPath + "treat.png");
                    break;
                case 3:
                    if (corgiCore.shipHP[i] == 2 || corgiCore.shipHP[i] == 1)
                        StdDraw.picture(m2, corgiCore.corgiPosY, corgiCore.osPath + "ship.png", 270); //w
                    if (corgiCore.shipHP[i] == 1)
                        StdDraw.text(m2 + .026, corgiCore.corgiPosY + .1, "50%");
                    if(corgiCore.shipHP[i] == 0)
                        StdDraw.picture(m2 + .035, .5, corgiCore.osPath + "treat.png"); //w
                    break;
            }
        }
    }
    public static int treat(){
        double treatBox = .03;
        StdDraw.picture(.5,.5,corgiCore.osPath + "treat.png");
        if((corgiCore.corgiPosX >=.5- treatBox && corgiCore.corgiPosX <= .5+ treatBox) &&
                (corgiCore.corgiPosY >=.5- treatBox && corgiCore.corgiPosY <=.5+ treatBox)){
            corgiCore.corgiPosY = .5;
            corgiCore.corgiPosX = .5;
            return 1;
        }
        return 0;
    }
    public static void treatWin(){
        double treatBox = .03;
        if((corgiCore.corgiPosX >=.5- treatBox && corgiCore.corgiPosX <= .5+ treatBox) && //North treat
                (corgiCore.corgiPosY >=m1 - .035 - treatBox && corgiCore.corgiPosY <=m1 - .035 + treatBox))
                corgiCore.shipHP[0]=-1;
        if((corgiCore.corgiPosX >=.5- treatBox && corgiCore.corgiPosX <= .5+ treatBox) && //South
                (corgiCore.corgiPosY >=m2 + .035 - treatBox && corgiCore.corgiPosY <=m2 + .035 + treatBox))
                corgiCore.shipHP[1]=-1;
        if((corgiCore.corgiPosX >=m1 - .035 - treatBox && corgiCore.corgiPosX <= m1 - .035 + treatBox) && //East
                (corgiCore.corgiPosY >=.5- treatBox && corgiCore.corgiPosY <=.5+ treatBox))
                corgiCore.shipHP[2]=-1;
        if((corgiCore.corgiPosX >=m2 + .035 - treatBox && corgiCore.corgiPosX <= m2 + .035 + treatBox) && //West
                (corgiCore.corgiPosY >=.5- treatBox && corgiCore.corgiPosY <=.5+ treatBox))
                corgiCore.shipHP[3]=-1;
    }
    public static void barkPts()
    {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(.053, .89,"Bark Points");
        StdDraw.square(.048,.83,.05);
        StdDraw.setFont(main);
        if(corgiCore.points >= 10)
        {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.text(.048,.83,"10/10");
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(.5, .5 + .2, "ULTRA SONIC CORGI BARK CHARGED! PRESS SPACE TO FIRE!");
        }
        if(corgiCore.points<10)
            StdDraw.text(.05,.83,corgiCore.points + "/10");
    }
    public static void corgiHealthChk()
    {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(disp);
        StdDraw.text(.11,.98,"Corgi Structural Integrity");
        StdDraw.rectangle(.01 ,.95,.2,.013);
        StdDraw.setPenColor(StdDraw.GREEN);
        double width = .02;
        if(corgiCore.health>3 && corgiCore.health <7)
            StdDraw.setPenColor(StdDraw.YELLOW);
        else if(corgiCore.health<4)
            StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledRectangle(.01,.95,.2-(width*(10-corgiCore.health)),.012);
    }
    public static void lose(){
        reDraw();
        StdDraw.setFont(end);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(.5,.73,"YOU ARE AN EMBARRASSMENT TO ALL CORGIS!");
        StdDraw.picture(.5,.5,corgiCore.osPath + "dogfail.png");
        StdDraw.text(.5,.05,"PRESS ENTER TO RESTART");
        if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER)){
            if(corgiCore.corgiState>-3)
                corgiCore.corgiState--;
            corgiCore.resetAll();
        }
        show(0);
    }
    public static void dogWin(){
        reDraw();
        StdDraw.setFont(end);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.text(.5,.6,"YOU WIN!");
        StdDraw.picture(.5,.3,corgiCore.osPath + "dogwin.png");
        StdDraw.text(.5,.05,"PRESS ENTER TO RESTART");
        if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (corgiCore.corgiState < 2)
                corgiCore.corgiState++;
            corgiCore.resetAll();
        }
        show(0);
    }
}

