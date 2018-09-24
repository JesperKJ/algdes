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
	}
	
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
		for (int j = i + 1; j < px.size(); j++) {
			if (distance(px.get(i),px.get(j)) < shortd) {
				shortd = distance(px.get(i),px.get(j));
				shorta = i;
				shortb = j;
			}
    	}
		}
		System.out.println(px.get(shorta).name + " + " + shortd + " + " + px.get(shortb).name);
	}
	
	public static double shortestPoint(ArrayList<Point> px){
		int shorta = 0;
		int shortb = 1;
		double shortd = distance(px.get(0),px.get(1));
		for (int i = 0; i < px.size() - 1; i++) {
			for (int j = i + 1; j < px.size(); j++) {
			if (distance(px.get(i),px.get(j)) < shortd) {
				shortd = distance(px.get(i),px.get(j));
				shorta = i;
				shortb = i+1;
				//ArrayList<Point> shortPoint = px.get(i); 
			}
    	}
		}
		return shortd; //shortPoint;
	}
	
	public static ArrayList<Point> shortestPointFifteen(ArrayList<Point> px, int b){
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
	
	
	public static ArrayList<Point> closestPairRec(ArrayList<Point> Px,ArrayList<Point> Py) {

		if (Px.size() <= 3) {
			return shortestPointFifteen(Px,Px.size());
		}
		else {
			ArrayList<Point> Qx = new ArrayList<Point>(Px.subList(0, (Px.size()/2)));
			ArrayList<Point> Rx = new ArrayList<Point>(Px.subList((Px.size()/2),Px.size()));
			ArrayList<Point> Qy = new ArrayList<Point>(Px.subList(0, (Px.size()/2)));
			ArrayList<Point> Ry = new ArrayList<Point>(Px.subList((Px.size()/2),Px.size()));
			
			Collections.sort(Qy , Point.ycomparator);
			Collections.sort(Ry , Point.ycomparator);
			
			double d = Math.min(shortestPoint(closestPairRec(Qx,Qy)),
					shortestPoint(closestPairRec(Rx,Ry)));
			double xstar = Qx.get(Qx.size()-1).x;
						
			ArrayList<Point> S = new ArrayList<Point>();
			for (int i = 0; i < Py.size();i++) {
				if (Py.get(i).x >= xstar - d & Py.get(i).x <= xstar + d )
					S.add(Py.get(i));
			}
			//Point.printPoints(listS);
			return (shortestPointFifteen(S,15));
		}
		}
	
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		ArrayList<Point> points = new ArrayList<>();
		// reading data from file
		Scanner scan = new Scanner(System.in);
		int i = 0;
		int s = 99;
		while (scan.hasNext()) {
			final String lineFromFile  = scan.nextLine().trim();
			if (lineFromFile.equalsIgnoreCase("NODE_COORD_SECTION"))
			{s = i;
			System.out.println(s);
			}
			System.out.println(lineFromFile.split("\\s+")[1]);
			if (i > s + 2) {
			points.add(new Point(lineFromFile.split("\\s+")[0],
					Double.parseDouble(lineFromFile.split("\\s+")[1]),
					Double.parseDouble(lineFromFile.split("\\s+")[2])));
			}
			i++;
		}

// Sortering:
ArrayList<Point> pointsx = (ArrayList<Point>) points.clone();
ArrayList<Point> pointsy = (ArrayList<Point>) points.clone();


Collections.sort(pointsy , Point.ycomparator);
Collections.sort(pointsx , Point.xcomparator);

Point.printPoints(closestPairRec(pointsx,pointsy)); 

long stopTime = System.nanoTime();
System.out.println(stopTime - startTime);
	}
}

