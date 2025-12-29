package com.template.module.bean.net

data class PostDishSaveBody(
    val id: Int? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val nutrients: String? = null,
    val portion: String? = null,
    val recommendationReasons: List<RecommendationReason>? = null
)
