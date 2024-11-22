public class test {
    public static void main(String[] args) {
        String inputWord = "plan";
        String suggestedWord = "plains";
    
        MinHeap heap = new MinHeap(10); // Example initialization
        boolean isValid = isValidWord(inputWord, suggestedWord);
    
        if (isValid) {
            System.out.println(suggestedWord + " is a valid suggestion for " + inputWord);
        } else {
            System.out.println(suggestedWord + " is NOT a valid suggestion for " + inputWord);
        }
    }
    
    public static boolean isValidWord(String inputWord, String suggestedWord) {
        int inputLength = inputWord.length();
        int suggestedLength = suggestedWord.length();
    
        // Check length constraint
        if (suggestedLength > inputLength + 2 || suggestedLength < inputLength - 1) {
            return false;
        }
    
        if(suggestedLength < inputLength) { // If smaller
            int i = 0;
            int j = 0;
            while(i < suggestedLength && j < inputLength) {
                if(inputWord.charAt(j) == suggestedWord.charAt(i)) {
                    j++;
                    i++;
                } else {
                    j++;
                }
            }
            
            return i == suggestedLength;
        }
        int i = 0;
        int j = 0;
        while(j < suggestedLength && i < inputLength) {
            if(inputWord.charAt(i) == suggestedWord.charAt(j)) {
                j++;
                i++;
            } else {
                j++;
            }
        }
        return i == inputLength;
    }
}
