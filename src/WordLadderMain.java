import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * Find the shortest word ladder between two words given a list of valid words.
 * Will prompt no solution if it is impossible to connect the two words.
 * @author Derrick Thai
 * @version December 10, 2014
 */
public class WordLadderMain
{
	public static void main(String[] args) throws IOException
	{
		// Load the word list from the text file
		List<String> wordList = new ArrayList<String>();
		BufferedReader wordFile = new BufferedReader(
				new FileReader("words.txt"));
		String nextWord;
		while ((nextWord = wordFile.readLine()) != null)
			wordList.add(nextWord.toUpperCase());
		wordFile.close();

		// Create a Scanner to get keyboard input and declare a char variable to
		// be used later to ask the user if they want to go again
		Scanner keyboard = new Scanner(System.in);
		char goAgain;

		System.out.printf("%47s%n", "Finding Word Ladders");
		do
		{
			// Get the first and last word and then start timing
			System.out.print("\nEnter the first word in the ladder: ");
			String firstWord = keyboard.nextLine().toUpperCase();
			System.out.print("Enter the last word in the ladder: ");
			String lastWord = keyboard.nextLine().toUpperCase();
			long start = System.nanoTime();

			// Ensure that the words are the same length
			int wordLength = firstWord.length();
			if (lastWord.length() == wordLength)
			{
				// Make a Set of all words of the same length as the given words
				Set<String> sameLengthWords = new HashSet<String>();
				for (String next : wordList)
					if (next.length() == wordLength)
						sameLengthWords.add(next);

				// Create a Queue and add the initial WordLadder
				Queue<WordLadder> queue = new LinkedList<WordLadder>();
				WordLadder initialLadder = new WordLadder(firstWord);
				queue.add(initialLadder);

				// The initial WordLadder is the solution if the first word and
				// last word are the same
				WordLadder solution = null;
				if (initialLadder.endsWith(lastWord))
					solution = initialLadder;

				// Until there is a solution or until the Queue is empty:
				while (solution == null && !queue.isEmpty())
				{
					// Remove the next WordLadder from the Queue and generate
					// all of the WordLadders that have a new word that is one
					// letter different than its last word
					WordLadder nextLadder = queue.remove();
					List<WordLadder> generatedLadders = nextLadder
							.generateLadders(sameLengthWords);

					// Check for a solution by comparing each generated
					// WordLadder's last word with the target last word
					for (WordLadder ladder : generatedLadders)
						if (ladder.endsWith(lastWord))
							solution = ladder;
						else
							// Add the new, one length longer, WordLadders to
							// the queue if it is not a solution
							queue.add(ladder);
				}

				// Stop timing and display the solution if there is one or tell
				// the user there is no solution if there isn't one
				long stop = System.nanoTime();
				if (solution != null)
					System.out.printf("A %d-word ladder starting with %s "
							+ "and ending with %s is:%n%n%s%n",
							solution.length(), firstWord, lastWord, solution);
				else
					System.out.println("No word ladder found for these words");

				// Display the run time is milliseconds
				System.out.println("Run time: " + (stop - start) / 1000000
						+ " ms");
			}
			else
				// Notify the user that the words are not the same length
				System.out.println("Words must be the same length");

			// Ask the user if they would like to find another word ladder and
			// if so, re-run the loop
			System.out
					.print("\nWould you like to find another word ladder? (Y/N): ");
			String response = keyboard.nextLine();
			while (response.length() == 0
					|| (goAgain = Character.toUpperCase(response.charAt(0))) != 'Y'
					&& goAgain != 'N')
			{
				System.out
						.print(" * Invalid input, find another word ladder? (Y/N): ");
				response = keyboard.nextLine();
			}
		}
		while (goAgain == 'Y');

		// Closing remarks
		System.out
				.println("Thank you for using the \"Finding Word Ladders\" program");
		keyboard.close();
	}
}
