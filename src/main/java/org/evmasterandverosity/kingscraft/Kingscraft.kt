package org.evmasterandverosity.kingscraft

import org.apache.logging.log4j.message.Message
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import org.evmasterandverosity.commands.KingdomCommand
import org.evmasterandverosity.commands.tabcompletion.KingdomTabCompletion


class Kingscraft : JavaPlugin() {

    companion object {

        const val dataPath: String = "plugins/Kingscraft/kingdoms.json"
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("Kingscraft Plugin Start...")
        logger.info("Kingscraft Plugin Enabled")

        getCommand("kingdom")?.setExecutor(KingdomCommand())
        getCommand("kingdom")?.setTabCompleter(KingdomTabCompletion())
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Kingscraft Plugin Disabled")
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
