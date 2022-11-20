package uz.learn.mapstruct.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T>(
    val success: Boolean = true,
    val data: T? = null,
    val message: String? = null
) {

    constructor(data: T) : this(true, data)
    constructor(message: String) : this(success = false, message = message)

}
