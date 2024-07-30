package net.neocosmic.neolanguages.repository;

import net.neocosmic.neolanguages.language.PlayerLanguage;

import java.util.HashMap;

//                                       // The message key enum
public abstract class MessagesRepository<T extends Enum<?>> {

    private final HashMap<T, HashMap<PlayerLanguage, String>> messages = new HashMap<>();

    protected void addMessage(PlayerLanguage language, T key, String message) {
        messages.computeIfAbsent(key, k -> new HashMap<>()).put(language, message);
    }

    public String getMessage(PlayerLanguage language, T key) {
        return messages.get(key).get(language);
    }

    public String getMessage(PlayerLanguage language, T key, Object... args) {
        return String.format(messages.get(key).get(language), args);
    }

}
