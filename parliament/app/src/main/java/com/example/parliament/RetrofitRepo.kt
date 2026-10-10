package com.example.parliament

import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitRepo(private val dao: MemberDao) {

    suspend fun inserting() {
        val members = Api.service.getMembers()
        dao.insertAll(members)
    }

    fun getAllParties(): Flow<List<String>> {
        val parties: Flow<List<String>> = dao.getParties()
        return parties
    }
    // equivalent to this
    // fun getAllParties(): Flow<List<String>> = dao.getParties()


    suspend fun setPartyFavorite(party: String, fav: Boolean) = dao.setPartyFavorite(party, fav)
    fun getFavoriteParties(): Flow<List<String>> = dao.getFavoriteParties()
    fun getFavoriteMemberCount(): Flow<Int> = dao.getFavoriteMemberCount()
    fun getMembersByParty(party: String): Flow<List<Member>> = dao.getMembersByParty(party)
}

object Api {
    const val URL = "https://users.metropolia.fi/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    interface Service {
        @GET("~peterh/seating.json")
        suspend fun getMembers(): List<Member>
    }

    val service: Service by lazy { retrofit.create(Service::class.java) }

}