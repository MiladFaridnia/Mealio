package com.faridnia.mealio.presentation.today

// Local UI payload for editing state
data class EditingPayload(
	val id: String?,
	val title: String,
	val unit: String,
	val amount: Int
)