package com.example.lotto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.update


/*

Create an Android lotto app where the user can select numbers from LazyVerticalGrid
and when 7 numbers have selected enable a button to check how many of the selected
numbers are correct. User must also be able to deselect numbers.
Use View Model to manage state. Split your app UI into at least 3 composables.
See "ViewModel and State in Compose" part in "Architecture Components" for how work
with view model (step 7 for how to update the view model).

Attached is an example of an ugly UI for the app.

*/

data class LottoUiState(
    val lottoNumbers: List<Int> = (1..48).shuffled().take(7),
    val selected: Set<Int> = emptySet(),
    val correct: Int? = null
)

class LottoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LottoUiState())
    val uiState: StateFlow<LottoUiState> = _uiState.asStateFlow()

    fun toggle(number: Int) = _uiState.update { s ->
        val newSelected = when {
            number in s.selected -> s.selected - number
            s.selected.size < 7 -> s.selected + number
            else -> s.selected
        }
        s.copy(selected = newSelected, correct = null)
    }

    fun check() = _uiState.update { s ->
        s.copy(correct = s.selected.count { it in s.lottoNumbers })
    }
}
@Preview(showBackground = true)
@Composable
fun LottoScreen(vm: LottoViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()

    Column(Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
        NumberGrid(
            selected = state.selected,
            onToggle = vm::toggle,
            modifier = Modifier.weight(1f)
        )
        ResultSection(
            state = state,
            onCheck = vm::check
        )
    }
}

@Composable
fun ResultSection(state: LottoUiState, onCheck: () -> Unit) {
    Column {
        Button(onClick = onCheck, enabled = state.selected.size == 7) {
            Text("Check (${state.selected.size}/7)")
        }
        state.correct?.let {
            Text("Lotto numbers: ${state.lottoNumbers.sorted()}")
            Text("User guessed correct: $it")
        }
    }
}

@Composable
fun NumberGrid(selected: Set<Int>, onToggle: (Int) -> Unit, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        contentPadding = PaddingValues(2.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items((1..48).toList()) { number ->
            GridCardItem(number, number in selected) { onToggle(number) }
        }
    }
}
@Composable
fun GridCardItem(number: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Blue else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(number.toString())
        }
    }
}