package com.template.common.room.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomBean(
	@PrimaryKey
	var id: Int,
	var nickname: String? = null,
	var phone: String? = null,
)