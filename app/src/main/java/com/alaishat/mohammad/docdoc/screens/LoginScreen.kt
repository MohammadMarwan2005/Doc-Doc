package com.alaishat.mohammad.docdoc.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.navigaiton.HomeScreenDestination
import com.alaishat.mohammad.docdoc.navigaiton.RegisterScreenDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocButton
import com.alaishat.mohammad.docdoc.resuables.DocDocTextField
import com.alaishat.mohammad.docdoc.resuables.WelcomeText
import com.alaishat.mohammad.docdoc.ui.theme.Gray
import com.alaishat.mohammad.docdoc.ui.theme.OnSuccessContainerColor
import com.alaishat.mohammad.docdoc.ui.theme.Seed
import com.alaishat.mohammad.docdoc.ui.theme.SuccessContainerColor
import com.alaishat.mohammad.docdoc.vm.LoginViewModel
import com.alaishat.mohammad.domain.model.login.UserLoginFailedResponse
import com.alaishat.mohammad.domain.model.login.UserLoginRequest
import com.alaishat.mohammad.domain.model.login.UserLoginSuccessResponse
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }

    val rememberMe = true

    val isLoading = loginViewModel.isLoading.collectAsStateWithLifecycle().value

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    var snackBarError by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { snackbarData: SnackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = if (snackBarError) MaterialTheme.colorScheme.errorContainer else SuccessContainerColor,
                    contentColor = if (snackBarError) MaterialTheme.colorScheme.onErrorContainer else OnSuccessContainerColor
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(36.dp),
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                WelcomeText(
                    title = stringResource(R.string.welcome_back),
                    body = stringResource(R.string.we_re_excited_to_have_you_back_can_t_wait_to_see_what_you_ve_been_up_to_since_you_last_logged_in)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    DocDocTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = stringResource(R.string.email)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

                        )

                    DocDocTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password, onValueChange = { password = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                        label = { Text(text = stringResource(R.string.password)) },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_password_eye),
                                    contentDescription = ""
                                )
                            }
                        }
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.clickable {  },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = {  },
                                colors = CheckboxDefaults.colors(checkedColor = Seed, uncheckedColor = Gray)
                            )
                            Text(
                                text = stringResource(R.string.remember_me), style = MaterialTheme.typography.bodySmall
                            )
                        }
                        val context = LocalContext.current
                        TextButton(onClick = {
                            Toast.makeText(context, context.getString(R.string.don_t_forget_it_again), Toast.LENGTH_LONG).show()
                        }) {
                            Text(
                                text = stringResource(R.string.forget_password),
                                style = MaterialTheme.typography.bodySmall,
                                color = Seed
                            )
                        }
                    }


                    DocDocButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            loginViewModel.login(UserLoginRequest(email, password))
                        },
                    ) {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                modifier = Modifier.padding(14.dp),
                                text = stringResource(R.string.login)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                if (isLoading)
                                    CircularProgressIndicator(
                                        strokeWidth = 3.dp,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                            }
                        }
                    }
                }

                val resultState = loginViewModel.loginResult.collectAsStateWithLifecycle().value

                LaunchedEffect(key1 = resultState) {
                    resultState.let {
                        when (resultState) {
                            is UserLoginSuccessResponse -> {
                                loginViewModel.saveUserTokenAndName()
                                coroutineScope.launch {
                                    snackBarError = false
                                    snackbarHostState.showSnackbar(resultState.message)
                                    loginViewModel.resetLoginResult()
                                }
                                navController.navigate(HomeScreenDestination.route) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }

                            }

                            is UserLoginFailedResponse -> {
                                coroutineScope.launch {
                                    snackBarError = true
                                    snackbarHostState.showSnackbar(resultState.message)
                                    loginViewModel.resetLoginResult()
                                }
                            }
                        }
                    }

                }


            }


            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.don_t_have_an_account_yet),
                    style = MaterialTheme.typography.bodySmall,
                )

                TextButton(onClick = {
                    navController.navigate(RegisterScreenDestination.route)
                }) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.bodySmall,
                        color = Seed
                    )
                }
            }
        }
    }
}

