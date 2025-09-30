package core.data.model.cdt

import core.data.model.MeasureObjectInt
import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CDTResults(

    var circle_perfection: MeasureObjectString = MeasureObjectString(measureObject = 89),
    var imageUrl: MeasureObjectString = MeasureObjectString(measureObject = 186),
    var numbers_sequence: MeasureObjectString = MeasureObjectString(measureObject = 90),
    var hands_position: MeasureObjectString = MeasureObjectString(measureObject = 91),
    var examiner_code: MeasureObjectString = MeasureObjectString(measureObject = 92),

    // Screen 2: Set Time 1
    var requested_time_1: MeasureObjectString = MeasureObjectString(measureObject = 190),
    var time1_imageUrl: MeasureObjectString = MeasureObjectString(measureObject = 585),
    var actual_time_1: MeasureObjectString = MeasureObjectString(measureObject = 193),

    // Screen 3: Set Time 2
    var requested_time_2: MeasureObjectString = MeasureObjectString(measureObject = 192),
    var time2_imageUrl: MeasureObjectString = MeasureObjectString(measureObject = 586),
    var actual_time_2: MeasureObjectString = MeasureObjectString(measureObject = 194)
)