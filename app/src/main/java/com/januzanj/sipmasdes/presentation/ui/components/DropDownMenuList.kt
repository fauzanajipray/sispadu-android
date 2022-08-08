package com.januzanj.sipmasdes.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.januzanj.sipmasdes.domain.model.Position
import com.januzanj.sipmasdes.presentation.theme.SispaduTheme

@Composable
fun DropDownMenuList(
    modifier: Modifier = Modifier,
    items: List<Position>,
    onItemSelected: (String, Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var selectedID by remember { mutableStateOf(-1) }
    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    var itemsDropDown by remember { mutableStateOf(items) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(){
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                onItemSelected(selectedText, selectedID)
                selectedText = it
            },
            readOnly = true,
            singleLine = true,
            modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .onGloballyPositioned { position ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = position.size.toSize()
                },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Dikirim ke",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            itemsDropDown.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label.name
                    selectedID = label.id.toInt()
                    expanded = false
                }) {
                    Text(label.name)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DropDownMenuListPreview(){
    SispaduTheme() {
        DropDownMenuList(
            items = listOf(Position(1, "Position 1"), Position(2, "Position 2")),
            onItemSelected = { text, id ->
                println(text)
            }
        )
    }
}