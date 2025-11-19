package de.linushuck.utopia.skyblock.tablistaddon;

import de.linushuck.utopia.skyblock.libs.api.UtopiaSkyBlockAddon;
import de.linushuck.utopia.skyblock.libs.api.aaanewstructure.events.LoadingState;
import de.linushuck.utopia.skyblock.libs.api.aaanewstructure.events.SkyBlockEvent_PlayerJoin;
import de.linushuck.utopia.skyblock.libs.api.aaanewstructure.events.SkyBlockEvent_PlayerQuit;
import de.linushuck.utopia.skyblock.libs.api.eventsystem.SkyBlockEventHandler;
import de.linushuck.utopia.skyblock.libs.essentials.Logger;
import de.linushuck.utopia.skyblock.tablistaddon.code.Tablist;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TablistAddon extends UtopiaSkyBlockAddon
{
    @Getter
    private static TablistAddon instance;
    @Getter
    private final HashMap<UUID, TablistCollectionEntry> playerTablists = new HashMap<>();
    private Tablist tablist;

    @Override
    public void onEnable()
    {
        instance = this;
        Logger.info("TablistAddon enabled");
        tablist = new Tablist(getPlugin());
        tablist.init();
    }

    @Override
    public void onDisable()
    {
        Logger.info("TablistAddon disabled");
        instance = null;
    }

    @SkyBlockEventHandler
    public boolean onPlayerJoin(SkyBlockEvent_PlayerJoin event)
    {
        if(event.getLoadingState() != LoadingState.START)
        {
            return false;
        }
        addPlayer(event.getPlayer());
        return true;
    }

    @SkyBlockEventHandler
    public boolean onPlayerQuit(SkyBlockEvent_PlayerQuit event)
    {
        if(event.getLoadingState() != LoadingState.START)
        {
            return false;
        }
        removePlayer(event.getPlayer());
        return true;
    }

    public void addPlayer(Player player)
    {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () ->
        {
            playerTablists.put(player.getUniqueId(), TablistCollection.getPlayerOrCreate(player.getUniqueId()));
            tablist.addPlayer(player);
        });
    }

    public void removePlayer(Player player)
    {
        tablist.removePlayer(player);
        if(Bukkit.isStopping())
        {
            TablistCollection.update(player.getUniqueId(), playerTablists.remove(player.getUniqueId()));
        }
        else
        {
            Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () ->
            {
                TablistCollection.update(player.getUniqueId(), playerTablists.remove(player.getUniqueId()));
            });
        }
    }
}