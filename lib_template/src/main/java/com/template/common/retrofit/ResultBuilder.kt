package com.template.common.retrofit

import com.template.module.debug.ToastEvent
import com.jeremyliao.liveeventbus.LiveEventBus
import com.orhanobut.logger.Logger

fun <T> ApiResponse<T>.parseData(listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    when (this) {
        is ApiSuccessResponse -> listener.onSuccess(this.data)
        is ApiEmptyBodyResponse -> listener.onDataEmpty(this.msg)
        is ApiFailedResponse -> listener.onFailed(this.code, this.msg, this.data)
        is ApiErrorResponse -> listener.onError(this.throwable, this.msg)
    }
    listener.onComplete()
}

class ResultBuilder<T> {
    var onSuccess: (data: T) -> Unit = {}
    var onDataEmpty: (message: String?) -> Unit = {
        Logger.d(it)
    }
    var onFailed: (errorCode: Int?, errorMsg: String?, data: T?) -> Unit =
        { errorCode, errorMsg, data ->
            Logger.d("onFailed")
            errorMsg?.let {
                LiveEventBus.get(ToastEvent::class.java)
                    .postAcrossProcess(ToastEvent(it))
//            ToastCCK.toast(it, ToastCCK.style.ERROR)
//            ToastCCK.error(it).show()
            }
        }
    var onError: (e: Throwable?, errorMsg: String?) -> Unit = { e, errorMsg ->
        Logger.d("onError:${errorMsg}\n ${e?.message}")
//        if (BuildConfig.DEBUG) {
            LiveEventBus.get(ToastEvent::class.java)
                .postAcrossProcess(
                    ToastEvent(e?.message.toString())
                )
//        } else {
//            LiveEventBus.get(ToastEvent::class.java)
//                .postAcrossProcess(ToastEvent("网络错误", ToastCCK.style.ERROR))
//        }
    }
    var onComplete: () -> Unit = {}
}