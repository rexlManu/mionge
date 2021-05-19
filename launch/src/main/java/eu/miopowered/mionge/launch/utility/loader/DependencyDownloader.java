package eu.miopowered.mionge.launch.utility.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DependencyDownloader {

    private File directory;

    public DependencyDownloader(File directory) {
        this.directory = directory;

        if (!this.directory.exists()) this.directory.mkdir();
    }

    public Path path(Dependency dependency) {
        return Paths.get(this.directory.toString(), dependency.jarName());
    }

    public void download(Dependency dependency) {
        try {
            InputStream in = new URL(dependency.downloadUrl()).openStream();
            Files.copy(in, path(dependency), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.printf("A error happen while downloading %s%n", dependency.jarName());
        }
    }

    public void downloadIfMissing(Dependency dependency) {
        if (path(dependency).toFile().exists()) {
            return;
        }
        System.out.printf("Downloading Library %s%n", dependency.jarName());
        this.download(dependency);
    }
}
