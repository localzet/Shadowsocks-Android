

package com.github.shadowsocks.database

import org.junit.Assert
import org.junit.Test

class KeyValuePairTest {
    @Test
    fun putAndGet() {
        val kvp = KeyValuePair()
        Assert.assertEquals(true, kvp.put(true).boolean)
        Assert.assertEquals(3f, kvp.put(3f).float)
        @Suppress("DEPRECATION")
        Assert.assertEquals(3L, kvp.put(3).long)
        Assert.assertEquals(3L, kvp.put(3L).long)
        Assert.assertEquals("3", kvp.put("3").string)
        val set = (0 until 3).map(Int::toString).toSet()
        Assert.assertEquals(set, kvp.put(set).stringSet)
        Assert.assertEquals(null, kvp.boolean)
    }
}
