package id.bachtiar.harits.githubuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import id.bachtiar.harits.githubuser.cache.dao.FakeUsersDao
import id.bachtiar.harits.githubuser.repository.FakeGithubUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
class ListViewModelTest {

    private lateinit var mViewModel: ListViewModel

    //    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        mViewModel = ListViewModel(FakeGithubUserRepository(), FakeUsersDao())
    }

    @Test
    fun `get users, returns list of user`() {
        mViewModel.getUsers()

        val value = mViewModel.users.getOrAwaitValue()

        assertThat(value.first().username).isEqualTo("bachtiar")
    }

    @Test
    fun `get users with search, returns list of user`() {
        mViewModel.username = "bach"
        mViewModel.getUsers()

        val value = mViewModel.users.getOrAwaitValue()

        assertThat(value.size).isNotNull()
    }
}