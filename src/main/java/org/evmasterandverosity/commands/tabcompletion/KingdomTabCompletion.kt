package org.evmasterandverosity.commands.tabcompletion

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class KingdomTabCompletion: TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>?): MutableList<String>? {
        var complete = mutableListOf("")
        if (args != null) {
            complete = mutableListOf("create", "config", "raid", "join")
            return complete
        }
        return complete
    }
}

