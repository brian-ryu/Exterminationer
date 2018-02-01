/*
 * CompSci Course Summative - "Exterminationer"
 * 
 * Allen
 * Sasha
 * Elizabeth
 * Brian
 * 
*/

//Import classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Scanner;

//Main class
class BasicMovement {
  
  //score-related variables
  static int topShekel;
  
  //song-related variables
  static int song;
  static int songTimer;
  static Clip clip;
  
  //Arraylists
  static ArrayList<Bullet> pool = new ArrayList<Bullet>();
  static ArrayList<Enemy> enemyPool = new ArrayList<Enemy>();
  static ArrayList<BasicBullet> enemyBulletPool = new ArrayList<BasicBullet>();
  static ArrayList<BasicBullet> wavePool = new ArrayList<BasicBullet>();
  static ArrayList<Powerups> powerupsPool = new ArrayList<Powerups>();
  
  static boolean play = true;
  static int round = -1; //Keeps track of how many waves of enemies the player has cleared
  
  //player movement and shooting controls
  static boolean shooting=false;
  static boolean left;
  static boolean right;
  static boolean up;
  static boolean down;
  static int speed = 1;
  
  //booleans to control menus
  static boolean mainMenu=true;
  static boolean paused;
  static boolean quitConfirm;
  static boolean gameOver;
  static boolean helpScreen;
  static boolean credits;
  
  static int timeInterval=5;
  static int bulletTime; //time since last bullet was fired
  static int bulletTimeMin = 200; //minimum time between bullets
  static int bulletTimeRate = 20;
  static int enemyBulletTime;
  static int enemyBulletTimeRate = 1000;
  static int bossBulletTime;
  static int bossBulletTimeRate = 1000;
  
  //background
  static int backgroundy=0;
  static int background2y=backgroundy-1080;
  static int maxx=Toolkit.getDefaultToolkit().getScreenSize().width; //get maximum screen width
  static int maxy=Toolkit.getDefaultToolkit().getScreenSize().height; //get maximum screen height
  
  static int selectedX;
  static int selectedY;
  static Image selectedOverlay=Toolkit.getDefaultToolkit().getImage("selected.png"); //image pointing to the button the mouse is over
  
  static Image creditsImage=Toolkit.getDefaultToolkit().getImage("credits.png"); //credits screen
  static Image helpScreenImage=Toolkit.getDefaultToolkit().getImage("helpScreen.png"); //help screen
  static Image mainMenuImage=Toolkit.getDefaultToolkit().getImage("mainMenu.png"); //main menu
  static Image gameOverMenu=Toolkit.getDefaultToolkit().getImage("gameOver.png"); //game over screen
  static Image pauseMenuImage=Toolkit.getDefaultToolkit().getImage("pauseMenu.png"); //pause menu
  static Image background = Toolkit.getDefaultToolkit().getImage("background.png"); //background image
  static Image background2=Toolkit.getDefaultToolkit().getImage("background.png"); //clone of background image to create scrolling background
  
  static Image coin=Toolkit.getDefaultToolkit().getImage("coin.png"); //coin sprite
  static Image shield=Toolkit.getDefaultToolkit().getImage("shield.png"); //shield sprite
  static Image shieldOverlay=Toolkit.getDefaultToolkit().getImage("shieldOverlay.png"); //shield as seen when equipped on player
  static Image warpDriveImage=Toolkit.getDefaultToolkit().getImage("warpdrive.png");
  static Image speedOverlay=Toolkit.getDefaultToolkit().getImage("speedOverlay.png");
  static Image repairKitImage=Toolkit.getDefaultToolkit().getImage("repairkit.png");
  static Image trishotImage=Toolkit.getDefaultToolkit().getImage("trishot.png");
  static Image trishotOverlay=Toolkit.getDefaultToolkit().getImage("triShotOverlay.png");
  static Image powerupsImage;
  
  static Image spriteRed=Toolkit.getDefaultToolkit().getImage("player.png"); //red player sprite
  static Image spriteGreen=Toolkit.getDefaultToolkit().getImage("player2.png"); //green player sprite
  static Image pBulletRed=Toolkit.getDefaultToolkit().getImage("bullet.png"); //red player bullet
  static Image pBulletGreen=Toolkit.getDefaultToolkit().getImage("bullet2.png"); //green player bullet
  static Image pSprite; //controls which player sprite is being drawn
  static Image pBullet; //controls which player bullet sprite is being drawn
  
  static Image enemyBulletRed=Toolkit.getDefaultToolkit().getImage("enemyBulletRed.png");//red bullet sprite
  static Image enemyBulletGreen=Toolkit.getDefaultToolkit().getImage("enemyBulletGreen.png");//green bullet sprite
  static Image enemyBullet; //controls which color bullet sprite is being shown
  static Image[] enemies=new Image[6]; //stores all enemy images
  
  static int[] shekelCounter=new int[1]; //array to store digits in number of coins
  static Image[] shekelImage=new Image[10]; //array to store all number images
  static Image shekelLabel=Toolkit.getDefaultToolkit().getImage("shekelLabel.png"); //shekel label 
  
  static Image playerExplosion1 = Toolkit.getDefaultToolkit().getImage("playerExplosion1.png"); 
  static Image playerExplosion2 = Toolkit.getDefaultToolkit().getImage("playerExplosion2.png"); 
  static Image playerExplosion3 = Toolkit.getDefaultToolkit().getImage("playerExplosion3.png"); 
  static Image playerExplosion4 = Toolkit.getDefaultToolkit().getImage("playerExplosion4.png"); 
  static Image playerExplosion5 = Toolkit.getDefaultToolkit().getImage("playerExplosion5.png"); 
  
  static Image bossSprite1 = Toolkit.getDefaultToolkit().getImage("boss1.png");
  static Image bossSprite2 = Toolkit.getDefaultToolkit().getImage("boss2.png");
  
  static graphicsPanel game;
  static Player player=new Player();
  static Color pauseMenuCol = Color.WHITE;
  static JFrame window;
  
  
  //Main method
  public static void main (String args[]) throws Exception {
    
    //load all enemy images into array
    enemies[0]=Toolkit.getDefaultToolkit().getImage("enemy1.png"); //green
    enemies[1]=Toolkit.getDefaultToolkit().getImage("enemy2.png"); //red
    enemies[2]=Toolkit.getDefaultToolkit().getImage("enemy3.png"); //green
    enemies[3]=Toolkit.getDefaultToolkit().getImage("enemy4.png"); //red
    enemies[4]=Toolkit.getDefaultToolkit().getImage("enemy5.png"); //green
    enemies[5]=Toolkit.getDefaultToolkit().getImage("enemy6.png"); //red
    
    //load all shekel counter images into array
    shekelImage[0]=Toolkit.getDefaultToolkit().getImage("zero.png"); 
    shekelImage[1]=Toolkit.getDefaultToolkit().getImage("one.png"); 
    shekelImage[2]=Toolkit.getDefaultToolkit().getImage("two.png"); 
    shekelImage[3]=Toolkit.getDefaultToolkit().getImage("three.png"); 
    shekelImage[4]=Toolkit.getDefaultToolkit().getImage("four.png"); 
    shekelImage[5]=Toolkit.getDefaultToolkit().getImage("five.png"); 
    shekelImage[6]=Toolkit.getDefaultToolkit().getImage("six.png"); 
    shekelImage[7]=Toolkit.getDefaultToolkit().getImage("seven.png"); 
    shekelImage[8]=Toolkit.getDefaultToolkit().getImage("eight.png"); 
    shekelImage[9]=Toolkit.getDefaultToolkit().getImage("nine.png"); 
    
    shekelCounter[0]=0;
        
    //JFrame
    window = new JFrame ();
    window.setSize (maxx, maxy);
    window.setResizable (false);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    game = new graphicsPanel();
    KeyListener listener = new MyKeyListener();
    MouseListener mListener=new MyMouseListener();
    MouseMotionListener mMListener=new MyMouseMotionListener();
    
    game.addKeyListener (listener);
    game.addMouseListener(mListener);
    game.addMouseMotionListener(mMListener);
    window.add (game);
    
    window.setVisible (true);
    
    game.requestFocusInWindow();
    
    //Reset player vars
    varReset();
    
    //Game loop
      do {
        
        if(paused){ //if the game is not being played -- ie. game is showing menus (paused, main, etc.)
            if(quitConfirm){ //if user wants to exit, confirm choice w/ dialogue box
              Object[] options = { "YES", "NO"};
              int selection = JOptionPane.showOptionDialog(null, "Are you sure?", "QUIT?",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
              if(selection==0){ //if they say yes
                
                if(mainMenu==true){ //if player is already at the main menu
                  window.dispose();
                }else{ //if they are at the pause menu
                  clip.stop();
                  varReset(); //reset the variables and return to the main menu
                }
                
              }quitConfirm=false; //reset variable so dialogue box disappears
              
            }
        }else{
          
          //manage music
          songTimer -= timeInterval;
          if (songTimer < 0) {
            songTimer = 100000;
            if (song == 1) {
              song = 2;
              playMusic("fat skel.wav");
            }else {
              song = 1;
              playMusic("Boats.wav");
            } 
          }
 
          if (player.health <= 0) {
            player.gameOverAnimationTimer -= timeInterval;
            if (player.gameOverAnimationTimer <= 0) {
              clip.stop();
              paused=true; //stop the game if player dies and game over animation is completed
              gameOver=true;
              selectedY=maxy/2+143;
            }
          }
          
          //Move player
          if (player.health > 0) {
            if (left && right) {
            }else if (left) {
              player.posx += -speed;
            }else if (right) {
              player.posx += speed;
            }
            if (up && down) {
            }else if (up) {
              player.posy += -speed;
            }else if (down) {
              player.posy += speed;
            }
            
            
            //Spawn bullets
            if (shooting && bulletTime >= bulletTimeMin) {
              if (player.trishotTimer <= 0) {
                Bullet bullet = new Bullet();
                bullet.x = player.posx;
                bullet.y = player.posy;
                if (player.isRed ==true){
                  bullet.isRed = true; 
                }else {
                  bullet.isRed = false;
                }
                pool.add (bullet);
              }else {
                Bullet bullet0 = new Bullet();
                bullet0.x = player.posx - 5;
                bullet0.y = player.posy;
                if (player.isRed ==true){
                  bullet0.isRed = true; 
                }else {
                  bullet0.isRed = false;
                }
                pool.add (bullet0);
                Bullet bullet1 = new Bullet();
                bullet1.x = player.posx;
                bullet1.y = player.posy;
                if (player.isRed ==true){
                  bullet1.isRed = true; 
                }else {
                  bullet1.isRed = false;
                }
                pool.add (bullet1);
                Bullet bullet2 = new Bullet();
                bullet2.x = player.posx + 5;
                bullet2.y = player.posy;
                if (player.isRed ==true){
                  bullet2.isRed = true; 
                }else {
                  bullet2.isRed = false;
                }
                pool.add (bullet2);
              }
              bulletTime = 0;
            }
            
          }
          
          //Move and remove bullets
          if (pool.size() > 0) {
            for (int i = pool.size() - 1; i >= 0; i--) {
              (pool.get (i)).y += -3;
              if ((pool.get (i)).bulletWall()) {
                pool.remove (i);
              }
            }
          }
          
          //Enemy bullets
          if (enemyPool.size() == 0) {
            round++;
            if (round < 2){
              spawnEnemy();
            }else if (round == 3){
              spawnSpiralEnemy();
            }
            else if (round == 4){
              spawnBoss();
              spawnSpiralEnemy();
              round = -1;
            }
          }else{
            for (int a = 0 ; a < enemyPool.size() ; a++){
            if (enemyPool.get(a).spiral){ //if spiral move enemy to mid screen
              if ((enemyPool.get(a).y) <= maxy/2){
                moveEnemy();
              }else if (enemyBulletTime >= enemyBulletTimeRate) {
                enemyShoot();
                enemyBulletTime = 0;
              }
              checkEnemy(); //checks if enemies have been hit by bullets and removes them from the list
            }else if (enemyPool.get(a).boss){ // if boss move left and right
              enemyPool.get(a).moveBoss();
              if (bossBulletTime >= bossBulletTimeRate) {
                enemyPool.get(a).spawnLaser();
                bossBulletTime = 0;
              }
            }else if (!enemyPool.get(a).spiral && !enemyPool.get(a).boss){ //if regular enemy keep near top
              if ((enemyPool.get(a).y) <= 70) {
                moveEnemy();
              }else if (enemyBulletTime >= enemyBulletTimeRate) {
                enemyShoot();
                enemyBulletTime = 0;
              }
              checkEnemy(); //checks if enemies have been hit by bullets and removes them from the list
            }
            }
            if (enemyBulletPool.size() > 0) {
              for (int i = enemyBulletPool.size() - 1; i >= 0; i--) {
                enemyBulletPool.get(i).moveBullet(); //relocate bullets
                if ((enemyBulletPool.get (i)).playerEnemyBullet(player.posx, player.posy) && player.isRed != enemyBulletPool.get(i).isRed && player.shieldTimer <= 0) {
                  player.health --;
                  enemyBulletPool.remove (i);
                }else if (enemyBulletPool.get(i).bulletWall(enemyBulletPool.get(i).x, enemyBulletPool.get(i).y)) {
                  enemyBulletPool.remove (i);
                }
              }
            }
          }
          //enemy waves
          if (wavePool.size() == 0) {
            if (Math.random() < 0.5) {
              spawnWave();
            }else if (Math.random() < 0.5) {
              spawnZigzagBulletWave();
            }else{
              spawnBombBulletWave();
            }
            
          }else {
            for (int i = wavePool.size() - 1; i >= 0; i--) {
              wavePool.get(i).moveBullet();
              if (wavePool.get(i).bulletWall(wavePool.get(i).x, wavePool.get(i).y)) {
                wavePool.remove(i);
              }else if ((wavePool.get (i)).playerEnemyBullet (player.posx, player.posy) && player.isRed != wavePool.get(i).isRed && player.shieldTimer <= 0) {
                player.health --;
                wavePool.remove (i);
              }
            }
          }
          
          //move powerups
          for (int i = 0; i < powerupsPool.size(); i++) {
            powerupsPool.get(i).y += 0.5;
            if (powerupsPool.get(i).hitWall()) {
              powerupsPool.remove(i);
            }else if (powerupsPool.get(i).pickupCheck()) {
              powerupPlayer(powerupsPool.get(i).sprite);
              powerupsPool.remove(i);
            }
          }
         
          
          bulletTime += timeInterval;
          enemyBulletTime += timeInterval;
          bossBulletTime += timeInterval;
          
          //manage powerup timers
          if (player.shieldTimer > 0) {
            player.shieldTimer -= timeInterval;
          }
          if (player.speedTimer > 0) {
            player.speedTimer -= timeInterval;
          }
          if (player.speedTimer > 0) {
            speed = 3;
          }else {
            speed = 1;
          }
          if (player.trishotTimer > 0) {
            player.trishotTimer -= timeInterval;
          }
          
          collisionCheck(); //stop player from moving offscreen
          backgroundUpdate(); //change background's y co-ordinate
          
          
          //Screen delay
          try {
            Thread.sleep (timeInterval);
          }catch (Exception exc) {}
          
        }
        window.repaint(); //repaint the graphics panel
        
      }while (play);
      
  } //Main method end
  
  //load top score
  public static void loadScore() throws Exception {
    File txt = new File("score.txt");
    Scanner in = new Scanner(txt);
    topShekel = in.nextInt();
    in.close();
  }
  
  //write top score
  public static void writeScore() throws Exception {
    File txt = new File("score.txt");
    PrintWriter writer = new PrintWriter(txt);
    writer.print(player.shekels);
    writer.close();
  }
  
  //spawn wave
  public static void spawnWave() {
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 3; j++) {
        BasicBullet bullet = new BasicBullet();
        bullet.x = 100 * i;
        bullet.y = -20 * j;
        bullet.directionLR = 0;
        bullet.directionUD = 1;
        if (Math.random() > 0.5) {
          bullet.isRed = true;
        }else {
          bullet.isRed = false;
        }
        wavePool.add(bullet);
      }
    }
  }
  
  //powerup player method
  public static void powerupPlayer(int sprite) {
    if (player.health > 0) {
      if (sprite == 1) { //shield
        player.shieldTimer += 8000;
        if (player.shieldTimer > 8000) {
          player.shieldTimer = 8000;
        }
      }else if (sprite == 2) { //speed
        player.speedTimer += 8000;
        speed = 3;
        if (player.speedTimer > 8000) {
          player.speedTimer = 8000;
        }
      }else if (sprite == 3 && player.health < 100) { //health
        player.health += 10;
        if (player.health > 100) {
          player.health = 100;
        }
      }else if (sprite == 4) { //trishot
        player.trishotTimer += 8000;
        if (player.trishotTimer > 8000) {
          player.trishotTimer = 8000;
        }
      }else { //shekel
        player.shekels++;
        shekelCountUpdate();
      }
    }
  }
  
  //Resetting all variables for a game restart
  public static void varReset(){
    paused=true;
    gameOver=false;
    mainMenu=true;
    helpScreen=false;
    credits=false;
    round = -1;
    
    player.posx = maxx/2;
    player.posy = maxy/3*2;
    player.isRed = true;
    player.health = 100;
    player.shieldTimer = 0;
    player.speedTimer = 0;
    player.trishotTimer = 0;
    player.gameOverAnimationTimer = 3800;
    player.shekels = 0;
    
    song = 2;
    songTimer = 0;
    
    left = false;
    right = false;
    up = false;
    down = false;
    shooting = false;
    bulletTime = bulletTimeMin;
    enemyBulletTime = enemyBulletTimeRate;

    enemyPool.clear();
    enemyBulletPool.clear();
    pool.clear();
    powerupsPool.clear();
    
    selectedX=maxx/2-180;
    selectedY=maxy/2;
    
    try {
      loadScore();
    }catch (Exception e) {
    }
  }
  
  //shekel update counter method
  public static void shekelCountUpdate() {
    String shekelString=Integer.toString(player.shekels); //convert number of shekels to a string
    int shekelDigits=shekelString.length(); //get number of digits  
    shekelCounter=new int[shekelDigits]; //create array storing digits of shekel counter
    for(int i=0;i<shekelDigits;i++){
      shekelCounter[i]=Integer.parseInt(shekelString.substring(0,1)); //load digits into array
      shekelString=shekelString.substring(1); //remove digit from string
    }
  }
  
  //play music method
  public static void playMusic(String filename) {
    try {
      File audio = new File(filename);
      AudioInputStream stream;
      AudioFormat format;
      DataLine.Info info;
      
      stream = AudioSystem.getAudioInputStream(audio);
      format = stream.getFormat();
      info = new DataLine.Info(Clip.class, format);
      clip = (Clip)AudioSystem.getLine(info);
      clip.open(stream);
      clip.start();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //Graphics panel
  public static class graphicsPanel extends JPanel {
    public void paintComponent (Graphics g) {
      super.paintComponent (g);
      
      //Background
      //g.setColor (Color.GREEN);
      //g.fillRect (0, 0, maxx, maxy);
      g.drawImage(background,0,(int)backgroundy,null);  
      g.drawImage(background2,0,(int)background2y,null);
      
      //Bullets
      //g.setColor (Color.YELLOW);
      for (int i = 0; i < pool.size(); i++) {
        //g.fillRect ((pool.get(i)).x - 5, (pool.get(i)).y - 5, 10, 10);
        if(pool.get(i).isRed){
          pBullet=pBulletRed;
        }else {
          pBullet=pBulletGreen;
        }
        g.drawImage(pBullet, (pool.get(i)).x-3, (pool.get(i)).y-20, null);
      }
      //Player
      //g.setColor (Color.RED);
      //g.fillRect (posx - 10, posy - 10, 40, 40);
      
      //Powerup effects
      if (player.health > 0) {
        if (player.isRed){
          pSprite=spriteRed;
        }else {
          pSprite=spriteGreen;
        }
        g.drawImage (pSprite, player.posx - 20, player.posy - 20, null);
        if(player.shieldTimer > 0){
          g.drawImage(shieldOverlay,player.posx-22,player.posy-21,null);
        }
        if (player.speedTimer > 0) {
          g.drawImage(speedOverlay,player.posx - 21, player.posy - 20, null);
        }
        if(player.trishotTimer > 0){
          g.drawImage(trishotOverlay,player.posx - 20, player.posy - 20, null);
        }
        
        
      }else {
        //when the game is over and the player explodes, draw the explosion animation
        if (player.gameOverAnimationTimer > 3700) {
          g.drawImage(playerExplosion1, player.posx - 25, player.posy - 25, null);
        }else if (player.gameOverAnimationTimer > 3600) {
          g.drawImage(playerExplosion2, player.posx - 25, player.posy - 25, null);
        }else if (player.gameOverAnimationTimer > 3500) {
          g.drawImage(playerExplosion3, player.posx - 25, player.posy - 25, null);
        }else if (player.gameOverAnimationTimer > 3300) {
          g.drawImage(playerExplosion4, player.posx - 25, player.posy - 25, null);
        }else if (player.gameOverAnimationTimer > 3100) {
          g.drawImage(playerExplosion5, player.posx - 25, player.posy - 25, null);
        }
          
      }
       //Enemies and enemy bullets
      for (int i = 0; i < enemyBulletPool.size(); i++) {
        if (enemyBulletPool.get(i).isRed) {
          enemyBullet=enemyBulletRed;
        }else {
          enemyBullet=enemyBulletGreen;
        }
        g.drawImage (enemyBullet,enemyBulletPool.get(i).x - 5, enemyBulletPool.get(i).y - 5, null);
      }
      
      for (int i = 0; i < enemyPool.size(); i++) {
        if (enemyPool.get(i).boss){
          if (enemyPool.get(i).sprite == 1) {
            g.drawImage(bossSprite1, enemyPool.get(i).x - 200, enemyPool.get(i).y - 100, 400, 200, null);
          }else {
            g.drawImage(bossSprite2, enemyPool.get(i).x - 200, enemyPool.get(i).y - 100, 400, 200, null);
          }
        }else{
          g.drawImage(enemies[enemyPool.get(i).sprite],(enemyPool.get(i)).x - 20, (enemyPool.get(i)).y - 20,null);
        }
      }
      
      for (int i = 0; i < wavePool.size(); i++) {
        if (wavePool.get(i).isRed) {
          enemyBullet=enemyBulletRed;
        }else {
          enemyBullet=enemyBulletGreen;
        }
        g.drawImage (enemyBullet,(wavePool.get(i)).x - 5, (wavePool.get(i)).y - 5, null);
      }
      
      //Powerups
      for(int i=0;i<powerupsPool.size();i++){
        if (powerupsPool.get(i).sprite==1){ //shield powerup
          powerupsImage=shield;
        }
        else if(powerupsPool.get(i).sprite==2){ //speed powerup
          powerupsImage=warpDriveImage;
        }
        else if(powerupsPool.get(i).sprite==3){ //health powerup
          powerupsImage=repairKitImage;
        }
        else if (powerupsPool.get(i).sprite == 4) {
          powerupsImage = trishotImage;
        }
        else{ //coins
          powerupsImage=coin;
        }

        g.drawImage(powerupsImage,(powerupsPool.get(i)).x,(int)powerupsPool.get(i).y,null);
      }
      
      
      //Health bar
      g.setColor(Color.WHITE);
      g.fillRect(0, maxy - 130, 710, 70);
      g.setColor(Color.BLACK);
      g.fillRect(5, maxy - 125, 700, 60);
      g.setColor(Color.RED);
      g.fillRect(5, maxy - 125, player.health * 7, 60);
      
      //shekel counter
      g.drawImage(shekelLabel,50,maxy - 160,null); //draw the "SHEKELS: " label
      if(player.shekels==0){ //at the start of the game, draw a zero
        g.drawImage(shekelImage[0], 251,maxy - 160,null);
      }
      else { //for the rest of the game, draw the corresponding numbers
        for(int i=0;i<shekelCounter.length;i++){
          g.drawImage(shekelImage[shekelCounter[i]], 251+26*i,maxy - 160,null);
        }
      }
      
      //Drawing menus and suchlike
      if(paused){ //if the game is not being played
        if(gameOver){ //if the player has lost
          g.setColor(Color.BLACK);
          g.fillRect(0, 0, maxx, maxy);
          g.drawImage(gameOverMenu,maxx/2-400,maxy/2-400,null); //draw the game over menu
          int fontSize = 100;
          g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
          g.setColor(Color.WHITE);
          g.drawString("Score : " + player.shekels, maxx/2-250, maxy/2-100);
          if (player.shekels > topShekel) {
            g.drawString("NEW HIGH SCORE!!!", maxx/2-350, maxy/2);
          }else {
            g.drawString("High Score : " + topShekel, maxx/2-250, maxy/2);
          }
        }
        else if(mainMenu){
          g.setColor(Color.BLACK);
          g.fillRect(0,0,maxx,maxy);
          g.drawImage(mainMenuImage,maxx/2-512,maxy/2-384,null);
        }
        else{
          g.drawImage(pauseMenuImage,maxx/2-400,maxy/2-300,null); //draw the pause menu
          
        }
        
        if(credits){ //if the credits screen is called up
          g.setColor(Color.BLACK);
          g.fillRect(0,0,maxx,maxy); //make the entire screen black
          g.drawImage(creditsImage,maxx/2-512,maxy/2-384,null); //draw the cerdits screen 
        }
        
        if(helpScreen){ //if the help screen is called up
          g.setColor(Color.BLACK);
          g.fillRect(0,0,maxx,maxy);
          g.drawImage(helpScreenImage,maxx/2-512,maxy/2-384,null);
        }
        
        
        if(helpScreen==false && credits==false){ //if the help screen is not showing
          g.drawImage(selectedOverlay,selectedX,selectedY,null); //draw the selection overlay to highlight selected option
        }
      }
      
    }
    
  }
   
  //Key listener
  public static class MyKeyListener implements KeyListener {
    
    public void keyTyped (KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
      
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        shooting=true;
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        left=true;
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        right=true;
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        up=true;
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        down=true;
      } 
      
    }
    
    public void keyReleased (KeyEvent e) {
      
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        shooting=false;
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        left=false;
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        right=false;
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        up=false;
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        down=false;
      }
      if (e.getKeyCode() == KeyEvent.VK_P) { //pause game
        if(mainMenu==false){
          if(paused){
            paused=false;
          }else if(paused==false){
            paused=true;
            helpScreen=false;
            mainMenu=false;
            selectedY=maxy/2-50;
          }
        }
      }
      if(e.getKeyCode() == KeyEvent.VK_SHIFT){ //change ship colour
        if(player.isRed){
          player.isRed=false;
        }
        else {
          player.isRed=true;
        }
      }
      
    }
    
  }
  
  //Mouse Listener
//  public static class MyMouseListener implements MouseListener{
//    public void mouseClicked (MouseEvent m){}
//    public void mouseEntered (MouseEvent m){} 
//    public void mouseExited (MouseEvent m){}
//    public void mousePressed (MouseEvent m){}
//    
//    public void mouseReleased (MouseEvent m){
//      int x=m.getX();
//      int y=m.getY();
//      
//      if(paused){
//        if(x>maxx/2-150 && x<maxx/2+150){
//          if(mainMenu==false){
//            if(gameOver == false){ //if the pause menu and not the game over menu is showing
//              if(y > maxy / 2 - 50 && y < maxy / 2 + 20){ //if release is inside the "RESUME" button
//                paused=false; //unpause the game
//              }
//              else if(y>maxy/2+25 && y<maxy/2+95){ //if release is inside "QUIT" button
//                quitConfirm=true; //call up a confirmation dialogue box
//              }
//            }
//            
//            if(y>maxy/2+100 && y<maxy/2+170){ //if release is inside the "YES" button in the game over screen OR the "RESTART" button in the pause menu
//              varReset(); //reset the entire game
//              paused=false; //remove the menu from screen
//              mainMenu=false;
//            }
//            
//            else if(y>maxy/2+175 && y<maxy/2+245){ //if release is inside the "HELP" button in the PAUSE MENU or the "NO" button in the GAME OVER menu
//              if(gameOver){
//                varReset();
//              }else{
//                helpScreen=true;
//              }
//            }
//          }
//          else if (mainMenu){ //if main menu is showing
//            
//            System.out.println(y);
//            if(y>maxy/2 && y<maxy/2+70){
//              paused=false;
//              mainMenu=false;
//            }else if(y>maxy/2+75 && y<maxy/2+145){
//              quitConfirm=true;
//            }else if(y>maxy/2+150 && y<maxy/2+220){
//              helpScreen=true;
//            }else if(y>maxy/2+225 && y<maxy/2+295){
//              credits=true;
//            }
//            
//          }
//        }
//        
//        else if (x>maxx/2+341 && x<maxx/2+492){
//          if(credits){
//            credits=false; //get rid of the credits screen
//          }else if(helpScreen){
//            helpScreen=false; //get rid of the help screen
//          }
//        }
//      }
//    }
//  }
  public static class MyMouseListener implements MouseListener {
    public void mouseClicked (MouseEvent m){}
    public void mouseEntered (MouseEvent m){} 
    public void mouseExited (MouseEvent m){}
    public void mousePressed (MouseEvent m){}
    
    public void mouseReleased (MouseEvent m) {
      int x=m.getX();
      int y=m.getY();
      
      if(paused){
        if(x>maxx/2-150 && x<maxx/2+150){
          if(mainMenu==false){
            if(gameOver == false){ //if the pause menu and not the game over menu is showing
              if(y > maxy / 2 - 50 && y < maxy / 2 + 20){ //if release is inside the "RESUME" button
                paused=false; //unpause the game
              }
              else if(y>maxy/2+25 && y<maxy/2+95){ //if release is inside "QUIT" button
                
                quitConfirm=true; //call up a confirmation dialogue box
              }
              
              
              if(y>maxy/2+100 && y<maxy/2+170){ //if release is inside the "YES" button in the game over screen OR the "RESTART" button in the pause menu
                varReset(); //reset the entire game
                paused=false; //remove the menu from screen
                mainMenu=false;
              }
              
              else if(y>maxy/2+175 && y<maxy/2+245){ //if release is inside the "HELP" button in the PAUSE MENU or the "NO" button in the GAME OVER menu
                helpScreen=true;
              }
            }
            
            if (gameOver) {
              if (x > maxx / 2 - 150 && x < maxx / 2 + 150) {
                if (y > maxy / 2 + 143 && y < maxy / 2 + 213) {
                  try {
                    if (player.shekels > topShekel) {
                      writeScore();
                    }
                  }catch(Exception e) {
                  }
                  varReset(); //reset the entire game
                  paused=false; //remove the menu from screen
                  mainMenu=false;
                }else if (y > maxy / 2 + 218 && y < maxy / 2 + 288){
                  try {
                    if (player.shekels > topShekel) {
                      writeScore();
                    }
                  }catch(Exception e) {
                  }
                  varReset();
                }
              }
            }
            
          }
          else if (mainMenu && !credits && !helpScreen){ //if main menu is showing
            
            if(y>maxy/2 && y<maxy/2+70){
              paused=false;
              mainMenu=false;
            }else if(y>maxy/2+75 && y<maxy/2+145){
              quitConfirm=true;
            }else if(y>maxy/2+150 && y<maxy/2+220){
              helpScreen=true;
            }else if(y>maxy/2+225 && y<maxy/2+295){
              credits=true;
            }
            
          }
        }
        
        else if (x>maxx/2+341 && x<maxx/2+492){
          if(credits){
            credits=false; //get rid of the credits screen
          }else if(helpScreen){
            helpScreen=false; //get rid of the help screen
          }
        }
      }
    }
  }
  
  //Mouse Motion Listener
//  public static class MyMouseMotionListener implements MouseMotionListener{
//    public void mouseDragged(MouseEvent m){}
//    
//    public void mouseMoved (MouseEvent m){ //track mouse movement
//      int x=m.getX(); //get x and y co-ordinates of the mouse
//      int y=m.getY();
//      
//      if(paused){ //if the pause, main, or game over menu is showing
//        if(x>maxx/2-150 && x<maxx/2+150){ //if the cursor is within a button's horizontal boundaries
//          
//          if(mainMenu){ //if the main menu screen is showing
//            if(y>maxy/2 && y<maxy/2+70){
//              selectedY=maxy/2;
//            }
//            else if(y>maxy/2+75 && y<maxy/2+145){
//              selectedY=maxy/2+75;
//            }
//            else if(y>maxy/2+150 && y<maxy/2+220){
//              selectedY=maxy/2+150;
//            }
//            else if(y>maxy/2+225 && y<maxy/2+295){
//              selectedY=maxy/2+225;
//            }
//          }
//          
//          else{ //if the pause or game over screens are showing
//            if(y>maxy/2+100 && y<maxy/2+170){ //if cursor is over the "RESTART" button in the pause menu OR the "YES" button in the game over menu
//              selectedY=maxy/2+100; //change selection overlay's co-ordinates to match the button
//            }
//            else if(y>maxy/2+175 && y<maxy/2+245){ //if cursor is over the "HELP" button OR the "NO" button in the game over menu
//              selectedY=maxy/2+175;
//            }
//            if(gameOver==false){
//              if(y>maxy/2-50 && y<maxy/2+20){ //if cursor is over the "RESUME" button
//                selectedY = maxy/2-50; //change selection overlay's co-ordinates to match the button
//              }
//              else if(y>maxy/2+25 && y<maxy/2+95){ //if cursor is over the "QUIT" button
//                selectedY = maxy/2+25;
//              }
//            }
//          }
//          
//        }        
//      }
//    }
//  } //end mouse motion listener
  public static class MyMouseMotionListener implements MouseMotionListener{
    public void mouseDragged(MouseEvent m){}
    
    public void mouseMoved (MouseEvent m){ //track mouse movement
      int x=m.getX(); //get x and y co-ordinates of the mouse
      int y=m.getY();
      
      if(paused){ //if the pause, main, or game over menu is showing
        if(x>maxx/2-150 && x<maxx/2+150){ //if the cursor is within a button's horizontal boundaries
          
          if(mainMenu){ //if the main menu screen is showing
            if(y>maxy/2 && y<maxy/2+70){
              selectedY=maxy/2;
            }
            else if(y>maxy/2+75 && y<maxy/2+145){
              selectedY=maxy/2+75;
            }
            else if(y>maxy/2+150 && y<maxy/2+220){
              selectedY=maxy/2+150;
            }
            else if(y>maxy/2+225 && y<maxy/2+295){
              selectedY=maxy/2+225;
            }
          }
          
          else if (!gameOver) { //if the pause menu is showing
            if(y>maxy/2+100 && y<maxy/2+170){ //if cursor is over the "RESTART" button in the pause menu OR the "YES" button in the game over menu
              selectedY=maxy/2+100; //change selection overlay's co-ordinates to match the button
            }
            else if(y>maxy/2+175 && y<maxy/2+245){ //if cursor is over the "HELP" button OR the "NO" button in the game over menu
              selectedY=maxy/2+175;
            }
            if(gameOver==false){
              if(y>maxy/2-50 && y<maxy/2+20){ //if cursor is over the "RESUME" button
                selectedY = maxy/2-50; //change selection overlay's co-ordinates to match the button
              }
              else if(y>maxy/2+25 && y<maxy/2+95){ //if cursor is over the "QUIT" button
                selectedY = maxy/2+25;
              }
            }
          }else { //if the game over screen is showing
            if (x > maxx / 2 - 150 && x < maxx / 2 + 150) {
              if (y > maxy / 2 + 143 && y < maxy / 2 + 213) {
                selectedY = maxy / 2 + 143;
              }else if (y > maxy / 2 + 218 && y < maxy / 2 + 288){
                selectedY = maxy / 2 + 218;
              }
            }
          }
        }        
      }
    }
  } //end mouse motion listener
  
  //edge of screen collision checking
  public static void collisionCheck(){
    
    if(player.posx<=20){
      player.posx=20;
    }
    if(player.posx>=maxx-20){
      player.posx=maxx-20;
    }
    if(player.posy<=20){
      player.posy=20;
    }
    if(player.posy>=maxy-100){
      player.posy=maxy-100;
    }
  }
  
  //Update background co-ordinates
  public static void backgroundUpdate(){
    backgroundy+=1;
    if(backgroundy>=1080){
      backgroundy=0;
    }
    
    background2y=backgroundy-1080;
  }
   
  //Spawn enemy
  public static void spawnEnemy() {
    for (int i = 0; i < 5; i++) {
      Enemy enemy = new Enemy();
      double random=Math.random()*5;
      enemy.sprite=(int)Math.round(random);
      
      enemy.x = (maxx/5 * i+maxx/10);
      enemy.y = -50;
      enemy.velx = 0;
      enemy.vely = 2;
      enemy.health = 10;
      
      enemy.marked = false;
      enemyPool.add (enemy);
      if (enemy.sprite % 2 == 0) {
        enemy.isRed = false;
      }else {
        enemy.isRed = true;
      }
      
    }
    enemyBulletTimeRate = 1000;
  }
  //Spawn Spiral enemy
  public static void spawnSpiralEnemy() {
    for (int i = 50; i < maxx-50; i+= 150) {
      SpiralEnemy enemy = new SpiralEnemy();
      enemy.x = i;
      enemy.y = -50;
      enemy.velx = 0;
      enemy.vely = 2;
      enemy.health = 10;
      enemy.marked = false;
      if (Math.random() > 0.5) {
        enemy.isRed = false;
        if (Math.random() < 0.3) {
          enemy.sprite = 0;
        }else if (Math.random() < 0.6) {
          enemy.sprite = 2;
        }else {
          enemy.sprite = 4;
        }
      }else {
        enemy.isRed = true;
        if (Math.random() < 0.3) {
          enemy.sprite = 1;
        }else if (Math.random() < 0.6) {
          enemy.sprite = 3;
        }else {
          enemy.sprite = 5;
        }
      }
      enemyPool.add (enemy);
      enemyBulletTimeRate = 100;
      
    }
  }
  
  //Spawn Boss
  public static void spawnBoss(){
    Boss boss = new Boss();
    boss.x = maxx/2;
    boss.y = 100;
    if (Math.random() > 0.5){
      boss.velx = -1;
    }else{
      boss.velx = 1;
    }
    enemyPool.add(boss);
  }
  
  //Move enemy
  public static void moveEnemy() {
    for (int i = 0; i < enemyPool.size(); i++) {
      (enemyPool.get (i)).y += (enemyPool.get (i)).vely;
    }
  }
  
  //Enemy shoot 
  public static void enemyShoot() {
    for (int i = 0; i < enemyPool.size(); i++) {
      if (enemyPool.get(i).spiral){
        enemyPool.get(i).spawnSpiralBullets();
      }else if (!enemyPool.get(i).boss){
        BasicBullet midBullet = new BasicBullet();
        midBullet.x = (enemyPool.get (i)).x;
        midBullet.y = (enemyPool.get (i)).y;
        midBullet.velocity = 1;
        midBullet.directionLR = 0;
        midBullet.directionUD = 2;
        if (Math.random() > 0.5) {
          midBullet.isRed = true;
        }else {
          midBullet.isRed = false;
        }
        BasicBullet leftBullet = new BasicBullet();
        leftBullet.x = (enemyPool.get (i)).x;
        leftBullet.y = (enemyPool.get (i)).y;
        leftBullet.velocity = 1;
        leftBullet.directionLR = -1;
        leftBullet.directionUD = 1;
        if (Math.random() > 0.5) {
          leftBullet.isRed = true;
        }else {
          leftBullet.isRed = false;
        }
        BasicBullet rightBullet = new BasicBullet();
        rightBullet.x = (enemyPool.get (i)).x;
        rightBullet.y = (enemyPool.get (i)).y;
        rightBullet.velocity = 1;
        rightBullet.directionLR = 1;
        rightBullet.directionUD = 1;
        if (Math.random() > 0.5) {
          rightBullet.isRed = true;
        }else {
          rightBullet.isRed = false;
        }
        BasicBullet rightBulletDown = new BasicBullet();
        rightBulletDown.x = (enemyPool.get (i)).x;
        rightBulletDown.y = (enemyPool.get (i)).y;
        rightBulletDown.velocity = 1;
        rightBulletDown.directionLR = 1;
        rightBulletDown.directionUD = 2;
        if (Math.random() > 0.5) {
          rightBulletDown.isRed = true;
        }else {
          rightBulletDown.isRed = false;
        }
        BasicBullet leftBulletDown = new BasicBullet();
        leftBulletDown.x = (enemyPool.get (i)).x;
        leftBulletDown.y = (enemyPool.get (i)).y;
        leftBulletDown.velocity = 1;
        leftBulletDown.directionLR = -1;
        leftBulletDown.directionUD = 2;
        if (Math.random() > 0.5) {
          leftBulletDown.isRed = true;
        }else {
          leftBulletDown.isRed = false;
        }
        enemyBulletPool.add (midBullet);
        enemyBulletPool.add (leftBullet);
        enemyBulletPool.add (rightBullet);
        enemyBulletPool.add (rightBulletDown);
        enemyBulletPool.add (leftBulletDown);
      }
    }
  }
  
    public static void spawnBombBullets(){
    for (int j = 0; j < enemyPool.size(); j++) {
      BombBullet bullet3 = new BombBullet();
      bullet3.x = enemyPool.get(j).x;
      bullet3.y = enemyPool.get(j).y;
      if (randomInteger() > 2) {
        bullet3.isRed = true;
      }else {
        bullet3.isRed = false;
      }
      enemyBulletPool.add(bullet3);
    }
    }
    
  public static void spawnBombBulletWave(){
    for (int j = 0; j < maxx; j += 150) {
      BombBullet bullet3 = new BombBullet();
      bullet3.x = j;
      if (randomInteger() > 2) {
        bullet3.isRed = true;
      }else {
        bullet3.isRed = false;
      }
      wavePool.add(bullet3);
    }
  }
  public static void spawnZigzagBulletWave(){
    for (int j = 30 ; j < maxx ; j+= 50){
      ZigzagBullet bullet2 = new ZigzagBullet();
          
      bullet2.velocity = 1;
      bullet2.x = j;
      bullet2.startx = j;
      
      if(Math.random()>0.5){
        bullet2.isRed=false;
      }else{
        bullet2.isRed=true;
      }
      
      
      enemyBulletPool.add(bullet2);
      
    }
  }
 
  //picks a random number from 0 to 5
  public static int randomInteger(){
    double random=Math.random()*5;
    return (int)random;
  }
  
  //Check enemy
  public static void checkEnemy() {
    for (int i = 0; i < enemyPool.size(); i++) {
      for (int j = 0; j < pool.size(); j++) {
        if (enemyPool.get(i).bulletEnemy((pool.get (j)).x, (pool.get (j)).y, enemyPool.get(i).boss) && enemyPool.get(i).boss) {
          (enemyPool.get (i)).health --;
          pool.remove (j);
          if ((enemyPool.get (i)).health == 0) {
            //Assign Powerup to fallen boss
            if (Math.round(Math.random() * 10) == 1) { //if random number out of 10 is 1 == shield
              for (int a = -100 ; a < 100 ; a += 40){
                Powerups powerup = new Powerups();
                powerup.x = enemyPool.get(i).x + a;
                powerup.y = enemyPool.get(i).y;
                powerup.sprite = 1;
                powerupsPool.add(powerup);
                powerup.velx = 0.5;
                powerup.vely = 0;
              }
              
            }else if (Math.round(Math.random()*10) == 2 ){ // if random number out of 10 is 2 == speed
              for (int a = -100 ; a < 100 ; a += 40){
                Powerups powerup = new Powerups();
                powerup.x = enemyPool.get(i).x + a;
                powerup.y = enemyPool.get(i).y;
                powerup.sprite = 2;
                powerupsPool.add(powerup);
                powerup.velx = 0.5;
                powerup.vely = 0;
              }
              
            }else if (Math.round(Math.random()*10) == 3 ){ // if random number out of 10 is 3 == health
              for (int a = -100 ; a < 100 ; a += 40){
                Powerups powerup = new Powerups();
                powerup.x = enemyPool.get(i).x + a;
                powerup.y = enemyPool.get(i).y;
                powerup.sprite = 3;
                powerupsPool.add(powerup);
                powerup.velx = 0.5;
                powerup.vely = 0;
              }
            }else if (Math.round(Math.random() * 10) == 4) { //if random number out of 10 is 4 == trishot
              for (int a = -100 ; a < 100 ; a += 40){
                Powerups powerup = new Powerups();
                powerup.x = enemyPool.get(i).x + a;
                powerup.y = enemyPool.get(i).y;
                powerup.sprite = 4;
                powerupsPool.add(powerup);
                powerup.velx = 0.5;
                powerup.vely = 0;
              }
              
            }else { //if random number out of 10 is 5,6,7,8,9 or 10 == shekel
              for (int a = -100 ; a < 100 ; a += 40){
                Powerups coin = new Powerups();
                coin.x = enemyPool.get(i).x + a;
                coin.y = enemyPool.get(i).y;
                coin.sprite = 5;
                powerupsPool.add(coin);
                coin.velx = 0.5;
                coin.vely = 0;
              }
            }
            (enemyPool.get (i)).marked = true;
          }
        }else if (enemyPool.get (i).bulletEnemy ((pool.get (j)).x, (pool.get (j)).y, enemyPool.get(i).boss) && enemyPool.get(i).isRed != pool.get(j).isRed && enemyPool.get(i).y >= 70) {
          (enemyPool.get (i)).health --;
          pool.remove (j);
          if ((enemyPool.get (i)).health == 0) {
            //Assign Powerup to fallen enemy
            if (Math.round(Math.random() * 10) == 1) { //if random number out of 10 is 1 == shield
              Powerups powerup = new Powerups();
              powerup.x = enemyPool.get(i).x;
              powerup.y = enemyPool.get(i).y;
              powerup.sprite = 1;
              powerupsPool.add(powerup);
              powerup.velx = 0.5;
              powerup.vely = 0;
              
            }else if (Math.round(Math.random()*10) == 2 ){ // if random number out of 10 is 2 == speed
              Powerups powerup = new Powerups();
              powerup.x = enemyPool.get(i).x;
              powerup.y = enemyPool.get(i).y;
              powerup.sprite = 2;
              powerupsPool.add(powerup);
              powerup.velx = 0.5;
              powerup.vely = 0;
              
            }else if (Math.round(Math.random()*10) == 3 ){ // if random number out of 10 is 3 == health
              Powerups powerup = new Powerups();
              powerup.x = enemyPool.get(i).x;
              powerup.y = enemyPool.get(i).y;
              powerup.sprite = 3;
              powerupsPool.add(powerup);
              powerup.velx = 0.5;
              powerup.vely = 0;
              
            }else if (Math.round(Math.random() * 10) == 4) { //if random number out of 10 is 4 == trishot
              Powerups powerup = new Powerups();
              powerup.x = enemyPool.get(i).x;
              powerup.y = enemyPool.get(i).y;
              powerup.sprite = 4;
              powerupsPool.add(powerup);
              powerup.velx = 0.5;
              powerup.vely = 0;
              
            }else { //if random number out of 10 is 5,6,7,8,9 or 10
              Powerups coin = new Powerups();
              coin.x = enemyPool.get(i).x;
              coin.y = enemyPool.get(i).y;
              coin.sprite = 5;
              powerupsPool.add(coin);
              coin.velx = 0.5;
              coin.vely = 0;
            }
            (enemyPool.get (i)).marked = true;
          }
        }
      }
    }
    for (int i = enemyPool.size() - 1; i >= 0; i--) {
      if ((enemyPool.get (i)).marked) {
        enemyPool.remove (i);
      }
    }
  }
  
  
} //Main class end

//player class
class Player{
  static int posx;
  static int posy;
  static double velx;
  static double vely;
  static int health;
  static int shekels;
  static boolean isRed;
  static int shieldTimer;
  static int speedTimer;
  static int trishotTimer;
  static int gameOverAnimationTimer;
}

//Powerups
class Powerups{
  
  //Variables
  int x;
  double y;
  int sprite;
  double velx;
  double vely;
  
  //wall collision check
  boolean hitWall() {
    if (y > BasicMovement.maxy - 100) {
      return true;
    }else {
      return false;
    }
  }
  
  //player hit check
  boolean pickupCheck() {
    if (Player.posx >= x - 20 && Player.posx <= x + 20 && Player.posy >= y - 20 & Player.posy <= y + 20) {
      return true;
    }else {
      return false;
    }
  }
  
  
}

//Bullet class
class Bullet {
  
  //Variables
  int x;
  int y;
  int sprite;
  boolean isRed;
    
  //Collision check 
  boolean bulletWall () {
    if (y >= BasicMovement.maxy || y < -200) {
      return true;
    }else{
      return false;
    }
  }
  
} //Bullet end

//Enemy class
class Enemy {
  
  //Variables
  int x;
  int y;
  int health;
  int velx;
  int vely;
  int sprite;
  boolean marked;
  boolean spiral = false;
  boolean boss = false;
  boolean isRed;
    
  //Bullet check
  boolean bulletEnemy (int bulletx, int bullety, boolean boss) {
    if (!boss) {
      if (bulletx >= (x - 20) && bulletx <= (x + 20) && bullety >= (y - 20) && bullety <= (y + 20)) {
        return true;
      }else{
        return false;
      }
    }else {
      if (bulletx >= (x - 200) && bulletx <= (x + 200) && bullety >= (y - 100) && bullety <= (y + 100)) {
        return true;
      }else{
        return false;
      }
    }
  }
  
  //ignore this, i need it in order to work
  void spawnSpiralBullets(){
  }
  void spawnLaser(){
  }
  void moveBoss(){
  }
} //Enemy end

class Boss extends Enemy{
  {
    health = 30;
    boss = true;
  }
  void spawnLaser(){
    for (int i = x-50 ; i < x+50 ; i += 10){
      for (int j = y ; j < BasicMovement.maxy-25 ; j += 10){
        BasicBullet bullet1 = new BasicBullet(); //bullet up straight
        if(Math.random() > 0.5){
          bullet1.isRed=false;
        }else{
          bullet1.isRed=true;
        }
        bullet1.x = i;
        bullet1.y = j;
        bullet1.directionLR = 0;
        bullet1.directionUD = 1;
        bullet1.velocity = 1;
        BasicMovement.enemyBulletPool.add(bullet1);
      }
    }
  }
  void moveBoss(){
    if (x-50 < 0 || x+50 > BasicMovement.maxx){
      velx = -velx;
    }
    x += velx;
  }
}

class SpiralEnemy extends Enemy{
  {
  spiral = true;
  }
  int count = 0;
  boolean go = true;
  void spawnSpiralBullets(){
    go = true;
    if (count == 0 && go){
      BasicBullet bullet1 = new BasicBullet(); //bullet up straight
      if(Math.random() > 0.5){
        bullet1.isRed=false;
      }else{
        bullet1.isRed=true;
      }
      bullet1.x = x;
      bullet1.y = y - 10;
      bullet1.directionLR = 0;
      bullet1.directionUD = -1;
      bullet1.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet1);
      count++;
      go = false;
    }else if (count == 1 && go){
      BasicBullet bullet6 = new BasicBullet(); //bullet up up left
      if(Math.random() > 0.5){
        bullet6.isRed=false;
      }else{
        bullet6.isRed=true;
      }
      bullet6.x = x;
      bullet6.y = y - 10;
      bullet6.directionLR = -1;
      bullet6.directionUD = -2;
      bullet6.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet6);
      count++;
      go = false;
    }else if (count == 2 && go){
      BasicBullet bullet6 = new BasicBullet(); //bullet up left
      if(Math.random() > 0.5){
        bullet6.isRed=false;
      }else{
        bullet6.isRed=true;
      }
      bullet6.x = x;
      bullet6.y = y - 10;
      bullet6.directionLR = -1;
      bullet6.directionUD = -1;
      bullet6.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet6);
      count++;
      go = false;
    }else if (count == 3 && go){
      BasicBullet bullet6 = new BasicBullet(); //bullet up left left
      if(Math.random() > 0.5){
        bullet6.isRed=false;
      }else{
        bullet6.isRed=true;
      }
      bullet6.x = x;
      bullet6.y = y - 10;
      bullet6.directionLR = -2;
      bullet6.directionUD = -1;
      bullet6.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet6);
      count++;
      go = false;
    }else if (count == 4 && go){
      BasicBullet bullet4 = new BasicBullet(); //bullet left straight
      if(Math.random() > 0.5){
        bullet4.isRed=false;
      }else{
        bullet4.isRed=true;
      }
      bullet4.x = x - 10;
      bullet4.y = y;
      bullet4.directionLR = -1;
      bullet4.directionUD = 0;
      bullet4.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet4);
      count++;
      go = false;
    }else if (count == 5 && go){
      BasicBullet bullet8 = new BasicBullet(); //bullet down left left
      if(Math.random() > 0.5){
        bullet8.isRed=false;
      }else{
        bullet8.isRed=true;
      }
      bullet8.x = x;
      bullet8.y = y + 10;
      bullet8.directionLR = -2;
      bullet8.directionUD = 1;
      bullet8.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet8);
      count++;
      go = false;
    }else if (count == 6 && go){
      BasicBullet bullet8 = new BasicBullet(); //bullet down left
      if(Math.random() > 0.5){
        bullet8.isRed=false;
      }else{
        bullet8.isRed=true;
      }
      bullet8.x = x;
      bullet8.y = y + 10;
      bullet8.directionLR = -1;
      bullet8.directionUD = 1;
      bullet8.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet8);
      count++;
      go = false;
    }else if (count == 7 && go){
      BasicBullet bullet8 = new BasicBullet(); //bullet down down left
      if(Math.random() > 0.5){
        bullet8.isRed=false;
      }else{
        bullet8.isRed=true;
      }
      bullet8.x = x;
      bullet8.y = y + 10;
      bullet8.directionLR = -1;
      bullet8.directionUD = 2;
      bullet8.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet8);
      count++;
      go = false;
    }else if (count == 8 && go){
      BasicBullet bullet2 = new BasicBullet(); //bullet down straight
      if(Math.random() > 0.5){
        bullet2.isRed=false;
      }else{
        bullet2.isRed=true;
      }
      bullet2.x = x;
      bullet2.y = y + 10;
      bullet2.directionLR = 0;
      bullet2.directionUD = 1;
      bullet2.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet2);
      count++;
      go = false;
    }else if (count == 9 && go){
      BasicBullet bullet7 = new BasicBullet(); //bullet down down right
      if(Math.random() > 0.5){
        bullet7.isRed=false;
      }else{
        bullet7.isRed=true;
      }
      bullet7.x = x;
      bullet7.y = y + 10;
      bullet7.directionLR = 1;
      bullet7.directionUD = 2;
      bullet7.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet7);
      count++;
      go = false;
    }else if (count == 10 && go){
      BasicBullet bullet7 = new BasicBullet(); //bullet down right
      if(Math.random() > 0.5){
        bullet7.isRed=false;
      }else{
        bullet7.isRed=true;
      }
      bullet7.x = x;
      bullet7.y = y + 10;
      bullet7.directionLR = 1;
      bullet7.directionUD = 1;
      bullet7.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet7);
      count++;
      go = false;
    }else if (count == 11 && go){
      BasicBullet bullet7 = new BasicBullet(); //bullet down right right
      if(Math.random() > 0.5){
        bullet7.isRed=false;
      }else{
        bullet7.isRed=true;
      }
      bullet7.x = x;
      bullet7.y = y + 10;
      bullet7.directionLR = 2;
      bullet7.directionUD = 1;
      bullet7.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet7);
      count++;
      go = false;
    }else if (count == 12 && go){
      BasicBullet bullet3 = new BasicBullet(); //bullet right straight
      if(Math.random() > 0.5){
        bullet3.isRed=false;
      }else{
        bullet3.isRed=true;
      }
      bullet3.x = x + 10;
      bullet3.y = y;
      bullet3.directionLR = 1;
      bullet3.directionUD = 0;
      bullet3.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet3);
      count++;
      go = false;
    }else if (count == 13 && go){
      BasicBullet bullet5 = new BasicBullet(); //bullet up right right
      if(Math.random() > 0.5){
        bullet5.isRed=false;
      }else{
        bullet5.isRed=true;
      }
      bullet5.x = x;
      bullet5.y = y - 10;
      bullet5.directionLR = 2;
      bullet5.directionUD = -1;
      bullet5.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet5);
      count++;
      go = false;
    }else if (count == 14 && go){
      BasicBullet bullet5 = new BasicBullet(); //bullet up right
      if(Math.random() > 0.5){
        bullet5.isRed=false;
      }else{
        bullet5.isRed=true;
      }
      bullet5.x = x;
      bullet5.y = y - 10;
      bullet5.directionLR = 1;
      bullet5.directionUD = -1;
      bullet5.velocity = 2;
      BasicMovement.enemyBulletPool.add(bullet5);
      count++;
      go = false;
    }else if (count == 15 && go){
      BasicBullet bullet5 = new BasicBullet(); //bullet up up right
      if(Math.random() > 0.5){
        bullet5.isRed=false;
      }else{
        bullet5.isRed=true;
      }
      bullet5.x = x;
      bullet5.y = y - 10;
      bullet5.directionLR = 1;
      bullet5.directionUD = -2;
      bullet5.velocity = 1;
      BasicMovement.enemyBulletPool.add(bullet5);
      count = 0;
      go = false;
    }
  }
}

class BasicBullet{ //the basic bullet, goes straight slowly
  int x = 0;
  int y = 0;
  int sprite;
  int velocity = 2;
  int directionLR = 0; //-1 left, 1 right
  int directionUD = 1; //-1 up, 1 down
  boolean isRed;
  boolean bomb = false;
  
  void moveBullet(){
    x += velocity * directionLR;
    y += velocity * directionUD;
  }
  
  boolean bulletWall(int x, int y){
    if (x > BasicMovement.maxx || x < 0 || y > BasicMovement.maxy || y < -200){
      return true;
    }else{
      return false;
    }
  }
    //Player check
  boolean playerEnemyBullet (int playerx, int playery) {
    if (x >= (playerx - 10) && x <= (playerx + 10) && y >= (playery - 10) && y <= (playery + 10)) {
      return true;
    }else{
      return false;
    }
  }
}

class ZigzagBullet extends BasicBullet{ //shoot in zig-zags
  int sprite;
  
  int startx = 0;
  int starty = 0;
  {
    velocity = 2;
    directionLR = 1; //-1 left, 1 right
    directionUD = 1; //-1 up, 1 down
  } 
  
  void moveBullet(){
    if (x > startx + 25){
      directionLR = -1;
    }else if (x < startx - 25){
      directionLR = 1;
    }
    x += velocity * directionLR;
    y += velocity * directionUD;
  }
}

class BombBullet extends BasicBullet{
  {
    velocity = 1;
    directionLR = 0;
    directionUD = 1;
    bomb = true;
  }
  
  void moveBullet(){
    if (y == BasicMovement.maxy/2){
      BasicBullet bullet1 = new BasicBullet(); //bullet up straight
      bullet1.x = x;
      bullet1.y = y - 10;
      bullet1.directionLR = 0;
      bullet1.directionUD = -1;
      bullet1.velocity = 2;
      if (Math.random() > 0.5) {
        bullet1.isRed = true;
      }else {
        bullet1.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet1);
      BasicBullet bullet2 = new BasicBullet(); //bullet down straight
      bullet2.x = x;
      bullet2.y = y + 10;
      bullet2.directionLR = 0;
      bullet2.directionUD = 1;
      bullet2.velocity = 2;
      if (Math.random() > 0.5) {
        bullet2.isRed = true;
      }else {
        bullet2.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet2);
      BasicBullet bullet3 = new BasicBullet(); //bullet right straight
      bullet3.x = x + 10;
      bullet3.y = y;
      bullet3.directionLR = 1;
      bullet3.directionUD = 0;
      bullet3.velocity = 2;
      if (Math.random() > 0.5) {
        bullet3.isRed = true;
      }else {
        bullet3.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet3);
      BasicBullet bullet4 = new BasicBullet(); //bullet left straight
      bullet4.x = x - 10;
      bullet4.y = y;
      bullet4.directionLR = -1;
      bullet4.directionUD = 0;
      bullet4.velocity = 2;
      if (Math.random() > 0.5) {
        bullet4.isRed = true;
      }else {
        bullet4.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet4);
      BasicBullet bullet5 = new BasicBullet(); //bullet up right
      bullet5.x = x;
      bullet5.y = y - 10;
      bullet5.directionLR = 1;
      bullet5.directionUD = -1;
      bullet5.velocity = 2;
      if (Math.random() > 0.5) {
        bullet5.isRed = true;
      }else {
        bullet5.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet5);
      BasicBullet bullet6 = new BasicBullet(); //bullet up left
      bullet6.x = x;
      bullet6.y = y - 10;
      bullet6.directionLR = -1;
      bullet6.directionUD = -1;
      bullet6.velocity = 2;
      if (Math.random() > 0.5) {
        bullet6.isRed = true;
      }else {
        bullet6.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet6);
      BasicBullet bullet7 = new BasicBullet(); //bullet down right
      bullet7.x = x;
      bullet7.y = y + 10;
      bullet7.directionLR = 1;
      bullet7.directionUD = 1;
      bullet7.velocity = 2;
      if (Math.random() > 0.5) {
        bullet7.isRed = true;
      }else {
        bullet7.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet7);
      BasicBullet bullet8 = new BasicBullet(); //bullet down left
      bullet8.x = x;
      bullet8.y = y + 10;
      bullet8.directionLR = -1;
      bullet8.directionUD = 1;
      bullet8.velocity = 2;
      if (Math.random() > 0.5) {
        bullet8.isRed = true;
      }else {
        bullet8.isRed = false;
      }
      BasicMovement.enemyBulletPool.add(bullet8);
      
    }
    x += velocity * directionLR;
    y += velocity * directionUD;
  }
}
