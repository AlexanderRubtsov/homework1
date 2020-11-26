package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
        int countReadline = 1;
        int countWords = 0;
//        int countLetters = 0;
        Pattern readline = Pattern.compile("\n");
        Pattern words = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b");
//        Pattern letters = Pattern.compile("\\w");
        Matcher readR = readline.matcher(text);
        Matcher readW = words.matcher(text);
//        Matcher readL = letters.matcher(text);
//        while (readL.find()) countLetters++;
        while (readW.find()) countWords++;
        while (readR.find()) countReadline++;
        return String.format("%d;%d;%d", countReadline, countWords, text.length());
    }
}
