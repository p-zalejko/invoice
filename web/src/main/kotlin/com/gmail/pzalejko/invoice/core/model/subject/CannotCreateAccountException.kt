package com.gmail.pzalejko.invoice.core.model.subject

import java.lang.RuntimeException

class CannotCreateAccountException(ex: Exception): RuntimeException(ex) {
}