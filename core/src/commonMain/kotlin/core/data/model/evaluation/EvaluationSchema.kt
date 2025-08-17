package core.data.model.evaluation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the schema for an evaluation, which includes a list of questions.
 * Each question can be of different types such as open text, single choice, multi-choice, etc.
 */
@Serializable
sealed class Question {
    abstract val id: Int
    abstract val label: String
    abstract val required: Boolean
    open val style: StyleHint = StyleHint()
}

@Serializable
data class StyleHint(
    val variant: String? = null, // "pill", "plain", "slider", etc.
    val dense: Boolean = false,
)

@Serializable
data class Option(
    val id: String,
    val text: String,
    val icon: String? = null
)


@SerialName("open_text")
data class OpenText(
    override val id: Int,
    override val label: String,
    override val required: Boolean,
    override val style: StyleHint = StyleHint(),
    val placeholder: String? = null,
    val maxLength: Int? = null
) : Question()


@SerialName("single_choice")
data class SingleChoice(
    override val id: Int,
    override val label: String,
    override val required: Boolean,
    override val style: StyleHint = StyleHint(),
    val options: List<Option>,
    val randomize: Boolean = false
) : Question()


@SerialName("multi_choice")
data class MultiChoice(
    override val id: Int,
    override val label: String,
    override val required: Boolean,
    override val style: StyleHint = StyleHint(),
    val options: List<Option>,
    val maxSelections: Int? = null
) : Question()


@SerialName("toggle")
data class ToggleQ(
    override val id: Int,
    override val label: String,
    override val required: Boolean,
    override val style: StyleHint = StyleHint(),
    val onLabel: String = "On",
    val offLabel: String = "Off"
) : Question()


@SerialName("slider")
data class SliderQ(
    override val id: Int,
    override val label: String,
    override val required: Boolean,
    override val style: StyleHint = StyleHint(variant = "slider"),
    val min: Float,
    val max: Float,
    val step: Float? = null,
    val startText: String? = null,
    val endText: String? = null
) : Question()


@SerialName("drawing")
data class DrawingQ(
    override val id: Int,
    override val label: String,
    override val required: Boolean,
    override val style: StyleHint = StyleHint(),
    val hint: String? = null
) : Question()



@SerialName("human_body")
data class HumanBodyQ(
    override val id: Int,
    override val label: String,
    override val required: Boolean,
    override val style: StyleHint = StyleHint(),
    val groups: List<BodyGroup>
) : Question()

@Serializable
data class BodyGroup(
    val title: String,
    val subOptions: List<String>
)