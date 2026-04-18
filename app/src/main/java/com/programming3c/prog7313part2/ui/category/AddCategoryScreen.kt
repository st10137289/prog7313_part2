package com.programming3c.prog7313part2.ui.category

// Author: ST10089492 Ophec Funis
// Section: Expense Entry

// References:
// Android Developers. (n.d.). Build a UI with Jetpack Compose.Available at: https://developer.android.com/compose[Accessed: 16 April 2026.

// Nielsen, J. (1994). 10 usability heuristics for user interface design.Available at: https://www.nngroup.com/articles/ten-usability-heuristics/
// Available at: https://www.nngroup.com/articles/ten-usability-heuristics/[Accessed: 16 April 2026].

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.filter

@Composable
fun AddCategoryScreen(
    userId: Int,
    onBack: () -> Unit
) {
    val viewModel: AddCategoryViewModel = viewModel()

    // resetAll runs first will clear any state left from a previous visit
    LaunchedEffect(Unit) {
        viewModel.resetAll()
        snapshotFlow { viewModel.isSaved }
            .filter { it }
            .collect { onBack() }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // title and subtitle give context without over-explaining
            Text(
                text = "New Category",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Categories keep your spending organised",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = viewModel.name,
                onValueChange = {
                    viewModel.name = it
                    viewModel.clearError()
                },
                label = { Text("Category name") },
                isError = viewModel.errorMessage != null,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Renders when there is something to show
            if (viewModel.errorMessage != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = viewModel.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.save(userId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Save Category",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Back",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(1.2f))
        }
    }
}
