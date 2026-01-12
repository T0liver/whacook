package hu.toliver.whacook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.toliver.whacook.data.local.dao.RecipeDao
import hu.toliver.whacook.data.local.dao.SettingDao
import hu.toliver.whacook.data.local.entity.RoomRecipeEntity
import hu.toliver.whacook.data.local.entity.RoomSettingEntity

@Database(entities = [RoomRecipeEntity::class, RoomSettingEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun settingDao(): SettingDao
}