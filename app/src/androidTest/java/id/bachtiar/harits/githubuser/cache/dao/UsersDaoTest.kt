package id.bachtiar.harits.githubuser.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import id.bachtiar.harits.githubuser.cache.GithubUserDB
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class UsersDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: GithubUserDB
    private lateinit var usersDao: UsersDao

    @Before
    fun setup() {
        hiltRule.inject()
        usersDao = database.usersDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlockingTest {
        val user = UsersEntity(
            id = 1,
            username = "username",
            avatar = "avatar_url",
            githubUrl = "github_url",
            url = "url"
        )

        usersDao.insert(user)
        val users = usersDao.getUsers()

        assertThat(users).contains(user)
    }

    @Test
    fun insertSameIDTwice_onlyFirstEntitySaved() = runBlockingTest {
        val user = UsersEntity(
            id = 1,
            username = "username",
            avatar = "avatar_url",
            githubUrl = "github_url",
            url = "url"
        )
        val user2 = UsersEntity(
            id = 1,
            username = "username_2",
            avatar = "avatar_url",
            githubUrl = "github_url",
            url = "url"
        )

        usersDao.insert(user)
        usersDao.insert(user2)

        val users = usersDao.getUsers()

        assertThat(users).contains(user)
        assertThat(users).doesNotContain(user2)
    }

    @Test
    fun deleteUsers() = runBlockingTest {
        val user1 = UsersEntity(
            id = 1,
            username = "username",
            avatar = "avatar_url",
            githubUrl = "github_url",
            url = "url"
        )
        val user2 = UsersEntity(
            id = 2,
            username = "username2",
            avatar = "avatar_url",
            githubUrl = "github_url",
            url = "url"
        )
        usersDao.insert(user1)
        usersDao.insert(user2)
        usersDao.deleteAll()

        val users = usersDao.getUsers()

        assertThat(users).doesNotContain(user1)
        assertThat(users).doesNotContain(user2)
    }
}