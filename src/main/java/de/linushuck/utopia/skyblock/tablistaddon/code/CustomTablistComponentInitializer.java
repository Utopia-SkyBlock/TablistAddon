package de.linushuck.utopia.skyblock.tablistaddon.code;

import de.linushuck.utopia.skyblock.libs.api.tablist.item.TextTabItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class CustomTablistComponentInitializer
{
    private static final HashMap<String, TablistEntry> entries = new HashMap<>();

    static
    {
        init();
    }

    public static TablistEntry getEntry(String key)
    {
        if(!entries.containsKey(key))
        {
            return entries.get("empty");
        }
        return entries.get(key);
    }

    private static void init()
    {
        title();
        custom();
        collection();
        skill();
        money();
        other();
        entries.put("empty", (player, arg, skin) -> new TextTabItem("", skin));
    }

    private static void other()
    {
    }

    private static void skill()
    {
        //entries.put("skill0", (player, arg, skin) ->
        //{
        //    SkyBlockSkill skill = SkyBlockProfileHelper.getIndividualProfile(player).getLastSkillProgress().getAtIndex(0);
        //    return SkyBlockSkill.getAsTextTabItem(player, skill.name(), skin);
        //});
        //entries.put("skill1", (player, arg, skin) ->
        //{
        //    SkyBlockSkill skill = SkyBlockProfileHelper.getIndividualProfile(player).getLastSkillProgress().getAtIndex(1);
        //    return SkyBlockSkill.getAsTextTabItem(player, skill.name(), skin);
        //});
        //entries.put("skill2", (player, arg, skin) ->
        //{
        //    SkyBlockSkill skill = SkyBlockProfileHelper.getIndividualProfile(player).getLastSkillProgress().getAtIndex(2);
        //    return SkyBlockSkill.getAsTextTabItem(player, skill.name(), skin);
        //});
    }

    private static void collection()
    {
        //entries.put("collection0", (player, arg, skin) ->
        //{
        //    SkyBlockCollection material = SkyBlockCollection.Carrot;
        //    //SkyBlockCollection material = SkyBlockProfileHelper.getCurrent(player).getMaterialUpdate(0);
        //    return SkyBlockCollection.getAsTextTabItem(player, material, skin);
        //});
        //entries.put("collection1", (player, arg, skin) ->
        //{
        //    SkyBlockCollection material = SkyBlockCollection.Coal;
        //    //SkyBlockCollection material = SkyBlockProfileHelper.getCurrent(player).getMaterialUpdate(1);
        //    return SkyBlockCollection.getAsTextTabItem(player, material, skin);
        //});
        //entries.put("collection2", (player, arg, skin) ->
        //{
        //    SkyBlockCollection material = SkyBlockCollection.Acacia_Log;
        //    //SkyBlockCollection material = SkyBlockProfileHelper.getCurrent(player).getMaterialUpdate(2);
        //    return SkyBlockCollection.getAsTextTabItem(player, material, skin);
        //});
    }

    private static void money()
    {
        entries.put("bank", (player, arg, skin) ->
        {
            double currentBankMoney = 123;
            //double currentBankMoney = SkyBlockMoney.getMoney(SkyBlockProfileType.CLUSTER_PROFILE, SkyBlockProfileHelper.getIndividualProfile(player).getClusterProfileUUID().toString());
            return new TextTabItem(" Bank: " + ChatColor.GOLD + currentBankMoney, skin);
        });
        entries.put("purse", (player, arg, skin) ->
        {
            double currentPurseMoney = 456;
            //double currentPurseMoney = SkyBlockMoney.getMoney(SkyBlockProfileType.INDIVIDUAL_PROFILE, SkyBlockProfileHelper.getIndividualProfile(player).getIndividualProfileUUID().toString());
            return new TextTabItem(" Purse: " + ChatColor.GOLD + currentPurseMoney, skin);
        });
    }

    private static void custom()
    {
        entries.put("custom", (player, arg, skin) ->
        {
            if(arg.isBlank() || arg.isEmpty())
            {
                return new TextTabItem(ChatColor.RED + "No input", skin);
            }
            return new TextTabItem(arg, skin);
        });
        entries.put("tps", (player, arg, skin) ->
        {
            double min1 = Bukkit.getServer().getTPS()[0];
            double min5 = Bukkit.getServer().getTPS()[1];
            double min15 = Bukkit.getServer().getTPS()[2];

            //round to 2 decimal places
            min1 = Math.round(min1 * 100.0) / 100.0;
            min5 = Math.round(min5 * 100.0) / 100.0;
            min15 = Math.round(min15 * 100.0) / 100.0;
            return new TextTabItem("TPS: " + ChatColor.GOLD + min1 + " " + min5 + " " + min15, skin);
        });
        //entries.put("collection", (player, arg, skin) ->
        //{
        //    if(arg.isBlank() || arg.isEmpty())
        //    {
        //        return new TextTabItem(ChatColor.RED + "No input", skin);
        //    }
        //    SkyBlockCollection collection = SkyBlockCollection.getByName(arg);
        //    return SkyBlockCollection.getAsTextTabItem(player, collection, skin);
        //});
        //entries.put("skill", (player, arg, skin) ->
        //{
        //    if(arg.isBlank() || arg.isEmpty())
        //    {
        //        return new TextTabItem(ChatColor.RED + "No input", skin);
        //    }
        //    return SkyBlockSkill.getAsTextTabItem(player, arg, skin);
        //});
        entries.put("stat", (player, arg, skin) ->
        {
            if(arg.isBlank() || arg.isEmpty())
            {
                return new TextTabItem(ChatColor.RED + "No input", skin);
            }
            return switch(arg)
            {
                //case "damage" -> new TextTabItem("Damage: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getDamage().getCurrent(), skin);
                //case "strength" -> new TextTabItem("Strength: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getStrength().getCurrent(), skin);
                //case "crit_chance" -> new TextTabItem("Crit Chance: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getCrit_Chance().getCurrent(), skin);
                //case "crit_damage" -> new TextTabItem("Crit Damage: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getCrit_Damage().getCurrent(), skin);
                //case "defense" -> new TextTabItem("Defense: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getDefense().getCurrent(), skin);
                //case "defense_true" -> new TextTabItem("Defense True: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getDefense_True().getCurrent(), skin);
                //case "defense_fire" -> new TextTabItem("Defense Fire: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getDefense_Fire().getCurrent(), skin);
                //case "defense_blast" -> new TextTabItem("Defense Blast: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getDefense_Blast().getCurrent(), skin);
                //case "defense_projectile" ->
                //        new TextTabItem("Defense Projectile: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getCombatStats().getDefense_Projectile().getCurrent(), skin);
                //case "speed" -> new TextTabItem("Speed: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getMiscStats().getSpeed().getCurrent(), skin);
                //case "speed_cap" -> new TextTabItem("Speed MAX: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getMiscStats().getSpeed().sumMax(), skin);
                //case "experience" -> new TextTabItem("Experience: " + ChatColor.GOLD + SkyBlockProfileHelper.getCurrentStats(player).getOtherStats().getExperience().getCurrent(), skin);
                default -> new TextTabItem("Not implemented yet", skin);
            };
        });
    }

    private static void title()
    {
        entries.put("titel.server", (player, arg, skin) -> new TextTabItem("          Server Info", skin));
        entries.put("titel.account", (player, arg, skin) -> new TextTabItem("         Account Info", skin));
    }
}
