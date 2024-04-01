package com.example.common

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.model.ApiError
import java.net.UnknownHostException

@Composable
fun ErrorDialog(
    title: String?,
    message: String,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        title = title?.let { { Text(text = it) } },
        text = { Text(text = message) },
        onDismissRequest = onConfirm,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.dialog_button_ok))
            }
        },
    )
}

@Preview
@Composable
private fun PreviewApplicationError() {
    val context = LocalContext.current
    val unknownHostErrorMessage = ErrorMessage.from(UnknownHostException())
    ErrorDialog(
        title = unknownHostErrorMessage.getTitle(),
        message = unknownHostErrorMessage.getMessage(context),
        onConfirm = {},
    )
}

@Preview
@Composable
private fun PreviewApiError() {
    val context = LocalContext.current
    val error = ErrorMessage.from(ApiError(500, "Server error"))
    ErrorDialog(
        title = error.getTitle(),
        message = error.getMessage(context),
        onConfirm = {},
    )
}
