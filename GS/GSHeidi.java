import java.util.*;
import java.io.*;

public class GS {

  private static int PopulationSize(Scanner sc) {
    int n = 0;
    String s;

    while (true) {
      s = sc.nextLine();
      if (s.length() > 0) {
        if ("n".equals(s.substring(0, 1))) {
          n = Integer.parseInt(s.substring(2, s.length()));
          break;
        }
      }
    }
    return n;
  }

  static class Data {
    public int n;
    public String[] ManNames;
    public String[] WomanNames;
    public int[][] ManPref;
    public int[][] WomanRank;  
  }

  public static Data readfile(String filepath) throws IOException {
    Scanner sc = new Scanner(new File(filepath));
    int n = PopulationSize(sc), indexManCounter = 0, indexWomanCounter = 0;
    String[] ManNames = new String[n], WomanNames = new String[n];
    int[][] ManPref = new int[n][n], WomanRank = new int[n][n];

    for (int i = 0; i < 2 * n; i++) {
      String[] parts = sc.nextLine().split(" ");
      if (Integer.parseInt(parts[0]) % 2 == 0) 
    	  WomanNames[indexWomanCounter++] = parts[1];
      else 
    	  ManNames[indexManCounter++] = parts[1];
    }

    sc.nextLine();
    indexManCounter = indexWomanCounter = 0;
    for (int i = 0; i < 2 * n; i++) {
      String[] parts = sc.nextLine().split(" ");
      if (Integer.parseInt(parts[0].substring(0, parts[0].indexOf(":"))) % 2 == 0) {
        for (int j = 0; j < n; j++) {
        	WomanRank[indexWomanCounter][(Integer.parseInt(parts[j + 1]) - 1) / 2] = j;
        }
        indexWomanCounter++;
      } else {
        for (int j = 0; j < n; j++) {
          ManPref[indexManCounter][j] = (Integer.parseInt(parts[j + 1]) - 2) / 2;
        }
        indexManCounter++;
      }
    }
    sc.close();

    Data output = new Data();
    output.n = n;
    output.ManNames = ManNames;
    output.WomanNames = WomanNames;
    output.ManPref = ManPref;
    output.WomanRank = WomanRank;
   
    return output;
  }
  
  private static int[] GS_algorithm(int n, int[][] ManPref, int[][] WomanRank) {

	    LinkedList<Integer> FreeMen = new LinkedList<Integer>();
	    int[] Next = new int[n], Current = new int[n];
	    int m, w, m1;

	    for (int i = 0; i < n; i++)
	      Current[i] = -1;
	    for (int i = 0; i < n; i++)
	      FreeMen.add(i);

	    while (FreeMen.size() > 0) {
	      m = FreeMen.getFirst();
	      w = ManPref[m][Next[m]];
	      if (Current[w] == -1) {
	        Current[w] = m;
	        FreeMen.removeFirst();
	      }
	      else {
	        m1 = Current[w];
	        if (WomanRank[w][m] > WomanRank[w][m1]) {
	          Next[m] = Next[m] + 1;
	        } 
	        else {
	          Current[w] = m;
	          FreeMen.removeFirst();
	          FreeMen.add(m1);
	        }
	      }
	    }
	    return Current;
	  }

  public static void main(String[] args) throws IOException {

    if (args.length != 1) {
      System.err.println("Please give a single file name as argument.");
      System.exit(2);
    }
    
    Data inputData = readfile(args[0]);// System.in);
    int[] Current = GS_algorithm(inputData.n, inputData.ManPref, inputData.WomanRank);
    for (int i = 0; i < inputData.n; i++)
      System.out.println(inputData.ManNames[Current[i]] + " -- " + inputData.WomanNames[i]);
  }
}

