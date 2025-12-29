package com.template.base.utils

/**
 * 状态视图的状态枚举
 */
// 密封类定义各种页面状态
sealed class UiState(code: Int) {
    class Success : UiState(1)

    //    class Initial : UiState(1)
    class Loading : UiState(2)

    // 错误状态，包含错误信息
    data class Error(
        val message: String = "",
        val cause: Throwable? = null,
        val errorCode: Int? = null
    ) : UiState(4)

    // 空数据状态（可选，根据需求添加）
    class Empty : UiState(8)
}

object SwitchUIState {
    const val SWITCH_1 = 1 shl 0  // 0001 (二进制) = 1 (十进制)
    const val SWITCH_2 = 1 shl 1  // 0010 (二进制) = 2 (十进制)
    const val SWITCH_3 = 1 shl 2  // 0100 (二进制) = 4 (十进制)
    const val SWITCH_4 = 1 shl 3  // 1000 (二进制) = 8 (十进制)

    // 所有开关的集合
    val ALL_SWITCHES = listOf(SWITCH_1, SWITCH_2, SWITCH_3, SWITCH_4)

}

// 状态管理类（确保最多只有一个开关打开）
class SingleSwitchManager {
    // 用一个整数存储所有开关的状态（初始值0表示所有开关关闭）
    private var currentState: Int = 0

    // 打开指定开关（会自动关闭其他所有开关）
    fun turnOn(switch: Int) {
        // 检查是否是有效的开关
        require(SwitchUIState.ALL_SWITCHES.contains(switch)) { "无效的开关" }

        // 先关闭所有开关，再打开指定开关
        currentState = switch
    }

    // 关闭指定开关
    fun turnOff(switch: Int) {
        if (isOn(switch)) {
            currentState = 0  // 关闭当前打开的开关（回到全关状态）
        }
    }

    // 关闭所有开关
    fun turnOffAll() {
        currentState = 0
    }

    // 切换指定开关状态（如果打开则关闭，如果关闭则打开并关闭其他）
    fun toggle(switch: Int) {
        if (isOn(switch)) {
            // 如果当前开关是打开的，则关闭它
            turnOff(switch)
        } else {
            // 如果当前开关是关闭的，则打开它（并关闭其他）
            turnOn(switch)
        }
    }

    // 检查指定开关是否打开
    fun isOn(switch: Int): Boolean {
        return currentState == switch
    }

    // 检查是否所有开关都关闭
    fun isAllOff(): Boolean {
        return currentState == 0
    }

    // 获取当前状态的二进制字符串（方便调试）
    fun getBinaryString(): String {
        // 补全4位二进制显示（例如1→0001）
        return String.format("%4s", currentState.toString(2)).replace(' ', '0')
    }

    // 获取当前打开的开关（如果有）
    fun getCurrentOnSwitch(): Int? {
        return if (currentState == 0) null else currentState
    }
}
