package tj.test.netup.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MediaResponse(
    @SerializedName("media") val mediaList: List<MediaDto> = listOf()
)

data class MediaDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name", defaultValue = "")
    val name: String,
    @ColumnInfo(name = "url", defaultValue = "")
    val url: String
)

