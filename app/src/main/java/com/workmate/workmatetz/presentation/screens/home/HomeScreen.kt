package com.workmate.workmatetz.presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.workmate.workmatetz.presentation.screens.components.BackButton
import com.workmate.workmatetz.presentation.screens.components.LoadingView
import com.workmate.workmatetz.presentation.screens.components.SnackBar

@Composable
fun HomeScreen(
    onGenerateClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackMessages.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackBar(snackBarHostState) }
    ) { paddingValues ->
        BackButton(paddingValues, onBackClick)

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                text = "Generate user",
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(24.dp))

            when (uiState) {
                is HomeUiState.Loading -> {
                    LoadingView()
                }

                is HomeUiState.Success -> {
                    val state = uiState as HomeUiState.Success

                    HomeContent(
                        onGenerateClick = onGenerateClick,
                        selectedGender = state.selectedGender,
                        selectedNationality = state.selectedNationality,
                        nationalities = state.nationalities,
                        viewModel = viewModel
                    )

                }

                is HomeUiState.Error -> {
                    HomeContent(
                        onGenerateClick = onGenerateClick,
                        selectedGender = "",
                        selectedNationality = "",
                        nationalities = listOf("au" to "Australian"),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeContent(
    onGenerateClick: (String, String) -> Unit,
    selectedGender: String,
    selectedNationality: String,
    nationalities: List<Pair<String, String>>,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Select Gender:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(12.dp))
        CustomDropdownMenu(
            items = listOf("male" to "Male", "female" to "Female"),
            onValueSelected = { viewModel.updateSelectedGender(it) }
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Select Nationality:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(12.dp))
        CustomDropdownMenu(
            items = nationalities,
            onValueSelected = { viewModel.updateSelectedNationality(it) }
        )

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                onGenerateClick(selectedGender, selectedNationality)
            }
        ) {
            Text("GENERATE")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomDropdownMenu(
    items: List<Pair<String, String>>,
    onValueSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[0].second) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                ),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { (value, displayText) ->
                DropdownMenuItem(
                    text = {
                        Text(
                            displayText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        onValueSelected(value)
                        selectedOptionText = displayText
                        expanded = false
                    }
                )
            }
        }
    }
}