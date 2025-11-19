package de.linushuck.utopia.skyblock.tablistaddon.code;

import de.linushuck.utopia.skyblock.libs.api.tablist.item.TextTabItem;
import de.linushuck.utopia.skyblock.libs.api.tablist.util.Skin;
import org.bukkit.entity.Player;

public interface TablistEntry
{
    TextTabItem get(Player player, String arg, Skin skin);
}
