
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Detector {

	public static void main(String[] args) {
		try {

			// Pass the absolute path of the file as the parameter to new File
			// object. The path in your pc will be different.
			FileReader file = new FileReader(new File("F:\\J2ee\\workspace\\testing\\src\\testing\\File1.java"));

			FileReader file3 = new FileReader(new File("F:\\BE project\\code\\LevelOne.java"));
			// This detector only calculates the halstead metrics for a single
			// file. Comparing diffferent files will be added later.
			BufferedReader br = new BufferedReader(file);
			HashMap<String, Integer> uniqueOperatorCount = new HashMap<String, Integer>();
			HashMap<String, Integer> uniqueOperandCount = new HashMap<String, Integer>();
			HashMap<String, Integer> operatorCount = new HashMap<String, Integer>();
			HashMap<String, Integer> operandCount = new HashMap<String, Integer>();
			String code = "";
			String currentLine = "";
			StreamTokenizer st = new StreamTokenizer(file3);

			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				switch (st.ttype) {
				case '"':
					System.out.println("String = " + st.sval);
					break;

				case StreamTokenizer.TT_EOL:
					System.out.println("End-of-line");
					break;

				case StreamTokenizer.TT_NUMBER:
					System.out.println("Number = " + st.nval);
					break;

				case StreamTokenizer.TT_WORD:
					System.out.println("Word = " + st.sval);
					break;

				default:
					System.out.println("Other = " + (char) st.ttype);
				}
			}

			// This algorithm only recognizes the following basic operators and
			// operands as operators.
			Set<String> operatorTypeSet = new HashSet<String>(
					Arrays.asList(new String[] { "+", "-", "*", "/", "++", "--", "%" }));
			Set<String> operandTypeSet = new HashSet<String>(
					Arrays.asList(new String[] { "int", "float", "long", "boolean", "double", "char" }));
			int lineNumber = 0;
			while ((currentLine = br.readLine()) != null) {
				code += currentLine;
				code += "\n";
				lineNumber++;
				boolean hm = false;

				if (!(currentLine.trim().startsWith("//")) && !(currentLine.trim().startsWith("System.out"))) { // ignores
																												// comments
					String[] currentWords = currentLine.split(" ");

					int counter = 0;
					for (String currWord : currentWords) {

						if (operandCount.containsKey(currWord)) {
							// System.out.println("currword"+ currWord +"found
							// at " + lineNumber);
							operandCount.put(currWord, operandCount.get(currWord) + 1);

						} else if (operandTypeSet.contains(currWord)) {
							if (!uniqueOperandCount.containsKey(currWord)) {
								uniqueOperandCount.put(currentWords[counter + 1], 1);
								operandCount.put(currentWords[counter + 1], 0);
							}

						} else if (operatorCount.containsKey(currWord)) {
							operatorCount.put(currWord, operatorCount.get(currWord) + 1);
						}

						else if (operatorTypeSet.contains(currWord)) {
							if (!uniqueOperatorCount.containsKey(currWord)) {
								uniqueOperatorCount.put(currWord, 1);
								operatorCount.put(currWord, 1);
							}

						}
						counter++;
					}

				}

			}
			System.out.println("operatorCount" + operatorCount);
			System.out.println("operandCount " + operandCount);
			System.out.println("uniqueOperandCount" + uniqueOperandCount);
			System.out.println("uniqueOperatorCount" + uniqueOperatorCount + "\n");

			int N1 = 0;
			for (int count : operatorCount.values()) {
				N1 += count;
			}
			System.out.println("N1 = " + N1);
			int N2 = 0;
			for (int count : operandCount.values()) {
				N2 += count;
			}
			System.out.println("N2 = " + N2);
			int n1 = 0;
			for (int count : uniqueOperandCount.values()) {
				n1 += count;
			}
			System.out.println("n1 = " + n1);
			int n2 = 0;
			for (int count : uniqueOperandCount.values()) {
				n2 += count;
			}
			System.out.println("n2 = " + n2 + "\n");

			double V = (N1 + N2) * (Math.log(n1 + n2) / Math.log(2));
			double E = (n1 * N2 * (N1 + N2) * (Math.log(n1 + n2) / Math.log(2))) / 2 * n2;
			System.out.println("V " + V);
			System.out.println("E " + E);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
