package io.titandata.plugin.remote

import io.grpc.ManagedChannel
import io.titandata.plugin.PluginFactory

class RemoteFactory(pluginDirectory: String) : PluginFactory(pluginDirectory) {

    private val magicCookieKey = "titan"
    private val magicCookieValue = "dba4fe2b-56ff-4a16-9bfc-bf651b8f12d6"

    data class LoadedPlugin(
        val process: Process,
        val channel: ManagedChannel,
        val client: RemoteClient
    )

    private val loadedPlugins: MutableMap<String, LoadedPlugin> = mutableMapOf()

    fun startProcess(pluginName: String): Process {
        return startProcess(pluginName, magicCookieKey, magicCookieValue)
    }

    @Synchronized
    fun load(pluginName: String): Remote {
        if (!loadedPlugins.containsKey(pluginName)) {
            val p = startProcess(pluginName)
            val header = getHeader(p)
            val channel = getManagedChannel(header)
            val stub = RemoteGrpc.newBlockingStub(channel)
            val client = RemoteClient(stub)
            loadedPlugins[pluginName] = LoadedPlugin(p, channel, client)
        }

        return loadedPlugins[pluginName]!!.client
    }

    @Synchronized
    fun unload(pluginName: String) {
        if (loadedPlugins.containsKey(pluginName)) {
            val lp = loadedPlugins[pluginName]!!
            lp.channel.shutdownNow()
            lp.process.destroyForcibly()
            loadedPlugins.remove(pluginName)
        }
    }
}
