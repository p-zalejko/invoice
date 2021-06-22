package com.gmail.pzalejko.invoice.core.model.subject

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * Address of the client who must pay off.
 */
data class SubjectAddress(val street: String, val number: String, val city: String) : ValueObject