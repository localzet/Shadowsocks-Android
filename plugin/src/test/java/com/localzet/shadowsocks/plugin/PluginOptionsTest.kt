

package com.localzet.shadowsocks.plugin

import org.junit.Assert
import org.junit.Test

class PluginOptionsTest {
    @Test
    fun basic() {
        val o1 = PluginOptions("obfs-local;obfs=http;obfs-host=localhost")
        val o2 = PluginOptions("obfs-local", "obfs-host=localhost;obfs=http")
        Assert.assertEquals(o1.hashCode(), o2.hashCode())
        Assert.assertEquals(true, o1 == o2)
        val o3 = PluginOptions(o1.toString(false))
        Assert.assertEquals(true, o2 == o3)
        val o4 = PluginOptions(o2.id, o2.toString())
        Assert.assertEquals(true, o3 == o4)
    }

    @Test
    fun nullValues() {
        val o = PluginOptions("", "a;b;c;d=3")
        Assert.assertEquals(true, o == PluginOptions("", o.toString()))
    }

    @Test
    fun escape() {
        val options = PluginOptions("escapeTest")
        options["subject"] = "value;semicolon"
        Assert.assertEquals(true, options == PluginOptions(options.toString(false)))
        options["key;semicolon"] = "object"
        Assert.assertEquals(true, options == PluginOptions(options.toString(false)))
        options["subject"] = "value=equals"
        Assert.assertEquals(true, options == PluginOptions(options.toString(false)))
        options["key=equals"] = "object"
        Assert.assertEquals(true, options == PluginOptions(options.toString(false)))
        options["advanced\\=;test"] = "in;=\\progress"
        Assert.assertEquals(true, options == PluginOptions(options.toString(false)))
    }
}
