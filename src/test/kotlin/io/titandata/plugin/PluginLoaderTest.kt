package io.titandata.plugin

import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class PluginLoaderTest : StringSpec() {

    val pluginDirectory = System.getProperty("pluginDirectory")

    init {
        "pluginDirectory property is configured" {
            pluginDirectory shouldNotBe null
        }
    }
}