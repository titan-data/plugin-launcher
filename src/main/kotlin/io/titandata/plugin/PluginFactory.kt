package io.titandata.plugin

import java.io.File
import java.lang.IllegalStateException

abstract class PluginFactory(val pluginDirectory : String) {

    data class Header(
        val coreVersion: Int,
        val protoVersion: Int,
        val network: String,
        val addr: String,
        val protoType: String,
        val serverCert: String
    )

    @Suppress("UNUSED_PARAMETER")
    fun startProcess(pluginName: String, magicCookieKey: String, magicCookieValue: String) : Process {
        val builder = ProcessBuilder("./$pluginName")
                .directory(File(pluginDirectory))
        val env = builder.environment()
        env[magicCookieKey] = magicCookieValue

        return builder.start()
    }

    fun readHeader(process : Process) : Header {
        val reader = process.inputStream?.bufferedReader() ?: throw IllegalStateException("failed to get output from plugin process")
        reader.use {
            for (line in reader.lines()) {
                val fields = line.trim().split("|")
                if (fields.size == 6) {
                    return Header(
                            coreVersion = fields[0].toInt(),
                            protoVersion = fields[1].toInt(),
                            network = fields[2],
                            addr = fields[3],
                            protoType = fields[4],
                            serverCert = fields[5]
                    )
                }
            }
            val errText = process.errorStream.bufferedReader().readText()
            if (process.isAlive) {
                throw IllegalStateException("failed to find plugin header line: $errText")
            } else {
                throw IllegalStateException("process exited before finding plugin header line: $errText")
            }
        }
    }
}