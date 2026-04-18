package com.programming3c.prog7313part2

import com.programming3c.prog7313part2.auth.AuthValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AuthValidatorTest {

    @Test
    fun emptyUsernameAndPassword_returnsCombinedError() {
        val result = AuthValidator.validateLoginInput("", "")
        assertEquals("Please enter your username and password.", result)
    }

    @Test
    fun emptyUsername_returnsUsernameError() {
        val result = AuthValidator.validateLoginInput("", "1234")
        assertEquals("Please enter your username.", result)
    }

    @Test
    fun emptyPassword_returnsPasswordError() {
        val result = AuthValidator.validateLoginInput("admin", "")
        assertEquals("Please enter your password.", result)
    }

    @Test
    fun validInput_returnsNull() {
        val result = AuthValidator.validateLoginInput("admin", "1234")
        assertNull(result)
    }
}