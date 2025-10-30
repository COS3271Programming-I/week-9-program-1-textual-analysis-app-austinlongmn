import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

class Main {

  public static void main(String[] args) {
    final String path = "data/text.txt";

    int numberOfLetters = 0;
    int numberOfWords = 0;
    int numberOfSentences = 0;
    boolean lastCharacterWasWhitespace = false;

    // Inspired by https://stackoverflow.com/a/811860/22407308
    try (InputStream inputStream = new FileInputStream(path)) {
      Reader reader = new BufferedReader(new InputStreamReader(inputStream));
      int value;
      String str;

      while ((value = reader.read()) != -1) {
        str = String.valueOf((char) value);

        if (str.matches("\\w")) {
          numberOfLetters++;
          lastCharacterWasWhitespace = false;
        }
        if (str.matches("\\s") && !lastCharacterWasWhitespace) {
          numberOfWords++;
          lastCharacterWasWhitespace = true;
        }
        if (str.matches("[.!?]")) {
          numberOfSentences++;
          lastCharacterWasWhitespace = false;
        }
      }
    } catch (IOException e) {
      System.err.format("I/O Error: %s", e.getMessage());
      System.exit(1);
    }

    System.out.format("------------ FILE STATISTICS ------------\n");
    System.out.format("File name:               %s\n", path);
    System.out.format("Number of letters:       %d\n", numberOfLetters);
    System.out.format("Number of words:         %d\n", numberOfWords);
    System.out.format("Number of sentences:     %d\n", numberOfSentences);
    System.out.format(
      "Avg. letters per word:   %.1f\n",
      ((double) numberOfLetters / (double) numberOfWords)
    );

    System.out.format(
      "Avg. words per sentence: %.1f\n",
      ((double) numberOfWords / (double) numberOfSentences)
    );
    System.out.format("---------- END FILE STATISTICS ----------\n");
  }
}
