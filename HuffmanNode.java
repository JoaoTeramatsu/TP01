
import java.util.Comparator;

class HuffmanNode {
    int frequency;
    byte data; // Use byte instead of char to handle binary files
    HuffmanNode left;
    HuffmanNode right;
}

class FrequencyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.frequency - y.frequency;
    }
}
