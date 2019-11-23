import java.util.Random;

public class LCSVerify {

    public static void main(String[] args) {

        //testing strings of different length
        String test1 = "jfieoslcodyspqaldx";
        String test2 = "spqakscodyld";
        String result1 = LCSBruteForce(test1, test2);
        String result2 = LCSFaster(test1, test2);
        System.out.println("Brute Force: " + result1);
        System.out.println("Faster: " + result2);
        if(result1.equals(result2) )
            System.out.println("Answers same!");
        else
            System.out.println("Answers different!");

        //testing strings with no common substring
        test1 = "abababababababababababab";
        test2 = "cdcdcdcdcdcdcdc";
        result1 = LCSBruteForce(test1, test2);
        result2 = LCSFaster(test1, test2);
        System.out.println("Brute Force: " + result1);
        System.out.println("Faster: " + result2);
        if(result1.equals(result2) )
            System.out.println("Answers same!");
        else
            System.out.println("Answers different!");

        //testing strings with a few short matching strings
        test1 = "pPjJksm23msDOGkskowkkc";
        test2 = "125iroUrbsLaoclM<wiod213DOGpso";
        result1 = LCSBruteForce(test1, test2);
        result2 = LCSFaster(test1, test2);
        System.out.println("Brute Force: " + result1);
        System.out.println("Faster: " + result2);
        if(result1.equals(result2) )
            System.out.println("Answers same!");
        else
            System.out.println("Answers different!");

        //paris with a few diff matching substrings 5-10
        test1 = "clockmississippiJuMpBLACKcathouSecoffee1234567";
        test2 = "houSeBLACK1234567mississippicoffeeclockJuMpcat";
        result1 = LCSBruteForce(test1, test2);
        result2 = LCSFaster(test1, test2);
        System.out.println("Brute Force: " + result1);
        System.out.println("Faster: " + result2);
        if(result1.equals(result2) )
            System.out.println("Answers same!");
        else
            System.out.println("Answers different!");

        //pairs with matching substrings >half length of longest of two strings
        test1 = "52jdksmynameiscodyschroederamsl";
        test2 = "15mynameiscodyschroeder7861";
        result1 = LCSBruteForce(test1, test2);
        result2 = LCSFaster(test1, test2);
        System.out.println("Brute Force: " + result1);
        System.out.println("Faster: " + result2);
        if(result1.equals(result2) )
            System.out.println("Answers same!");
        else
            System.out.println("Answers different!");

        //test randomly created long strings with a random substring inserted in a random area of the second string
        int n = 10000;
        test1 = createRandomStrings(n);
        test2 = createRandomStrings(n);
        test2 = insertSubString(test1, test2);
        result1 = LCSBruteForce(test1, test2);
        result2 = LCSFaster(test1, test2);
        System.out.println("Brute Force: " + result1);
        System.out.println("Faster: " + result2);
        if(result1.equals(result2) )
            System.out.println("Answers same!");
        else
            System.out.println("Answers different!");

    }

    //implemented from video, could not get instances to work correctly... so improvised. returns correct substring tho
    public static String LCSBruteForce(String s1, String s2) {

        //initialize lengths, index trackers, answer string
        int length1 = s1.length();
        int length2 = s2.length();
        int lcsLength = 0;
        int s1Start = 0;
        int s2Start = 0;
        String answer = "";

        //use two loops to loop through possibilities
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                //use third loop to loop over matching characters of both
                for (int k = 0; k < Math.min(length1 - i, length2 - j); k++) {
                    //if no match, break
                    if (s1.charAt(i + k) != s2.charAt(j + k))
                        break;
                    //if length of matching substrings larger than before, save length and starting indexes
                    if (k + 1 > lcsLength) {
                        lcsLength = k + 1;
                        s1Start = i;
                        s2Start = j;
                    }
                }
            }

        }
        if (lcsLength == 0) {
            answer = "No Common Substring";
            return answer;
        }
        else {
            answer = s1.substring(s1Start, s1Start + lcsLength);
            return answer;
        }
    }

    //implemented using dynamic programming from here: https://www.geeksforgeeks.org/longest-common-substring-dp-29/
    public static String LCSFaster(String s1, String s2) {

        //get lengths, set lcsLength, answer string
        int length1 = s1.length();
        int length2 = s2.length();
        int lcsLength = 0;
        String answer = "";

        //create a table to store lengths of longest common suffixes of substrings
        int[][] LCSuffix = new int[length1 + 1][length2 + 1];

        //store index of cell which has max value
        int row = 0, col = 0;

        //build 2d array from bottom up
        for (int i = 0; i <= length1; i++) {
            for (int j = 0; j <= length2; j++) {
                if (i == 0 || j == 0)
                    LCSuffix[i][j] = 0;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    LCSuffix[i][j] = LCSuffix[i - 1][j - 1] + 1;
                    if (lcsLength < LCSuffix[i][j]) {
                        lcsLength = LCSuffix[i][j];
                        row = i;
                        col = j;
                    }
                }
                else
                    LCSuffix[i][j] = 0;
            }
        }
        // if true, then no common substring exists
        if (lcsLength == 0) {
            answer = "No Common Substring";
            return answer;
        }

        // traverse up diagonally form the (row, col) cell
        // until LCSuffix[row][col] != 0
        while (LCSuffix[row][col] != 0) {
            answer = s1.charAt(row - 1) + answer; // or Y[col-1]
            --lcsLength;

            // move diagonally up to previous cell
            row--;
            col--;
        }

        return answer;
    }

    //generate random alphanumeric string, implemented from here: https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    public static String createRandomStrings(int size){

        //choose a random character from this string
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        //create buffer for size of string
        StringBuilder sb = new StringBuilder(size);
        //loop thru adding a character each time until size is reached
        for(int i = 0; i < size; i++){
            int index = (int)(alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    //insert a substring of one string into the other
    public static String insertSubString(String s1, String s2){

        //create random size
        int size = (int)(s1.length() * Math.random());
        //set bounds
        int lower = 0;
        int upper = s1.length() - size;
        //create random index for insertion
        Random random = new Random();
        int index = lower + (int)(random.nextFloat() * (upper - lower + 1));
        //insert in other string and return new string
        String insertion = s1.substring(index, index + size);
        StringBuffer newString = new StringBuffer(s2);
        newString.insert((int)random.nextFloat(), insertion);
        return newString.toString();

    }
}
