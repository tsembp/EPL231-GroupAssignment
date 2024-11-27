public class tester {
    
    public static void main(String[] args) {
        String word = "!HAJIKOUpaRTIS!";
        word = word.toLowerCase();
        System.out.println(word);
        System.out.println(containsPunctuation(word));
    }

    private static boolean containsPunctuation(String word) {
        if (word.length() <= 2) {
            return false; // No inner characters to check for punctuation
        }
        for (int i = 1; i < word.length() - 1; i++) {
            char c = word.charAt(i);
            if (!Character.isLetterOrDigit(c)) { // Check for non-alphanumeric characters
                return true;
            }
        }
        return false;
    }

}
