package org.evmasterandverosity.kingscraft

import org.bukkit.plugin.java.JavaPlugin

class Kingscraft : JavaPlugin() {
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

}
