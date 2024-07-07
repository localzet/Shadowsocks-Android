

package com.github.shadowsocks.database

import androidx.core.net.toUri
import org.junit.Assert
import org.junit.Test

class ProfileTest {
    @Test
    fun parsing() {
        val results = Profile.findAllUrls("garble ss://YmYtY2ZiOnRlc3RAMTkyLjE2OC4xMDAuMTo4ODg4#example-server garble")
                .toList()
        Assert.assertEquals(1, results.size)
        Assert.assertEquals("ss://YmYtY2ZiOnRlc3Q@192.168.100.1:8888#example-server".toUri(),
                results.single().toUri())
    }
}
