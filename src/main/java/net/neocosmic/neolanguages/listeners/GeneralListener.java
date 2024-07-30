package net.neocosmic.neolanguages.listeners;

import lombok.RequiredArgsConstructor;
import net.neocosmic.neolanguages.NeoLanguages;
import net.neocosmic.neolanguages.utils.PlayerCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class GeneralListener implements Listener {

    private final NeoLanguages instance;

    @EventHandler
    public void preLoginEvent(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();

        instance.getDatabase().getPlayerLanguage(uuid.toString())
                .whenComplete((language, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        return;
                    }

                    PlayerCache.setPlayerLanguage(uuid, language, false);
                });
    }

}
