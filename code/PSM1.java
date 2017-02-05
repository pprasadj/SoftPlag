import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
 
public class PSM1 {

	private int newCount;
	private int implementCount;
	private int classCount;

	private List<String> contentFile1;
	private File file;

	private float psm7cWeight = 0.1f;
	private float psm7eWeight = 0.38f;
	private float psm7jWeight = 0.2f;

	public code(File file) throws IOException {
		this.file = file;
		calculate();

	}

	public float getWMStyleValue(){
		int sumOfPSM = 0;
		STYCalculator sty = new STYCalculator(this.file);
		sumOfPSM += psm7cWeight * (classCount/sty.getNonCommentLinesCount());
		sumOfPSM += psm7eWeight * (implementCount/sty.getNonCommentLinesCount());
		sumOfPSM += psm7jWeight * (newCount/sty.getNonCommentLinesCount());
		
	}

	private void calculate() throws IOException {
		FileReader fileRead1 = new FileReader(this.file);
		BufferedReader br1 = new BufferedReader(fileRead1);
		contentFile1 = br1.lines().collect(Collectors.toList());
		br1.close();

		String currentLine = "";

		for (int i = 0; i < contentFile1.size(); i++) {
			currentLine = contentFile1.get(i);
			getNewCount(currentLine);
			getImplementCount(currentLine);
			getPrivateCount(currentLine);

		}
	}

	private void getNewCount(String currentLine) {
		String tempCurr = currentLine;
		if (currentLine.contains("new")) {
			// System.out.println("found new");
			tempCurr = tempCurr.replaceAll("new", "");
			// System.out.println("curlength" + currentLine.length() + "temp len
			// " + tempCurr.length());
			// System.out.println("added " + (currentLine.length() -
			// tempCurr.length())/3 + " to count");
		}
		newCount += (currentLine.length() - tempCurr.length()) / 3;

	}

	private void getImplementCount(String currentLine) {
		String tempCurr = currentLine;
		if (currentLine.contains("implements")) {
			// System.out.println("found new");
			tempCurr = tempCurr.replaceAll("implements", "");
			// System.out.println("curlength" + currentLine.length() + "temp len
			// " + tempCurr.length());
			// System.out.println("added " + (currentLine.length() -
			// tempCurr.length())/3 + " to count");
		}
		implementCount += (currentLine.length() - tempCurr.length()) / 10;

	}
	private void getClassCount(String currentLine) {
		String tempCurr = currentLine;
		if (currentLine.contains("class")) {
			// System.out.println("found new");
			tempCurr = tempCurr.replaceAll("class", "");
			// System.out.println("curlength" + currentLine.length() + "temp len
			// " + tempCurr.length());
			// System.out.println("added " + (currentLine.length() -
			// tempCurr.length())/3 + " to count");
		}
		classCount += (currentLine.length() - tempCurr.length()) / 4;

	}

	public static void main(String[] args) {
		try {
			code sm = new code(new File("F:\\BE project\\code\\File1.java"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
