package com.example.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarSection(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    keyword: String,
    isSearchEnabled: Boolean,
    onChange: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    val iconColor = remember(isDarkTheme, isSearchEnabled) {
        val color = if (isDarkTheme) Color.White else Color.DarkGray
        if (isSearchEnabled) color else color.copy(alpha = 0.2f)
    }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(CircleShape),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = keyword,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
            onValueChange = { onChange(it) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable(enabled = isSearchEnabled) {
                        onSearchClick()
                        focusManager.clearFocus()
                    },
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = iconColor,
                )
            },
            placeholder = { Text(text = "keyword") },
            maxLines = 1,
        )
    }
}
