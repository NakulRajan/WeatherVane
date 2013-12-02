import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.json.simple.parser.ParseException;


public class Framework extends Canvas {
 
    public static int frameWidth;
    public static int frameHeight;
    public static final long secInNanosec = 1000000000L;
    public static final long milisecInNanosec = 1000000L;
    final int GAME_FPS = 60;
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED, Weather_Disp}
    public static GameState gameState;
    private long gameTime;
    private long lastTime;
    private Game game;
	private BufferedImage weatherVaneMenuImg;
	private Image weatherDispImg;
	private Weather wthr;
    private WeatherData	wd;
    
    public Framework ()
    {
        super();
        
        gameState = GameState.VISUALIZING;
        
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    
    
 
    private void Initialize()
    {
    	try {
			wthr = new Weather(1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
   
    private void LoadContent()
    {
    	 try
         {
             URL weatherVaneUrl = this.getClass().getResource("resources/images/WeatherVaneEdit.jpg");
             weatherVaneMenuImg = ImageIO.read(weatherVaneUrl);
             
             URL weatherDispUrl = this.getClass().getResource("resources/images/weatherDisp.png");
             weatherDispImg = ImageIO.read(weatherDispUrl);
         }
         catch (IOException ex) {
             Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    
    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated and then the game is drawn on the screen.
     */
    private void GameLoop()
    {
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                    //...
                break;
                case MAIN_MENU:
                    //...
                break;
                case OPTIONS:
                    //...
                break;
                case GAME_CONTENT_LOADING:
                    //...
                break;
                case STARTING:
                    Initialize();
                    LoadContent();
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();
                        gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
            // Repaint the screen.
            repaint();
            
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    

    @Override
    public void Draw(Graphics2D g2d)
    {
        switch (gameState)
        {
            case PLAYING:
                game.Draw(g2d, mousePosition());
            break;
            case GAMEOVER:
                //...
            break;
            case MAIN_MENU:
            	 g2d.drawImage(weatherVaneMenuImg, 0, 0, frameWidth, frameHeight, null);            break;
            case OPTIONS:
                //...
            break;
            case GAME_CONTENT_LOADING:
                //...
            break;
            case Weather_Disp:
			try {
				weatherDisplay(g2d);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
          break;
        }
    }
    
    private void weatherDisplay(Graphics2D g2d) throws FileNotFoundException, IOException, ParseException{
    	g2d.drawImage(weatherDispImg, 0, 0, frameWidth, frameHeight, null);
        g2d.setColor(Color.white);
        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        int fontSize = (int)Math.round(22.0 * screenRes / 72.0);
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);
        
         
        wd = wthr.getweatherData(Game.cubeId-1);
        
        g2d.drawString("Sq No:" + wd.getSquareId() , frameWidth / 2 - 145, frameHeight/2 - 200);
        g2d.drawString("Temprature:" + wd.getTemp(), frameWidth / 2 - 145, frameHeight/2 - 140);
        g2d.drawString("Humidity:" + wd.getHumidity(), frameWidth / 2 - 145, frameHeight / 2 - 100);
        g2d.drawString("Wind Velocity: " + wd.getSpeed() + " mph", frameWidth / 2 - 145, frameHeight / 2 - 60);
        g2d.drawString("Wind Direction:" + wd.getDirection(), frameWidth / 2 - 145, frameHeight / 2 - 20);
    }

    private void newGame()
    {
        gameTime = 0;
        lastTime = System.nanoTime();
        game = new Game();
    }
    
 
    private void restartGame()
    {
        gameTime = 0;
        lastTime = System.nanoTime();
        game.RestartGame();
        gameState = GameState.PLAYING;
    }
    
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    
    
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
 	
    }
    
    public void keyPressedFramework(KeyEvent e){
    	
    	 if(Canvas.keyboardKeyState(KeyEvent.VK_S)){
    		   switch (gameState)
    	       {
    	       case MAIN_MENU:
    	           newGame();
    	       break;
    	       }  
         }
    		
    	 if(Canvas.keyboardKeyState(KeyEvent.VK_ESCAPE)){
    		 Framework.gameState = Framework.GameState.MAIN_MENU;
         }
    	 
    	 if(Canvas.keyboardKeyState(KeyEvent.VK_M)){
    		 Framework.gameState = Framework.GameState.PLAYING;
         }
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
    
    }
}
