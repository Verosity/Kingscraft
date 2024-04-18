package org.evmasterandverosity.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

import org.evmasterandverosity.kingscraft.Kingscraft as craft

@Suppress("DEPRECATION")
class KingdomCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, string: String, args: Array<out String>?): Boolean {
        if (sender !is Player){
            sender.sendMessage("You need to be a player to run this command")
            return true
        }

        if (args != null) {
            if (args.isEmpty()){
                sender.sendMessage("You need to provide arguments lil bitch")
            }else if(args[0] == "create"){
                sender.sendMessage("Create Kingdom")
            }else if(args[0] == "config"){
                sender.sendMessage("Configure Kingdom")
                configUI(sender)
            }else if(args[0] == "join"){
                sender.sendMessage("Join Kingdom")
            }
            else if(args[0] == "raid"){
                sender.sendMessage("Raid Kingdom")
            }
        }
        return true
    }

    fun configUI(sender: CommandSender){
        val player: Player =  sender as Player
        val inventory: Inventory = Bukkit.createInventory(player, 9*3 , "Kingdom Create")

        val color:ItemStack = ItemStack(Material.WHITE_WOOL)
        val colorMeta:ItemMeta = color.itemMeta
        colorMeta.setDisplayName("Kingdom Color")
        color.setItemMeta(colorMeta)
        logger.info("Renamed and set meta to Color")

        val pList:ItemStack = ItemStack(Material.PLAYER_HEAD)
        val pListMeta:ItemMeta = pList.itemMeta
        pListMeta.setDisplayName("Kingdom Citizens")
        pList.setItemMeta(pListMeta)
        craft.message("Renamed and set meta to Players")

        val del:ItemStack = ItemStack(Material.BARRIER)
        val delMeta:ItemMeta = del.itemMeta
        delMeta.setDisplayName("${ChatColor.RED}DELETE KINGDOM")
        del.setItemMeta(delMeta)
        craft.message("Renamed and set meta to Delete")

        val password:ItemStack = ItemStack(Material.TRIPWIRE_HOOK)
        val passwordMeta:ItemMeta = password.itemMeta
        passwordMeta.setDisplayName("Kingdom Password")
        password.setItemMeta(passwordMeta)
        craft.message("Renamed and set meta to Password")

        val flagLoc:ItemStack = ItemStack(Material.WHITE_BANNER)
        val flagLocMeta:ItemMeta = flagLoc.itemMeta
        flagLocMeta.setDisplayName("Kingdom Flag Location")
        flagLoc.setItemMeta(flagLocMeta)
        craft.message("Renamed and set meta to Flag")

        inventory.setItem(10, color)
        inventory.setItem(12, pList)
        inventory.setItem(13, del)
        inventory.setItem(14, password)
        inventory.setItem(16, flagLoc)

        player.openInventory(inventory)

    }

}