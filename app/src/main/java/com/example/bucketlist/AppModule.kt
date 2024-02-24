package com.example.bucketlist


import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBucketItemDatabase(app: Application): BucketItemDatabase {
        return Room.databaseBuilder(
            app,
            BucketItemDatabase::class.java,
            BucketItemDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBucketItemRepository(db: BucketItemDatabase): BucketItemRepository {
        return BucketItemRepositoryImpl(db.bucketItemDao)
    }

    @Provides
    @Singleton
    fun provideBucketItemUseCases(repository: BucketItemRepository): AllBucketItemUseCases {
        return AllBucketItemUseCases(
            getBucketItems = GetBucketItemUseCase(repository),
            deleteBucketItem = DeleteBucketItemUseCase(repository),
            addBucketItem = AddBucketItemUseCase(repository),
            getBucketItem = GetOneBucketItemUseCase(repository),
            updateBucketItem = UpdateBucketItemUseCase(repository)
        )
    }
}