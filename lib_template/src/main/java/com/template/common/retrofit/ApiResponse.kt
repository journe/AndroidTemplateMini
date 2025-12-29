package com.template.common.retrofit

import java.io.Serializable

open class ApiResponse<T>(
    open val data: T? = null,
    open val code: Int? = null,
    open val msg: String? = null,
    open val throwable: Throwable? = null,//这个error是协程抛出的异常
) : Serializable {
    val isSuccess: Boolean
        get() = code == 200 || code == 0

    override fun toString(): String {
        return "ApiResponse(data=$data, errorCode=$code, message=$msg, error=$throwable)"
    }
}

data class ApiSuccessResponse<T>(override val data: T, override val msg: String?) :
    ApiResponse<T>()

data class ApiEmptyBodyResponse<T>(
    override val code: Int? = null,
    override val msg: String? = null
) : ApiResponse<T>()

data class ApiFailedResponse<T>(
    override val code: Int?,
    override val msg: String?,
    override val data: T? = null
) : ApiResponse<T>()

data class ApiErrorResponse<T>(override val throwable: Throwable?) : ApiResponse<T>()