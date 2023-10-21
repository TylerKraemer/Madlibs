import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.lang.String;

public class Madlibs {
    /**
     * This function sets the category equal to a number to be called later on by an if else statement so it knows what to return.
     */
    public static final int adj = 0;
    /**
     * This function sets the category equal to a number to be called later on by an if else statement so it knows what to return.
     */
    public static final int singnoun = 1;
    /**
     * This function sets the category equal to a number to be called later on by an if else statement so it knows what to return.
     */
    public static final int plunoun = 2;
    /**
     * This function sets the category equal to a number to be called later on by an if else statement so it knows what to return.
     */
    public static final int singverb = 3;
    /**
     * This function sets the category equal to a number to be called later on by an if else statement so it knows what to return.
     */
    public static final int pluverb = 4;
    /**
     * This function sets the category equal to a number to be called later on by an if else statement so it knows what to return.
     */
    public static final int adv = 5;
    /**
     * This function sets the category equal to a number to be called later on by an if else statement so it knows what to return.
     */
    public static final int pastverb = 6;


    /**
     * This function prints the welcome banner for the program
     */
    public static void printWelcome() {
        System.out.println("*****************************************************************");
        System.out.println("*                   Welcome to Madlibs V1.0                     *");
        System.out.println("*****************************************************************");
        return;
    }

    /**
     * This fucntion stores the wordLists as an array
     */
    public static List<List<String>> wordLists;

    /**
     * This function imports the words from the .txt files.
     * @param folderName this is the name of the folder the user inputs as where the .txt files are stored
     * @return this returns the words from the .txt files to a string.
     */
    public static String importWords(String folderName) {
        String[] categories = {"adj", "singnoun", "plunoun", "singverb", "pluverb", "adv", "pastverb"};
        wordLists = new ArrayList<>();
        String word;
        List<String> words = null;
        for (String category : categories) {
            String fileName = folderName + "\\" + category + ".txt";
            words = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while ((word = reader.readLine()) != null) {
                    words.add(word);
                }
            } catch (IOException e) {
                //
                // e.printStackTrace();
            }

            wordLists.add(words);
        }
        return words.toString();
    }


    /**
     * This function writes the code to create the madlib and replace the categories with random words from that category text file.
     * @param storyFileName this is the file name the stories are stores at
     * @return this returns the madlib as a string
     */
    public static String createMadlib(String storyFileName) {
        StringBuilder madlib = new StringBuilder();
        Random random = new Random();
        try (BufferedReader reader = new BufferedReader(new FileReader(storyFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (word.startsWith("<")) {
                        int categoryIndex = getCategoryIndex(word.toLowerCase());
                        List <String> wordList = wordLists.get(categoryIndex);
                        String randomWord = wordList.get(random.nextInt(wordList.size()));
                        madlib.append(randomWord).append(" ");
                    } else {
                            madlib.append(word).append(" ");
                        }
                }
                madlib.append(("\n"));
            }
        } catch(Exception ex) {
            System.out.println("File was not found.");
        }

        return madlib.toString();
    }

    /**
     * This function reads which category is being prompted in the madlib and returns the appropriate category
     * @param category this is the string that will store which category is being prompted in the madlib
     * @return this returns the correct category to have the random word from the category replace the madlib
     */
    private static int getCategoryIndex(String category) {
       // System.out.println("Category: " + category);
        category = category.toLowerCase();
        if (category.equals("<adj>")) {
            return 0;
        } else if (category.equals("<singnoun>")) {
            return 1;
        } else if (category.equals("<plunoun>")) {
            return 2;
        } else if (category.equals("<singverb>")) {
            return 3;
        } else if (category.equals("<pluverb>")) {
            return 4;
        } else if (category.equals("<adv>")) {
            return 5;
        } else if (category.equals("<pastverb>")) {
            return 6;
        } else if (category.equals("<singnoun>,")) {
            return 1;
        } else if (category.equals("<singnoun>.")) {
            return 1;
        } else if (category.equals("<adj>,")) {
            return 0;
        } else if (category.equals("<adj>.")) {
            return 0;
        } else if (category.equals("<plunoun>.")) {
            return 2;
        } else if (category.equals("<plunoun>,")) {
            return 2;
        } else if (category.equals("<singverb>,")) {
            return 3;
        } else if (category.equals("<singverb>.")) {
            return 3;
        } else if (category.equals("<pluverb>,")) {
            return 4;
        } else if (category.equals("<pluverb>.")) {
            return 4;
        } else if (category.equals("<adv>.")) {
            return 5;
        } else if (category.equals("<adv>,")) {
            return 5;
        } else if (category.equals("<pastverb>.")) {
            return 6;
        } else if (category.equals("<pastverb>,")) {
            return 6;
        } else {
            return -1;
        }



    }

    /**
     * This is the main function for the program that prints all the fucntions and also prints the menu for the user to use.
     * @param args
     */
    public static void main(String[] args) {

        printWelcome();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the folder where the stories and wordlists are.");
        System.out.print("Or just press Enter to accept the default location: ");
        String folderName = scanner.nextLine();

        if (folderName.trim().isEmpty()) {
            folderName = "C:\\temp\\m1";
        }

        importWords(folderName);


        while (true) {
            System.out.print("Enter a story number or q to quit: ");
            String userChoice = scanner.nextLine();
            if (userChoice.equals("q") || userChoice.equals("Q")) {
                System.out.println("Thank you for using this program.");
                break;
            }

            try {
                int storyNumber = Integer.parseInt(userChoice);
                String storyFileName = folderName + "\\" + "story" + storyNumber + ".txt";
                String madlib = createMadlib(storyFileName);
                System.out.println("Here is your Madlib: \n" + madlib);
            } catch (Exception ex) {
                System.out.println("That story does not exist. Choose again.");
            }
        }

        scanner.close();

    }

}
