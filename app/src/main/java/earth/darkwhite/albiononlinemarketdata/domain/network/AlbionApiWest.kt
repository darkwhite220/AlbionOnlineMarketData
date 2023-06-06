package earth.darkwhite.albiononlinemarketdata.domain.network

import earth.darkwhite.albiononlinemarketdata.domain.model.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbionApiWest {
  // Brecilien = 5003
  @GET(
    "stats/prices/{search}?locations=5003,BlackMarket,Bridgewatch,Caerleon,FortSterling,Lymhurst,Martlock," +
      "Thetford&qualities=0"
  )
  suspend fun getServerMainCitiesPrices(@Path("search") search: String): List<ItemResponse>
  
}