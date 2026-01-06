package com.workmate.workmatetz.presentation.screens.usersList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.workmate.workmatetz.presentation.screens.components.SnackBar

@Composable
fun ExistingUsersListScreen(
    onGenerateClick: () -> Unit,
    onDetailsClick: (String) -> Unit,
    viewModel: UsersListViewModel = hiltViewModel()
) {
    val users by viewModel.allUsers.collectAsState(initial = emptyList())
    val deleteState by viewModel.deleteState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackMessages.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onGenerateClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "generate")
            }
        },
        snackbarHost = { SnackBar(snackBarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (users.isEmpty()) {
                EmptyStateView()
            } else {
                UsersListContent(
                    users = users,
                    deleteState = deleteState,
                    paddingValues = paddingValues,
                    onDetailsClick = onDetailsClick,
                    onDeleteClick = { viewModel.deleteUser(it) },
                    onShareClick = { viewModel.shareUser(it) },
                    onCopyClick = { viewModel.copyUserToClipboard(it) }
                )
            }
        }
    }
}