package com.workmate.workmatetz.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun BackButton(paddingValues: PaddingValues, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "back"
            )
        }
    }
}

@Composable
internal fun PrimaryText(value: String) {
    Text(
        text = value,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleMedium,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
internal fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SnackBar(state: SnackbarHostState) {
    SnackbarHost(hostState = state) { data ->
        Snackbar(
            modifier = Modifier
                .wrapContentSize()
                .padding(48.dp)
                .clip(MaterialTheme.shapes.medium),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = data.visuals.message
            )
        }
    }
}