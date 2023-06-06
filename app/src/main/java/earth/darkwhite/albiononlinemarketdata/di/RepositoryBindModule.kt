package earth.darkwhite.albiononlinemarketdata.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import earth.darkwhite.albiononlinemarketdata.domain.implementation.DataStoreRepoImpl
import earth.darkwhite.albiononlinemarketdata.domain.implementation.DatabaseRepoImpl
import earth.darkwhite.albiononlinemarketdata.domain.implementation.ResultsRepoImpl
import earth.darkwhite.albiononlinemarketdata.domain.repository.DataStoreRepository
import earth.darkwhite.albiononlinemarketdata.domain.repository.DatabaseRepository
import earth.darkwhite.albiononlinemarketdata.domain.repository.ResultsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {
  
  @Binds
  @Singleton
  abstract fun bindMainRepository(impl: DatabaseRepoImpl): DatabaseRepository
  
  @Binds
  @Singleton
  abstract fun bindNetworkRepository(impl: ResultsRepoImpl): ResultsRepository
  
  @Binds
  @Singleton
  abstract fun bindDataStoreRepository(impl: DataStoreRepoImpl): DataStoreRepository
}