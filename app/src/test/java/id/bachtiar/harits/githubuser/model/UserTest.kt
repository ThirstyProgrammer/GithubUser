package id.bachtiar.harits.githubuser.model

import id.bachtiar.harits.githubuser.UserAdapter
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserTest {
    private val id = 1
    private val username = "MOJOMBO"
    private val avatar = "avatar_url"
    private val name = "Mojombo"
    private val company = "company"
    private val location = "location"
    private val followers = 150
    private val following = 10
    private val repos = 5
    private val url = "url_detail"
    private val githubUrl = "github_url"
    private val followingUrl = "following_url"
    private val followersUrl = "followers_url"

    @Mock
    var user: User? = null

    annotation class Mock

    @Before
    fun setUp() {
        user = User(
            id = id,
            username = username,
            avatar = avatar,
            name = name,
            company = company,
            location = location,
            followers = followers,
            following = following,
            repos = repos,
            url = url,
            githubUrl = githubUrl,
            followersUrl = followersUrl,
            followingUrl = followingUrl
        )
    }

    @After
    fun tearDown() {
        user = null
    }

    @Test
    fun getId() {
        assertEquals(id, user?.id)
    }

    @Test
    fun getUsername() {
        assertEquals(username, user?.username)
    }

    @Test
    fun getAvatar() {
        assertEquals(avatar, user?.avatar)
    }

    @Test
    fun getName() {
        assertEquals(name, user?.name)
    }

    @Test
    fun getCompany() {
        assertEquals(company, user?.company)
    }

    @Test
    fun getLocation() {
        assertEquals(location, user?.location)
    }

    @Test
    fun getFollowers() {
        assertEquals(followers, user?.followers)
    }

    @Test
    fun getFollowing() {
        assertEquals(following, user?.following)
    }

    @Test
    fun getRepos() {
        assertEquals(repos, user?.repos)
    }

    @Test
    fun getUrl() {
        assertEquals(url, user?.url)
    }

    @Test
    fun getGithubUrl() {
        assertEquals(githubUrl, user?.githubUrl)
    }

    @Test
    fun getFollowersUrl() {
        assertEquals(followersUrl, user?.followersUrl)
    }

    @Test
    fun getFollowingUrl() {
        assertEquals(followingUrl, user?.followingUrl)
    }

    @Test
    fun getTypeItem() {
        assertEquals(UserAdapter.TYPE_ITEM, user?.typeItem)
    }
}