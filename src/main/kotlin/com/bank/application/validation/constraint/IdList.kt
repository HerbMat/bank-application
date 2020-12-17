package com.bank.application.validation.constraint

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.ReportAsSingleViolation
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass

/**
 * It checks if String follows [0-9],[0-9] or [0-9] or ALL pattern.
 */
@ReportAsSingleViolation
@Constraint(validatedBy = [])
@Target(AnnotationTarget.FIELD)
@Pattern(regexp = "(([0-9],?)*)|(ALL)\$")
annotation class IdList(
        val message: String = "{id.list.invalid}",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)