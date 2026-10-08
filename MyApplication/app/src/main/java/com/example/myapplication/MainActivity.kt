package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val vm: MyViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    PresidentScreen(
                        presidents = DataProvider.presidents,
                        selected = vm.selected,
                        hits = vm.hits,
                        onSelect = vm::select,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}


class President(var name: String, var startDuty: Int, var endDuty: Int, var description: String) :
    Comparable<President> {
    override fun compareTo(other: President): Int {
        return this.startDuty.compareTo(other.startDuty)
    }

    override fun toString(): String {
        return "$name $startDuty $endDuty"
    }
}


object DataProvider {
    val presidents: MutableList<President> = ArrayList<President>()

    init {
        Log.d("USR", "This ($this) is a singleton")
        presidents.add(President("Kaarlo Stahlberg", 1919, 1925, "Eka presidentti"))
        presidents.add(President("Lauri Relander", 1925, 1931, "Toka presidentti, Reissu-Lasse"))
        presidents.add(President("P. E. Svinhufvud", 1931, 1937, "Kolmas presidentti, Ukko-Pekka"))
        presidents.add(President("Kyösti Kallio", 1937, 1940, "Neljas presidentti"))
        presidents.add(President("Risto Ryti", 1940, 1944, "Viides presidentti"))
        presidents.add(President("Juho Kusti Paasikivi", 1946, 1956, "Äkäinen ukko"))
        presidents.add(President("Urho Kekkonen", 1956, 1982, "Pelimies"))
        presidents.add(President("Mauno Koivisto", 1982, 1994, "Manu"))
        presidents.add(President("Martti Ahtisaari", 1994, 2000, "Mahtisaari"))
        presidents.add(President("Tarja Halonen", 2000, 2012, "Eka naispresidentti"))
        presidents.add(President("Alexander Stubb", 2024, 2030, "Kolme pointtia"))
        presidents.sort()
    }
}


class MyViewModel : ViewModel() {
    private val repository: WikiRepository = WikiRepository()
    var hits by mutableIntStateOf(0)
        private set
    var selected by mutableStateOf("")
        private set

    fun select(name: String) {
        selected = name
        viewModelScope.launch { hits = repository.hitCountCheck(name) }
    }
}


@Composable
fun PresidentScreen(
    presidents: List<President>,
    selected: String,
    hits: Int,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = if (selected.isEmpty()) "" else "$selected: Hits $hits",
            fontSize = 36.sp
        )
        HorizontalDivider()
        LazyColumn {
            items(presidents) { p ->
                Text(
                    text = p.name,
                    fontSize = 24.sp,
                    color = if (p.name == selected) Color.Black else Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(p.name) }
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}
