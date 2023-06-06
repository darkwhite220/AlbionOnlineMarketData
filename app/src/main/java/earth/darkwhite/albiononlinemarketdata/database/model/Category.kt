package earth.darkwhite.albiononlinemarketdata.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import earth.darkwhite.albiononlinemarketdata.database.StringTypeConverter

@Entity(tableName = "category_table")
@TypeConverters(value = [StringTypeConverter::class])
data class Category(
  @PrimaryKey(autoGenerate = true)
  val index: Long = 0,
  val name: String,
  val items: List<String>
)

