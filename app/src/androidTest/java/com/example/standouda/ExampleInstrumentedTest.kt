package com.example.standouda

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.standouda", appContext.packageName)
    }

    @Test
    fun isPackageInstalled(){
        //Vérifie si la fonction d'installation de package fonctionne
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val testApp = MyApplication(0,"","com.android.chrome","","","","","")

        assert(testApp.isInstalled(appContext))
    }

    @Test
    fun isPackageNotInstalled(){
        //Vérifie si la fonction d'installation de package fonctionne
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val testApp = MyApplication(0,"","com.android.foo","","","","","")

        assert(!testApp.isInstalled(appContext))
    }
}