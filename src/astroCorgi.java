/*import java.awt.Font;
import java.awt.event.KeyEvent;
///////////////////////////////////////////////////////MAIN/////////////////////////////////////////////////////////////////////
public class astroCorgi
{
   public static Font main = new Font("Gill Sans MT", Font.PLAIN, 25);
   public static Font intro = new Font("Gill Sans MT", Font.BOLD, 25);
   public static Font disp = new Font("Gill Sans MT", Font.BOLD, 15);
   public static Font end = new Font("Gill Sans MT", Font.BOLD, 30);
   public static Font bigNum = new Font("Gill Sans MT", Font.BOLD, 50);
   
   public static void main(String[] args)
   {
      StdDraw.setCanvasSize(800,800);
      StdDraw.setFont(main);
      while(true)
      {
         mainScreen();
         gamePlay();
         if(health==0)
            youLose();  
         else
            youWin();
         resetGame(); //for replays
      }
   }
/////////////////////////////PUBLIC VARS///////////////////////////////////////////////////// 
   //(these two are retained after playthrough))//
   public static boolean audioLag = true; //change this if you experience mid-gameplay audio lag. Disables the success bell sound and fail boom sound.
   public static int mentalStatus = 0; //Used to determine the Corgi's mental state before starting (-2 to 3), increases after each failed play through, decreases from a win
   
   public static double diff = 0; 
   public static double diffM = .0001; //diff choice alters this, rate ball speeds up
   public static int diffSelection = 0; //diff selection
   
   public static int rCtrlFlag = 0; //controls
   public static int lCtrlFlag = 0; 
   public static int tCtrlFlag = 0; 
   public static int bCtrlFlag = 0; 

   public static int shipBottX = 0; //SHIP HP, [2]=0% HP, [1]=50% HP, [0]=100% HP///CANNOT ALL BE SET TO 2//
   public static int shipTopX = 0;
   public static int shipLeftX = 0;
   public static int shipRightX = 0;
   
   public static int health = 10; //DOG HP//
   public static int score = 0; //BARK POINTS//

   public static int rightTreat = 0;
   public static int leftTreat = 0;
   public static int topTreat = 0;
   public static int bottTreat = 0;
   
   public static int secondBall = 0; 
   public static int twoBallDiffX = 25; //DEFAULT SPEED OF SECOND BALL
   public static int twoBallDiffY = 26;
   public static int bounceCount = 0; //NUMBER OF BOUNCES OF SECOND BALL, limit of 5
   public static int holdFlag = 0; //TO RESET BALLS AFTER DOG BARKS OR TELL IT TO KEEP GOING IF PURPLE BALL HITS FIRST

////////////WIN/LOSE/RESET////////////////////////////////////////////
   public static void resetGame()//resets everything for another play through, does not retain custom settings unless changed here.
   {
      shipBottX = 0;//HP
      shipTopX = 0;//HP
      shipLeftX = 0;//HP
      shipRightX = 0;//HP
      health = 10;
      score = 0;
      rightTreat = 0;
      leftTreat = 0;
      topTreat = 0;
      bottTreat = 0;
      secondBall = 0;
      diff = 0;
      diffM = .0001;
      holdFlag = 0;
      twoBallDiffX = 25;
      twoBallDiffY = 26;
      diffSelection = 0;
   }   
   public static void youLose()
   {
      StdAudio.play("KO.wav");//sometimes has a weird lag bug i cant figure out
      while(true)
      {
         bg();
         StdDraw.setFont(end);
         StdDraw.setPenColor(StdDraw.RED);
         StdDraw.text(.5,.73,"YOU ARE AN EMBARRASSMENT TO ALL CORGIS!");
         dispReset();
         StdDraw.picture(.5,.5,"faildog.png");
         StdDraw.show(1000/24);
         if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
         {
            if(mentalStatus <=2)
               mentalStatus++;
            break;
         }   
      }
   }  
   public static void youWin()
   {
      StdDraw.show(1000);
      StdDraw.clear();
      StdDraw.show();
      double x = .5;
      double y = .5;
      while(true)
      {
         treatChk();
         if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) || StdDraw.isKeyPressed(KeyEvent.VK_D))
         {
            StdDraw.picture(x,y,"dog.png");
            x+=.007;
         }
         else if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT) || StdDraw.isKeyPressed(KeyEvent.VK_A))
         {
            StdDraw.picture(x,y,"dog2.png");
            x-=.007;
         }
         else if(StdDraw.isKeyPressed(KeyEvent.VK_UP) || StdDraw.isKeyPressed(KeyEvent.VK_W))
         {
            StdDraw.picture(x,y,"dog.png");
            y+=.007;
         }
         else if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN) || StdDraw.isKeyPressed(KeyEvent.VK_S))
         {
            StdDraw.picture(x,y,"dog.png");
            y-=.007;
         }
         else
            StdDraw.picture(x,y,"dog.png");
         /////////////////////////////////////////////////////////////////////
         if((x >= .81 && x <= .99 && y >= .46 && y <= .64) && rightTreat == 1)
         {
            rightTreat++;
            StdAudio.play("nom.wav");
         }
         if((x >= .01 && x <= .19 && y >= .46 && y <= .64) && leftTreat == 1)
         {
            leftTreat++;
            StdAudio.play("nom.wav");
         }
         if((x >= .46 && x <= .64 && y >= .81 && y <= .99) && topTreat == 1)
         {
            topTreat++;
            StdAudio.play("nom.wav");
         }
         if((x >= .46 && x <= .64 && y >= .01 && y <= .19) && bottTreat == 1)
         {
            bottTreat++;
            StdAudio.play("nom.wav");
         }
         StdDraw.show(1000/60);
         if(rightTreat == 2 && leftTreat == 2 && topTreat == 2 && bottTreat == 2)
         {//WIN SCREEN!//
            StdAudio.close(); //hoping this helps with lag, but who knows what it does?
            StdDraw.show(1000);
            x=.5;
            y=.4;
            double dx = Math.random() / (5 * 24);
            double dy = Math.random() / (5 * 24);
            bg();
            StdDraw.picture(x,y,"windog.png");
            StdDraw.show(1000);
            StdAudio.play("upbark.wav");
            StdDraw.show(1000);
            StdAudio.play("bark.wav");
            while(true)
            {
               bg();
               StdDraw.setFont(end);
               StdDraw.setPenColor(StdDraw.GREEN);
               StdDraw.text(.5,.6,"YOU WIN!");
               dispReset();
               StdDraw.picture(x,y,"windog.png");
               x = x + dx;
               y = y + dy;
               if (x > 1 - .13 || x < .13)
                  dx = dx * -1;
               if (y > 1 - .1 || y < .1)
                  dy = dy * -1;
               StdDraw.show(1000 / 24);
               if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
               {
                  if(mentalStatus >= -1)
                     mentalStatus--;
                  break;
               }      
            }
          break;  
         }
      }
   } 
//////(end of win/lose/reset section)//////////////////////
//////////////////////INTRO MAIN SCREENS/////////////////////////////////////////////
   public static void mainScreen()
   {
      StdDraw.setPenColor(StdDraw.WHITE);
      start();
      getReady();
   }
   public static void start()
   {
      int p=0;
      while(p!=1)
      {  
         bg();
         StdDraw.picture(.5,.85,"logo.png");
         StdDraw.picture(.5,.5,"dogbig.png");
         StdDraw.setFont(intro);
         StdDraw.setPenColor(StdDraw.YELLOW);
         StdDraw.text(.5,.2,"PRESS ENTER");
         StdDraw.setPenRadius(.005);
         StdDraw.setPenColor(StdDraw.CYAN);
         StdDraw.line(.37,.18,.63,.18);
         StdDraw.setFont(main);
         if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
         {
            StdAudio.play("beep.wav");
            bg();
            while(p!=1)
            {
               bg();
               mentalHealth();
               StdDraw.setPenColor(StdDraw.YELLOW);
               StdDraw.text(.5,.54,"ENTER A DIFFICULTY TO BEGIN!");
               StdDraw.setPenColor(StdDraw.CYAN);
               StdDraw.line(.25,.52,.75,.52);
               StdDraw.setPenColor(StdDraw.YELLOW);
               StdDraw.text(.4,.48,"[ 1 ]");
               StdDraw.text(.4,.45,"[ 2 ]");
               StdDraw.text(.4,.42,"[ 3 ]");
               StdDraw.setPenColor(StdDraw.WHITE);
               StdDraw.text(.48,.48," - EASY");
               StdDraw.text(.51,.45," - NORMAL");
               StdDraw.text(.488,.42," - HARD");
               StdDraw.setFont(disp);
               StdDraw.text(.5,.05,"HOLD W,A,S,D OR THE ARROW KEYS TO BLOCK INCOMING BULLETS!");
               StdDraw.text(.5,.02,"BLOCK BULLETS TO GAIN BARK POINTS, GET 10 POINTS AND USE SPACE BAR TO FIRE AT A SHIP.");
               StdDraw.setFont(main);
               if(StdDraw.isKeyPressed(KeyEvent.VK_1))
               {
                  diffM*=1.8; //main difficulty modification spot (easy mode)
                  diffSelection=1;
                  p++;
               }
               else if(StdDraw.isKeyPressed(KeyEvent.VK_2))
               {
                  diffM*=2.8;
                  diffSelection=2;
                  p++;
               }
               else if(StdDraw.isKeyPressed(KeyEvent.VK_3))
               {
                  diffM*=4.1;
                  diffSelection=3;
                  p++;
               }
               StdDraw.show(1000/60);
            }
         }
         StdDraw.show(1000/24);
      }  
   }
   public static void getReady()
   {
      StdDraw.setFont(bigNum);
      getReadyFormatting("3");
      getReadyFormatting("2");
      getReadyFormatting("1");
      StdDraw.setFont(main);
   }
   public static void getReadyFormatting(String num)
   {
      bg();
      StdDraw.setPenColor(StdDraw.CYAN);
      if(diffSelection==1)
         StdDraw.text(.5,.9,"EASY MODE");
      if(diffSelection==2)
         StdDraw.text(.5,.9,"NORMAL MODE");
      if(diffSelection==3)
         StdDraw.text(.5,.9,"HARD MODE");  
      StdDraw.setPenColor(StdDraw.YELLOW); 
      StdDraw.text(.5,.5,num);
      StdDraw.show(900);
      StdAudio.play("cd.wav");
   }
///(end intro stuff section)//////// 
////////////////CHECKS, BALANCES, AND DISPLAYS/////////////////////////////////////////
   public static void dispReset()
   {//Shows reset option at bottom of win/lose
      StdDraw.setPenRadius(.004);
      StdDraw.setFont(disp);
      StdDraw.setPenColor(StdDraw.YELLOW);
      StdDraw.text(.5,.05,"PRESS ENTER TO RESTART");
      StdDraw.setPenColor(StdDraw.CYAN);
      StdDraw.line(.35,.035,.65,.035);
   }
   public static void mentalHealth()//Dog status. vErYY imMPortTANt!!1!!
   {
      if(mentalStatus <= -2)
      {
         StdDraw.setFont(disp);
         StdDraw.picture(.5,.65,"superhappydog.png");
         StdDraw.text(.5,.75,"Your corgi is now addicted to dog treats. It's almost kind of disgusting.");
         StdDraw.setFont(main);   
      }
      else if(mentalStatus == -1)
      {
         StdDraw.setFont(disp);
         StdDraw.picture(.5,.65,"happydog.png");
         StdDraw.text(.5,.75,"Your corgi really, really wants more dog snacks.");
         StdDraw.setFont(main);
      }
      else if(mentalStatus == 0)
         StdDraw.picture(.5,.65,"dogb.png");
      else if(mentalStatus == 1)
      {
         StdDraw.setFont(disp);
         StdDraw.text(.6,.65,"Please don't make me go through that again...");
         StdDraw.picture(.3,.65,"saddog.png");
         StdDraw.setFont(main);
      }
      else if(mentalStatus == 2)
      {
         StdDraw.setFont(disp);
         StdDraw.text(.5,.65,"Your corgi is trying to float away. He really doesn't want to play again.");
         StdDraw.picture(.95,.7,"saddog.png");
         StdDraw.setFont(main);
      }
      else if(mentalStatus >= 3)
      {
         StdDraw.setFont(disp);
         StdDraw.text(.5,.65,"Your corgi is crying on a nearby asteroid.");
         StdDraw.setFont(main);
      }      
   } 
   public static void treatChk() ///CHECKS TREAT STATUS BEFORE YOU WIN///
   {
      bg();
      StdDraw.setPenColor(StdDraw.YELLOW);
      StdDraw.text(.5,.7,"MOVE AND COLLECT THE DOG SNACKS!");
      if(rightTreat == 1)
         StdDraw.picture(.9,.5,"treat.png");
      if(leftTreat == 1)
         StdDraw.picture(.1,.5,"treat.png");
      if(topTreat == 1)
         StdDraw.picture(.5,.9,"treat.png");
      if(bottTreat == 1)
         StdDraw.picture(.5,.1,"treat.png");
   }
   public static void indicators() ////HP AND BP INDICATORS////
   {
      StdDraw.setFont(disp);
      StdDraw.setPenColor(StdDraw.WHITE);
      healthChk();
      barkPts();
   }   
   public static void healthChk() ///CORGI HP BAR///
   {
      StdDraw.text(.115,.976,"Corgi Structural Integrity");
      StdDraw.rectangle(.01,.95,.2,.013);
      StdDraw.setPenColor(StdDraw.GREEN);
      double width = .02;
      if(health>3 && health <7)
         StdDraw.setPenColor(StdDraw.YELLOW);
      else if(health<4)
         StdDraw.setPenColor(StdDraw.RED);
      StdDraw.filledRectangle(.01,.95,.2-(width*(10-health)),.012);
   }
   public static void barkPts() ///CORGI BP DISPLAY///
   {
      StdDraw.setPenColor(StdDraw.WHITE);
      StdDraw.text(.053,.89,"Bark Points");
      StdDraw.square(.048,.83,.05);
      StdDraw.setFont(main);
      if(score >= 10)
      {
         StdDraw.setPenColor(StdDraw.GREEN);
         StdDraw.text(.048,.83,"10/10");
      }
      if(score<10)
         StdDraw.text(.05,.83,score + "/10");
      }   
   public static void pointCheck() ///ULTRA BARK CANNON & BP CHECKER///
   {
      if(score >= 10)
      {
         StdDraw.text(.5,.7,"ULTRA SONIC CORGI BARK CHARGED! PRESS SPACE TO FIRE!");
         if(bCtrlFlag == 1 && StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && shipBottX < 2)
         {
            StdAudio.play("superbark.wav");
            StdDraw.picture(.5,.5,"dogb.png",270);
            for(double i=.4;i>0;i-=.01)
            {
               StdDraw.filledCircle(.5,i-.02,.06);
               StdDraw.show(1000/60);
            }
         shipBottX++;
         postPointChk();            
         }
         else if(rCtrlFlag == 1 && StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && shipRightX < 2)
         {
            StdAudio.play("superbark.wav");
            StdDraw.picture(.5,.5,"dogb.png");
            for(double i=.6;i<1;i+=.01)
            {
               StdDraw.filledCircle(i+.02,.5,.06);
               StdDraw.show(1000/60);
            }
            postPointChk();
            shipRightX++;
         }
         else if(lCtrlFlag == 1 && StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && shipLeftX < 2)
         {
            StdAudio.play("superbark.wav");
            StdDraw.picture(.5,.5,"dogb2.png");
            for(double i=.4;i>0;i-=.01)
            {
               StdDraw.filledCircle(i-.02,.5,.06);
               StdDraw.show(1000/60);
            }
            postPointChk();
            shipLeftX++; 
         }
         else if(tCtrlFlag == 1 && StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && shipTopX < 2)
         {
            StdAudio.play("superbark.wav");
            StdDraw.picture(.5,.5,"dogb.png", 90);
            for(double i=.6;i<1;i+=.01)
            {
               StdDraw.filledCircle(.5,i+.02,.06);
               StdDraw.show(1000/60);
            }
            postPointChk();
            shipTopX++;  
         }
      }
   }
   public static void postPointChk()
   {
      StdDraw.show(1000);
      ships();
      score = 0;
      holdFlag=1;   
   }
      ///BALL STATUS////
   public static void goodBall()
   {
      if(audioLag == true) 
         StdAudio.play("bell.wav");
      greenBox();
      score++;
      ctrlReset();
   }
   public static void badBall()
   {
      if(audioLag==true)
         StdAudio.play("boom.wav");
      redBox();
      if(score >=1)
         score--;
      if(health >=1)
         health--;
      bg();   
      mainControls();
      if(health == 0)//this doesnt always show,but can make lag seem intended...it seems random unfortunately, more likely to occur in easy mode
         StdDraw.picture(.5,.5,"saddog.png"); 
      StdDraw.show(1000/100); 
   }
   //BOXES AND BACKGROUND// 
   public static void bg()
   {
      StdDraw.clear();
      StdDraw.picture(.5,.5,"bg.jpg");
   }
   public static void blueBox()
   {
      StdDraw.setPenRadius(.007);
      StdDraw.setPenColor(StdDraw.CYAN);
      StdDraw.square(.5,.5,.06);
   }
   public static void redBox()
   {
      StdDraw.setPenRadius(.007);
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.square(.5,.5,.06);
      mainControls();
      StdDraw.show(1000/2);
   }
   public static void greenBox()
   {
      StdDraw.setPenRadius(.007);
      StdDraw.setPenColor(StdDraw.GREEN);
      StdDraw.square(.5,.5,.06);
      mainControls();
      StdDraw.show(1000/2);
   }
   public static void incRefr()
   {
      StdDraw.show(1000/60);
      bg();
      blueBox();
      StdDraw.setPenColor(StdDraw.YELLOW);
      pointCheck();
   }
   ///(end of checks and displays section)/////
 ////////////////////GAMEPLAY///////////////////////////////////////////////////////////////////////////////////////////////  
   public static void gamePlay()
   {  
      bg();
      while(health!=0 && (shipBottX != 2 || shipTopX != 2 || shipLeftX != 2 || shipRightX != 2))
      {
         difficulty();
         incoming();
      }
   }
   public static void difficulty()
   {
      int shipTotal = rightTreat + leftTreat + topTreat + bottTreat; //TO DETERMINE REMAINING SHIPS, [4] = 0 ships, [0] = 4 ships
      if(holdFlag == 2)
         holdFlag=0;
         //EASY/////////////////////////
      if(shipTotal == 3 && diffSelection==1)
         diff = .15;  //LAST SHIP
      if(shipTotal == 2 && diffSelection==1)
         diff = .01; //TWO SHIPS       
         //NORMAL///////////////////////////
      if(shipTotal == 3 && diffSelection==2)
      {
         diff = .0135; //DIFFICULTY WITH ONE REMAINING ON NORMAL
         secondBall=1;
         twoBallDiffX = 22;
         twoBallDiffY = 21;
      }
      if(shipTotal == 2 && diffSelection==2)
      {
         diff = .0133;   //TWO REMAINING ON NORMAL 
         secondBall=1;
      }   
         //HARD//////////////////////////////
      if(diffSelection==3)
      {
         secondBall=1;
      }   
      if(shipTotal == 3 && diffSelection==3)
      {//ONE SHIP
         diff = .014;
         secondBall=1;
         twoBallDiffX = 21;
         twoBallDiffY = 20;
      }
      if(shipTotal == 2 && diffSelection==3)
      {
         diff = .0137; //TWO SHIPS
         twoBallDiffX = 23;
         twoBallDiffY = 24;
         secondBall=1;
      }
         //MAIN DIFF///////////////////////
      if(diff<=.01) //DIFFICULTY LIMITER, MOSTLY FOR EASY MODE
         diff+=diffM; //MAIN DIFF. MOSTLY FOR RED BALL, DIFFICULTY PLUS USER INPUT
   }
//////////INCOMING BALLS////////////////////////////////////////////////////////////////////////////
   public static void incoming()
   {
      int i = (int)(Math.random()*(4)); //RANDOMIZES DIRECTION
      if (i==0 && shipBottX <=1)
         incomingBott();
      if (i==1 && shipTopX <=1)
         incomingTop();
      if (i==2 && shipLeftX <=1)
         incomingLeft();
      if (i==3 & shipRightX <=1)
         incomingRight();      
      indicators(); //UPDATE DISPLAYS 
   }
   public static void incomingBott()
   {
      double y=-.2;
      double x=.5;
      double x1=.5;
      double y1=.01;
      double zX= ((Math.random()*(.5+.5)-.5) / (twoBallDiffX-diff));
      double zY= ((Math.random()*(.35-.4)+.35) / (twoBallDiffY-diff));
      while(secondBall!=3)
      {
         StdDraw.picture(x,y,"ball.png");
         y+=(Math.random()*(.01)+diff);
         mainControls();
         incRefr();
         if(holdFlag == 1)
         {//had to be an if statement due to weird phantom ball appearing bug
            holdFlag = 0;
            break;
         }
         if(secondBall >= 1 && holdFlag!=2)
         {
            StdDraw.picture(x1,y1,"ballP.png");
            x1= zX+x1;
            y1= zY+y1;
            bonusBall(x1,y1,zY,zX);
            if(bounceCount<=5 && health!=0) //makes sure to stop ball if it takes too long or if the hp=0
            {
               if (x1 > 1 - .01 || x1 < .0001)
               {
                  zX = zX * -1;
                  bounceCount++;
               }   
               if (y1 > 1 - .01 || y1 < .0001)
               {
                  zY = zY * -1;
                  bounceCount++;
               }   
            }
            else
            {
               if(health==0)
                  break;
               secondBall++;
               bounceCount=0;
            }   
         }
         if(y>= .44 && bCtrlFlag == 1)
         {
            goodBall();
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=-100000;
               y=-100000;
            }   
         }
         else if(y>=.44)
         {
            badBall();
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=-100000;
               y=-100000;
            }
         }
      }
      ships(); //make sure that last treat isnt left behind
   }
   public static void incomingTop()/////////
   {
      double x=.5;
      double y=1.2;
      double x1=.5;
      double y1=.9;
      double zX= ((Math.random()*(.5+.5)-.5) / (twoBallDiffX-diff));
      double zY= ((Math.random()*(.35-.4)+.4) / (twoBallDiffY-diff));
      while(secondBall!=3)
      {
         StdDraw.picture(.5,y,"ball.png");
         y-=(Math.random()*(.01)+diff);
         mainControls();
         incRefr(); 
         if(holdFlag == 1)
         {
            holdFlag = 0;
            break;
         }
         if(secondBall >= 1 && holdFlag!=2)
         {
            StdDraw.picture(x1,y1,"ballP.png");
            x1= x1-zX;
            y1= y1-zY;
            bonusBall(x1,y1,zY,zX);
            if(bounceCount<=5 && health!=0)
            {
               if (x1 > 1 - .01 || x1 < .0001)
               {
                  zX = zX * -1;
                  bounceCount++;
               }   
               if (y1 > 1 - .01 || y1 < .0001)
               {
                  zY = zY * -1;
                  bounceCount++;
               }   
            }
            else
            {
               if(health==0)
                  break;
               secondBall++;
               bounceCount=0;  
            }   
         }
         if(y<=.56 && tCtrlFlag == 1)
         {
            goodBall();
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=100000;
               y=100000;
            }   
         }
         else if(y<=.56)
         {
            badBall();
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=100000;
               y=100000;
            }   
         }
      }
      ships();
   }
   public static void incomingLeft()//////////////
   {
      double y=.5;
      double x1=.1;
      double y1=.5;
      double zY= ((Math.random()*(.35-.4)+.4) / (twoBallDiffX-diff));
      double zX= ((Math.random()*(.5+.5)-.5) / (twoBallDiffY-diff));
      double x=-.2;
      while(secondBall!=3)
      {
         StdDraw.picture(x,.5,"ball.png");
         x+=(Math.random()*(.01)+diff);
         mainControls();
         incRefr(); 
         if(holdFlag == 1)
         {
            holdFlag = 0;
            break;
         }
         if(secondBall >= 1 && holdFlag!=2)
         {
            StdDraw.picture(x1,y1,"ballP.png");
            x1= zX+x1;
            y1= zY+y1;
            if(bounceCount<=5 && health!=0)
            {
               if (x1 > 1 - .01 || x1 < .0001)
               {
                  zX = zX * -1;
                  bounceCount++;
               }   
               if (y1 > 1 - .01 || y1 < .0001)
               {
                  zY = zY * -1;
                  bounceCount++;
               }   
               bonusBall(x1,y1,zY,zX);
            }
            else
            {
               if(health==0)
                  break;
               secondBall++;
               bounceCount=0;
            } 
         }
         if(x>=.44 && lCtrlFlag == 1)
         {
            goodBall();
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=-100000;
               y=-100000;
            }  
         }
         else if(x>=.44)
         {
            badBall();
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=-100000;
               y=-100000;
            }  
         }
      }
      ships();
   }
   public static void incomingRight()////////////////
   {
      double y=.5;
      double x1=.9;
      double y1=.5;
      double zY= ((Math.random()*(.35-.4)+.4) / (twoBallDiffX-diff));
      double zX= ((Math.random()*(.5+.5)-.5) / (twoBallDiffY-diff));
      double x=1.2;
      while(secondBall!=3)
      {
         StdDraw.picture(x,.5,"ball.png");
         x-=(Math.random()*(.01)+diff);
         mainControls();
         incRefr();
         if(holdFlag == 1)
         {
            holdFlag = 0;
            break;
         }
         if(secondBall >= 1 && holdFlag!=2)
         {
            StdDraw.picture(x1,y1,"ballP.png");
            x1= zX+x1;
            y1= zY+y1;
             bonusBall(x1,y1,zY,zX);
            if(bounceCount<=5 && health!=0)
            {
               if (x1 > 1 - .01 || x1 < .0001)
               {
                  zX = zX * -1;
                  bounceCount++;
               }   
               if (y1 > 1 - .01 || y1 < .0001)
               {
                  zY = zY * -1;
                  bounceCount++;
               }   
            }
            else
            {
               if(health==0)
                  break;
               secondBall++;
               bounceCount=0;
            }   
         }
         if(x<=.56 && rCtrlFlag == 1)
         {
            goodBall();
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=100000;
               y=100000;
            }  
         }
         else if(x<=.56)
         {
            badBall(); 
            if(secondBall==0)
               break;
            else
            {
               secondBall++;
               x=100000;
               y=100000;
            }  
         }
      }
      ships();
   }
/////////////((END INCOMING))///////////////////////////////////
 ////Main important stuff//////
   public static void mainControls() 
   {  
      String dog;
      String dog2; 
      if(mentalStatus >= 1)
      {
         dog = "saddog.png";
         dog2 = "saddog2.png";
      }
      else if(mentalStatus == -1)
      {
         dog = "happydog.png";
         dog2 = "happydog2.png";
      }
      else if(mentalStatus < -1)
      {
         dog = "superhappydog.png";
         dog2 = "superhappydog2.png";
      }   
      else
      {
         dog = "dog.png";
         dog2 = "dog2.png";
      }   
      ships();
      indicators();
      ctrlReset();
      if((StdDraw.isKeyPressed(KeyEvent.VK_DOWN) || StdDraw.isKeyPressed(KeyEvent.VK_S)) && bCtrlFlag == 0)
      {
         bCtrlFlag = 1;
         StdDraw.picture(.5,.5,dog, 270);
         StdDraw.picture(.5,.4,"barrier.png",270);
      }
      else if((StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) || StdDraw.isKeyPressed(KeyEvent.VK_D))&& rCtrlFlag == 0)
      {
         rCtrlFlag = 1;
         StdDraw.picture(.5,.5,dog);
         StdDraw.picture(.6,.5,"barrier.png");
      }
      else if((StdDraw.isKeyPressed(KeyEvent.VK_LEFT)|| StdDraw.isKeyPressed(KeyEvent.VK_A)) && lCtrlFlag == 0)
      {
         lCtrlFlag = 1;
         StdDraw.picture(.5,.5,dog2);
         StdDraw.picture(.4,.5,"barrier.png",180);
      }
      else if((StdDraw.isKeyPressed(KeyEvent.VK_UP)|| StdDraw.isKeyPressed(KeyEvent.VK_W)) && tCtrlFlag == 0)
      {
         tCtrlFlag = 1;
         StdDraw.picture(.5,.5,dog, 90);
         StdDraw.picture(.5,.6,"barrier.png",90);
      }
      else
      { 
         ctrlReset();
         StdDraw.picture(.5,.5,dog);
      }
   }
   public static void ctrlReset() //partially to prevent cheating by holding multiple directional keys
   {
      tCtrlFlag = 0;
      bCtrlFlag = 0;
      lCtrlFlag = 0;
      rCtrlFlag = 0;
   }
 //////////SHIPS/////////////////////////////////////////////
   public static void ships()
   {
    if(secondBall>=1)
    {
      shipBott("shipP.png");
      shipTop("shipP.png");
      shipLeft("shipP.png");
      shipRight("shipP.png");
    }  
    else
    {
      shipBott("ship.png");
      shipTop("ship.png");
      shipLeft("ship.png");
      shipRight("ship.png");
    }
   }
   public static void shipBott(String ship)
   {
      if(shipBottX <= 1) //IF STILL ALIVE
         StdDraw.picture(.5,0,ship);
      if(shipBottX == 2) //IF DEAD SHOW TREAT
      {
         StdDraw.picture(.5,.1,"treat.png");
         bottTreat = 1;
      }   
      if(shipBottX == 1) //50% SHIP HP
      {
         StdDraw.setPenColor(StdDraw.WHITE);
         StdDraw.text(.6,.05,"50%");
      }   
   }
   public static void shipTop(String ship)
   {
      if(shipTopX <= 1)
         StdDraw.picture(.5,1,ship,180);
      if(shipTopX == 2)
      {
         StdDraw.picture(.5,.9,"treat.png");
         topTreat = 1;
      }   
      if(shipTopX == 1)
      {
         StdDraw.setPenColor(StdDraw.WHITE);
         StdDraw.text(.6,.95,"50%");   
      }   
   }
   public static void shipLeft(String ship)
   {
      if(shipLeftX <= 1)
         StdDraw.picture(0,.5,ship,270);
      if(shipLeftX == 2)
      {
          StdDraw.picture(.1,.5,"treat.png");
          leftTreat = 1;
      }   
      if(shipLeftX == 1)
      {
         StdDraw.setPenColor(StdDraw.WHITE);
         StdDraw.text(.05,.6,"50%");
      }   
   }
   public static void shipRight(String ship)
   {
      if(shipRightX <= 1)
         StdDraw.picture(1,.5,ship,90);
      if(shipRightX == 2)
      {
         StdDraw.picture(.9,.5,"treat.png");
         rightTreat = 1;
      }      
      if(shipRightX == 1)
      {
         StdDraw.setPenColor(StdDraw.WHITE);
         StdDraw.text(.95,.6,"50%");
      }   
   }
   ////BONUS BALL////
   public static void bonusBallSuccess()
   {
      secondBall++;
      bounceCount=0;
      holdFlag=2;
   }
   public static void bonusBall(double x1,double y1,double zY,double zX)
   {//this ball is pretty generous with whether or not you catch it, reduces chance the ball will be too fast and bug
      double innerEdgeA = .5; //important in: BOTTOM AND LEFT
      double outerEdgeA = .433; //BOTTOM AND LEFT
      double outerEdgeB = .567; //TOP AND RIGHT
      double innerEdgeB = .5; //TOP AND RIGHT
      if(y1>= outerEdgeA && y1<=innerEdgeA && x1>=outerEdgeA && x1<=outerEdgeB && bCtrlFlag == 1)
      {
         goodBall();
         bonusBallSuccess();//bottom, Y values more important
      }
      else if(y1<=outerEdgeB && y1>=innerEdgeB && x1>=outerEdgeA && x1<=outerEdgeB && tCtrlFlag == 1)
      {
         goodBall();
         bonusBallSuccess();//top, Y values more important
      }
      else if(x1<=outerEdgeB && x1>=innerEdgeB && y1<=outerEdgeB && y1>=outerEdgeA && rCtrlFlag == 1)
      {
         goodBall();
         bonusBallSuccess();//right, X values important
      }
      else if(x1>=outerEdgeA && x1<=innerEdgeA && y1<=outerEdgeB && y1>=outerEdgeA && lCtrlFlag == 1)
      {
         goodBall();
         bonusBallSuccess();//left, X values important
      }
      else if((x1>=outerEdgeA && x1<=innerEdgeA && y1<=outerEdgeB && y1>=outerEdgeA) ||(x1<=outerEdgeB && x1>=innerEdgeB && y1<=outerEdgeB && y1>=outerEdgeA) || (y1<=outerEdgeB && y1>=innerEdgeB && x1>=outerEdgeA && x1<=outerEdgeB) || (y1>= outerEdgeA && y1<=innerEdgeA && x1>=outerEdgeA && x1<=outerEdgeB))
      {
         badBall();
         secondBall++;
         bounceCount=0;
         holdFlag=2;
      }
   } 
}

/* Vague Credits:
Audio: Mostly from undertale, super dog bark and "bell" on a sound sharing website, sounds modified somewhat by myself
Images: 8 bit corgi from some tumblr person i think, image modifications by myself. 8 bit stars also from some tumblr i think. Who knows where the treat, ship, and ball images came from? \_(o3o)_/ What's a reverse image search?
astroCorgi: collaboration between me and jGrasp
astroCorgi class file: mostly jGrasp's work, but i guess i was involved
instructions dot txt: me. 

Editor's comment: StdDraw.show is the worst thing ever.
Special Thanks: I'd like to thank jGrasp, this keyboard, electricity, and oxygen. It wouldn't have been possible without all of you.
Disclaimer: These statements have not been evaluated by the Food and Drug Administration. This product is not intended to diagnose, treat, cure, or prevent any disease.*/