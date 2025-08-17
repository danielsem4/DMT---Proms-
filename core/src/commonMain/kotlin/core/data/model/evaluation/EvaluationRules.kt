package core.data.model.evaluation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Evaluation rules for visibility and requirement of fields in the evaluation form.
 * These rules are used to determine if a field should be visible or required based on certain conditions.
 */

@Serializable
sealed class Expr {
    @Serializable @SerialName("equals") data class Equals(val id: Int, val value: String) : Expr()
    @Serializable @SerialName("in") data class In(val id: Int, val values: List<String>) : Expr()
    @Serializable @SerialName("gt") data class GreaterThan(val id: Int, val number: Double) : Expr()
    @Serializable @SerialName("not") data class Not(val expr: Expr) : Expr()
    @Serializable @SerialName("and") data class And(val exprs: List<Expr>) : Expr()
    @Serializable @SerialName("or") data class Or(val exprs: List<Expr>) : Expr()
}

@Serializable
data class RuleSet(
    val visibleIf: Expr? = null,
    val requiredIf: Expr? = null
)