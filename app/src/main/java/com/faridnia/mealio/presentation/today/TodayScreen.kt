package com.faridnia.mealio.presentation.today

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
	modifier: Modifier = Modifier,
	viewModel: TodayViewModel = hiltViewModel()
) {
	val logs by viewModel.todayLogs.collectAsState(initial = emptyList())
	val snackbarHostState = remember { SnackbarHostState() }
	val scope = rememberCoroutineScope()

	// Bottom sheet state and editing state
	var isSheetOpen by remember { mutableStateOf(false) }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	var editingItem by remember { mutableStateOf<EditingPayload?>(null) }

	// Recompute total calories whenever logs change (amount * 10)
	val totalCalories by remember(logs) {
		mutableIntStateOf(
			logs.sumOf { (it.amount * 10.0) }.toInt()
		)
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "Today") }
			)
		},
		snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					editingItem = null
					isSheetOpen = true
				}
			) {
				Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
			}
		},
		modifier = modifier.fillMaxSize()
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			// Total calories card
			Card(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 16.dp, vertical = 12.dp)
			) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(16.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Column(modifier = Modifier.weight(1f)) {
						Text(
							text = "Total Calories",
							style = MaterialTheme.typography.titleMedium
						)
						Spacer(modifier = Modifier.height(4.dp))
						Text(
							text = "$totalCalories kcal",
							style = MaterialTheme.typography.headlineSmall,
							fontWeight = FontWeight.Bold
						)
					}
				}
			}

			// List of today's logs
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(bottom = 96.dp) // leave space for FAB
			) {
				items(
					items = logs,
					key = { it.id ?: it.hashCode().toLong() }
				) { log ->
					FoodLogItem(
						title = log.foodTitle,
						amount = log.amount,
						unit = log.unit,
						onEdit = {
							editingItem = EditingPayload(
								id = log.id,
								title = log.foodTitle,
								unit = log.unit,
								amount = log.amount
							)
							isSheetOpen = true
						},
						onDelete = {
							// Perform delete then show snackbar with Undo
							viewModel.deleteLog(log)
							scope.launch {
								val result = snackbarHostState.showSnackbar(
									message = "Item deleted",
									actionLabel = "Undo"
								)
								if (result == SnackbarResult.ActionPerformed) {
									viewModel.undoLastDelete()
								}
							}
						}
					)
				}
			}
		}
	}

	// Add/Edit bottom sheet
	if (isSheetOpen) {
		ModalBottomSheet(
			onDismissRequest = {
				isSheetOpen = false
				editingItem = null
			},
			sheetState = sheetState
		) {
			AddEditFoodSheet(
				initialTitle = editingItem?.title.orEmpty(),
				initialUnit = editingItem?.unit.orEmpty(),
				initialAmount = editingItem?.amount?.toString().orEmpty(),
				onCancel = {
					isSheetOpen = false
					editingItem = null
				},
				onSave = { name, unit, amount ->
					// Route to ViewModel
					if (editingItem == null) {
						// Adding new item
						viewModel.addLog(name, unit, amount)
					} else {
						// Updating existing item
						val id = editingItem?.id
						if (id != null) {
							viewModel.updateLog(id, name, unit, amount)
						} else {
							// Fallback if no id available, treat as add
							viewModel.addLog(name, unit, amount)
						}
					}
					isSheetOpen = false
					editingItem = null
				}
			)
		}
	}
}

