package com.example.parliament

import android.content.Context
import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Entity
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.PrimaryKey
import androidx.room3.Room
import androidx.room3.RoomDatabase
import kotlinx.coroutines.flow.Flow


@Database(entities = [Member::class], version = 2, exportSchema = false)
abstract class MemberDatabase : RoomDatabase() {
    abstract val membersDao: MemberDao
    companion object {
        @Volatile
        private var INSTANCE: MemberDatabase? = null
        fun getInstance(context: Context): MemberDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemberDatabase::class.java, "parties"
                    )
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


@Entity(tableName = "member")
data class Member(
    @PrimaryKey
    val hetekaId: Int,
    val seatNumber: Int,
    val firstname: String,
    val lastname: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String,
    val isFavorite: Boolean
)


@Dao
interface MemberDao {

    @Insert
    suspend fun insert(member: Member)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(members: List<Member>)

    @Query("SELECT * FROM member WHERE party = :party")
    fun getMembersByParty(party: String): Flow<List<Member>>

    @Query("UPDATE member SET isFavorite = :fav WHERE party = :party")
    suspend fun setPartyFavorite(party: String, fav: Boolean)

    @Query("SELECT DISTINCT party FROM member WHERE isFavorite = 1")
    fun getFavoriteParties(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM member WHERE isFavorite = 1")
    fun getFavoriteMemberCount(): Flow<Int>
    @Query("SELECT count(hetekaId) FROM member where member.party= :party")
    suspend fun membersInParty(party: String): Int

    @Query("SELECT DISTINCT member.party FROM member")
    fun getParties(): Flow<List<String>>


}
