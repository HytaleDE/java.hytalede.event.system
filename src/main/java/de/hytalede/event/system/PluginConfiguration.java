package de.hytalede.event.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PluginConfiguration {
    private Path dataDirectory;
    private File configFile;
    private Properties properties;

    public PluginConfiguration(Path dataDirectory, Properties properties) {
        this.dataDirectory = dataDirectory;
        this.configFile = new File(Path.of(dataDirectory.toString(), "config.properties").toString());
        this.properties = properties;
        this.initProperties();
    }

    private void initProperties() {
        if (this.configFile.exists()) {
            this.loadExistingProperties();
        } else {
            this.saveDefaultProperties();
        }
    }

    private void loadExistingProperties() {
        try {
            this.properties.load(new FileInputStream(configFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveDefaultProperties() {
        try {
            Files.createDirectories(Path.of(this.dataDirectory.toString()));
            this.properties.store(new FileOutputStream(configFile), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
