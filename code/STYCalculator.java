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

	public float getWMLayoutValue() {
		int sumOfSty = 0;
		sumOfSty += sty1cWeight * ((lastOpenBraceCount / totalOpenBraces) * 100);
		sumOfSty += sty1eWeight * ((firstCloseBraceCount / totalCloseBraces) * 100);
		sumOfSty += sty1fWeight * ((lastCloseBraceCount / totalCloseBraces) * 100);
		sumOfSty += sty1gWeight * ((numindentSpace / numIndentation) * 100);
		sumOfSty += sty1hWeight * (((numindentSpace / 4) / numIndentation) * 100); // assuming 1 tab to be 4 spaces
		sumOfSty += sty2aWeight
				* ((pureCommentCount / (traditionalCommentCount + endofLineCommentCount + pureCommentCount)) * 100);
		sumOfSty += sty2bWeight * ((endofLineCommentCount / (traditionalCommentCount + endofLineCommentCount)) * 100);
		sumOfSty += sty4Weight * ((totalSpaceBeforeOperator / totalOperators) * 100);
		sumOfSty += sty5Weight * ((totalSpaceAfterOperator / totalOperators) * 100);
		float weightedMeanSum = sty1cWeight + sty1eWeight + sty1fWeight + sty1gWeight + sty1hWeight + sty2aWeight
				+ sty2bWeight + sty4Weight + sty5Weight;
		return sumOfSty/weightedMeanSum;
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
			String[] operators = { "+", "-", "/", "*", "%", "<", ">", "<=", ">=", "instanceof", "==", "!=", "&", "^",
					"|", "||", "&&", "+=", "+=", "-=", "*=", "/=", "%=" };
			List<String> operatorList = stringContainsOperatorsFromList(currentLine, operators);
			totalOperators += operatorList.size();
			if (operatorList.size() != 0) {
				operatorWhiteSpaceCount(currentLine, operatorList);
			}

		}

	}

	private List<String> stringContainsOperatorsFromList(String currentLine, String[] operators) {
		char[] charArr = currentLine.toCharArray();
		List<String> operatorList = new ArrayList<String>();
		for (int i = 0; i < operators.length; i++) {
			if (currentLine.contains(operators[i])) {
				if (operators[i].length() > 1) {
					char[] opArray = operators[i].toCharArray();
					String singleOperator = String.valueOf(opArray[0]);
					operatorList.remove(singleOperator);
				}
				if (operators[i].equals("/")) {
					int indexOfDiv = operators[i].indexOf("/");
					if (charArr[indexOfDiv + 1] == '/' && (indexOfDiv != charArr.length - 1)) {
						continue;
					}
				}
				int commentStartIndex = currentLine.length() - 1;
				int commentEndIndex = 0;
				if (currentLine.contains("//") || currentLine.contains("/*") || currentLine.contains("*/")) {
					if (currentLine.contains("//")) {
						commentStartIndex = currentLine.indexOf("//");
					} else if (currentLine.contains("/*")) {
						commentStartIndex = currentLine.indexOf("/*");
					}
					if (currentLine.contains("*/")) {
						commentEndIndex = currentLine.indexOf("/*");
					}
				}
				if (!traditionalContinueFlag) {
					int operatorIndex = currentLine.indexOf(operators[i]);
					if (operatorIndex < commentStartIndex && operatorIndex > commentEndIndex) {
						operatorList.add(operators[i]);
					}
				}
			}
		}
		/*
		 * if (operatorList.size() != 0) { System.out.println(
		 * "\tLine contains following operators : "); for (String operator :
		 * operatorList) { //System.out.print("\t" + operator + " "); }
		 * //System.out.println(); }
		 */
		return operatorList;
	}

	private void operatorWhiteSpaceCount(String currentLine, List<String> operators) {
		// counts whitespcae around operaotr
		// System.out.println("\tThe line is : " + currentLine);
		char[] lineCharArr = currentLine.toCharArray();
		linesWithOperatorCount++;
		for (String operator : operators) {
			int indOperator = currentLine.indexOf(operator);
			int leadingSpace = 0;
			int trailingSpace = 0;
			int i1 = indOperator + 1;
			if (operator.length() > 1) {
				i1 += (operator.length() - 1);
			}
			for (; i1 < currentLine.length(); i1++) {
				if (lineCharArr[i1] != ' ') {
					break;
				}
				trailingSpace++;
			}
			for (int i = indOperator - 1; i > 0; i--) {
				if (lineCharArr[i] != ' ') {
					break;
				}
				leadingSpace++;
			}
			// System.out.println("cuurent line is " + currentLine);
			// System.out.println("\tspace near " + operator + " is
			// leadingspace: " + leadingSpace + " trailingspace: "+
			// trailingSpace);
			totalSpaceBeforeOperator += leadingSpace;
			totalSpaceAfterOperator += trailingSpace;
		}

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

	/* TESTING METHODS */

	private int[] getClosebr() {
		int[] arr = new int[3];
		arr[0] = totalCloseBraces;
		arr[1] = lastCloseBraceCount;
		arr[2] = firstCloseBraceCount;
		return arr;
	}

	private int[] getOpebr() {
		int[] arr = new int[2];
		arr[0] = totalOpenBraces;
		arr[1] = lastOpenBraceCount;
		return arr;
	}

	private void getIndentList() {
		for (int ind : indentList) {
			System.out.print(ind + " ");
		}
		System.out.println();
	}

	private void getNumLineswithOperators() {
		System.out.println(linesWithOperatorCount);
	}

	private void getTotalSpaceAroundOperator() {
		System.out.println("total trailing space : " + totalSpaceBeforeOperator + "|| total space after operator : "
				+ totalSpaceAfterOperator + "|| total operators : " + totalOperators);
	}

	private void getComments() {
		System.out.println("tradiotional comments : " + traditionalCommentCount + "|| end of line : "
				+ endofLineCommentCount + "|| pure comment line : " + pureCommentCount);
	}

	public static void main(String[] args) {
		try {
			STYCalculator sm = new STYCalculator(new File("F:\\BE project\\code\\File1.java"));
			System.out.println(sm.getOpebr()[0] + " " + sm.getOpebr()[1]);
			System.out.println("Total close braces : " + sm.getClosebr()[0] + " ||| Total close braces that are last : "
					+ sm.getClosebr()[1] + " ||| Total close braces that are first : " + sm.getClosebr()[2]);
			sm.getIndentList();
			sm.getNumLineswithOperators();
			sm.getTotalSpaceAroundOperator();
			sm.getComments();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* TESTING METHODS END */

}