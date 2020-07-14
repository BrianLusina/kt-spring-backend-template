package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class DeleteAnEventUseCaseTest {
    private val mockEventDataStore = mockk<EventsDataStore>()
    private val deleteAnEventUseCase by lazy {
        DeleteAnEventUseCase(
            mockEventDataStore
        )
    }

    @Test
    fun `Should throw illegal argument exception when no identifier is passed`() {
        val expectedErr = "Must pass in valid identifier"
        val actual = Assertions.assertThrows(IllegalArgumentException::class.java) {
            deleteAnEventUseCase.execute()
        }
        Assertions.assertEquals(expectedErr, actual.message)
    }

    @Test
    fun `Should return true boolean when successful deletion occurs`() {
        val identifier = "identifier"
        every {
            mockEventDataStore.delete(identifier)
        } returns true

        val actual = deleteAnEventUseCase.execute(identifier)

        Assertions.assertTrue(actual)
    }

    @Test
    fun `Should return false when failure to delete occurs`() {
        val identifier = "identifier"
        every {
            mockEventDataStore.delete(identifier)
        } returns false

        val actual = deleteAnEventUseCase.execute(identifier)

        Assertions.assertFalse(actual)
    }

    @Test
    fun `Should return false when an exception is thrown by data store`() {
        val identifier = "identifier"
        every {
            mockEventDataStore.delete(identifier)
        } throws com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
            "Failed to delete event"
        )

        val actual = deleteAnEventUseCase.execute(identifier)

        Assertions.assertFalse(actual)
    }
}
