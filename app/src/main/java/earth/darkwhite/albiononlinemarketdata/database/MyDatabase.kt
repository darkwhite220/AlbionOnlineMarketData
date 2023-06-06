package earth.darkwhite.albiononlinemarketdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.database.model.GameItem

@Database(entities = [GameItem::class, Category::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
  abstract val databaseMyDao: MyDao
}