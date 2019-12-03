package com.battleship.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Verify {

	public static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9]{0,100}$", Pattern.CASE_INSENSITIVE);

	public default boolean isUsername(String userStr) {
		Matcher matcher = VALID_USERNAME_REGEX.matcher(userStr);
		return matcher.find();
	}

}