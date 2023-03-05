package ru.megboyzz.vk.audionotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.megboyzz.vk.audionotes.ui.composebase.shapes
import ru.megboyzz.vk.audionotes.ui.composebase.surfaceWhite

@Composable
fun MainSurface(
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val color = if (colors.isLight) {
        surfaceWhite
    } else {
        colors.surface
    }

    Surface(
        elevation = 2.dp,
        color = color,
        modifier = modifier.clip(shapes.medium)
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
    ) {
        content()
    }
}