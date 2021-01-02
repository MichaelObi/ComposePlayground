package dev.michaelobi.clubhouse.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.michaelobi.clubhouse.R
import dev.michaelobi.clubhouse.ui.theme.jungleGreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Home() {
    val viewModel: HomeViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HomeAppBar(
                avatarUrl = viewState.avatarUrl,
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            )
            ScrollableColumn() {
                RoomList()
            }
        }
    }
}

@Composable
fun HomeAppBar(avatarUrl: String?, modifier: Modifier) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = modifier.wrapContentHeight()
    ) {
        IconButton(
            onClick = { Log.i("CH", "Search") },
            modifier = Modifier
        ) {
            Icon(
                imageVector = vectorResource(id = R.drawable.ic_round_search_24),
                tint = MaterialTheme.colors.onBackground,
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { Log.i("CH", "Events") },
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Icon(
                    imageVector = vectorResource(id = R.drawable.ic_round_calendar_today_24),
                    tint = MaterialTheme.colors.onBackground,
                )
            }
            IconButton(
                onClick = { Log.i("CH", "Notifications") },
            ) {
                Icon(
                    imageVector = vectorResource(id = R.drawable.ic_round_notifications_none_24),
                    tint = MaterialTheme.colors.onBackground,
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Avatar(
                    imageUrl = avatarUrl,
                    Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun RoomList() {
    for (n in 1..15) {
        RoomCard("Vibes and Insha'Allah ($n)")
    }
}

@Composable
fun RoomCard(roomName: String?) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 1.dp,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {

        val constraintSet = ConstraintSet {
            val clubName = createRefFor("clubName")
            val clubNameIcon = createRefFor("clubNameIcon")

            constrain(clubName) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
            constrain(clubNameIcon) {
                start.linkTo(clubName.end)
                top.linkTo(clubName.top)
                bottom.linkTo(clubName.bottom)
                height = Dimension.fillToConstraints
            }
        }
        ConstraintLayout(
            constraintSet = constraintSet,
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
        ) {
            if (!roomName.isNullOrEmpty()) {
                Text(
                    text = roomName.toUpperCase(Locale.getDefault()),
                    style = MaterialTheme.typography.caption.copy(letterSpacing = 1.0.sp),
                    modifier = Modifier.layoutId("clubName")
                )
                Image(
                    imageVector = vectorResource(id = R.drawable.ic_round_home_24),
                    colorFilter = ColorFilter.tint(color = jungleGreen),
                    modifier = Modifier.layoutId("clubNameIcon")
                        .padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun Avatar(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        CoilImage(
            data = imageUrl ?: R.drawable.placeholder_avatar,
            contentScale = ContentScale.Fit,
            fadeIn = true,
            modifier = Modifier.fillMaxSize()
        )
    }
}