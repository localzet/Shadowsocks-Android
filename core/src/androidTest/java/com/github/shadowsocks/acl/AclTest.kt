

package com.github.shadowsocks.acl

import org.junit.Assert
import org.junit.Test

class AclTest {
    companion object {
        const val BYPASS_BASE = """[bypass_all]
[proxy_list]"""
        const val INPUT1 = """$BYPASS_BASE
1.0.1.0/24
2000::/8
(?:^|\.)4tern\.com$
"""
        const val INPUT2 = """[proxy_all]
[bypass_list]
10.3.0.0/16
10.0.0.0/8
(?:^|\.)chrome\.com$

[proxy_list]
# ignored
0.0.0.0/0
(?:^|\.)about\.google$
"""
    }

    @Test
    fun parse() {
        Assert.assertEquals(INPUT1, Acl().fromReader(INPUT1.reader()).toString())
        Assert.assertEquals(INPUT2, Acl().fromReader(INPUT2.reader()).toString())
    }
}
