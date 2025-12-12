package com.workmate.workmatetz.presentation.screens.usersList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.workmate.workmatetz.R
import com.workmate.workmatetz.domain.entity.User
import com.workmate.workmatetz.presentation.screens.components.LoadingView
import com.workmate.workmatetz.presentation.screens.components.SnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UsersListScreen(
    gender: String,
    nationality: String,
    onGenerateClick: () -> Unit,
    onDetailsClick: (String) -> Unit,
    viewModel: UsersListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val users by viewModel.allUsers.collectAsStateWithLifecycle()
    val deleteState by viewModel.deleteState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackMessages.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(key1 = gender, key2 = nationality) {
        if (uiState is UsersListUiState.Idle) {
            viewModel.generateUser(gender, nationality)
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
            when (uiState) {
                UsersListUiState.Loading -> {
                    UsersListContent(
                        users = users,
                        deleteState = deleteState,
                        paddingValues = paddingValues,
                        onDetailsClick = onDetailsClick,
                        onDeleteClick = { viewModel.deleteUser(it) }
                    )
                    LoadingView()
                }

                else -> {
                    if (users.isEmpty()) {
                        val message = "No users yet. Generate your first user!"
                        EmptyStateView(message)
                    } else {
                        UsersListContent(
                            users = users,
                            deleteState = deleteState,
                            paddingValues = paddingValues,
                            onDetailsClick = onDetailsClick,
                            onDeleteClick = { viewModel.deleteUser(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun UsersListContent(
    users: List<User>,
    deleteState: DeleteState,
    paddingValues: PaddingValues,
    onDetailsClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    if (users.isEmpty()) {
        EmptyStateView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(users) { user ->
                val isDeleting = deleteState is DeleteState.Deleting &&
                        deleteState.seed == user.seed
                UserCard(
                    isDeleting = isDeleting,
                    data = user,
                    onDetailsClick = { onDetailsClick(user.seed) },
                    onDeleteClick = onDeleteClick,
                )
            }
        }
    }
}

@Composable
internal fun EmptyStateView(text: String = "") {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
internal fun UserCard(
    isDeleting: Boolean,
    data: User,
    onDetailsClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isDeleting) { onDetailsClick(data.seed) }
    ) {
        Box {
            IconButton(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.TopEnd),
                onClick = { onDeleteClick(data.seed) }
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "delete"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = data.imageUrl,
                    contentDescription = "avatar",
                    modifier = Modifier
                        .size(108.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_placeholder)
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 12.dp),
                ) {
                    Text(
                        text = "${data.firstName} ${data.lastName}",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily.SansSerif,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = data.phone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = data.country,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}