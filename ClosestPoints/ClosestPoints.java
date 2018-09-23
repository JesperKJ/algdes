import java.util.*;
import java.math.*;
import java.util.regex.Pattern;

class Point {
	String name;
	double x;
	double y;
	
	public Point(String name, double x,double y) {
		this.name = name;
		this.x = x;
		this.y = y;
		
	}
	
	public static void printPoints(ArrayList<Point> p){
		for (int i = 0; i < p.size(); i++)
			System.out.println(p.get(i).name +  ": (x" + i + " , y" + i + ") = (" + p.get(i).x + " , " + p.get(i).y + ")");
		//for(Point str: points){
		//	System.out.println(str.x + " - " + str.y);
		//}
	}
	
	//implements Cloneable {
		//String name;
		//double x;
		//double y;
	
	//public Point clone() throws CloneNotSupportedException {
		//return (Point) super.clone();
//		Point clonedObj = (Point) super.clone();
		//return clonedObj;
	//}
	//};
	
	public static Comparator<Point> xcomparator= new Comparator<Point>() {
		public int compare (Point s1, Point s2) {
			double s1x = s1.x;
			double s2x = s2.x;
			return Double.compare(s1x,s2x);
		}	
	};
	
	public static Comparator<Point> ycomparator= new Comparator<Point>() {
		public int compare (Point s1, Point s2) {
			double s1y = s1.y;
			double s2y = s2.y;
			return Double.compare(s1y,s2y);
		}
	};
}

public class ClosestPoints {
// Opret klasse (points) med x og y variabel til koordinater.
	//indlæs dette i en arrayList.

//afstand mellem to punkter
	private static double distance(Point a,Point b) {
		 return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y,2)); 
	}
	
	//Returnere nærmeste punkter i liste
	public static void shortest(ArrayList<Point> px){
		int shorta = 0;
		int shortb = 1;
		double shortd = distance(px.get(0),px.get(1));
		for (int i = 0; i < px.size() - 1; i++) {
		//	for (int j = 0; j < Distance.length; j++)
			//shortd = distance(points.get(i),points.get(i+1));
			if (distance(px.get(i),px.get(i+1)) < shortd) {
				shortd = distance(px.get(i),px.get(i+1));
				shorta = i;
				shortb = i+1;
			}
    	}
		System.out.println(px.get(shorta).name + " + " + shortd + " + " + px.get(shortb).name);
	}
	
	public static double shortestPoint(ArrayList<Point> px){
		int shorta = 0;
		int shortb = 1;
		double shortd = distance(px.get(0),px.get(1));
		for (int i = 0; i < px.size() - 1; i++) {
		//	for (int j = 0; j < Distance.length; j++)
			//shortd = distance(points.get(i),points.get(i+1));
			if (distance(px.get(i),px.get(i+1)) < shortd) {
				shortd = distance(px.get(i),px.get(i+1));
				shorta = i;
				shortb = i+1;
				//ArrayList<Point> shortPoint = px.get(i); 
			}
    	}
		return shortd; //shortPoint;
	}
	
	public static ArrayList shortestPointFifteen(ArrayList<Point> px, int b){
		double shortd = distance(px.get(0),px.get(1));
		ArrayList<Point> shortPoint = new ArrayList<>();
		shortPoint.add(px.get(0));
		shortPoint.add(px.get(1));
		for (int i = 0; i < px.size() - 1; i++) {
			for (int j = i + 1; j < px.size(); j++) { 
		//	for (int j = 0; j < Distance.length; j++)
			//shortd = distance(points.get(i),points.get(i+1));
			if (distance(px.get(i),px.get(j)) < shortd & i != j) {
				shortd = distance(px.get(i),px.get(j));
				shortPoint.set(0, px.get(i));
				shortPoint.set(1, px.get(j));
			}
			if (j == i + b)
				break;
			}
    	}
		return shortPoint; //shortPoint;
	}
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		ArrayList<Point> points = new ArrayList<>();
		// reading data from file
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			int i = 0;
			//Pattern pattern = Pattern.compile("n=\\d*");
			final String lineFromFile  = scan.nextLine();
			points.add(new Point(lineFromFile.split("\\s+")[0],
					Double.parseDouble(lineFromFile.split("\\s+")[1]),
					Double.parseDouble(lineFromFile.split("\\s+")[2])));
			i++;
		}

// Sortering:
ArrayList pointsx = (ArrayList) points.clone();
ArrayList pointsy = (ArrayList) points.clone();


Collections.sort(pointsy , Point.ycomparator);
Collections.sort(pointsx , Point.xcomparator);
//Point.printPoints(pointsy);
//Point.printPoints(pointsx);
shortest(points);

if (pointsx.size() <= 3) {
	Point.printPoints(shortestPointFifteen(points,points.size()));
}
else {
//while (pointsx.size() <= 3) {
	ArrayList<Point> listQx = new ArrayList<Point>(pointsx.subList(0, (pointsx.size()/2)));
	ArrayList<Point> listRx = new ArrayList<Point>(pointsx.subList((pointsx.size()/2),pointsx.size()));
	ArrayList<Point> listQy = new ArrayList<Point>(pointsy.subList(0, pointsy.size()/2));
	ArrayList<Point> listRy = new ArrayList<Point>(pointsy.subList((pointsy.size()/2),pointsy.size()));
	double d = Math.min(shortestPoint(listQx),shortestPoint(listRx));
	double xstar = listQx.get(listQx.size()-1).x;
	
	
	ArrayList<Point> listS = new ArrayList<Point>();
	for (int i = 0; i < points.size();i++) {
		if (points.get(i).x >= xstar - d & points.get(i).x <= xstar + d )
			listS.add(points.get(i));
	}
	//Point.printPoints(listS);
	Point.printPoints(shortestPointFifteen(listS,15));
	
//}	
}

long stopTime = System.nanoTime();
//System.out.println(stopTime - startTime);
	}
}

