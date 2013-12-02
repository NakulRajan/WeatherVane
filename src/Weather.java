import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Weather {
	static ArrayList<WeatherData> wdList = new ArrayList<WeatherData>();


	public Weather(int choice) throws FileNotFoundException, IOException, ParseException{
		if(choice == 1){
			Weather.parseData("/Users/nakulrajan/Documents/workspace/eclipse/WeatherVane/src/sample-json.txt");
		}
		else{
			Weather.parseData("/Users/nakulrajan/Documents/workspace/eclipse/WeatherVane/src/sample-json.txt");
		}

	}


	public WeatherData getweatherData(int i){
		return wdList.get(i);
	}


	public static void parseData(String str) throws FileNotFoundException, IOException, ParseException{

		JSONParser parser = new JSONParser();
		JSONArray obj = (JSONArray) parser.parse(new FileReader(str));

		for(Object o: obj ){
			JSONObject jsonWeatherObj = (JSONObject) o;
			long squareId = (Long) jsonWeatherObj.get("sqid");
			long temp = (Long) jsonWeatherObj.get("temp");
			String humdty = (String) jsonWeatherObj.get("humidity"); 
			String direction = (String) jsonWeatherObj.get("direction");
			long speed = (Long) jsonWeatherObj.get("speed");
			wdList.add(new WeatherData(squareId,temp,
					humdty,direction,speed));

		}
	}
}

