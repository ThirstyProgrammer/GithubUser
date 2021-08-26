package id.bachtiar.harits.githubuser.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.bachtiar.harits.githubuser.cache.GithubUserDB
import id.bachtiar.harits.githubuser.network.ApiService
import id.bachtiar.harits.githubuser.repository.GithubUserRepository
import id.bachtiar.harits.githubuser.repository.UsersRepository
import id.bachtiar.harits.githubuser.util.Constant
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module(
    includes = [NetworkModule::class]
)
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGithubUserDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, GithubUserDB::class.java, Constant.DATABASE_NAME).build()

   @Singleton
   @Provides
   fun provideUsersDao(
       database: GithubUserDB
   ) = database.usersDao()

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService
    ) = GithubUserRepository(apiService) as UsersRepository
}