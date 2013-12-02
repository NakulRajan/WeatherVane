import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Game {

	private int[][] squares;
	public static int cubeId;
	
	private BufferedImage backgroundImg;


	public Game()
	{
		Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;

		Thread threadForInitGame = new Thread() {
			@Override
			public void run(){
				Initialize();
				LoadContent();

				Framework.gameState = Framework.GameState.PLAYING;
			}
		};
		threadForInitGame.start();
	}


	private void Initialize()
	{	
		squares = new int [5][5];
		
		int i,j,count=1;
		for(i=0; i<5; i++){
			for(j=0; j<5; j++){
				squares[i][j] = count++;
			}
		}
	}

	private void LoadContent()
	{
		try{
			URL backgroundImgUrl = this.getClass().getResource("resources/images/Numbers.jpg");
			backgroundImg = ImageIO.read(backgroundImgUrl);
		}
		catch (IOException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}    


	public void RestartGame()
	{

	}

	public int getSquares(int x, int y){
		return squares[x][y];	
	}


	public void UpdateGame(long gameTime, Point mousePosition)
	{

		if(Canvas.mouseButtonState(MouseEvent.BUTTON1))
		{
			int x = (int)Math.ceil(mousePosition.getX()/80.0);
			int y = (int)Math.ceil(mousePosition.getY()/120.0);
					
			Game.cubeId = getSquares(y-1,x-1);
					
			if(Framework.gameState ==  Framework.GameState.PLAYING)
				Framework.gameState = Framework.GameState.Weather_Disp;
		}
	}


	public void Draw(Graphics2D g2d, Point mousePosition)
	{
		g2d.drawImage(backgroundImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

	}
}
