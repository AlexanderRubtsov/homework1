package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName)  {
            List list = new ArrayList();
            File dir = new File(pluginDirName);
            if (!dir.isDirectory()) throw new IllegalArgumentException();
        File[] files = dir.listFiles(new MyFileNameFilter(PLUGIN_EXT));
        try {
            for (File file: files){
                String name = file.getName();
                URI uri = file.toURI();
                URL url = uri.toURL();
                URL[] urls = new URL[]{url};
                Class<?> aClass = null;
                URLClassLoader classLoader = new URLClassLoader(urls);
                URL[] urLs = classLoader.getURLs();
                JarFile jarFile = new JarFile(file);
                Enumeration<JarEntry> e = jarFile.entries();
                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if (je.isDirectory() || !je.getName().endsWith(".class")) {
                        continue;
                    }
                    // -6 because of .class
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class c = classLoader.loadClass(className);

                    list.add(c);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        // TODO: NotImplemented
        return list;
    }

    public static class MyFileNameFilter implements FilenameFilter{

        private String ext;

        public MyFileNameFilter(String ext){
            this.ext = ext.toLowerCase();
        }
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }
}
