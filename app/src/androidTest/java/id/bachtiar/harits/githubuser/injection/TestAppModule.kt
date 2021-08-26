package id.bachtiar.harits.githubuser.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.bachtiar.harits.githubuser.cache.GithubUserDB
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class  TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryGithubUserDatabase(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(context, GithubUserDB::class.java).allowMainThreadQueries()
        .build()

}