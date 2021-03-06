package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher to match text (String) to given regular expression.
 * 
 * @author rhopp
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.common.matcher.RegexMatcher}
 *
 */
@Deprecated
public class RegexMatcher extends TypeSafeMatcher<String> {

	private final String regex;

	/**
	 * Default constructor.
	 * 
	 * @param regex
	 *            regular expression to match against.
	 */

	public RegexMatcher(String regex) {
		this.regex = regex;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("regular expression \"" +
			(regex == null ? "null" : regex)	+ "\"");
	}

	@Override
	protected boolean matchesSafely(String textToMatch) {
		return textToMatch.matches(regex);
	}

	@Override
	public String toString() {
		return "Matcher matching text to regular expression '" +
			(regex == null ? "null" : regex) + "'";
	}

}
