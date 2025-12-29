package com.template.common.model.db


data class AccountBean(
	var id: Int,
	var nickname: String? = null,
	var phone: String? = null,
) {
}