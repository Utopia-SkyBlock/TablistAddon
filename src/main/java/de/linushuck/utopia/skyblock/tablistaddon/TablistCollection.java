package de.linushuck.utopia.skyblock.tablistaddon;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.linushuck.utopia.skyblock.libs.api.PublicSkyBlockAPI;

import java.util.UUID;

public class TablistCollection
{
    private static final String collectionName = "tablist_addon";

    public static TablistCollectionEntry getPlayerOrCreate(UUID playerUUID)
    {
        TablistCollectionEntry existing = getCollection().find(Filters.eq("playerUUID", playerUUID)).first();
        if(existing != null)
        {
            return existing;
        }

        TablistCollectionEntry newEntry = new TablistCollectionEntry();
        newEntry.setPlayerUUID(playerUUID);
        newEntry.setRow1("<titel.server color=orange></titel.server><custom color=aqua>This is a Test</custom><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty><empty></empty>");
        newEntry.setRow2("<titel.account color=aqua></titel.account><custom color=aqua>§eSkills:§r</custom><skill color=orange>Mining</skill><skill color=orange>Combat</skill><skill color=orange>Foraging</skill><empty></empty><custom color=aqua>§eCollections:§r</custom><collection0></collection0><collection1></collection1><collection2></collection2><empty></empty><custom color=aqua>§eStats§r</custom><stat>damage</stat><stat>strength</stat><stat>crit_damage</stat><stat>crit_chance</stat><empty></empty><custom color=aqua>§eMoney§r</custom><bank></bank><purse></purse>");
        getCollection().insertOne(newEntry);
        return newEntry;
    }

    public static MongoCollection<TablistCollectionEntry> getCollection()
    {
        return PublicSkyBlockAPI.getInstance()
                .getMongoDBConnection()
                .getSkyBlockDB(collectionName, TablistCollectionEntry.class);
    }


    public static void update(UUID uniqueId, TablistCollectionEntry entry)
    {
        getCollection().replaceOne(Filters.eq("playerUUID", uniqueId), entry);
    }
}
