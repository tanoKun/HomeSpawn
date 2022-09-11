package com.github.tanokun.homespawn.util

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class Config(private val plugin: Plugin, private val file: File, var fileName: String) {
    val config: FileConfiguration

    init {
        config = YamlConfiguration.loadConfiguration(file)
        val defConfigStream: InputStream? = plugin.getResource(fileName)

        if (defConfigStream != null) {
            config.setDefaults(YamlConfiguration.loadConfiguration(InputStreamReader(defConfigStream, StandardCharsets.UTF_8)))
            defConfigStream.close()
        }
    }

    constructor(plugin: Plugin): this(plugin, File(plugin.dataFolder, "config.yml"), "config.yml")

    constructor(plugin: Plugin, name: String): this(plugin, File(plugin.dataFolder, name), name)

    fun isExists(): Boolean = file.exists()

    fun createConfig(): Boolean {
        if (file.exists()) return true
        else file.createNewFile()
        return false
    }

    fun createDefaultConfig() {
        if (!file.exists()) plugin.saveResource(fileName, false)
    }

    fun saveConfig() = config.save(file)
}