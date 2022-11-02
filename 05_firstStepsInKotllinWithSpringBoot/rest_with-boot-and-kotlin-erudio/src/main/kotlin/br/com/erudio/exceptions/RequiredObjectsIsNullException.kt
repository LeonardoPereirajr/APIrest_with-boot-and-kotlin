package br.com.erudio.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.*

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectsIsNullException: RuntimeException {
    constructor(): super ("It is not allowed to persist a null Object")
    constructor(exception: String?):super(exception)
}