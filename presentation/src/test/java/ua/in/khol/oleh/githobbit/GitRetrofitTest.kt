package ua.`in`.khol.oleh.githobbit

import org.junit.Assert
import org.junit.Test
import ua.`in`.khol.oleh.githobbit.data.network.github.GitRetrofit

class GitRetrofitTest {

    @Test
    fun gitRetrofit_checkBaseUrlEquals() {
        Assert.assertEquals(GitRetrofit.BASE_URL, "https://api.github.com/")
    }

    @Test
    fun gitRetrofit_checkServiceNotNull() {
        Assert.assertNotNull(GitRetrofit.service)
    }
}