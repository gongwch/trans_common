package message;

/**
 * sample
 */
public class SampleMessageTest extends MessageTestUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printlnMessages("I00088", new Object[] { "1111" + "-" + "22222" });

		printlnMessages("I00144");

		printlnMessages("I00096", "C02829");// ˆ—‹æ•ª‚ð‘I‘ð‚µ‚Ä‚­‚¾‚³‚¢B

		printlnWord("C01698");

		printlnMessages("W00150", null, "C03988", "0");// ”—Ê > 0‚É‚µ‚Ä‚­‚¾‚³‚¢B

	}

}
