package io.titandata.plugin.remote

import io.titandata.plugin.PluginFactory

class RemoteFactory(pluginDirectory: String) : PluginFactory(pluginDirectory) {

    private val magicCookieKey = "titan"
    private val magicCookieValue = "dba4fe2b-56ff-4a16-9bfc-bf651b8f12d6"

    fun startProcess(pluginName: String): Process {
        return startProcess(pluginName, magicCookieKey, magicCookieValue)
    }
}
