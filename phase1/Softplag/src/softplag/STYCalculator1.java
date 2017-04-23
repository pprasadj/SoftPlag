/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softplag;

/**
 *
 * @author kunal
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
public class STYCalculator1 {
    private File file;
	private static  List<String> contentFile1;

	ArrayList<Integer> stringIndexes;

	// STY1c variables
	private static int totalOpenBraces;
	private static int lastOpenBraceCount;

	// STY1e and STY1f variables
	private static int totalCloseBraces;
	private static int lastCloseBraceCount; // STY1f
	private static int firstCloseBraceCount; // STY1e

	// STY1g and STY1h
	private static int numIndentation;
	private static int numindentSpace;
	private static List<Integer> indentList = new ArrayList<Integer>();

	// STY4 and STY5
	private static int linesWithOperatorCount;
	private static int totalOperators;
	private static int totalSpaceBeforeOperator;
	private static int totalSpaceAfterOperator;
	private static List<String> totalOperatorList = new ArrayList<String>();
	private static List<String> singleOperatorTypeList;
	private static String[] operators;
	HashMap<Integer, ArrayList<Integer>> totalOperatorIndexMap;

	private static int commentStartIndex;
	private static int commentEndIndex;

	// STY2a
	private static int pureCommentCount;

	// STY2b
	private static int traditionalCommentCount; /* this is traditional */
	private static int endofLineCommentCount; // this is end of line
	private static boolean traditionalContinueFlag;

	// PRO1
	private static int totalLines = 0;
	private static int totalCharacters = 0;
	private static List<Integer> lineLengthList = new ArrayList<Integer>();

	// PRO2
	private static int totalFunctions;
	private static int totalFunctionNameLength = 0;
	private static List<String> methodNameList = new ArrayList<String>();

	// PSM
	private static int newCount;
	private static int implementCount;
	private static int classCount;
	private static int privateCount;

	// PSM5
	private static Set<String> variableTypeSet;
	private static HashMap<String, Integer> variableCount;
	private static HashMap<String, Integer> typeCount;
	private static int totalVariableCount;

	// Weights
	private static float sty1cWeight = 0.39f;
	private static float sty1eWeight = 0.41f;
	private static float sty1fWeight = 0.29f;
	private static float sty1gWeight = 0.25f;
	private static float sty1hWeight = 0.40f;
	private static float sty2aWeight = 0.39f;
	private static float sty2bWeight = 0.23f;
	private static float sty4Weight = 0.38f;
	private static float sty5Weight = 0.40f;

	private static float pro1Weight = 0.14f;
	private static float pro2Weight = 0.22f;

	private static float psm5Weight = 0.32f;
	private static float psm6Weight = 0.03f;
	private static float psm7cWeight = 0.1f;
	private static float psm7eWeight = 0.38f;
	private static float psm7jWeight = 0.2f;
	private static float psm7lWeight = 0.27f;

	private static float weightedMeanSum;

	public STYCalculator1(File file) throws IOException {
		this.file = file;
		stringIndexes = new ArrayList<Integer>();
		weightedMeanSum = sty1cWeight + sty1eWeight + sty1fWeight + sty1gWeight + sty1hWeight + sty2aWeight
				+ sty2bWeight + sty4Weight + sty5Weight + pro1Weight + pro2Weight + psm5Weight + psm6Weight
				+ psm7cWeight + psm7eWeight + psm7lWeight + psm7jWeight;
		
		System.out.println("weighted mean sum is " + weightedMeanSum);
		operators = new String[] { "+", "-", "/", "*", "%", "<", ">", "<=", ">=", "instanceof", "==", "!=", "&", "^",
				"|", "||", "&&", "+=", "+=", "-=", "*=", "/=", "%=" };
		String[] singleOperators = new String[] { "+", "-", "/", "*", "%", "<", ">", "&", "^", "|", };
		singleOperatorTypeList = Arrays.asList(singleOperators);
		totalOperatorIndexMap = new HashMap<Integer, ArrayList<Integer>>();
		variableTypeSet = new HashSet<String>(
				Arrays.asList(new String[] { "int", "float", "long", "boolean", "double", "char" }));
		variableCount = new HashMap<String, Integer>();
		typeCount = new HashMap<String, Integer>();
		calculate();

		for (String key : variableCount.keySet()) {
			totalVariableCount += variableCount.get(key);
		}

	}

	public  static float getWMLayoutValue() {
		int sumOfSty = 0;
                try{sumOfSty += sty1cWeight * ((lastOpenBraceCount / totalOpenBraces) * 100);}catch(ArithmeticException e){}
                try{sumOfSty += sty1eWeight * ((firstCloseBraceCount / totalCloseBraces) * 100);}catch(ArithmeticException e){}
                try{sumOfSty += sty1fWeight * ((lastCloseBraceCount / totalCloseBraces) * 100);}catch(ArithmeticException e){}
                try{sumOfSty += sty1gWeight * ((numindentSpace / numIndentation) * 100);}catch(ArithmeticException e){}
                try{sumOfSty += sty1hWeight * (((numindentSpace / 4) / numIndentation) * 100);}catch(ArithmeticException e){}
                
		try{
                    sumOfSty += sty2aWeight
				* ((pureCommentCount / (traditionalCommentCount + endofLineCommentCount + pureCommentCount)) * 100);}
                catch(ArithmeticException e){
                    
                }
                try{
		sumOfSty += sty2bWeight * ((endofLineCommentCount / (traditionalCommentCount + endofLineCommentCount)) * 100);
                }
                catch(ArithmeticException e){
                }
                //sumOfSty += sty4Weight * ((totalSpaceBeforeOperator / totalOperators) * 100);
		//sumOfSty += sty5Weight * ((totalSpaceAfterOperator / totalOperators) * 100);
		return sumOfSty / weightedMeanSum;
	}

	public static float getWMStyleValue() {
		float sumOfPro = 0;
                try{sumOfPro += pro1Weight * (totalCharacters / totalLines);}catch(ArithmeticException e){}
                try{sumOfPro += pro2Weight * (totalFunctionNameLength / totalFunctions);}catch(ArithmeticException e){}
		
		

		return sumOfPro / weightedMeanSum;
	}

	public static float getWMStructure() {

		float sumOfStr = 0;
		int numOfNonCommentLines = (contentFile1.size()
				- ((traditionalCommentCount + endofLineCommentCount + pureCommentCount)));
		
		sumOfStr += psm5Weight * ((float)totalVariableCount / numOfNonCommentLines);
		
		sumOfStr += psm6Weight * ((float)totalFunctions / numOfNonCommentLines);
		
		sumOfStr += psm7eWeight * ((float)implementCount / numOfNonCommentLines);
		
		sumOfStr += psm7jWeight * ((float)newCount / numOfNonCommentLines);
		
		sumOfStr += psm7cWeight * ((float)classCount / numOfNonCommentLines);
		
		sumOfStr += psm7lWeight * ((float)privateCount / numOfNonCommentLines);
		

		return sumOfStr / weightedMeanSum;

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

			if (currentLine.length() > 0) {

				commentsCounter(currentLine);
				setCommentIndexes(currentLine);

				if (!traditionalContinueFlag) {

					if (currentLine.indexOf("{") != -1) {
						openBraceCount(currentLine);
					}

					if (currentLine.indexOf("}") != -1) {
						closedBraceCount(currentLine);
					}

					if (currentLine.indexOf("{") != -1 && currentLine.indexOf("{") == currentLine.length() - 1) {
						avgIndentationSpace(i);
					}

					ArrayList<Integer> operatorList = stringContainsOperatorsFromList(currentLine, operators, i);
					// totalOperatorList.addAll(operatorList);
					totalOperators += operatorList.size();
					if (operatorList.size() != 0) {
						operatorWhiteSpaceCount(currentLine, operatorList);
					}

					lineLengthCalculator(currentLine);

					String regex = "((public|private|protected|static|final|native|synchronized|abstract|transient)+\\s)+[\\$_\\w\\<\\>\\w\\s\\[\\]]*\\s+[\\$_\\w]+\\([^\\)]*\\)?\\s*(\\s+|\\w+|\\W+)*\\{*";
					if (currentLine.trim().matches(regex)) {
						functionNameCounter(currentLine);
					}

					getPrimitiveVariableCount(currentLine);
					getNewCount(currentLine);
					getImplementCount(currentLine);
					getPrivateCount(currentLine);
					getClassCount(currentLine);

				}
				// else{
				// System.out.println(currentLine);
				// }

			}

		}
	}

	private void setCommentIndexes(String currentLine) {
		currentLine = currentLine.trim();
		commentStartIndex = currentLine.length();
		commentEndIndex = -1;

		if (currentLine.contains("//") || currentLine.contains("/*") || currentLine.contains("*/")) {

			// System.out.println(currentLine);

			String stringRegex = "\"";
			Matcher m2 = Pattern.compile(stringRegex).matcher(currentLine);
			stringIndexes = new ArrayList<Integer>();
			while (m2.find()) {
				stringIndexes.add(m2.start());
				// System.out.println(m2.start());

			}

			String endOflineRegex = "\\/\\/";
			Matcher m3 = Pattern.compile(endOflineRegex).matcher(currentLine);
			boolean inString = false;
			if (m3.find()) {
				commentStartIndex = m3.start();
				// System.out.println("comment found at " + commentStartIndex);
				for (int i = 0; i < stringIndexes.size() / 2; i += 2) {
					if (commentStartIndex > stringIndexes.get(i) && commentStartIndex < stringIndexes.get(i + 1)) {
						// System.out.println("in string");
						inString = true;
						break;
					}
				}
			}
			if (inString) {
				commentStartIndex = currentLine.length();
				commentEndIndex = -1;
			}

			String traditionalRegex = "\\/\\*";
			Matcher m4 = Pattern.compile(traditionalRegex).matcher(currentLine);
			if (m4.find()) {
				commentStartIndex = m4.start();
				traditionalContinueFlag = true;
				for (int i = 0; i < stringIndexes.size() / 2; i += 2) {
					if (commentStartIndex > stringIndexes.get(i) && commentStartIndex < stringIndexes.get(i + 1)) {
						// System.out.println("in string");
						commentStartIndex = currentLine.length();
						traditionalContinueFlag = false;
						break;
					}
				}
			}

			String traditionalEndRegex = "\\*\\/";
			Matcher m5 = Pattern.compile(traditionalEndRegex).matcher(currentLine);
			if (m5.find()) {
				commentEndIndex = m5.start();
				traditionalContinueFlag = false;
				for (int i = 0; i < stringIndexes.size() / 2; i += 2) {
					if (commentEndIndex > stringIndexes.get(i) && commentEndIndex < stringIndexes.get(i + 1)) {
						// System.out.println("in string");
						commentEndIndex = -1;
						traditionalContinueFlag = false;
						break;
					}
				}
			}

			// System.out.println("comment end at " + commentEndIndex);
			// System.out.println("comment start at " + commentStartIndex);

		}

	}

	private void getPrimitiveVariableCount(String currentLine) {
		currentLine = currentLine.trim();
		for (String type : variableTypeSet) {
			if (currentLine.contains(type)) {
				String regexVariableType = type + "\\s+";
				Matcher m = Pattern.compile(regexVariableType).matcher(currentLine);
				int endOfvariable = 0;
				String foundType = "";
				String foundVariableName = "";
				while (m.find(endOfvariable)) {
					endOfvariable = m.end();

					if (!traditionalContinueFlag) {

						int startOfVariable = m.start();
						if (startOfVariable < commentStartIndex && startOfVariable > commentEndIndex) {

							foundType = m.group(0);
							// System.out.println("found type : " + foundType);
							String temSubString = currentLine.substring(endOfvariable);
							// System.out.println("sub string is:" +
							// temSubString);
							String regexVariableName = "\\w+";
							Matcher m2 = Pattern.compile(regexVariableName).matcher(temSubString);
							while (m2.find()) {
								foundVariableName = m2.group(0);
								// System.out.println("found variable name: " +
								// foundVariableName);
								if (typeCount.get(foundType) == null) {
									typeCount.put(foundType, 1);
								} else {
									typeCount.put(foundType, typeCount.get(foundType) + 1);
								}

								if (variableCount.get(foundVariableName) == null) {
									variableCount.put(foundVariableName, 1);
								} else {
									variableCount.put(foundVariableName, variableCount.get(foundVariableName) + 1);
								}
								break;
							}
						}
					}
				}
			}
		}

	}

	private void functionNameCounter(String currentLine) {
		// TODO comment bug
		totalFunctions++;
		int nameIndex = currentLine.trim().indexOf("(");
		String methodName = "";
		char[] charArray = currentLine.trim().toCharArray();
		for (int i = nameIndex - 1; i > 0; i--) {
			if (charArray[i] == ' ') {
				break;
			}
			methodName += Character.toString(charArray[i]);

		}
		StringBuilder str = new StringBuilder(methodName);
		str.reverse();
		methodName = str.toString();
		methodNameList.add(methodName);

		totalFunctionNameLength += methodName.length();
	}

	private void lineLengthCalculator(String currentLine) {
		totalLines++;
		currentLine = currentLine.trim();
		String[] str1 = currentLine.replaceAll("\\s+", " ").replaceAll("\\W", "").split(" ");
		for (String s : str1) {
			lineLengthList.add(s.length());
			totalCharacters += s.length();
		}

	}

	private void getNewCount(String currentLine) {
		currentLine = currentLine.trim();
		String regex = "new" + "\\s+";
		Matcher m = Pattern.compile(regex).matcher(currentLine);
		int start = 0;
		while (m.find(start)) {
			start = m.end();
			if (start < commentStartIndex && start > commentEndIndex) {
				newCount++;
			}
		}

	}

	private void getImplementCount(String currentLine) {
		currentLine = currentLine.trim();
		String regex = "implements" + "\\s+";
		Matcher m = Pattern.compile(regex).matcher(currentLine);
		int start = 0;
		while (m.find(start)) {
			start = m.end();
			if (start < commentStartIndex && start > commentEndIndex) {
				implementCount++;
			}
		}

	}

	private void getPrivateCount(String currentLine) {
		currentLine = currentLine.trim();
		String regex = "private" + "\\s+";
		Matcher m = Pattern.compile(regex).matcher(currentLine);
		int start = 0;
		while (m.find(start)) {
			start = m.end();
			if (start < commentStartIndex && start > commentEndIndex) {
				privateCount++;
			}
		}
	}

	private void getClassCount(String currentLine) {
		currentLine = currentLine.trim();
		String regex = "class" + "\\s+";
		Matcher m = Pattern.compile(regex).matcher(currentLine);
		int start = 0;
		while (m.find(start)) {
			start = m.end();
			if (start < commentStartIndex && start > commentEndIndex) {
				classCount++;
			}
		}

	}

	private ArrayList<Integer> stringContainsOperatorsFromList(String currentLine, String[] operators,
			int currentLineIndex) {
		char[] currentLineCharArr = currentLine.trim().toCharArray();
		currentLine = currentLine.trim();

		ArrayList<Integer> operatorIndexList = new ArrayList<Integer>();

		for (int i = 0; i < operators.length; i++) {
			if (currentLine.contains(operators[i])) {
				if (operators[i].length() > 1) {
					continue;
				}
				if (operators[i].equals("/")) {
					int indexOfDiv = operators[i].indexOf("/");
					if ((currentLineCharArr[indexOfDiv + 1] == '/' && (indexOfDiv != currentLineCharArr.length - 1))
							|| (currentLineCharArr[indexOfDiv + 1] == '/' && indexOfDiv != 0)) {
						continue;
					}
				}

				if (!traditionalContinueFlag) {
					int operatorIndex = currentLine.indexOf(operators[i]);

					if (operatorIndex < commentStartIndex && operatorIndex > commentEndIndex) {

						String regex = "\\" + operators[i];
						Matcher m = Pattern.compile(regex).matcher(currentLine);
						int start = 0;
						String foundOperator = "";
						while (m.find(start)) {
							start = m.end();
							foundOperator = m.group(0);
							if (singleOperatorTypeList.contains(foundOperator)) {
								// System.out.println(start + " " + m.start());
								// System.out.println((start - 1 !=
								// currentLineCharArr.length - 1));
								if ((start - 1 != currentLineCharArr.length - 1)
										&& currentLineCharArr[m.start() + 1] == '=') {
									foundOperator += "=";
								}
								if ((start != currentLineCharArr.length - 1) && foundOperator == "&"
										&& currentLineCharArr[start + 1] == '&') {
									foundOperator += "&";
								}
								if ((start != 0) && foundOperator == "&" && currentLineCharArr[start - 1] == '&') {
									continue;
								}
								if ((start != currentLineCharArr.length - 1) && foundOperator == "&"
										&& currentLineCharArr[start + 1] == '&') {
									foundOperator += "&";
								}
								if ((start != 0) && foundOperator == "&" && currentLineCharArr[start - 1] == '&') {
									continue;
								}

							}
							// System.out.println("operator being added : " +
							// foundOperator + " and index is " + start);
							operatorIndexList.add(m.start());

						}

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
		return operatorIndexList;
	}

	private void operatorWhiteSpaceCount(String currentLine, ArrayList<Integer> operators) {
		// counts whitespcae around operaotr
		// System.out.println("\tThe line is : " + currentLine);
		currentLine = currentLine.trim();
		char[] lineCharArr = currentLine.toCharArray();
		linesWithOperatorCount++;
		// System.out.println("curent line is : " + currentLine);
		for (int indOperator : operators) {
			// System.out.print(currentLine.charAt(indOperator) + " ");

			int leadingSpace = 0;
			int trailingSpace = 0;

			int i1 = indOperator + 1;
			// System.out.println(" " + indOperator + " " + lineCharArr.length
			// );
			if (indOperator != lineCharArr.length - 1 && lineCharArr[indOperator + 1] == '=') {
				i1++;
				totalOperatorList.add(Character.toString(lineCharArr[indOperator])
						+ Character.toString(lineCharArr[indOperator + 1]));
			} else {
				totalOperatorList.add(Character.toString(lineCharArr[indOperator]));
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

			// System.out.println("\tspace near " +
			// currentLine.charAt(indOperator) + " is leadingspace: " +
			// leadingSpace
			// + " trailingspace: " + trailingSpace);
			totalSpaceBeforeOperator += leadingSpace;
			totalSpaceAfterOperator += trailingSpace;
		}
		// System.out.println();

	}

	private void commentsCounter(String currentLine) {
		// counts traditional and end of line comments counter
		currentLine = currentLine.trim();
		if (currentLine.contains("//") || currentLine.contains("/*")) {
			if (currentLine.contains("/*")) {
				traditionalContinueFlag = true;
				// System.out.println("found traditional : \t" + currentLine);
				traditionalCommentCount++;
				int index = currentLine.indexOf("/*");
				for (int i = 0; i < stringIndexes.size() / 2; i += 2) {
					if (index > stringIndexes.get(i) && index < stringIndexes.get(i + 1)) {
						// System.out.println("in string");
						traditionalContinueFlag = false;

						traditionalCommentCount--;
						break;
					}
				}
				if (currentLine.startsWith("/*") && !currentLine.contains("*/")) {
					pureCommentCount++;
				}

			}
			if (currentLine.contains("//")) {
				int endofLineIndex = currentLine.indexOf("//");
				// System.out.println("found end of line : \t" + currentLine);
				endofLineCommentCount++;
				for (int i = 0; i < stringIndexes.size() / 2; i += 2) {
					if (endofLineIndex > stringIndexes.get(i) && endofLineIndex < stringIndexes.get(i + 1)) {
						// System.out.println("in string");

						endofLineCommentCount--;
						break;
					}
				}
				if (currentLine.startsWith("//")) {
					pureCommentCount++;
					// System.out.println("found pure comment line : \t" +
					// currentLine);
				}
			}
		}
		if (traditionalContinueFlag) {
			if (currentLine.contains("*/")) {
				traditionalContinueFlag = false;
				// System.out.println("traditonal ended : " + currentLine);
				if (currentLine.endsWith("*/")) {
					pureCommentCount++;
					// System.out.println("pure comment : " + currentLine);

				}
			} else {
				// System.out.println("found traditional continued : \t" +
				// currentLine);
				// System.out.println("found pure comment line : \t" +
				// currentLine);
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

		int indexNextLine = lineNumber + 1;
		while (contentFile1.get(indexNextLine).replaceAll(" ", "").length() == 0) {
			// System.out.println("next line is " +
			// contentFile1.get(indexNextLine).replaceAll(" ", ""));
			indexNextLine++;
		}

		String nextLine = contentFile1.get(indexNextLine);
		// System.out.println(currentLine);
		// System.out.println(nextLine);
		currentLine = currentLine.replaceAll("\t", "    ");
		nextLine = nextLine.replaceAll("\t", "    ");
		int indentStartIndex = 0;
		char[] currentLineArr = currentLine.toCharArray();
		for (int i = 0; i < currentLine.length(); i++) {
			if (currentLineArr[i] != ' ') {
				// System.out.println("found char at " + i + currentLineArr[i]);
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
		// System.out.println("found");
		currentLine = currentLine.trim();
		int openBraceIndex = currentLine.indexOf('{');

		if (openBraceIndex < commentStartIndex && openBraceIndex > commentEndIndex) {
			totalOpenBraces++;
			// System.out.println(currentLine);
			if (openBraceIndex == (currentLine.length() - 1)) {
				lastOpenBraceCount++;
			}
		}

	}

	private void closedBraceCount(String currentLine) {
		// counts number of closing brackets that are first and last of line
		currentLine = currentLine.trim();
		int closeBraceIndex = currentLine.indexOf('}');
		if (!traditionalContinueFlag) {

			if (closeBraceIndex < commentStartIndex && closeBraceIndex > commentEndIndex) {
				totalCloseBraces++;
				if (closeBraceIndex == (currentLine.length() - 1)) {
					lastCloseBraceCount++;
				}
				if (closeBraceIndex == 0) {
					firstCloseBraceCount++;
				}
			}
		}
	}

	/* TESTING METHODS */

	/* TESTING METHODS BEGIN */

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

	private void getNumLineswithOperators() {
		System.out.println(linesWithOperatorCount);
	}

	private void getTotalSpaceAroundOperator() {
		System.out.println("Total trailing space : " + totalSpaceBeforeOperator + "|| Total space after operator : "
				+ totalSpaceAfterOperator + "|| Total operators : " + totalOperators);
		System.out.println("The operators are: ");
		for (String oper : totalOperatorList) {
			System.out.print(oper + " ");
		}
		System.out.println();
	}

	private void getComments() {
		System.out.println("Traditional comments : " + traditionalCommentCount + "|| End of line : "
				+ endofLineCommentCount + "|| Pure comment lines : " + pureCommentCount);
	}

	private void getLineLengths() {
		System.out.println("The line lengths are: ");
		for (int i : lineLengthList) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	private void getMethodLengths() {
		System.out.println("the method names and lengths are: ");
		for (String i : methodNameList) {
			System.out.print("\"" + i + "\"" + ": " + i.length() + " || ");
		}
		System.out.println();
	}

	private void getVariableCount() {
		System.out.println("The type count is : ");
		for (String type : typeCount.keySet()) {
			System.out.print(type + ": " + typeCount.get(type) + "||");
		}
		System.out.println();
		System.out.println("The variable count is : ");
		for (String varName : variableCount.keySet()) {
			System.out.print(varName + " || ");
		}
		System.out.println();
	}

	private void getKeyowordCount() {
		System.out.println("the countof 'new' keyword is " + newCount);
		System.out.println("the countof 'private' keyword is " + privateCount);
		System.out.println("the countof 'implements' keyword is " + implementCount);
		System.out.println("the countof 'class' keyword is " + classCount);
	}

	public static void main(String[] args) {
		try {
			STYCalculator1 sm = new STYCalculator1(new File("C:\\Users\\kunal\\Desktop\\repo\\AreaCircle1.java"));
                        Main m=new Main();
			System.out.println("======= Results ========");

			//System.out.println("Total open braces are: " + sm.getOpebr()[0]);
			//System.out.println("Total open braces that are the last character in line are: " + sm.getOpebr()[1]);

			//System.out.println();

			//System.out.println("Total close braces : " + sm.getClosebr()[0]);
			//System.out.println("Total close braces that are the last character in line are: " + sm.getClosebr()[1]);
			//System.out.println("Total close braces that are first : " + sm.getClosebr()[2]);

			// System.out.println();
			//
			// System.out.println("The indent spaces after each open brace in
			// sequential list : ");
			// sm.getIndentList();

			//System.out.println();

			//System.out.println("Total number of lines with operators: ");
			//sm.getNumLineswithOperators();
			//System.out.println("Total space around operators: ");
			//sm.getTotalSpaceAroundOperator();

			//System.out.println();

			//sm.getComments();

			//System.out.println();

			//sm.getLineLengths();

			//System.out.println();

			//sm.getMethodLengths();

			//System.out.println();

			//sm.getVariableCount();

			//System.out.println();

			//sm.getKeyowordCount();

			//System.out.println();

			//System.out.println("Layout value is ="+sm.getWMLayoutValue());
			//System.out.println();
			
			//System.out.println("Structure value is="+sm.getWMStructure());
			//System.out.println();
			
			//System.out.println("Style value is="+sm.getWMStyleValue());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
