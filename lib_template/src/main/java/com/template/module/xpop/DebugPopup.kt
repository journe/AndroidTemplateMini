package com.template.module.xpop

import android.content.Context
import com.blankj.utilcode.util.SPUtils
import com.template.CheeseConstant
import com.template.R
import com.template.base.ktx.clickDelay
import com.template.databinding.PopupDebugBinding
import com.template.module.action.ActionHelper
import com.template.module.debug.DebugHelper
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lxj.xpopup.core.BottomPopupView

class DebugPopup(context: Context) : BottomPopupView(context) {
    lateinit var mBinding: PopupDebugBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_debug
    }

    override fun onCreate() {
        super.onCreate()
        mBinding = PopupDebugBinding.bind(popupImplView)
        mBinding.apply {
            viewday.clickDelay { ActionHelper.viewDay("2025-10-27") }
            replaceday.clickDelay {
                ActionHelper.replaceDay(
                    "2025-10-28",
                    DebugHelper.addDay("2025-10-28")
                )
            }
            deleteday.clickDelay { ActionHelper.deleteDay("2025-10-27") }
            copyday.clickDelay { ActionHelper.copyDay("2025-10-26", "2025-10-27") }

            viewMeal.clickDelay { ActionHelper.viewMeal("2025-10-26", "dinner") }
            replaceMeal.clickDelay {
                ActionHelper.replaceMeal(
                    "2025-10-26", "breakfast",
                    DebugHelper.addMeal("breakfast")
                )
            }
            addMeal.clickDelay {
                ActionHelper.addMeal(
                    "2025-10-26",
                    "lunch",
                    DebugHelper.addMeal("lunch")
                )
            }
            deleteMeal.clickDelay { ActionHelper.deleteMeal("2025-10-26", "breakfast") }

            searchDish.clickDelay { ActionHelper.searchDish("娃娃菜") }
            addDish.clickDelay {
                ActionHelper.addDish(
                    "2025-10-26", "breakfast", DebugHelper.addDish()
                )
            }

            deleteDish.clickDelay { ActionHelper.deleteDish("2025-10-26", "breakfast", "牛奶") }
            updateDish.clickDelay {
                ActionHelper.updateDish(
                    "2025-10-26",
                    "breakfast",
                    "牛奶",
                    DebugHelper.addDish()
                )
            }
            replaceDish.clickDelay {
                ActionHelper.replaceDish(
                    "2025-10-26", "breakfast", "牛奶",
                    DebugHelper.addDish()
                )
            }

            loginBtn.clickDelay {
                LiveEventBus.get<String>("login").post("login")
            }
            featureBtn.clickDelay {
                LiveEventBus.get<String>("featureBtn").post("featureBtn")
            }

            logoutBtn.clickDelay {
                LiveEventBus.get<String>("logout").post("logout")
                CheeseConstant.userToken = ""
                SPUtils.getInstance().put(CheeseConstant.TOKEN_KEY, "")
            }

            switchBtn.setOnCheckedChangeListener { btn, checked ->
                CheeseConstant.mockApi = checked
                SPUtils.getInstance().put(CheeseConstant.MOCK_KEY, checked)
                if (checked) {
                    btn.text = "本地数据  "
                } else {
                    btn.text = "线上数据  "
                }
            }
            switchBtn.isChecked = SPUtils.getInstance().getBoolean(CheeseConstant.MOCK_KEY)

        }
    }

}