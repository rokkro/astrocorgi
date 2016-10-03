import java.awt.Color;
import java.util.stream.IntStream;

public class corgiGame {
    public static void start(){
        corgiCore.setCorgi();
        corgiScene.mainScreen();
        corgiScene.levelScreen();
        intro();
        if(gameBegin()==0)
            loseScreen();
        else
            winScreen();
    }
    private static void intro() {
        while(corgiScene.treat()!=1){
            corgiCore.control(.002,false);
        }
        while(corgiScene.shipAndTreats(.002)!=1) {
            corgiCore.control(0,false);
        }
    }
    private static int gameBegin(){
        corgiBall[] a = {new corgiBall("ballR.png",true),
                new corgiBall(corgiImage.reColor("ballG.png",new Color(0,255,0)),true),
                new corgiBall(corgiImage.reColor("ballR.png","ballB.png", new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)), true),true),
                new corgiBall("ballD.png",true)}; //Array of corgiballs
        while(true) {
            corgiScene.shipAndTreats();
            corgiScene.box();
            for(int i=0;i<a.length;i++)
                a[i].activateBall();
            corgiCore.control(0,true, a);
            corgiScene.corgiHealthChk();
            corgiScene.barkPts();
            if(corgiCore.health<=0)
                return 0;
            if(IntStream.of(corgiCore.shipHP).sum()==0)
                return 1;
        }
    }
    private static void loseScreen(){
        while(true) {
            corgiScene.lose();
        }
    }
    private static void winScreen(){
        while(IntStream.of(corgiCore.shipHP).sum()!=-4) {
            corgiScene.shipAndTreats();
            corgiScene.treatWin();
            corgiCore.control(.004, false);
        }
        while(true){
            corgiScene.dogWin();
        }
    }
}
