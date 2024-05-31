@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.data.DataSource
import com.example.proyectofinal.model.Chevrolet
import com.example.proyectofinal.model.Ford
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme



class MainActivity : ComponentActivity() {

    private val favoriteChevrolets = mutableStateListOf<Chevrolet>()
    private val favoriteFords = mutableStateListOf<Ford>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen(
                        favoriteChevrolets = favoriteChevrolets,
                        favoriteFords = favoriteFords)
                }
            }
        }
    }
}

@Composable
fun MainScreen(favoriteChevrolets: MutableList<Chevrolet>,favoriteFords: MutableList<Ford>) {
    val navController = rememberNavController()
    val dataSource = DataSource()
    val chevroletList: List<Chevrolet> = dataSource.loadChevrolets()
    val fordList: List<Ford> = dataSource.loadFords()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("screen1") { ScreenChevrolet(navController,favoriteChevrolets) }
        composable("screen2") { ScreenFord(navController, favoriteFords) }
        composable("favoritos") {
            FavoriteScreen(
                favoriteChevrolets = favoriteChevrolets,
                favoriteFords = favoriteFords,
                navController = navController
            )
        }
        composable("buscar"){
            SearchScreen(navController, chevroletList, fordList)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("screen1") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "CHEVROLET", color = Color.Red)
        }
        Button(
            onClick = { navController.navigate("screen2") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "FORD", color = Color.Red)
        }
    }
}


@Composable
fun ScreenChevrolet(navController: NavHostController, favoriteChevrolets: MutableList<Chevrolet>) {
    val dataSource = DataSource()
    val chevroletList: List<Chevrolet> = dataSource.loadChevrolets()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Red,
                contentColor = Color.Black
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate("favoritos") }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate("buscar") }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ChevroletList(chevroletList = chevroletList, favoriteChevrolets = favoriteChevrolets)
        }
    }
}

@Composable
fun ScreenFord(navController: NavHostController, favoriteFords: MutableList<Ford>) {
    val dataSource = DataSource()
    val fordList: List<Ford> = dataSource.loadFords()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Red,
                contentColor = Color.Black
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate("favoritos") }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate("buscar") }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            FordList(fordList = fordList, favoriteFords = favoriteFords)
        }
    }
}

@Composable
fun FavoriteScreen(
    favoriteChevrolets: List<Chevrolet>,
    favoriteFords: List<Ford>,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Red,
                contentColor = Color.Black
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { /* Acción para Favoritos */ }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate("buscar")}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = "Favoritos Chevrolet",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(favoriteChevrolets) { chevrolet ->
                ChevroletCard(chevrolet = chevrolet, modifier = Modifier.padding(8.dp))
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Favoritos Ford",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(favoriteFords) { ford ->
                FordCard(ford = ford, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun SearchScreen(navController: NavHostController, chevroletList: List<Chevrolet>, fordList: List<Ford>) {
    var searchText by remember { mutableStateOf("") }

    // Filtra las listas en función del texto de búsqueda
    val filteredChevroletList = chevroletList.filter {chevrolet: Chevrolet -> stringResource(chevrolet.stringResourceId).contains(searchText, ignoreCase = true) }
    val filteredFordList = fordList.filter {ford: Ford -> stringResource(ford.stringResourceId).contains(searchText, ignoreCase = true) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Red,
                contentColor = Color.Black
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { navController.navigate("favoritos") }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { /* Acción para Buscar */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra los resultados de búsqueda solo si hay texto en el campo de búsqueda
            if (searchText.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Text(
                            text = "Resultados de Chevrolet",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(filteredChevroletList) { chevrolet ->
                        ChevroletCard(chevrolet = chevrolet, modifier = Modifier.padding(8.dp))
                    }

                    item {
                        Text(
                            text = "Resultados de Ford",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(filteredFordList) { ford ->
                        FordCard(ford = ford, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ChevroletList(
    chevroletList: List<Chevrolet>,
    favoriteChevrolets: MutableList<Chevrolet>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(chevroletList) { chevrolet ->
            ChevroletCard(
                chevrolet = chevrolet,
                modifier = Modifier.padding(8.dp),
                isFavorite = chevrolet in favoriteChevrolets,
                onFavoriteClicked = { // Manejar clic en el ícono de favorito
                    if (chevrolet in favoriteChevrolets) {
                        favoriteChevrolets.remove(chevrolet)
                    } else {
                        favoriteChevrolets.add(chevrolet)
                    }
                }
            )
        }
    }
}

@Composable
fun ChevroletCard(
    chevrolet: Chevrolet,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClicked: () -> Unit
) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(chevrolet.imageResourceId),
                contentDescription = stringResource(chevrolet.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(chevrolet.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(
                onClick = onFavoriteClicked,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                val icon = if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "Favorito",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun ChevroletCard(chevrolet: Chevrolet, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(chevrolet.imageResourceId),
                contentDescription = stringResource(chevrolet.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(chevrolet.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(
                onClick = { /* Acción de añadir a favoritos */ },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = Color.Red
                )
            }
        }
    }
}
@Composable
fun FordList(
    fordList: List<Ford>,
    favoriteFords: MutableList<Ford>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(fordList) { ford ->
            FordCard(
                ford = ford,
                modifier = Modifier.padding(8.dp),
                isFavorite = ford in favoriteFords,
                onFavoriteClicked = { // Manejar clic en el ícono de favorito
                    if (ford in favoriteFords) {
                        favoriteFords.remove(ford)
                    } else {
                        favoriteFords.add(ford)
                    }
                }
            )
        }
    }
}

@Composable
fun FordCard(
    ford: Ford,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClicked: () -> Unit
) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(ford.imageResourceId),
                contentDescription = stringResource(ford.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(ford.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(
                onClick = onFavoriteClicked,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                val icon = if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "Favorito",
                    tint = Color.Red
                )
            }
        }
    }
}
@Composable
fun FordList(fordList: List<Ford>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(fordList) { ford ->
            FordCard(ford = ford, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun FordCard(ford: Ford, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(ford.imageResourceId),
                contentDescription = stringResource(ford.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(ford.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(
                onClick = { /* Acción de añadir a favoritos */ },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = Color.Red
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val favoriteChevrolets = mutableListOf(
        Chevrolet(R.string.camaro2024, R.drawable.camaro2024),
        Chevrolet(R.string.camaro1969, R.drawable.camaro1969)
    )
    val favoriteFords = mutableListOf(
        Ford(R.string.mustang1969,R.drawable.mustang1969),


    )

    ProyectoFinalTheme {
        MainScreen(
            favoriteChevrolets = favoriteChevrolets,
            favoriteFords = favoriteFords
        )
    }
}
