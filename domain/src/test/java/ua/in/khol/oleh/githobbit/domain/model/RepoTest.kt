package ua.`in`.khol.oleh.githobbit.domain.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val ID: Long = 1
private const val OWNER_NAME = "olehkhol"
private const val OWNER_IMAGE = "owner_image.png"
private const val REPO_NAME = "GitHobbit"
private const val STARS_COUNT = 5

class RepoTest {

    private lateinit var repo: Repo

    @Before
    fun setUp() {
        repo = Repo(
            id = ID,
            ownerName = OWNER_NAME,
            ownerImage = OWNER_IMAGE,
            repoName = REPO_NAME,
            starsCount = STARS_COUNT
        )
    }

    @Test
    fun repoDataClass_checkDestructuring() {
        val (id, ownerName, ownerImage, repoName, _, starsCount, _) = repo

        Assert.assertEquals(id, ID)
        Assert.assertEquals(ownerName, OWNER_NAME)
        Assert.assertEquals(ownerImage, OWNER_IMAGE)
        Assert.assertEquals(repoName, REPO_NAME)
        Assert.assertEquals(starsCount, STARS_COUNT)
    }

    @Test
    fun repoDataClass_checkCoping() {
        val copy = repo.copy()

        Assert.assertEquals(copy.id, ID)
        Assert.assertEquals(copy.ownerName, OWNER_NAME)
        Assert.assertEquals(copy.ownerImage, OWNER_IMAGE)
        Assert.assertEquals(copy.repoName, REPO_NAME)
        Assert.assertEquals(copy.starsCount, STARS_COUNT)
    }
}