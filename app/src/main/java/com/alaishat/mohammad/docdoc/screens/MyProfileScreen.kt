package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.errohandling.ErrorHandler
import com.alaishat.mohammad.docdoc.resuables.DocDocTheme
import com.alaishat.mohammad.docdoc.resuables.DocDocTopAppBar
import com.alaishat.mohammad.docdoc.ui.theme.DocDocTheme
import com.alaishat.mohammad.docdoc.ui.theme.Gray
import com.alaishat.mohammad.docdoc.ui.theme.Seed
import com.alaishat.mohammad.docdoc.vm.UserProfileViewModel
import com.alaishat.mohammad.domain.model.show_profile.UserProfileSuccessResponse

/**
 * Created by Mohammad Al-Aishat on Aug/31/2024.
 * DocDoc Project.
 */

@Composable
fun MyProfileScreen(
    userProfileViewModel: UserProfileViewModel = hiltViewModel(),
    navController: NavHostController,
    onLogout: () -> Unit,
    onSnackBarHostEvent: (String) -> Unit
) {
    val dadResponse = userProfileViewModel.userProfileResponse.collectAsStateWithLifecycle().value
    val successData = (dadResponse as? UserProfileSuccessResponse)?.data
    val isLoading = userProfileViewModel.isLoading.collectAsStateWithLifecycle().value

    MyProfileComposable(
        name = successData?.first()?.name ?: "",
        email = successData?.first()?.email ?: "",
        phone = successData?.first()?.phone ?: "",
        onLogout = onLogout,
        onBackClicked = {
            navController.navigateUp()
        },
        isLoading = isLoading
    )

    LaunchedEffect(key1 = dadResponse) {
        ErrorHandler.handle(
            dadResponse,
            onResetState = { userProfileViewModel.resetProfileResponse() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }
}

@Composable
fun MyProfileComposable(
    name: String,
    email: String,
    phone: String,
    onLogout: () -> Unit,
    onBackClicked: () -> Unit,
    isLoading: Boolean = false,
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // The top blue color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-100).dp)
                .background(Seed)
        )

        // The bottom white color:
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.77f)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
            )
        }


        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            containerColor = Color.Transparent,
            topBar = {
                DocDocTopAppBar(navController = null, text = stringResource(R.string.profile), trailingIcon = {
                    IconButton(
                        onClick = { showDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onBackClicked() },
                            painter = painterResource(id = R.drawable.ic_back_button),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    if (isLoading)
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(color = Color.White) }
                    else
                        SelectionContainer {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(painter = painterResource(id = R.drawable.ic_profile), contentDescription = "")
                                Text(text = name, style = DocDocTheme.typography.titleMedium)
                                Text(text = email, style = MaterialTheme.typography.bodyLarge, color = Gray)
                                Text(text = phone, style = MaterialTheme.typography.bodyLarge, color = Gray)
                            }

                        }
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Box(modifier = Modifier.padding(4.dp)) {
                        FilledTonalButton(
                            onClick = {
                                onLogout()
                                showDialog = false
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        )
                        {
                            Text(text = stringResource(R.string.logout))
                        }
                    }
                },
                dismissButton = {
                    Box(modifier = Modifier.padding(4.dp)) {
                        FilledTonalButton(onClick = {
                            showDialog = false
                        }) {
                            Text(text = stringResource(R.string.no_stay_logged_in))
                        }
                    }
                },
                title = { Text(text = stringResource(R.string.confirmation)) },
                text = {
                    Text(text = stringResource(R.string.are_you_sure_you_want_to_log_out))
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewMyProfileComposable() {
    DocDocTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MyProfileComposable(
                name = "Mohammad",
                email = "Mohammad@Mohammad.com",
                phone = "0998877665",
                {},
                {}
            )
        }
    }
}
