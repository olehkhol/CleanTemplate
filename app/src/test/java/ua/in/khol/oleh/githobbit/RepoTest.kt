package ua.`in`.khol.oleh.githobbit

import org.junit.Assert
import org.junit.Test
import ua.`in`.khol.oleh.githobbit.data.Repo

const val OWNER_NAME = "olehkhol"
const val OWNER_IMAGE = "owner_image.png"
const val REPO_NAME = "GitHobbit"
const val STARS_COUNT = 5

class RepoTest {

    @Test
    fun repoDataClass_checkDestructuring() {
        val (ownerName, ownerIMAGE, repoName, starsCount) = Repo(
            ownerName = OWNER_NAME,
            ownerImage = OWNER_IMAGE,
            repoName = REPO_NAME,
            starsCount = STARS_COUNT
        )

        Assert.assertEquals(ownerName, OWNER_NAME)
        Assert.assertEquals(ownerIMAGE, OWNER_IMAGE)
        Assert.assertEquals(repoName, REPO_NAME)
        Assert.assertEquals(starsCount, STARS_COUNT)
    }

    @Test
    fun repoDataClass_checkCoping() {
        val repo = Repo(
            ownerName = OWNER_NAME,
            ownerImage = OWNER_IMAGE,
            repoName = REPO_NAME,
            starsCount = STARS_COUNT

        ).copy()

        Assert.assertEquals(repo.ownerName, OWNER_NAME)
        Assert.assertEquals(repo.ownerImage, OWNER_IMAGE)
        Assert.assertEquals(repo.repoName, REPO_NAME)
        Assert.assertEquals(repo.starsCount, STARS_COUNT)
    }

}