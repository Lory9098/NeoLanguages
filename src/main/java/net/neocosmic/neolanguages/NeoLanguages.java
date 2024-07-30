package net.neocosmic.neolanguages;


import lombok.Getter;
import net.neocosmic.neolanguages.database.Database;
import net.neocosmic.neolanguages.listeners.GeneralListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class NeoLanguages {

    @Getter
    private static NeoLanguages instance;
    private final Database database;


    protected NeoLanguages(String host, String port, String user, String password) {
        instance = this;

        this.database = new Database(
                host,
                port,
                user,
                password
        );
    }

    public static void init(String host, String port, String user, String password, JavaPlugin javaPlugin) {
        new NeoLanguages(host, port, user, password);

        javaPlugin.getServer().getPluginManager().registerEvents(new GeneralListener(instance), javaPlugin);
    }

}
