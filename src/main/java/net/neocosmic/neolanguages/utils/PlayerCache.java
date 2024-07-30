package net.neocosmic.neolanguages.utils;

import lombok.experimental.UtilityClass;
import net.neocosmic.neolanguages.NeoLanguages;
import net.neocosmic.neolanguages.language.PlayerLanguage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

@UtilityClass
public class PlayerCache {

    public final HashMap<UUID, PlayerLanguage> playerLanguages = new HashMap<>();

    public @NotNull PlayerLanguage getPlayerLanguage(UUID player) {
        if (!playerLanguages.containsKey(player)) {
            playerLanguages.put(player, PlayerLanguage.getDefault());
        }

        return playerLanguages.getOrDefault(player, PlayerLanguage.getDefault());
    }

    public void setPlayerLanguage(@NotNull UUID player, @NotNull PlayerLanguage language, boolean shouldUpdateIntoDB) {
        playerLanguages.put(player, language);

        if (shouldUpdateIntoDB) {
            NeoLanguages.getInstance().getDatabase().setPlayerLanguage(player.toString(), language);
        }
    }

}
