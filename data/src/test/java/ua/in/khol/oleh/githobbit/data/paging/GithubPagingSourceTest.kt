package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.paging.PagingSource.LoadParams.Append
import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Page
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import ua.`in`.khol.oleh.githobbit.data.network.github.FakeGitService
import ua.`in`.khol.oleh.githobbit.data.network.github.RepoFactory
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import java.lang.Integer.min
import kotlin.test.assertEquals

private const val TOTAL_COUNT = 113
private const val LOAD_SIZE = 7

@ExperimentalCoroutinesApi
class GithubPagingSourceTest {

    private val service = FakeGitService(TOTAL_COUNT)

    // Fetch data with empty 'query', no need to make it so real
    private val pagingSource = GithubPagingSource(service, "")
    private val factory = RepoFactory()

    /**
     * Checks the first elements from the first page for equality
     */
    @Test
    fun testFromBegin() = runBlockingTest {
        val data = arrayListOf<Repo>()
        // 'min' - for the case when less than requested is available
        for (i in 0 until min(TOTAL_COUNT, LOAD_SIZE))
            data.add(factory.create(i))
        val nextPageNumber = 1
        val prevKey = null
        val nextKey = if (data.isEmpty()) {
            null
        } else {
            nextPageNumber + LOAD_SIZE / service.pageSize
        }

        assertEquals(
            expected = Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            ),
            actual = pagingSource.load(
                Refresh(
                    key = null,
                    loadSize = LOAD_SIZE,
                    placeholdersEnabled = false
                )
            ),
        )
    }

    /**
     * Checks the last elements from the last page for equality
     */
    @Test
    fun testFromEnd() = runBlockingTest {
        val data = arrayListOf<Repo>()
        val lastCount = TOTAL_COUNT % LOAD_SIZE
        for (i in lastCount downTo 1)
            data.add(factory.create(TOTAL_COUNT - i))
        val nextPageNumber = TOTAL_COUNT / LOAD_SIZE + 1
        val prevKey = if (nextPageNumber == service.startPage) {
            null
        } else {
            nextPageNumber - 1
        }
        val nextKey = if (data.isEmpty()) {
            null
        } else {
            nextPageNumber + (LOAD_SIZE / service.pageSize)
        }

        assertEquals(
            expected = Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            ),
            actual = pagingSource.load(
                Append(
                    key = TOTAL_COUNT / LOAD_SIZE + 1,
                    loadSize = LOAD_SIZE,
                    placeholdersEnabled = false
                )
            ),
        )
    }
}