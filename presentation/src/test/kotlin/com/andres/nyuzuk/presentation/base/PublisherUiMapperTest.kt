package com.andres.nyuzuk.presentation.base

import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class PublisherUiMapperTest {
    private lateinit var publisherUiMapper: PublisherUiMapper

    @Before
    fun setup() {
        publisherUiMapper = PublisherUiMapper()
    }

    @Test
    fun `should get null when mapping null Publisher`() {
        val publisherUi = publisherUiMapper.map(null)

        assertThat(publisherUi, nullValue())
    }
}