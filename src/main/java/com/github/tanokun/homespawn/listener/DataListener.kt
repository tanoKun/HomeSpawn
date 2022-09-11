package com.github.tanokun.homespawn.listener

import com.github.tanokun.homespawn.data
import com.github.tanokun.homespawn.loadSpawn
import com.github.tanokun.homespawn.saveSpawn
import com.github.tanokun.homespawn.spawns
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class DataListener: Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        if (data.config.isSet("${e.player.uniqueId}")) spawns[e.player.uniqueId] = loadSpawn(e.player.uniqueId, data)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        spawns[e.player.uniqueId]?.let { saveSpawn(e.player.uniqueId, it, data) }

        spawns.remove(e.player.uniqueId)
    }
}