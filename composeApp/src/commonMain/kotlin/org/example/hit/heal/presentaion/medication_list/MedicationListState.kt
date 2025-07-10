package org.example.hit.heal.presentaion.medication_list

import core.data.model.Medications.Medication


class MedicationListState() {

    val medicationList: List<Medication> = emptyList()
    val searchMedications: List<Medication> = emptyList()
    val selectedMedication: Medication? = null
    val selectedMedicationIndex: Int = 0
    val errorMessage: String? = null


}