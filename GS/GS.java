import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GS {
	public static void main(String[] args) {

		//Scanner scan;
		Scanner scan = new Scanner(System.in); 

		List<String> Names = new ArrayList<String>();
		List<String> Prefs = new ArrayList<String>();
		 int n = 0;
		while (scan.hasNext()) {
			Pattern pattern = Pattern.compile("n=\\d*");
			Pattern pattern2 = Pattern.compile("(?m)^\\d* .*[^\\s]");
			Pattern pattern3 = Pattern.compile("(?m)^\\d*: .*");
			final String lineFromFile = scan.nextLine();
	        String str = scan.findInLine(pattern);
	        String pers = scan.findInLine(pattern2);
	        String pref = scan.findInLine(pattern3);
       
	        if (str != null) {
	        n = Integer.parseInt(str.split("=")[1]);
	        }
	        else 	if (pers != null) {
	        		    Names.add(pers); // 
	        	        }
	        else	if (pref != null) {
    		    Prefs.add(pref);
    	        }
	        
		}
		
		// Parse read lines to correct data structures
		String[] manName;
		manName = new String[n];
		String[] womanName;
		womanName = new String[n];
		for (int i = 1; i <= n*2; i++) {
			if (Integer.parseInt(Names.get(i-1).split(" ")[0]) % 2 != 0) {
				manName[(i-1)/2] = Names.get(i-1).split(" ")[1];
		} else {
			womanName[(i/2)-1] = Names.get(i-1).split(" ")[1];
		}
		}
		int[][] manPref;
		 manPref = new int[n][n];
		int[][] womanPref;
		womanPref = new int[n][n];
		for (int j = 0; j < n; j++) {
		for (int i = 1; i <= n*2; i++) {
			if (Integer.parseInt(Prefs.get(i-1).split(": ")[0]) % 2 != 0) {
				manPref[(i-1)/2][j] = Integer.parseInt(Prefs.get(i-1).split(" ")[j+1]);
		} else {
			womanPref[(i/2)-1][j] = Integer.parseInt(Prefs.get(i-1).split(" ")[j+1]);
		}
		}
		}

		int[][] womanRanking;	
		womanRanking = new int[n][n];
			for (int i = 0; i < womanPref.length;i++) {
				for (int j = 0; j < womanPref.length;j++) {
				womanRanking[i][(womanPref[i][j]-1)/2] = j;
				}
			}
	/*		
			for (int i = 0; i < womanPref.length; i++) {
	    	    for (int j = 0; j < womanRanking[i].length; j++)
	    		System.out.print(womanRanking[i][j] + " ");
	    	    System.out.println();
	    	}
	*/	
			LinkedList<Integer> men = new LinkedList<>();
			for (int i = 0; i < manPref.length ; i++) {
				men.add(i);
			}
					
			int[] current = new int[n];
			for (int i = 0; i < current.length ; i++) {
				current[i] = -1;
			}
			
			int[] next = new int[n];
			for (int i = 0; i < next.length ; i++) {
				next[i] = 0;
			}
			//Matching algorithm			
			while (men.size() > 0) {
				int j = men.remove();
				while(next[j] < n) {
					int i = (manPref[j][next[j]]/2)-1;
					if (current[i] == -1) {
						current[i] = j;
						next[j] = next[j] + 1;
						break;
					} else if (womanRanking[i][j] < womanRanking[i][current[i]]) {
						men.add(current[i]);
						current[i] = j;
						next[j] = next[j] + 1;
						break;
					} else {
						next[j] = next[j] + 1;
					}
				}
			}

			for (int i = 0; i < current.length; i++) {
	    		System.out.print(manName[current[i]] + " -- " + womanName[i]);
	    	    System.out.println();
	    	}
					
		    }

	}