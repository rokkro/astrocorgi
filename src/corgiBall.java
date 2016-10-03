public class corgiBall{
    private  double ballPosX = 0;
    private  double ballPosY = 0;
    public  boolean ballExists;
    private String ball;
    private int ballBounce =0;
    private int ship = getRandomShip();
    public double mX;
    public double mY;
    public char dir;
    public char dir2;
    public boolean crazyBall;

    public corgiBall(String ball, boolean crazyBall){
        this.ball = ball; //yes, you can even set another corgi to be the ball
        this.crazyBall = crazyBall;
    }
    public corgiBall(){
        this.ball = "ballR.png";
    }
    private int getRandomShip(){
        return (int)(Math.random()*4);
    }
    private void accelerateBall(double acc){
        switch((int)(Math.random()*4)) {
            case 0:
                mX += (Math.random() * (.00001+acc)/(Math.random() * 1000));
                mY += (Math.random() * (.00001+acc)/(Math.random() * 1000));
                break;
            case 1:
                mX += (Math.random() * (.00002+acc)/(Math.random() * 1000));
                mY += (Math.random() * (.00002+acc)/(Math.random() * 1000));
                break;
            case 2:
                mX += (Math.random() * (.00003+acc)/(Math.random() * 1000));
                mY += (Math.random() * (.00003+acc)/(Math.random() * 1000));
                break;
            case 3:
                mX += (Math.random() * (.00005+acc)/(Math.random() * 1000));
                mY += (Math.random() * (.00005+acc)/(Math.random() * 1000));
                break;
        }
    }
    private void ballSpeed(){
        //determine a random speed to move
        switch(corgiCore.diff){
            case 1:
                accelerateBall(.000001);
                break;
            case 2:
                accelerateBall(.000003);
                break;
            case 3:
                accelerateBall(.000006);
                break;

        }
    }
    public void activateBall() {
        ballSpeed();
        if(ballBounce > 5)
            reset();
        switch (ship) {
            case (0)://North
                if(corgiCore.shipHP[ship]>0) {
                    getBall(corgiCore.corgiPosX, corgiScene.m1 -.01);
                    ballPosY -=mY;
                    if(crazyBall)
                        ballPosX +=mX;
                }
                else
                    reset();
                break;
            case (1)://South
                if(corgiCore.shipHP[ship]>0) {
                    getBall(corgiCore.corgiPosX, corgiScene.m2 + .01);
                    ballPosY +=mY;
                    if(crazyBall)
                        ballPosX +=mX;
                }
                else
                    reset();
                break;
            case (2)://East
                if(corgiCore.shipHP[ship]>0) {
                    getBall(corgiScene.m1 - .01, corgiCore.corgiPosY);
                    ballPosX -=mY;
                    if(crazyBall)
                        ballPosY +=mX;
                }
                else
                    reset();
                break;
            case (3)://West
                if(corgiCore.shipHP[ship]>0) {
                    getBall(corgiScene.m2 + .01,corgiCore.corgiPosY);
                    ballPosX +=mX;
                    if(crazyBall)
                        ballPosY +=mY;
                }
                else
                    reset();
                break;
        }

    }
    public void getBall(double x, double y){
        if(!ballExists) { //sets where ball starts
            ballPosX = x;
            ballPosY = y;
        }
        ballExists = true;
        StdDraw.picture(ballPosX,ballPosY,corgiCore.osPath+ ball);
        ballCheck();
    }

    public void ballCheck(){
        if((ballPosX >1 && ballPosX < 1.5)  || (ballPosX < 0 && ballPosX > -.5)){ //If the ball hits L/R sides of screen, bounce off it
            mX*=-1;
            ballBounce++;
        }
        else if((ballPosY <0 && ballPosY > -.5) || (ballPosY >1 && ballPosY <1.5)){ //Top/Bottom of screen
            mY*=-1;
            ballBounce++;
        }
        double boundaryA = corgiCore.corgiPosX - corgiScene.midBox;
        double boundaryB = corgiCore.corgiPosX + corgiScene.midBox;
        double boundaryC = corgiCore.corgiPosY - corgiScene.midBox;
        double boundaryD = corgiCore.corgiPosY + corgiScene.midBox;
        dir = '0';//unspecified direction
        dir2 = '0';
        if(ballPosX <=boundaryB && ballPosX >= corgiCore.corgiPosX + .01  && ballPosY <= boundaryD  && ballPosY >= boundaryC) {
            dir = 'E';
        }
        if(ballPosX >= boundaryA && ballPosX <= corgiCore.corgiPosX - .01 && ballPosY <= boundaryD  && ballPosY >= boundaryC ){
            dir = 'W';
        }
        if(ballPosX >= boundaryA && ballPosX <= boundaryB && ballPosY <= boundaryD && ballPosY >= corgiCore.corgiPosY + .01){
            dir2 = 'N';
        }
        if(ballPosX <=boundaryB && ballPosX >= boundaryA && ballPosY >= boundaryC && ballPosY <= corgiCore.corgiPosY - .01) {
            dir2 = 'S';
        }
    }
    public void reset(){
        ballExists=false;
        ship = getRandomShip();
        dir = dir2 =  '0';
        ballPosX = ballPosY = 0;
        ballBounce = 0;
    }
    public double getX(){
        return ballPosX;
    }
    public double getY(){
        return ballPosY;
    }

}
