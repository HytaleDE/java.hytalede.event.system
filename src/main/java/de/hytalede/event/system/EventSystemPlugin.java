package de.hytalede.event.system;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import de.hytalede.event.system.commands.EventCommand;

import javax.annotation.Nonnull;
import java.util.Properties;

public class EventSystemPlugin extends JavaPlugin {
    private Properties pluginProperties = new Properties();
    private PluginConfiguration pluginConfiguration;

    public EventSystemPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        this.pluginConfiguration = new PluginConfiguration(this.getDataDirectory(), pluginProperties);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new EventCommand("event", "Play and join events."));
    }
}