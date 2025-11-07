package com.faridnia.mealio.presentation.today

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.faridnia.mealio.presentation.theme.MealioTheme

@Composable
fun FoodLogItem(
    title: String,
    amount: Int,
    unit: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${amount/*.removeTrailingZeros()*/} $unit",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

private fun Double.removeTrailingZeros(): String {
    val s = this.toString()
    return if (s.contains('.')) s.trimEnd('0').trimEnd('.') else s
}

@PreviewLightDark
@Composable
fun FoodLogItemPreview() {
    MealioTheme {
        FoodLogItem(
            title = "Banana",
            amount = 100,
            unit = "g",
            onEdit = {},
            onDelete = {}
        )
    }
}
