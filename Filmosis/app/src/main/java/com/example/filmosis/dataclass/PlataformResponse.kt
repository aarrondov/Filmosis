package com.example.filmosis.dataclass

import com.google.gson.annotations.SerializedName


data class NetworkDetailsResponse(
    val id: Int,
    val results: Map<String, PaisServicios>
)

data class PaisServicios(
    val link: String,
    val flatrate: List<Servicio>,
    val rent: List<Servicio>,
    val buy: List<Servicio>
)
data class Servicio(
    val logo_path: String,
    val provider_id: Int,
    val provider_name: String,
    val display_priority: Int
)



data class Network(
    val id: Int,
    val name: String,
    val logo_path: String,
    val headquarters: String,
    val homepage: String,
    val origin_country: String
)


//{
//    "headquarters": "New York City, New York",
//    "homepage": "https://www.hbo.com",
//    "id": 49,
//    "logo_path": "/tuomPhY2UtuPTqqFnKMVHvSb724.png",
//    "name": "HBO",
//    "origin_country": "US"
//}




