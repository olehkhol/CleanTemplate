package ua.`in`.khol.oleh.cleantemplate.data.paging

import androidx.paging.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ua.`in`.khol.oleh.cleantemplate.data.database.GitDatabase
import ua.`in`.khol.oleh.cleantemplate.data.database.entity.RepoEntity
import ua.`in`.khol.oleh.cleantemplate.data.network.github.FakeGitService
import ua.`in`.khol.oleh.cleantemplate.data.network.github.RepoFactory
import ua.`in`.khol.oleh.cleantemplate.data.network.github.serialized.RepoItem

private const val TOTAL_COUNT: Long = 113
private const val DEFAULT_QUERY = "Android"

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GitRemoteMediatorTest {
    private val repoFactory = RepoFactory()
    private val repoItems = arrayListOf<RepoItem>().also { list ->
        for (id in 0..TOTAL_COUNT)
            list.add(repoFactory.createRepoItem(id))
    }
    private val fakeService = FakeGitService()
    private val inMemoryDatabase =
        GitDatabase.getInstance(ApplicationProvider.getApplicationContext(), useInMemory = true)

    @Before
    fun raise() {
    }

    @After
    fun fall() {
        fakeService.clearRepoItems()
        inMemoryDatabase.clearAllTables()
    }

    /**
     * The first case is when mockApi returns valid data.
     * The load() function should return MediatorResult.Success,
     * and the endOfPaginationReached property should be false
     */
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        // Add mock result for the APU to return.
        repoItems.forEach { fakeService.addRepoItem(it) }

        val remoteMediator = GitRemoteMediator(
            DEFAULT_QUERY,
            fakeService,
            inMemoryDatabase
        )
        val pagingState = PagingState<Int, RepoEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result: RemoteMediator.MediatorResult =
            remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    /**
     * The second case is when mockApi returns a successful response,
     * but the returned data is empty. The load() function should return MediatorResult.Success,
     * and the endOfPaginationReached property should be true.
     */
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        // To test endOfPaginationReached, don't set up the fakeService to return repo data here.
        fakeService.clearRepoItems()

        val remoteMediator = GitRemoteMediator(
            DEFAULT_QUERY,
            fakeService,
            inMemoryDatabase
        )
        val pagingState = PagingState<Int, RepoEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    /**
     * The third case is when mockApi throws an exception when fetching the data.
     * The load() function should return MediatorResult.Error.
     */
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        // Set up failure message to throw exception from the mock API.
        fakeService.failureMsg = "Throw test failure"
        val remoteMediator = GitRemoteMediator(
            DEFAULT_QUERY,
            fakeService,
            inMemoryDatabase
        )
        val pagingState = PagingState<Int, RepoEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}