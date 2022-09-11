package com.github.tanokun.homespawn.commands

import com.github.tanokun.homespawn.HomeSpawn
import com.github.tanokun.homespawn.spawns
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.math.round

class SpawnCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cこのコマンドはプレイヤー以外で実行できません")
            return false
        }

        spawns[sender.uniqueId]?.let {
            it.yaw = sender.location.yaw
            it.pitch = sender.location.pitch
            sender.teleport(it)
            sender.sendMessage("§bスポーン位置にテレポートしました")
            return false
        }

        sender.sendMessage("§cスポーン位置が設定されていません")

        return false
    }
}