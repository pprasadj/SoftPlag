
package soft;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Soft {

   public static Map<String, Integer> getTermFrequencyMap(String[] terms) {
        Map<String, Integer> termFrequencyMap = new HashMap<>();
        for (String term : terms) {
            Integer n = termFrequencyMap.get(term);
            n = (n == null) ? 1 : ++n;
            termFrequencyMap.put(term, n);
        }
        return termFrequencyMap;
    }

    /**
     * @param text1 
     * @param text2 
     * @return cosine similarity of text1 and text2
     */
    public static double cosineSimilarity(String text1, String text2) {
        //Get vectors
        Map<String, Integer> a = getTermFrequencyMap(text1.split("\\W+"));
        Map<String, Integer> b = getTermFrequencyMap(text2.split("\\W+"));

        //Get unique words from both sequences
        HashSet<String> intersection = new HashSet<>(a.keySet());
        intersection.retainAll(b.keySet());

        double dotProduct = 0, magnitudeA = 0, magnitudeB = 0;

        //Calculate dot product
        for (String item : intersection) {
            dotProduct += a.get(item) * b.get(item);
        }

        //Calculate magnitude a
        for (String k : a.keySet()) {
            magnitudeA += Math.pow(a.get(k), 2);
        }

        //Calculate magnitude b
        for (String k : b.keySet()) {
            magnitudeB += Math.pow(b.get(k), 2);
        }

        //return cosine similarity
        return dotProduct / Math.sqrt(magnitudeA * magnitudeB);
    }
    public static void main(String[] args) {
        Soft s=new Soft();
        double score=s.cosineSimilarity("public12","public");
        System.out.println(score);
    }
}