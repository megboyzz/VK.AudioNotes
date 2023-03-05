package ru.megboyzz.vk.audionotes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.megboyzz.vk.audionotes.entities.Recording
import ru.megboyzz.vk.audionotes.ui.composebase.blue700
import ru.megboyzz.vk.audionotes.ui.composebase.grey700

@Composable
fun RecordingCard(
    recording: Recording,
    onClickListener: () -> Unit
) {

    MainSurface(
        onClick = onClickListener,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .weight(8f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = recording.readableDayTime,
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = recording.readableDate,
                        style = MaterialTheme.typography.body1,
                        color = grey700
                    )
                }
                Text(
                    text = recording.duration,
                    style = MaterialTheme.typography.body1,
                    color = blue700
                )

            }
        }
    }
}