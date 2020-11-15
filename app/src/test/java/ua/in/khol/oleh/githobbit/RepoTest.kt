package ua.`in`.khol.oleh.githobbit

import org.junit.Assert
import org.junit.Test
import ua.`in`.khol.oleh.githobbit.data.Repo

const val KOTLIN = "Kotlin"
const val STARS_COUNT = 5

class RepoTest {

    @Test
    fun repoDataClass_checkDestructuring() {
        val (name, stars) = Repo(KOTLIN, STARS_COUNT)

        Assert.assertEquals(name, KOTLIN)
        Assert.assertEquals(stars, STARS_COUNT)
    }

    @Test
    fun repoDataClass_checkCoping() {
        val repo = Repo(KOTLIN, STARS_COUNT).copy()

        Assert.assertEquals(repo.name, KOTLIN)
        Assert.assertEquals(repo.stars, STARS_COUNT)
    }

}