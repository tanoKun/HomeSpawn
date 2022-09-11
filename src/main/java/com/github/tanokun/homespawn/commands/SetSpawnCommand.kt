package com.github.tanokun.homespawn.commands

import com.github.tanokun.homespawn.spawns
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.math.round

class SetSpawnCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cこのコマンドはプレイヤー以外で実行できません")
            return false
        }

        spawns[sender.uniqueId] = sender.location

        sender.sendMessage("§dスポーン位置を「${round(sender.location.x)}, ${round(sender.location.y)}, ${round(sender.location.z)}」に変更しました")
        sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0F, 1.0F)

        return false
    }
}