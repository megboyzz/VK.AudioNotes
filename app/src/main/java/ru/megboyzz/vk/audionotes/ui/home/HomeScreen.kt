package ru.megboyzz.vk.audionotes.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.megboyzz.vk.audionotes.entities.Recording
import ru.megboyzz.vk.audionotes.ui.components.RecordButton
import ru.megboyzz.vk.audionotes.ui.components.RecordingCard
import ru.megboyzz.vk.audionotes.ui.composebase.shapes
import ru.megboyzz.vk.audionotes.ui.composebase.surfaceWhite
import ru.megboyzz.vk.audionotes.ui.composebase.typography
import ru.megboyzz.vk.audionotes.ui.sortbybottomsheet.SortMethodListSheetFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.megboyzz.vk.audionotes.R


@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    activity: HomeActivity,
    recordingList: MutableList<Recording>,
    onClick: (item: Recording) -> Unit
) {

    val recordingState = homeViewModel.recordingState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if (recordingList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_no_results),
                    modifier = Modifier.padding(horizontal = 32.dp),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
                Text(
                    text = "Нет голосовых заметок",
                    style = typography.body1,
                    color = colors.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        } else {
            LazyColumn {
                itemsIndexed(recordingList) { index, recording ->
                    when (recording) {
                        recordingList.last() -> {
                            RecordingCard(recording, onClickListener = { onClick(recording) })
                            Box(Modifier.height(80.dp))
                        }
                        recordingList.first() -> {
                            RecordingCard(recording, onClickListener = { onClick(recording) })
                        }
                        else -> {
                            RecordingCard(recording, onClickListener = { onClick(recording) })
                        }
                    }
                }
            }
        }

        RecordButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            isRecording = recordingState.value,
            onClickListener = onRecordPressed(
                activity = activity,
                homeViewModel = homeViewModel
            )
        )
    }
}

@Composable
private fun SortButton(modifier: Modifier, onClick: () -> Unit) {
    Surface(
        elevation = 2.dp,
        color = if (colors.isLight) {
            surfaceWhite
        } else {
            colors.surface
        },
        modifier = modifier
            .clip(shapes.small)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
        ) {
            Text(
                text = "Sort",
                style = typography.button,
                color = colors.primary,
                modifier = Modifier.padding(end = 4.dp)
            )
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_sort),
                colorFilter = ColorFilter.tint(colors.primary),
                contentDescription = null
            )
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
private fun onRecordPressed(
    activity: HomeActivity,
    homeViewModel: HomeViewModel
): () -> Unit {
    return {
        val isRecording = homeViewModel.recordingState.value

        // haptic feedback

        if (isRecording) {
            homeViewModel.stopRecording()
        } else {
            activity.requestAudioRecording()
        }

    }
}