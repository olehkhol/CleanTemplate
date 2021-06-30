package ua.`in`.khol.oleh.githobbit.domain.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val ID = 2
private const val NAME = "olehkhol"
private const val IMAGE = "image.png"

class SubTest {

    private lateinit var sub: Sub

    @Before
    fun setUp() {
        sub = Sub(
            id = ID,
            name = NAME,
            image = IMAGE
        )
    }

    @Test
    fun repoDataClass_checkDestructuring() {
        val (id, name, image) = sub

        Assert.assertEquals(id, ID)
        Assert.assertEquals(name, NAME)
        Assert.assertEquals(image, IMAGE)
    }

    @Test
    fun repoDataClass_checkCoping() {
        val copy = sub.copy()

        Assert.assertEquals(copy.id, ID)
        Assert.assertEquals(copy.name, NAME)
        Assert.assertEquals(copy.image, IMAGE)
    }
}