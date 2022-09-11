package com.github.tanokun.homespawn

import com.github.tanokun.homespawn.commands.SetSpawnCommand
import com.github.tanokun.homespawn.commands.SpawnCommand
import com.github.tanokun.homespawn.listener.DataListener
import com.github.tanokun.homespawn.util.Config
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

val spawns = HashMap<UUID, Location>()

lateinit var data: Config

class HomeSpawn : JavaPlugin() {
    override fun onEnable() {
        data = Config(this, "data.yml")

        data.createDefaultConfig()

        Bukkit.getPluginCommand("spawn")?.setExecutor(SpawnCommand())
        Bukkit.getPluginCommand("setspawn")?.setExecutor(SetSpawnCommand())

        Bukkit.getPluginManager().registerEvents(DataListener(), this)
    }

    override fun onDisable() {
        spawns.forEach {
            saveSpawn(it.key, it.value, data)
        }
    }
}

fun saveSpawn(uuid: UUID, location: Location, config: Config) {
    println("${location.world!!.name} ${location.x} ${location.y} ${location.z}")
    config.config.set("$uuid", "${location.world!!.name} ${location.x} ${location.y} ${location.z}")
    config.saveConfig()
}

fun loadSpawn(uuid: UUID, config: Config): Location {
    val locT = config.config.getString("$uuid")?.split(" ".toRegex())?.toTypedArray() ?: arrayOf("world", "0", "0", "0", "0", "0")
    val world = Bukkit.getWorld(locT[0])
    val x = locT[1].toDouble()
    val y = locT[2].toDouble()
    val z = locT[3].toDouble()

    return Location(world, x, y, z)
}