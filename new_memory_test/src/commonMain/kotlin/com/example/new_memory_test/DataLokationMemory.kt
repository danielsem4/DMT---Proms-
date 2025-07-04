package com.example.new_memory_test

import kotlinx.serialization.Serializable

@Serializable
data class DataLokationMemory (
    val id: Int,
    val points: Int,
    val bedroomClosetBlock :List<String>,
    val bedroomBedBlock :List<String>,
    val bedroomNightstandBlock :List<String>,

    val bedroomHacngerBlock :List<String>,
    val bedroomFlorBlock :List<String>,
    val salonTableBlock :List<String>,
    val salonSofaBlock :List<String>,
    val salonHangerBlock :List<String>,
    val kitchenTableBlock :List<String>,
    val kitchenFridgeBlock :List<String>,
    val kitchenBlock :List<String>,

    val book :List<String>,
    val dress :List<String>,
    val phone :List<String>,
    val coffee :List<String>,
    val shoes :List<String>,
    val glasses :List<String>,
    val listenApp :List<String>,
    val backpack :List<String>,
    val keys :List<String>,
    val bottles :List<String>,
    val wallet :List<String>,
    val records :List<String>

)