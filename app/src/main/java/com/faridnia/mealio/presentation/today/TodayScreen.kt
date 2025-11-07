package com.faridnia.mealio.presentation.today

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.faridnia.mealio.R
import com.faridnia.mealio.domain.model.FoodLog
import com.faridnia.mealio.presentation.add_edit.AddEditFoodSheet
import com.faridnia.mealio.presentation.today.component.FoodLogItem
import com.faridnia.mealio.presentation.today.component.TotalCaloriesCard
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

    val totalCalories by viewModel.totalCalories.collectAsState()

    val today = LocalDate.now(ZoneId.of("Asia/Tehran"))
    val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = today.format(formatter)
                    )
                })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingItem = null
                    isSheetOpen = true
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "Add")
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TotalCaloriesCard(totalCalories)

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

private fun calculateCalories(logs: List<FoodLog>): Int = logs.sumOf { (it.amount * 10.0) }.toInt()


