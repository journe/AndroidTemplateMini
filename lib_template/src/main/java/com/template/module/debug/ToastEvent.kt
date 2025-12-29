package com.template.module.debug

import com.jeremyliao.liveeventbus.core.LiveEvent

data class ToastEvent(val msg: String) : LiveEvent
