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


    protected NeoLanguages() {
        instance = this;

        this.database = new Database(
                "localhost",
                "3306",
                "root",
                "password"
        );
    }

    public static void init(JavaPlugin javaPlugin) {
        new NeoLanguages();

        javaPlugin.getServer().getPluginManager().registerEvents(new GeneralListener(instance), javaPlugin);
    }

}
