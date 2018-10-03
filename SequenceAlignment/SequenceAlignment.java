import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.Pattern;
public class SequenceAlignment {
	
	static class penalties {
		public String name;
		public int[][] matrix;
	}
	
	static class species {
		String name;
		String protein;
		int penalty;
		public species(String name, String protein, int penalty){
			this.name = name;
			this.protein = protein;
			this.penalty = penalty;
		}
	}
	
	private static penalties dataReader(String filepath) throws IOException { 
		String name = "A";
		int[][] matrix = new int[24][24];
		try {
			Scanner scan = null;
			File file = new File("BLOSUM62.txt");
			scan = new Scanner(file);
			Pattern pattern = Pattern.compile("(  [A-Z])+|  //*");
			Pattern pattern2 = Pattern.compile("(\\s+-?\\d+)+");
			int j = 0;
			//Pattern pattern = Pattern.compile("[A-Z]");
			while (scan.hasNextLine()) {
				if (scan.nextLine().contains("Cluster Percentage"))
					break;
			}
			while (scan.hasNext()) {
				String str;
				String str2;
				final String line = scan.nextLine().trim();
				str =  scan.findInLine(pattern);
				str2 = scan.findInLine(pattern2);
				if (str != null)
					name = str.replaceAll("\\s+", "");
				if (str2 != null) {
					for (int i = 0; i < str2.split("[\\r\\n]+")[0].split("\\s+").length-1; i++) {
					matrix[j][i] = Integer.parseInt(str2.split("[\\r\\n]+")[0].split("\\s+")[i+1]); 
					}
					j++;
				}
				
			}
			
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
	    penalties output = new penalties();
	    output.name = name;
	    output.matrix = matrix;
	    
		return output;
		}
		
	//Define recurrence
	//Algorithm: Two strings as input, double as output
	
		private static int opt(String X, String Y, String name, int[][] matrix, int I, int J ) {
			int opt;
			int alpha;
			int delta;
			
			if (I == -1 | J == -1) {
				opt = 0;
				return opt;
			} else {
				alpha = matrix[1][1];
				delta = -4;
				opt = Math.max(alpha + opt(X, Y, name, matrix,I-1,J-1),
						Math.max(delta + opt(X, Y, name, matrix,I-1,J), 
								delta + opt(X, Y, name, matrix,I,J-1))); 
			}
			return opt;
		}
			
		private static ArrayList<species> AbuttomUp(String X, String Y, String name, int[][] matrix) {
			ArrayList<species> matchSpecies = new ArrayList<>();
			int lengthx = X.length();
			int lengthy = Y.length();
			int d1, d2, d3;
			//StringBuilder Xout = new StringBuilder("Hej");
			char[] Xout = new char[Math.max(lengthx, lengthy)];
			char[] Yout = new char[Math.max(lengthx, lengthy)];
			
			int[][] A = new int[lengthx + 1][lengthy + 1];
			int[][] Aux = new int[lengthx + 1][lengthy + 1];
			int d = -4;
			int opt;
			int alpha = 0;
			
			for (int i = 0; i <= lengthx; i++) {
				A[i][0] = i*d;
				Aux[i][0] = 2;
			}
			for (int i = 0; i <= lengthy; i++) {
				A[0][i] = i*d;
				Aux[0][i] = 3;
			}
			Aux[0][0] = 1;
			for (int i = 1; i <= lengthx; i++) {
				for (int j = 1; j <= lengthy; j++){
			alpha = matrix[name.indexOf(X.charAt(i-1))][name.indexOf(Y.charAt(j-1))];
			d1 = alpha + A[i-1][j-1];			
			d2 = d + A[i-1][j];
			d3 = d + A[i][j-1];
			A[i][j] = Math.max(d1, Math.max(d2, d3));
			
			if (d1 >= d2 & d1 >= d3)
			Aux[i][j] = 1;
			else if (d2 >= d3)
				Aux[i][j] = 2;
			else
				Aux[i][j] = 3;
				}
			}
			/*
			for (int i = 0 ; i < lengthx +1 ; i++) {
				for (int j = 0; j < lengthy +1; j++)
				System.out.print(A[i][j] + " ");
				System.out.println();
			}
			
			
			for (int i = 0 ; i <= lengthx ; i++) {
				for (int j = 0; j <= lengthy; j++)
				System.out.print(Aux[i][j] + " ");
				System.out.println();
			}
			*/
			int lx = lengthx;
			int ly = lengthy;
			for (int i = Math.max(lengthx, lengthy)-1; i>=0;i--) { 
			if (Aux[lx][ly] == 1) {
			Xout[i] = X.charAt(lx-1);
			Yout[i] = Y.charAt(ly-1);
			if (lx > 0) 
				lx--;
			if (ly > 0)
				ly--;
			}
			else if (Aux[lx][ly] == 2) {
			Xout[i] = X.charAt(lx-1);
			Yout[i] = '-';
			if (lx > 0) 
				lx--;
			}
			else if (Aux[lx][ly] == 3) {
				Xout[i] = '-';
				Yout[i] = Y.charAt(ly-1);
			if (ly > 0)
				ly--;
			}
			}
			String XoutS = String.valueOf(Xout);
			String YoutS = String.valueOf(Yout);
			matchSpecies.add(new species("a",XoutS,A[lengthx][lengthy]));
			matchSpecies.add(new species("a",YoutS,A[lengthx][lengthy]));
			return matchSpecies;
			
		}
	
	//Algorithm: Two strings as input, double as output
	
	private static int alignment(String X, String Y) {
		int max = Math.max(X.length(), Y.length());
		String[][] A = new String[2][max];
		for (int i = 0; i < X.length(); i++)
			A[1][i] = X.split("(?!^)")[i];
		for (int i = 0; i < X.length(); i++)
			A[2][i] = Y.split("(?!^)")[i];
		
		
		return A.length;
	}
	
	
	
	public static void main(String[] args) throws IOException {
	    penalties inputData = dataReader(args[0]);
		
	    ArrayList<String> names = new ArrayList<>();
		ArrayList<String> gene = new ArrayList<>();
		int nfiles = args.length - 1;
		while (nfiles >= 0) {
			String protein = "";
		try {
			Scanner scan = null;
			File file = new File(args[nfiles]);
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
				final String line = scan.nextLine().trim();
				if (line.contains(">")) {
					if (protein.length()>0) { 
						gene.add(protein);
						protein = "";
					}
					names.add(line.replaceAll(">","").split("\\s+")[0]);
				}
				else
					protein = protein + line;
			}
			gene.add(protein);

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		nfiles--;
		}				
		//Algorithm: Two strings as input, double as output
				
				int I = 2;
				int J = 2;
				ArrayList<species> matchSpecies = new ArrayList<>();
			for (int i = 0; i < gene.size(); i++){
				for (int j = i + 1; j +1 < gene.size(); j++) {
					matchSpecies = AbuttomUp(gene.get(i),gene.get(j), inputData.name, inputData.matrix);
				System.out.println(names.get(i) + "--" + names.get(j) + ": " + matchSpecies.get(1).penalty);
				System.out.println(matchSpecies.get(0).protein);
				System.out.println(matchSpecies.get(1).protein);
				}
			}
	}	
	
	}



