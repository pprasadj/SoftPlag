import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class STYCalculator {

	private File file;
	List<String> contentFile1;

	// STY1c variables
	int totalOpenBraces;
	int lastOpenBraceCount;

	// STY1e and STY1f variables
	int totalCloseBraces;
	int lastCloseBraceCount; // STY1f
	int firstCloseBraceCount; // STY1e

	// STY1g and STY1h
	int numIndentation;
	int numindentSpace;
	int numIndentTabs;
	List<Integer> indentList = new ArrayList<Integer>();

	// STY4 and STY5
	int linesWithOperatorCount;
	int totalOperators;
	int totalSpaceBeforeOperator;
	int totalSpaceAfterOperator;

	// STY2a
	int pureCommentCount;

	// STY2b
	int traditionalCommentCount; /* this is traditional */
	int endofLineCommentCount; // this is end of line
	boolean traditionalContinueFlag;

	// Weights
	float sty1cWeight = 0.39f;
	float sty1eWeight = 0.41f;
	float sty1fWeight = 0.29f;
	float sty1gWeight = 0.25f;
	float sty1hWeight = 0.40f;
	float sty2aWeight = 0.39f;
	float sty2bWeight = 0.23f;
	float sty4Weight = 0.38f;
	float sty5Weight = 0.40f;

	public STYCalculator(File file) throws IOException {
		this.file = file;
		calculate();
	}

	private void calculate() throws IOException {
		FileReader fileRead1 = new FileReader(this.file);
		BufferedReader br1 = new BufferedReader(fileRead1);
		contentFile1 = br1.lines().collect(Collectors.toList());
		br1.close();

		// contentFile2.forEach(System.out::println);

		String currentLine = null;

		for (int i = 0; i < contentFile1.size(); i++) {
			currentLine = contentFile1.get(i);
			openBraceCount(currentLine);
			closedBraceCount(currentLine);
			if (currentLine.indexOf("{") != -1) {
				avgIndentationSpace(i);
			}
			commentsCounter(currentLine);
			
		}

	}
	
	private void openBraceCount(String currentLine) {
		// counts open braces and open braces that are last in line
		currentLine = currentLine.trim();
		int openBraceIndex = currentLine.indexOf('{');
		if (openBraceIndex != -1) {
			totalOpenBraces++;
			if (openBraceIndex == (currentLine.length() - 1)) {
				lastOpenBraceCount++;
			}
		}

	}

	private void closedBraceCount(String currentLine) {
		// counts number of closing brackets that are first and last of line
		currentLine = currentLine.trim();
		int closeBraceIndex = currentLine.indexOf('}');
		if (closeBraceIndex != -1) {
			totalCloseBraces++;
			if (closeBraceIndex == (currentLine.length() - 1)) {
				lastCloseBraceCount++;
			}
			if (closeBraceIndex == 0) {
				firstCloseBraceCount++;
			}
		}
	}

	private void avgIndentationSpace(int lineNumber) {
		// counts avg in space
		numIndentation++;
		String currentLine = contentFile1.get(lineNumber);
		String nextLine = contentFile1.get(lineNumber + 1);
		// System.out.println(currentLine);
		// System.out.println(nextLine);
		int indentStartIndex = 0;
		char[] currentLineArr = currentLine.toCharArray();
		for (int i = 0; i < currentLine.length(); i++) {
			if (currentLineArr[i] != ' ') {
				indentStartIndex = i;
				break;
			}
		}
		// System.out.println(indentStartIndex);
		int indentNextLine = 0;
		char[] nextLineArr = nextLine.toCharArray();
		for (int i = indentStartIndex; i < nextLine.length(); i++) {
			if (nextLineArr[i] != ' ') {
				break;
			}
			indentNextLine++;
		}
		// System.out.println(indentNextLine);
		indentList.add(indentNextLine);
		numindentSpace += indentNextLine;

	}

	private void commentsCounter(String currentLine) {
		// counts traditional and end of line comments counter
		currentLine = currentLine.trim();
		if (currentLine.contains("//") || currentLine.contains("/*")) {
			if (currentLine.contains("/*")) {
				traditionalContinueFlag = true;
				System.out.println("found traditional : \t" + currentLine);
				traditionalCommentCount++;
			}
			if (currentLine.contains("//")) {
				System.out.println("found end of line : \t" + currentLine);
				endofLineCommentCount++;
				if (currentLine.startsWith("//")) {
					pureCommentCount++;
					System.out.println("found pure comment line : \t" + currentLine);
				}
			}
		}
		if (traditionalContinueFlag) {
			if (currentLine.contains("*/")) {
				traditionalContinueFlag = false;
				System.out.println("traditonal ended : " + currentLine);
				if (currentLine.endsWith("*/")) {
					pureCommentCount++;
					System.out.println("pure comment : " + currentLine);

				}
			} else {
				System.out.println("found traditional continued : \t" + currentLine);
				System.out.println("found pure comment line : \t" + currentLine);
				if (!currentLine.startsWith("/*")) {
					pureCommentCount++;
				}
			}
		}

	}


}