package eu.miopowered.mionge.launch.utility.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class DependencyLoader {

    public static final Dependency[] DEPENDENCIES = new Dependency[]{
            Dependency.of(
                    "com.google.code.gson",
                    "gson",
                    "2.8.6",
                    "https://repo1.maven.org/maven2/"
            ),
            Dependency.of(
                    "org.jline",
                    "jline",
                    "3.19.0",
                    "https://repo1.maven.org/maven2/"
            ),
            Dependency.of(
                    "org.apache.logging.log4j",
                    "log4j-core",
                    "2.14.1",
                    "https://repo1.maven.org/maven2/"
            )
    };

    public static final File DIRECTORY = new File("libraries");

    private static DependencyClassLoader classLoader = new DependencyClassLoader(new URL[0], DependencyLoader.class.getClassLoader());
    private static DependencyDownloader downloader = new DependencyDownloader(DIRECTORY);

    public static void loadDependencies() {
        Arrays.stream(DEPENDENCIES).forEach(dependency -> {
            downloader.downloadIfMissing(dependency);
            DependencyLoader.inject(dependency);
        });
        System.out.println();
    }

    private static void inject(Dependency dependency) {
        System.out.printf("Injecting Library %s%n", dependency.jarName());
        try {
            classLoader.addURL(DependencyLoader.downloader.path(dependency).toUri().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
