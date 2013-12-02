import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


class WeatherData{

	private long squareId;
	private long temp;
	private String humidity;
	private String direction;
	private long speed;

	public long getSquareId() {
		return squareId;
	}

	public void setSquareId(long squareId) {
		this.squareId = squareId;
	}

	public long getTemp() {
		return temp;
	}

	public void setTemp(long temp) {
		this.temp = temp;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public WeatherData(long sqId, long temp, String humidity, 
			String direction, long speed){
		this.squareId = sqId;
		this.temp = temp;
		this.humidity = humidity;
		this.direction = direction;
		this.speed = speed;
	}
}