package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
        text = text.toLowerCase();
        Pattern words = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b");
        Matcher matcher = words.matcher(text);
        TreeMap<String, Integer> treeMap = new TreeMap<>(Comparator.naturalOrder());
        while (matcher.find()){
            Integer integer = treeMap.putIfAbsent(matcher.group(), 1);
            if (integer != null) treeMap.put(matcher.group(), integer+1);
        }
        StringBuilder result = new StringBuilder();
        treeMap.forEach((k,v) -> {
            result.append(k).append(" ").append(v).append("\n");
        });
        return result.toString();
    }
}
