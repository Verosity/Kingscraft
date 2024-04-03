package org.evmasterandverosity.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team
import org.evmasterandverosity.kingscraft.Kingscraft
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception

@Suppress("DEPRECATION")
class KingdomUtils {
    companion object {
        fun createKingdom(name: String, player: Player){
            player.sendMessage("Creating Kingdom!")

            if (true != true){ //getPlayerKingdom != null
                player.sendMessage("You are already in a Kingdom, please leave, or delete your kingdom")
                return
            }

            if (!teamCreate(name, player ,ChatColor.WHITE)){
                player.sendMessage("A kingdom with that name already exists. Please re-run the comman with using a different name")
                return
            }

            //PARSE JSON
            val owner: String = player.uniqueId.toString()
            val color: String = "white"
            val password:String = "Do This Later"
            val beingRaided: Boolean = false
            val maxFlagHealth: Int = 500

            val players: JSONArray = JSONArray()
            players.add(player.uniqueId.toString())

            val kingdom: JSONObject = JSONObject()
            kingdom.put("name", name)
            kingdom.put("owner", name)
            kingdom.put("color", name)
            kingdom.put("password", name)
            kingdom.put("beingRaided", name)
            kingdom.put("flagMaxHealth", name)
            player.sendMessage("Your password is \"${password}\". Keep it safe!")
            //FLAG
            kingdom.put("players", players)
            kingdom.put("crown", false)

            try{
                val parser: JSONParser = JSONParser()

                val fr: FileReader = FileReader(Kingscraft.dataPath)

                if (fr.ready()){
                    val root:JSONObject = parser.parse(fr) as JSONObject
                    val kingdoms:JSONArray = root.get("kingdoms") as JSONArray

                    kingdoms.add(kingdom)

                    val writer = FileWriter(Kingscraft.dataPath)
                    writer.write(root.toString())
                    writer.close()
                }else {
                    val kingdoms = JSONArray()
                    kingdoms.add(kingdom)
                    val root = JSONObject()
                    root.put("kingdoms", kingdoms)
                    val writer = FileWriter(Kingscraft.dataPath)
                    writer.write(root.toString())
                    writer.close()
                }

                fr.close()
            } catch (e: Exception){
                when (e) {
                    is IOException, is ParseException -> e.printStackTrace()
                    else -> throw e
                }
            }
            player.sendMessage("Created Kingdom");
        }

        fun teamCreate(name: String, player: Player, color: ChatColor): Boolean {
            val sb: Scoreboard = Bukkit.getScoreboardManager().mainScoreboard

            if (sb.getTeam(name) != null){
                return false
            }

            val team: Team = sb.registerNewTeam(name)

            team.prefix = "[${name}] "
            team.color = color

            team.addEntry(player.name)

            return true
        }
    }
}