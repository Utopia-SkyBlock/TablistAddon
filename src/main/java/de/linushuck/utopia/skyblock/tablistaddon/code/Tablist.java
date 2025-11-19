package de.linushuck.utopia.skyblock.tablistaddon.code;

import de.linushuck.utopia.skyblock.libs.api.tablist.Tabbed;
import de.linushuck.utopia.skyblock.libs.api.tablist.item.PlayerTabItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Tablist
{
    protected static final int minWidth = 35;
    private static final int max = 3;
    private static final int updateInterval = 30;
    protected final ArrayList<HashMap<Player, PlayerTablist>> betterTabs = new ArrayList<>();
    protected final HashMap<Player, PlayerTabItem> tabItems = new HashMap<>();
    private final Tabbed tabbed;
    protected boolean somethingChanged = false;
    private PlayerTabItem.PlayerProvider<String> playerNameTextProvider;
    private int currentIndex = 0;

    public Tablist(JavaPlugin plugin)
    {
        tabbed = new Tabbed(plugin);
    }

    public void init()
    {
        playerNameTextProvider = Player::getName;
        for(int i = 0; i < max; i++)
        {
            betterTabs.add(new HashMap<>());
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(tabbed.getPlugin(), () ->
        {
            if(somethingChanged)
            {
                somethingChanged = false;
                for(HashMap<Player, PlayerTablist> tabs : betterTabs)
                {
                    for(Player player : tabs.keySet())
                    {
                        tabs.get(player).updatePlayers();
                    }
                }
            }
            HashMap<Player, PlayerTablist> tabs = betterTabs.get(currentIndex);
            for(Player player : tabs.keySet())
            {
                tabs.get(player).updateColumnThree();
                tabs.get(player).updateColumnFour();
            }
            currentIndex++;
            if(currentIndex >= max)
            {
                currentIndex = 0;
            }

        }, 0, updateInterval / max);
    }

    public void unload()
    {
        betterTabs.forEach(HashMap::clear);
        tabItems.clear();
    }

    public void addPlayer(Player player)
    {
        int indexWithLeastPlayers = 0;
        for(int i = 1; i < max; i++)
        {
            if(this.betterTabs.get(i).size() < this.betterTabs.get(indexWithLeastPlayers).size())
            {
                indexWithLeastPlayers = i;
            }
        }
        betterTabs.get(indexWithLeastPlayers).put(player, new PlayerTablist(player, this));
        tabItems.put(player, new PlayerTabItem(player, playerNameTextProvider));
        somethingChanged = true;
    }

    public Tabbed getTablist()
    {
        return tabbed;
    }

    public void removePlayer(Player player)
    {
        betterTabs.forEach(tab -> tab.remove(player));
        tabItems.remove(player);
        tabbed.destroyTabList(player);
        somethingChanged = true;
    }

    public void updateTabItem(Player player)
    {
        tabItems.get(player).updateText();
        somethingChanged = true;
    }

    public PlayerTablist getTabList(Player player)
    {
        for(HashMap<Player, PlayerTablist> map : betterTabs)
        {
            PlayerTablist playerTablist = map.get(player);
            if(playerTablist == null)
            {
                continue;
            }
            return playerTablist;
        }
        return null;
    }
}
