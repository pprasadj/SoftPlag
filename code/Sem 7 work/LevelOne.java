import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LevelOne {
	private File file1;
	private File file2;

	public LevelOne(File file1, File file2) {
		this.file1 = file1;
		this.file2 = file2;
	}

	public boolean isPlagiarised() throws IOException {
		FileReader fileRead1 = new FileReader(file1);

		FileReader fileRead2 = new FileReader(file2);

		BufferedReader br1 = new BufferedReader(fileRead1);
		BufferedReader br2 = new BufferedReader(fileRead2);

		List<String> contentFile1 = br1.lines().collect(Collectors.toList());
		List<String> contentFile2 = br2.lines().collect(Collectors.toList());

		br1.close();
		br2.close();
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
						reachedCodeFile2 = true;
						// System.out.println("code line");
					}
					// System.out.println("Current line file 2 is " +
					// currentLineFile2);
				}
			}
			if (!currentLineFile1.equals(currentLineFile2)) {
				System.out.println("\t\tmismatch");
				System.out.println(currentLineFile1 + "   " + currentLineFile2);
				return false;
			}

		}

		// commentsFile1.forEach(System.out::println);
		// commentsFile2.forEach(System.out::println);

		return true;

	}

	public static void main(String[] args) {
		LevelOne l1 = new LevelOne(new File("F:\\BE project\\code\\File1.java"),
				new File("F:\\BE project\\code\\File1Copy.java"));
		try {
			boolean isP = l1.isPlagiarised();
			if (isP) {
				System.out.println("PLAGIARISED with comments added or changed");
			} else {
				System.out.println("Not plagiaried or high level plagisrism");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
