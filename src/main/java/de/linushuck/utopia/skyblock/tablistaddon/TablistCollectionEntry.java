package de.linushuck.utopia.skyblock.tablistaddon;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Data
public class TablistCollectionEntry
{
    @BsonProperty("playerUUID")
    private UUID playerUUID;
    @BsonProperty("row1")
    private String row1;
    @BsonProperty("row2")
    private String row2;
}
