package org.evmasterandverosity.utils

import com.sun.tools.javac.jvm.Items
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.evmasterandverosity.kingscraft.Kingscraft
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class JsonParsingUtils {
    fun getKingdomOwner(kingdomName: String?): String? {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    val owner = kingdom["owner"] as String
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        fileReader.close()
                        return owner
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun getKingdomPassword(kingdomName: String?): String? {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    val password = kingdom["password"] as String
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        fileReader.close()
                        return password
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun getPlayerKingdom(player: Player): String? {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    val players = kingdom["players"] as JSONArray
                    if (players.contains(player.uniqueId.toString())) {
                        fileReader.close()
                        return name
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun getPlayers(kingdomName: String?): ArrayList<String> {
        val playersInKingdom = ArrayList<String>()
        try {
            // Create a Parser
            val parser = JSONParser()

            // Read existing JSON data
            val fileReader = FileReader(Kingscraft.dataPath)
            val root = parser.parse(fileReader) as JSONObject
            fileReader.close()

            // Get the "kingdoms" array from the root
            val kingdoms = root["kingdoms"] as JSONArray

            // Search for the specified kingdom
            for (obj in kingdoms) {
                val kingdom = obj as JSONObject
                val name = kingdom["name"] as String

                // Check if the current kingdom matches the specified name
                if (name.equals(kingdomName, ignoreCase = true)) {
                    val playerUUIDs = kingdom["players"] as JSONArray

                    // Loop through the players in the kingdom
                    for (playerObj in playerUUIDs) {
                        val playerUUID = playerObj as String
                        playersInKingdom.add(playerUUID)
                    }

                    // Break the loop since we found the specified kingdom
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return playersInKingdom
    }

    fun getAllKingdoms(): ArrayList<String> {
        val allKingdoms = ArrayList<String>()
        try {
            // Create a Parser
            val parser = JSONParser()

            // Read existing JSON data
            val fileReader = FileReader(Kingscraft.dataPath)
            val root = parser.parse(fileReader) as JSONObject
            fileReader.close()

            // Get the "kingdoms" array from the root
            val kingdoms = root["kingdoms"] as JSONArray

            // Loop through all the kingdoms
            for (obj in kingdoms) {
                val kingdom = obj as JSONObject
                val name = kingdom["name"] as String
                allKingdoms.add(name)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return allKingdoms
    }


    fun addPlayer(kingdomName: String?, player: Player) {
        try {
            // Create a parser
            val parser = JSONParser()

            // Read the existing JSON data from the file
            val fileReader = FileReader(Kingscraft.dataPath)
            val root = parser.parse(fileReader) as JSONObject
            val kingdoms = root["kingdoms"] as JSONArray

            // Find the kingdom with the given name and update its color
            for (obj in kingdoms) {
                val kingdom = obj as JSONObject
                val name = kingdom["name"] as String
                val players = kingdom["players"] as JSONArray
                if (name.equals(kingdomName, ignoreCase = true)) {
                    val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
                    Objects.requireNonNull(scoreboard.getTeam(kingdomName!!))?.addEntry(player.name)
                    players.add(player.uniqueId.toString())
                } else {
                    // Remove the player from other kingdoms
                    val iterator: MutableIterator<Any?> = players.iterator()
                    while (iterator.hasNext()) {
                        val playerId = iterator.next()
                        if (playerId == player.uniqueId.toString()) {
                            iterator.remove()
                            break
                        }
                    }
                }
            }

            // Write the updated JSON data back to the file
            val writer = FileWriter(Kingscraft.dataPath)
            writer.write(root.toString())
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun removePlayer(kingdomName: String?, player: Player) {
        try {
            // Create a parser
            val parser = JSONParser()

            // Read the existing JSON data from the file
            val fileReader = FileReader(Kingscraft.dataPath)
            val root = parser.parse(fileReader) as JSONObject
            val kingdoms = root["kingdoms"] as JSONArray

            // Find the kingdom with the given name and remove the player
            for (obj in kingdoms) {
                val kingdom = obj as JSONObject
                val name = kingdom["name"] as String
                val players = kingdom["players"] as JSONArray
                if (name.equals(kingdomName, ignoreCase = true)) {
                    val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
                    Objects.requireNonNull(scoreboard.getTeam(kingdomName!!))?.removeEntry(player.name)
                    players.remove(player.uniqueId.toString())
                    break
                }
            }

            // Write the updated JSON data back to the file
            val writer = FileWriter(Kingscraft.dataPath)
            writer.write(root.toString())
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun setKingdomPassword(kingdomName: String?, newPassword: String?) {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        kingdom["password"] = newPassword
                        fileReader.close()
                        val fileWriter = FileWriter(Kingscraft.dataPath)
                        fileWriter.write(root.toJSONString())
                        fileWriter.flush()
                        fileWriter.close()
                        return
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun changeOwner(kingdomName: String, newOwner: Player) {
        try {
            // Create a parser
            val parser = JSONParser()

            // Read the existing JSON data from the file
            val fileReader = FileReader(Kingscraft.dataPath)
            val root = parser.parse(fileReader) as JSONObject
            val kingdoms = root["kingdoms"] as JSONArray

            // Find the kingdom with the given name
            for (obj in kingdoms) {
                val kingdom = obj as JSONObject
                val name = kingdom["name"] as String
                if (name.equals(kingdomName, ignoreCase = true)) {
                    val currentOwner = kingdom["owner"] as String

                    // Check if the current owner is different from the new owner
                    if (currentOwner != newOwner.uniqueId.toString()) {
                        // Update the owner and remove the new owner from the players list
                        kingdom["owner"] = newOwner.uniqueId.toString()
                        val players = kingdom["players"] as JSONArray

                        // Add the previous owner to the players list
                        //players.add(currentOwner);

                        // Write the updated JSON data back to the file
                        val writer = FileWriter(Kingscraft.dataPath)
                        writer.write(root.toString())
                        writer.close()
                        newOwner.sendMessage("You are now the owner of the kingdom $kingdomName.")
                        val currentOwnerPlayer = Bukkit.getPlayer(UUID.fromString(currentOwner))
                        currentOwnerPlayer?.sendMessage("You are no longer the owner of the kingdom $kingdomName.")
                    } else {
                        newOwner.sendMessage("You are already the owner of this kingdom.")
                    }
                    return  // Exit the method after handling the kingdom
                }
            }
            fileReader.close()

            // If the loop finishes, the specified kingdom was not found
            newOwner.sendMessage("Kingdom '$kingdomName' does not exist.")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun isBeingRaided(kingdomName: String?): Boolean {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    val beingRaided = kingdom["beingRaided"] as Boolean
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        fileReader.close()
                        return beingRaided
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun isOnline(name: String?): Boolean {
        val players = getPlayers(name)
        for (s in players) {
            val uuid = UUID.fromString(s)
            for (p in Bukkit.getOnlinePlayers()) {
                if (p.uniqueId == uuid) {
                    return true
                }
            }
        }
        return false
    }

    fun setFlagMaxHealth(kingdomName: String?, health: Int) {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        val healthToPut = getFlagMaxHealth(kingdomName) + health
                        Bukkit.getLogger().info(healthToPut.toString())
                        kingdom["flagMaxHealth"] = healthToPut
                        fileReader.close()
                        val fileWriter = FileWriter(Kingscraft.dataPath)
                        fileWriter.write(root.toJSONString())
                        fileWriter.flush()
                        fileWriter.close()
                        return
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun getFlagMaxHealth(kingdomName: String?): Int {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        val flagMaxHealthObj: Any? = kingdom["flagMaxHealth"]
                        if (flagMaxHealthObj is Long) {
                            return flagMaxHealthObj.toInt()
                        } else if (flagMaxHealthObj is Int) {
                            return flagMaxHealthObj
                        }
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    fun hasCrown(kingdomName: String?): Boolean {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    val crown = kingdom["crown"] as Boolean
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        fileReader.close()
                        return crown
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun setHasCrown(kingdomName: String?, value: Boolean?) {
        try {
            val parser = JSONParser()
            val fileReader = FileReader(Kingscraft.dataPath)
            if (fileReader.ready()) {
                val root = parser.parse(fileReader) as JSONObject
                val kingdoms = root["kingdoms"] as JSONArray
                for (obj in kingdoms) {
                    val kingdom = obj as JSONObject
                    val name = kingdom["name"] as String
                    if (name.equals(kingdomName, ignoreCase = true)) {
                        kingdom["crown"] = value
                        fileReader.close()
                        val fileWriter = FileWriter(Kingscraft.dataPath)
                        fileWriter.write(root.toJSONString())
                        fileWriter.flush()
                        fileWriter.close()
                        return
                    }
                }
            }
            fileReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}