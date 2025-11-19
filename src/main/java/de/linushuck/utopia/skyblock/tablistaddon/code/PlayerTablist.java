package de.linushuck.utopia.skyblock.tablistaddon.code;

import de.linushuck.utopia.skyblock.libs.api.models.pair.Pair;
import de.linushuck.utopia.skyblock.libs.api.tablist.item.*;
import de.linushuck.utopia.skyblock.libs.api.tablist.tablist.TableTabList;
import de.linushuck.utopia.skyblock.libs.api.tablist.util.Skin;
import de.linushuck.utopia.skyblock.libs.api.tablist.util.Skins;
import de.linushuck.utopia.skyblock.tablistaddon.TablistAddon;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerTablist
{
    @Getter
    private static final Pattern pattern = Pattern.compile("<([^>\\s]+)\\s*([^>]*)>(.*?)<\\/\\1>");
    @Getter
    private static final Pattern deserliazePattern = Pattern.compile("(\\d+_\\d+):\\s*\\[([^\\]]+)\\]");
    private static final Map<String, List<TablistComponent>> tabListCaches = new HashMap<>();
    private static final int[] PLAYERS_TABLIST = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40};

    private final TableTabList tab;
    private final Tablist instance;
    private final Player player;

    public PlayerTablist(Player player, Tablist instance)
    {
        this.instance = instance;
        this.player = player;
        tab = instance.getTablist().newTableTabList(player, 4, Tablist.minWidth, Tablist.minWidth);
        tab.enable();
        TabItem item1 = new TextTabItem(ChatColor.GREEN + "            Players", Skins.GREEN);
        tab.set(0, 0, item1);
        tab.set(1, 0, item1);
        tab.setBatchEnabled(true);
        updateHeader(ChatColor.GRAY + "[" + ChatColor.GOLD + "Utopia Network" + ChatColor.GRAY + "]");
        updateFooter(ChatColor.YELLOW + "Currently playing: " + ChatColor.GRAY + "[" + ChatColor.AQUA + "SkyBlock" + ChatColor.GRAY + "]\nNew Line");
        updateFull();
    }

    public static void removeCache(String rowString)
    {
        tabListCaches.remove(rowString);
    }

    public static void serializeRow(int row, String rowString, StringBuilder builder)
    {
        builder.append("-----------------------Row ").append(row).append("-----------------------\n");
        Matcher matcher = PlayerTablist.getPattern().matcher(rowString);
        int i = 0;
        while(matcher.find())
        {
            String line = matcher.group();
            i++;
            int decimalsOfLineNumber = String.valueOf(i).length();
            builder.append(row)
                    .append("_")
                    .append(i)
                    .append(decimalsOfLineNumber == 1 ? ":  " : ": ")
                    .append("[")
                    .append(line)
                    .append("]")
                    .append(matcher.hitEnd() ? "" : "\n");
        }
        builder.append("---------------------------------------------------").append(row == 3 ? "\n\n" : "");
    }

    public static Pair<StringBuilder, StringBuilder> deserializeRow(String tabData) throws Exception
    {
        StringBuilder row3 = new StringBuilder();
        StringBuilder row4 = new StringBuilder();
        Matcher matcher = deserliazePattern.matcher(tabData);
        int row3Length = 0;
        int row4Length = 0;
        while(matcher.find())
        {
            String[] rowAndLineNumber = matcher.group(1).split("_");
            String line = matcher.group(2);
            int rowNumber = Integer.parseInt(rowAndLineNumber[0]);
            int lineNumber = Integer.parseInt(rowAndLineNumber[1]);
            switch(rowNumber)
            {
                case 3 ->
                {
                    row3.append(line);
                    row3Length++;
                }
                case 4 ->
                {
                    row4.append(line);
                    row4Length++;
                }
            }
        }

        if(row3Length != 20)
        {
            throw new Exception("Sorry but your import failed cause: Incorrect row line length Received " + row3Length + " | Expected 20 | Row 3");
        }
        else if(row4Length != 20)
        {
            throw new Exception("Sorry but your import failed cause: Incorrect row line length Received " + row4Length + " | Expected 20 | Row 4");
        }
        return new Pair<>(row3, row4);
    }

    public void updateFull()
    {
        updatePlayers();
        updateCustom();
    }

    public void updateCustom()
    {
        updateColumnThree();
        updateColumnFour();
    }

    public void updateHeader(String header)
    {
        tab.setHeader(header);
    }

    public void updateFooter(String footer)
    {
        tab.setFooter(footer);
    }

    public void updatePlayers()
    {
        int i = 0;
        ArrayList<PlayerTabItem> playerTabItems = new ArrayList<>(instance.tabItems.values());
        for(int slot : PLAYERS_TABLIST)
        {
            if(i >= playerTabItems.size())
            {
                tab.set(slot, new BlankTabItem());
                continue;
            }
            tab.set(slot, playerTabItems.get(i++));
        }
        tab.batchUpdate();
    }

    public void updateColumnThree()
    {
        match(TablistAddon.getInstance().getPlayerTablists().get(player.getUniqueId()).getRow1(), 2);
    }

    public void updateColumnFour()
    {
        match(TablistAddon.getInstance().getPlayerTablists().get(player.getUniqueId()).getRow2(), 3);
    }

    public void match(String rowString, int row)
    {
        List<TablistComponent> tabListCache = tabListCaches.get(rowString);
        boolean doMatch = false;

        if(tabListCache == null)
        {
            tabListCaches.put(rowString, tabListCache = new ArrayList<>());
            doMatch = true;
        }

        if(doMatch)
        {
            Matcher matcher = pattern.matcher(rowString);
            while(matcher.find())
            {
                String tag = matcher.group(1);
                String option = matcher.group(2);
                String content = matcher.group(3);
                tabListCache.add(new TablistComponent(tag, option, content));
            }
        }
        for(int i = 0; i < tabListCache.size(); i++)
        {
            TablistComponent component = tabListCache.get(i);

            String tag = component.getTag();
            String option = component.getOption();
            String content = component.getContent();

            // Retrieve the entry for the tag and add it to the list
            TablistEntry entry = CustomTablistComponentInitializer.getEntry(tag);
            Skin skin = Skins.DEFAULT_SKIN;
            if(option != null && !option.isEmpty() && !option.isBlank())
            {
                String[] options = option.split(",");
                for(String opt : options)
                {
                    opt = opt.trim();
                    String[] parts = opt.split("=");
                    if(parts.length != 2)
                    {
                        continue;
                    }
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    switch(key)
                    {
                        case "color" ->
                        {
                            skin = Skins.getByName(value);
                        }
                    }
                }
            }
            TextTabItem tabItem = entry.get(player, content, skin);
            tab.set(row, i, tabItem);
        }
        tab.batchUpdate();
    }


}
