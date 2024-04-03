package org.evmasterandverosity.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

class KingdomUtils {
    companion object {
        fun createKingdom(name: String, player: Player){
            player.sendMessage("Creating Kingdom!")

            if (true != true){ //getPlayerKingdom != null
                player.sendMessage("You are already in a Kingdom, please leave, or delete your kingdom")
                return
            }

            if (!teamCreate(name, ChatColor.WHITE)){
                player.sendMessage("A kingdom with that name already exists. Please re-run the comman with using a different name")
                return
            }

            //PARSE JSON
        }

        fun teamCreate(name: String, color: ChatColor): Boolean {
            var sb: Scoreboard = Bukkit.getScoreboardManager().mainScoreboard

            if (sb.getTeam(name) != null){
                return false
            }

            var team: Team = sb.registerNewTeam(name)

            team.prefix = "[Test]"
            return true
        }
    }
}