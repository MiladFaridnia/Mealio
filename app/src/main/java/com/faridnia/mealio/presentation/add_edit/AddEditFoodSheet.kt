package com.faridnia.mealio.presentation.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.faridnia.mealio.presentation.theme.MealioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFoodSheet(
	initialTitle: String,
	initialUnit: String,
	initialAmount: String,
	onSave: (name: String, unit: String, amount: Double) -> Unit,
	onCancel: () -> Unit,
	modifier: Modifier = Modifier
) {
	var name by remember(initialTitle) { mutableStateOf(initialTitle) }
	var unit by remember(initialUnit) { mutableStateOf(initialUnit) }
	var amountText by remember(initialAmount) { mutableStateOf(initialAmount) }

	val isSaveEnabled = name.isNotBlank() && unit.isNotBlank() && amountText.toDoubleOrNull() != null

	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 12.dp)
	) {
		Text(text = if (initialTitle.isBlank()) "Add Food" else "Edit Food")
		Spacer(modifier = Modifier.height(12.dp))

		OutlinedTextField(
			value = name,
			onValueChange = { name = it },
			label = { Text("Food name") },
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(12.dp))

		OutlinedTextField(
			value = unit,
			onValueChange = { unit = it },
			label = { Text("Unit") },
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(12.dp))

		OutlinedTextField(
			value = amountText,
			onValueChange = { new ->
				// Allow only valid numeric input (optional leading/trailing dots handled by toDoubleOrNull at save)
				amountText = new
			},
			label = { Text("Amount") },
			singleLine = true,
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(16.dp))

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			TextButton(
				onClick = onCancel,
				modifier = Modifier.weight(1f)
			) {
				Text("Cancel")
			}
			Button(
				onClick = {
					val amount = amountText.toDoubleOrNull() ?: return@Button
					onSave(name.trim(), unit.trim(), amount)
				},
				enabled = isSaveEnabled,
				modifier = Modifier.weight(1f)
			) {
				Text("Save")
			}
		}

		Spacer(modifier = Modifier.height(12.dp))
	}
}

@PreviewLightDark
@Composable
fun AddEditFoodSheetPreview() {
    MealioTheme {
        AddEditFoodSheet(
            initialTitle = "Apple",
            initialUnit = "g",
            initialAmount = "100.0",
            onSave = { _, _, _ -> },
            onCancel = {}
        )
    }
}
