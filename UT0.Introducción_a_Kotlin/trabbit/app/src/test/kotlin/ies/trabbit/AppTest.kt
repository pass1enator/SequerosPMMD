/*
 * This source file was generated by the Gradle 'init' task
 */
package ies.trabbit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppTest {
    @Test fun appHasAGreeting() {
        val classUnderTest = App()
        assertEquals(4f,4.3f, "app shoukkkld have a greeting")
    }
    @Test fun appHasAGreeting2() {
        val classUnderTest = App()
        assertNotNull(classUnderTest.greeting, "app shoul2 have a greeting")
    }
}
