import java.util.*;
import java.util.regex.Pattern;

public class ClosestPoints {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// reading data from file
		// 
		Scanner scan = new Scanner(System.in);
		
		List<String> Names = new ArrayList<String>();
		List<Double> Coordinates = new ArrayList<Double>();
		int i = 0;
		while (scan.hasNext()) {
			
			//Pattern pattern = Pattern.compile("n=\\d*");
			final String lineFromFile  = scan.nextLine();
	        //String str = scan.findInLine(pattern);
			Names.add(lineFromFile.split("\\s+")[0]);
			//Coordinates.addAll(Double.parseDouble(lineFromFile.split("\\s+")[1-2]));
			i++;
		}
		System.out.println(Names);
		
	}

}
