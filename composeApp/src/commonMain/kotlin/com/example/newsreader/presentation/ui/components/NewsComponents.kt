package com.example.newsreader.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newsreader.data.model.Article
import com.example.newsreader.presentation.ui.theme.*

@Composable
fun CustomBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BgDark)
            .padding(vertical = 12.dp, horizontal = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onTabSelected(0) }) {
            Icon(Icons.Filled.Home, "Home", tint = if (selectedTab == 0) AccentBlue else TextGray)
        }
        IconButton(onClick = { onTabSelected(1) }) {
            Icon(Icons.Outlined.FavoriteBorder, "Saved", tint = if (selectedTab == 1) AccentBlue else TextGray)
        }
        IconButton(onClick = { onTabSelected(2) }) {
            Icon(Icons.Outlined.Person, "Profile", tint = if (selectedTab == 2) AccentBlue else TextGray)
        }
    }
}
@Composable
fun FeaturedNewsCard(article: Article, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(260.dp)
            .padding(bottom = 16.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(16.dp)).align(Alignment.TopCenter)
        )
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardDark),
            modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.BottomCenter).shadow(8.dp, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?u=${article.author}",
                            contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(24.dp).clip(CircleShape)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text("by ${article.author}", color = TextWhite, fontSize = 10.sp)
                            Text(article.publishedAt.take(10), color = TextGray, fontSize = 10.sp)
                        }
                    }
                    Icon(Icons.Filled.Favorite, "Like", tint = HeartPink, modifier = Modifier.size(16.dp))
                }
                Spacer(Modifier.height(8.dp))
                Text(article.title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun StandardNewsCard(article: Article, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardDark),
            modifier = Modifier.fillMaxWidth().padding(start = 24.dp).align(Alignment.CenterEnd).height(100.dp)
        ) {
            Column(modifier = Modifier.padding(start = 64.dp, top = 12.dp, end = 12.dp, bottom = 12.dp), verticalArrangement = Arrangement.Center) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?u=${article.author}",
                            contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(20.dp).clip(CircleShape)
                        )
                        Spacer(Modifier.width(6.dp))
                        Column {
                            Text("by ${article.author}", color = TextWhite, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(article.publishedAt.take(10), color = TextGray, fontSize = 8.sp)
                        }
                    }
                    Icon(Icons.Filled.Favorite, "Like", tint = HeartPink, modifier = Modifier.size(14.dp))
                }
                Spacer(Modifier.height(4.dp))
                Text(article.title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier.size(80.dp).align(Alignment.CenterStart).offset(x = (-8).dp).clip(RoundedCornerShape(12.dp)).shadow(6.dp, RoundedCornerShape(12.dp))
        )
    }
}