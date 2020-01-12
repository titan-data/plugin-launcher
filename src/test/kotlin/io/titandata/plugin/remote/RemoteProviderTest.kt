package io.titandata.plugin.remote

import io.kotlintest.Spec
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class RemoteProviderTest : StringSpec() {

    val pluginDirectory = System.getProperty("pluginDirectory")
    val provider = RemoteProvider(pluginDirectory)
    lateinit var remote: Remote

    override fun beforeSpec(spec: Spec) {
        remote = provider.load("echo")
    }

    override fun afterSpec(spec: Spec) {
        provider.unload("echo")
    }

    init {
        "pluginDirectory property is configured" {
            pluginDirectory shouldNotBe null
        }

        "can start echo process" {
            val p = provider.startProcess("echo")
            p.destroy()
        }

        "get header succeeds" {
            val p = provider.startProcess("echo")
            try {
                val header = provider.getHeader(p)
                header.coreVersion shouldBe 1
                header.protoVersion shouldBe 1
                header.protoType shouldBe "grpc"
                header.serverCert shouldBe ""
            } finally {
                p.destroy()
            }
        }

        "get managed channel succeeds" {
            val p = provider.startProcess("echo")
            try {
                val header = provider.getHeader(p)
                val mc = provider.getManagedChannel(header)
                mc.shutdownNow()
            } finally {
                p.destroy()
            }
        }

        "get remote type succeeds" {
            remote.type() shouldBe "echo"
            remote.type() shouldBe "echo"
        }

        "fromURL succeeds" {
            val res = remote.fromURL("echo://echo", mapOf("a" to "b"))
            res.size shouldBe 2
            res["url"] shouldBe "echo://echo"
            res["a"] shouldBe "b"
        }

        "toURL succeeds" {
            val res = remote.toURL(mapOf("a" to "b"))
            res.first shouldBe "echo://echo"
            res.second.size shouldBe 1
            res.second["a"] shouldBe "b"
        }

        "getParameters succeeds" {
            val res = remote.getParameters(mapOf("a" to "b"))
            res.size shouldBe 1
            res["a"] shouldBe "b"
        }
    }
}
