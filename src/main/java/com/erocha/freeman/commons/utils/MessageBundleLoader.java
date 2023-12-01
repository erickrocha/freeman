package com.erocha.freeman.commons.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundleLoader {

	public static final String MESSAGE_PATH = "com.erocha.freeman.commons.resources.messages";

	private static final HashMap<String, ResourceBundle> messageBundles = new HashMap<>();

	private MessageBundleLoader(){}


	public static String getMessage(String key) {
		if (key == null) {
			return null;
		}
		try {
			Locale locale = Locale.ENGLISH;
			ResourceBundle messages = messageBundles.get(locale.toString());
			if (messages == null) {
				messages = ResourceBundle.getBundle(MESSAGE_PATH, locale);
				messageBundles.put(locale.toString(), messages);
			}
			return messages.getString(key);
		} catch (Exception e) {
			return key;
		}
	}
}
