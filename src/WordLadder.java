import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Keeps track of a WordLadder's information such as its last word, previous
 * WordLadder, and length. Can generate all WordLadders that have an extra word
 * one letter different than the last word of this WordLadder and can display
 * this WordLadder in a vertical format.
 * @author Derrick Thai
 * @version December 10, 2014
 */
public class WordLadder
{
	// Instance variables to keep track of this WordLadder's previous
	// WordLadder, last word, and length
	private WordLadder previous;
	private String lastWord;
	private int length;

	/**
	 * Constructs a new WordLadder object of length one with the given starting
	 * word. This WordLadder is the first link therefore it does not have a
	 * previous WordLadder.
	 * @param startWord the first word of the WordLadder
	 */
	public WordLadder(String startWord)
	{
		lastWord = startWord;
		length = 1;
	}

	/**
	 * Constructs a new WordLadder object with the given last word of the ladder
	 * and given WordLadder in which this WordLadder is linked to.
	 * @param previous the WordLadder in which this WordLadder is linked to
	 * @param lastWord the last word of this WordLadder
	 */
	public WordLadder(WordLadder previous, String lastWord)
	{
		this.previous = previous;
		this.lastWord = lastWord;
		length = previous.length + 1;
	}

	/**
	 * Determines if this WordLadder ends with the given word.
	 * @param endWord the word to check if this WordLadder ends in
	 * @return true if this WordLadder ends in the given word or false if not
	 */
	public boolean endsWith(String endWord)
	{
		return lastWord.equals(endWord);
	}

	/**
	 * Returns the length of this WordLadder.
	 * @return the length of this WordLadder
	 */
	public int length()
	{
		return length;
	}

	/**
	 * Generates all of the WordLadders that have a new word that is one letter
	 * different than this WordLadder's last word.
	 * @param wordSet a Set of valid words that are all of the same length as
	 *            the words in this WordLadder
	 * @return the List of all of the WordLadders that have a new word that is
	 *         one letter different than this WordLadder's last word
	 */
	public List<WordLadder> generateLadders(Set<String> wordSet)
	{
		// Create a List to store the new WordLadders and remove this
		// WordLadder's last word from the valid word Set
		List<WordLadder> newLadders = new ArrayList<WordLadder>();
		int wordLength = lastWord.length();
		wordSet.remove(lastWord);

		// Change each letter of this WordLadder's last word to every letter in
		// the alphabet and see if the new word created is in the word Set
		for (int pos = 0; pos < wordLength; pos++)
			for (char ch = 'A'; ch <= 'Z'; ch++)
			{
				StringBuilder wordToChange = new StringBuilder(lastWord);
				wordToChange.setCharAt(pos, ch);
				String newWord = wordToChange.toString();

				// Once a word made is contained in the word Set, make a new
				// WordLadder with the new word and add it to the List (after,
				// remove the word used from the valid word Set)
				if (wordSet.contains(newWord))
				{
					newLadders.add(new WordLadder(this, newWord));
					wordSet.remove(newWord);
				}
			}

		return newLadders;
	}

	/**
	 * Returns a String representation of this WordLadder.
	 * @return all of the words in this WordLadder, each on separate lines, with
	 *         the first word at the top and the last word at the bottom
	 */
	public String toString()
	{
		// In a recursive fashion, return this WordLadder's previous
		// WordLadder's last word plus this WordLadder's last word
		if (this.previous != null)
			return String.format("%s%s%n", previous.toString(), lastWord);
		else
			return String.format("%s%n", lastWord);
	}
}
