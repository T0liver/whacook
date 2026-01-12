package hu.toliver.whacook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class RoomSettingEntity(
    @PrimaryKey val key: String,
    val value: String
)

fun RoomSettingEntity.toCommon() = SettingEntity(
    key = key,
    value = value
)

fun SettingEntity.toRoom() = RoomSettingEntity(
    key = key,
    value = value
)

