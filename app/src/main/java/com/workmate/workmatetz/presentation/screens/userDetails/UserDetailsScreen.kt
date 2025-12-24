package com.workmate.workmatetz.presentation.screens.userDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.workmate.workmatetz.R
import com.workmate.workmatetz.domain.entity.User
import com.workmate.workmatetz.presentation.screens.components.BackButton
import com.workmate.workmatetz.presentation.screens.components.LoadingView
import com.workmate.workmatetz.presentation.screens.components.PrimaryText
import com.workmate.workmatetz.presentation.screens.components.SnackBar

@Composable
fun UserDetailsScreen(
    seed: String,
    onBackClick: () -> Unit,
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackMessages.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadUser(seed)
    }

    Scaffold(snackbarHost = { SnackBar(snackBarHostState) }) { paddingValues ->
        when (uiState) {
            UserDetailsUiState.Loading -> {
                LoadingView()
            }

            is UserDetailsUiState.Success -> {
                val user = (uiState as UserDetailsUiState.Success).user

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.2f)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                        MaterialTheme.colorScheme.primary
                                    )
                                )
                            )
                    )

                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.26f)
                        ) {
                            AsyncImage(
                                model = user.imageUrl,
                                contentDescription = "avatar",
                                modifier = Modifier
                                    .size(108.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.BottomCenter),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.ic_placeholder)
                            )
                        }
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Hi how are you today?")
                            Text("I'm")
                            Text(
                                text = "${user.firstName} ${user.lastName}",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            TabCard(user)
                        }
                    }
                }
                BackButton(paddingValues, onBackClick)
            }

            is UserDetailsUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = (uiState as UserDetailsUiState.Error).message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadUser(seed) }
                    ) {
                        Text("Retry")
                    }
                }
            }
        }

    }
}

@Composable
fun TabCard(data: User) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        Icons.Default.Person,
        Icons.Default.Call,
        Icons.Default.Email,
        Icons.Default.Place
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                MaterialTheme.colorScheme.primary
                            )
                        )
                    )
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEachIndexed { index, icon ->
                    val isSelected = selectedTab == index

                    val backgroundModifier = if (!isSelected) {
                        Modifier.background(Color.Transparent)
                    } else {
                        Modifier
                            .background(
                                MaterialTheme.colorScheme.surfaceContainerHighest,
                                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                            )
                            .clip(MaterialTheme.shapes.medium)
                    }

                    Box(
                        modifier = backgroundModifier
                            .weight(1f)
                            .height(48.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) { selectedTab = index }
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (!isSelected) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp)
            ) {
                when (selectedTab) {
                    0 -> LazyColumn {
                        item {
                            PrimaryText("First name: ${data.firstName}")
                            Spacer(Modifier.height(8.dp))
                            PrimaryText("Last name: ${data.lastName}")
                            Spacer(Modifier.height(8.dp))
                            PrimaryText("Gender: ${data.gender}")
                            Spacer(Modifier.height(8.dp))
                            PrimaryText("Age: ${data.age}")
                            Spacer(Modifier.height(8.dp))
                            PrimaryText("Date of birth: ${data.date}")
                        }
                    }

                    1 -> Column {
                        PrimaryText("Phone number: ${data.phone}")
                    }

                    2 -> Column {
                        PrimaryText("Email: ${data.email}")
                    }

                    3 -> Column {
                        PrimaryText("Position: ${data.position}")
                    }
                }
            }
        }
    }
}