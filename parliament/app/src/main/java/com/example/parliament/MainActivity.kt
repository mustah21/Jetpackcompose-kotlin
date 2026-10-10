package com.example.parliament

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parliament.ui.theme.ParliamentTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val MAJORITY = 101

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParliamentTheme {
                Scaffold(Modifier.fillMaxSize()) { padding ->
                    PartyScreen(Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
private fun rememberMemberViewModel(): MemberViewModel {
    val context = LocalContext.current.applicationContext
    return viewModel(
        factory = viewModelFactory {
            initializer {
                MemberViewModel(RetrofitRepo(MemberDatabase.getInstance(context).membersDao))
            }
        }
    )
}

@Composable
fun PartyScreen(modifier: Modifier = Modifier) {

    val vm = rememberMemberViewModel()
    val selected by vm.selectedParty.collectAsStateWithLifecycle()
    val members by vm.members.collectAsStateWithLifecycle()
    val parties by vm.parties.collectAsStateWithLifecycle()
    val favorites by vm.favorites.collectAsStateWithLifecycle()
    val count by vm.favoriteCount.collectAsStateWithLifecycle()

    BackHandler(enabled = selected != null) { vm.selectParty(null) }

    when (val party = selected) {
        null -> PartyList(parties, favorites, count, vm::toggleFavorite, vm::selectParty, modifier)
        else -> MemberList(party, members, modifier)
    }
}

@Composable
private fun PartyList(
    parties: List<String>,
    favorites: Set<String>,
    count: Int,
    onToggle: (String) -> Unit,
    onOpen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Text("Members in favorite parties: $count", style = MaterialTheme.typography.titleMedium)
        if (count >= MAJORITY) {
            Text("Majority government can be formed", color = MaterialTheme.colorScheme.primary)
        }
        LazyColumn {
            items(parties) { party ->
                PartyRow(party, party in favorites, { onToggle(party) }, { onOpen(party) })
            }
        }
    }
}

@Composable
private fun PartyRow(
    party: String,
    isFavorite: Boolean,
    onToggle: () -> Unit,
    onOpen: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onOpen),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = isFavorite, onCheckedChange = { onToggle() })
        Text(party.uppercase())
    }
}

@Composable
private fun MemberList(party: String, members: List<Member>, modifier: Modifier = Modifier) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Text(party.uppercase(), style = MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(members, key = { it.hetekaId }) { member ->
                Text("${member.firstname} ${member.lastname}", Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

class MemberViewModel(private val repo: RetrofitRepo) : ViewModel() {
    private val _selectedParty = MutableStateFlow<String?>(null)
    val selectedParty: StateFlow<String?> = _selectedParty.asStateFlow()

    val parties = repo.getAllParties().asState(emptyList())
    val favorites = repo.getFavoriteParties().map { it.toSet() }.asState(emptySet())
    val favoriteCount = repo.getFavoriteMemberCount().asState(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val members = _selectedParty
        .flatMapLatest { it?.let(repo::getMembersByParty) ?: flowOf(emptyList()) }
        .asState(emptyList())

    init {
        viewModelScope.launch { repo.inserting() }
    }

    fun selectParty(party: String?) {
        _selectedParty.value = party
    }

    fun toggleFavorite(party: String) {
        viewModelScope.launch { repo.setPartyFavorite(party, party !in favorites.value) }
    }

    private fun <T> Flow<T>.asState(initial: T): StateFlow<T> =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initial)

}