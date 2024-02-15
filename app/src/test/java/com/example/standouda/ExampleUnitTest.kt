package com.example.standouda

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun isParsing(){
        val appExemple = "1: Standouda\n" +
                "2: com.example.standouda\n"+
                "3: Romb38\n" +
                "4: 1.1.0\n" +
                "5: \n" +
                "6: https://github.com/Romb38/Standouda_private"

        val parsedInfo = GestionnaireApplication.parsingInfo(appExemple)
        val correctAwnser = listOf(
            "Standouda",
            "com.example.standouda",
            "Romb38",
            "1.1.0",
            "",
            "https://github.com/Romb38/Standouda_private"
        )
        assertEquals(correctAwnser,parsedInfo)
    }
}