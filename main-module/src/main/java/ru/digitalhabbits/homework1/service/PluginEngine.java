package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        Object invoke;
        try {
            Constructor<T> constructor = cls.getConstructor();
            T t = constructor.newInstance();
            Method apply = cls.getDeclaredMethod("apply", String.class);
            invoke = apply.invoke(t, text);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            invoke = e.getMessage();
        }
        return (String) invoke;
    }
}
