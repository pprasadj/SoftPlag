import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LevelThree {
	private File file1;
	private File file2;

	private static HashMap<String, Integer> variableCountFile1;
	private static HashMap<String, Integer> variableCountFile2;
	private Set<String> variableTypeSet;
	
	public LevelThree(File file1, File file2) {
		this.file1 = file1;
		this.file2 = file2;
		variableCountFile1 = new HashMap<String, Integer>();
		variableCountFile2 = new HashMap<String, Integer>();
		variableTypeSet = new HashSet<String>(
				Arrays.asList(new String[] { "int", "float", "long", "boolean", "double", "char"}));
	}
	
	
	public boolean isPlagiarised() throws FileNotFoundException {
		FileReader fileRead1 = new FileReader(file1);

		FileReader fileRead2 = new FileReader(file2);

		BufferedReader br1 = new BufferedReader(fileRead1);
		BufferedReader br2 = new BufferedReader(fileRead2);

		List<String> contentFile1 = br1.lines().collect(Collectors.toList());
		List<String> contentFile2 = br2.lines().collect(Collectors.toList());
		
		try {
			br1.close();
			br2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// contentFile2.forEach(System.out::println);
		// contentFile1.forEach(System.out::println);

		Iterator<String> itr1 = contentFile1.iterator();
		Iterator<String> itr2 = contentFile2.iterator();

		String currentLineFile1 = null;
		String currentLineFile2 = null;
		List<String> commentsFile1 = new ArrayList<String>();
		List<String> commentsFile2 = new ArrayList<String>();
		boolean multiLineComment = false;
		boolean multiLineComment2 = false;
		while (itr1.hasNext() && itr2.hasNext()) {
			boolean reachedCodeFile1 = false;
			boolean reachedCodeFile2 = false;
			currentLineFile1 = itr1.next();
			currentLineFile2 = itr2.next();
			while (!reachedCodeFile1 || !reachedCodeFile2) {
				if (!reachedCodeFile1) {
					// System.out.println("file 1 " + currentLineFile1.trim());
					if (currentLineFile1.trim().isEmpty() || currentLineFile1.startsWith("public class")
							|| currentLineFile1.startsWith("class")) {
						currentLineFile1 = itr1.next();
						// System.out.println("empty line or class line");

					} else if (currentLineFile1.contains("*/")) {
						// System.out.println("\t\t\tdetected end of multi");
						multiLineComment = false;
						currentLineFile1 = itr1.next();
					} else if (multiLineComment) {
						currentLineFile1 = itr1.next();
					} else if (currentLineFile1.contains("/*")) {
						// System.out.println("\t\t\tdetected multi");
						multiLineComment = true;
					} else if (currentLineFile1.trim().startsWith("//")) {
						// System.out.println("\t\t\tdetected simple");
						commentsFile1.add(currentLineFile1);
						currentLineFile1 = itr1.next();
						// System.out.println("comment line");
					} else {
						getCurrLineVariables(currentLineFile1,"file1");
						reachedCodeFile1 = true;
					}
					// System.out.println("Current line file 1 is " +
					// currentLineFile1);
				}

				if (!reachedCodeFile2) {
					// System.out.println( "file 2 " + currentLineFile2.trim());

					if (currentLineFile2.trim().isEmpty() || currentLineFile2.startsWith("public class")
							|| currentLineFile2.startsWith("class")) {
						currentLineFile2 = itr2.next();
						// System.out.println("empty line or class line");
					} else if (currentLineFile2.trim().contains("*/")) {
						multiLineComment2 = false;
						currentLineFile2 = itr2.next();
					} else if (multiLineComment2) {
						currentLineFile2 = itr2.next();
					} else if (currentLineFile2.trim().contains("/*")) {
						// System.out.println("\t\t\tdetected multi");
						multiLineComment2 = true;
					} else if (currentLineFile2.trim().startsWith("//")) {
						commentsFile2.add(currentLineFile2);
						currentLineFile2 = itr2.next();
						// System.out.println("comment line");
					} else {
						getCurrLineVariables(currentLineFile2,"file2");
						reachedCodeFile2 = true;
						// System.out.println("code line");
					}
					// System.out.println("Current line file 2 is " +
					// currentLineFile2);
				}
			}
			

		}
		//System.out.println(variableCountFile1);
		int totalVariablesFile1 = 0;
		for(int count : variableCountFile1.values()){
			totalVariablesFile1 += count;
		}
		//System.out.println(variableCountFile2);
		int totalVariablesFile2 = 0;
		for(int count : variableCountFile2.values()){
			totalVariablesFile2 += count;
		}
		//System.out.println(totalVariablesFile1);
		//System.out.println(totalVariablesFile2);
		if(totalVariablesFile1 == totalVariablesFile2 )
			return true;
		return false;

	}

	private void getCurrLineVariables(String currentLineFile, String fileName) {
		String[] currentWords = currentLineFile.split(" ");
		HashMap<String, Integer> variableCount = null;
		if(fileName.equals("file1")){
			variableCount = variableCountFile1;
		}
		else if(fileName.equals("file2")){
			variableCount = variableCountFile2;
		}
		int counter = 0;
		for (String currWord : currentWords) {
			if(variableTypeSet.contains(currWord)){
				variableCount.put(currentWords[counter+1], 0);
			}
			if(variableCount.containsKey(currWord)){
				variableCount.put(currWord, variableCount.get(currWord)+1);
			}
			counter++;
		} 
	}

	public static void main(String[] args) {
		LevelThree l3 = new LevelThree(new File("F:\\BE project\\code\\File1.java"),
				new File("F:\\BE project\\code\\File1Copy.java"));
		try {
			boolean isP = l3.isPlagiarised();

			if (isP) {
				System.out.println("high possiility of Plagiarism: same number of variables");
				System.out.println("variables in file 1");
				System.out.println(variableCountFile1);
				System.out.println("variables in file 2");
				System.out.println(variableCountFile2);
			} else {
				System.out.println("Not plagiaried or high level plagisrism");
				System.out.println("variables in file 1");
				System.out.println(variableCountFile1);
				System.out.println("variables in file 2");
				System.out.println(variableCountFile2);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
