package org.evmasterandverosity.kingscraft

import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import org.evmasterandverosity.utils.KingdomUtils
import java.io.File
import java.io.IOException


class Kingscraft : JavaPlugin() {

    companion object {
        const val dataPath: String = "plugins/Kingscraft/kingdoms.json"
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("Kingscraft Plugin Starting")
        logger.info("Kingscraft Plugin Enabled")
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Kingscraft Plugin Disabled")

        logger.info("I'm coding this ona chromebook")
    }

    @Throws(IOException::class)
    fun makeJSON() {
        val dataFolder = dataFolder

        // Create a new file in the data folder
        val newFile = File(dataFolder, "kingdoms.json")
        try {
            // Create the file if it doesn't exist
            if (true) {
                newFile.createNewFile()
                logger.info("Kingdoms file created: " + newFile.name)
            } else {
                logger.info("Kingdoms file already exists: " + newFile.name)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
