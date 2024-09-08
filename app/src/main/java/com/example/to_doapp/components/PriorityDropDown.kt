package com.example.to_doapp.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.to_doapp.R
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.ui.theme.MEDIUM_PADDING
import com.example.to_doapp.ui.theme.PRIORITY_DROPDOWN_HEIGHT
import com.example.to_doapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_doapp.ui.theme.Shapes
import com.example.to_doapp.ui.theme.ToDoAppTheme

@Composable
fun PriorityDropDown(
    modifier: Modifier = Modifier,
    priority: Priority,
    onPrioritySelected: (priority: Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val angle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = stringResource(id = R.string.dropdown_angle)
    )
    var parentWidth by remember {
        mutableIntStateOf(IntSize.Zero.width)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentWidth = it.size.width
            }
            .height(PRIORITY_DROPDOWN_HEIGHT)
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                expanded = true
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = Shapes.medium
            )
            .padding(horizontal = MEDIUM_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1.5f)
        ) {
            drawCircle(
                color = priority.color
            )
        }
        Text(
            modifier = Modifier
                .weight(7f),
            text = priority.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        IconButton(
            modifier = Modifier
                .weight(1.5f)
                .rotate(angle),
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = R.string.toggle_dropdown)
            )
        }
        DropdownMenu(
            modifier = Modifier
                .width(with(LocalDensity.current){ parentWidth.toDp() })
                .background(MaterialTheme.colorScheme.surfaceContainer),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            Priority.entries.slice(1..3).forEach {  priority ->
                DropdownMenuItem(
                    text = {
                        PriorityItem(priority = priority)
                    },
                    onClick = {
                        expanded = false
                        onPrioritySelected(priority)
                    }
                )
            }
        }
    }

}

@Preview
@Composable
private fun PriorityDropDownPrev() {
    ToDoAppTheme {
        PriorityDropDown(
            priority = Priority.LOW,
            onPrioritySelected = {}
        )
    }
}