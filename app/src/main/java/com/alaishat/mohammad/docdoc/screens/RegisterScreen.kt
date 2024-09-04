package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.navigaiton.HomeScreenDestination
import com.alaishat.mohammad.docdoc.navigaiton.LoginScreenDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocButton
import com.alaishat.mohammad.docdoc.resuables.DocDocTextField
import com.alaishat.mohammad.docdoc.resuables.WelcomeText
import com.alaishat.mohammad.docdoc.ui.theme.OnSuccessContainerColor
import com.alaishat.mohammad.docdoc.ui.theme.Seed
import com.alaishat.mohammad.docdoc.ui.theme.SuccessContainerColor
import com.alaishat.mohammad.docdoc.vm.RegisterViewModel
import com.alaishat.mohammad.domain.model.register.RegisterResponse
import com.alaishat.mohammad.domain.model.register.UserRegisterRequest
import com.alaishat.mohammad.domain.model.register.failureresponse.RegisterFailureResponse
import com.alaishat.mohammad.domain.model.register.failureresponse.bothtaken.EmailAndPhoneTakenRegisterResponse
import com.alaishat.mohammad.domain.model.register.failureresponse.emailtaken.EmailTakenRegisterResponse
import com.alaishat.mohammad.domain.model.register.failureresponse.local.LocalRegisterFailureResponse
import com.alaishat.mohammad.domain.model.register.failureresponse.phonetaken.PhoneTakenRegisterResponse
import com.alaishat.mohammad.domain.model.register.success.RegisterSuccessResponse
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConfirmation by rememberSaveable { mutableStateOf("") }

    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    var showPasswordConfirm by rememberSaveable {
        mutableStateOf(false)
    }
    val isLoading = registerViewModel.isLoading.collectAsStateWithLifecycle().value

    val resultState: RegisterResponse? = registerViewModel.registerResult.collectAsStateWithLifecycle().value

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(32.dp))
                    WelcomeText(
                        title = stringResource(R.string.create_account),
                        body = stringResource(R.string.sign_up_now_and_start_exploring_all_that_our_app_has_to_offer_we_re_excited_to_welcome_you_to_our_community)
                    )
                    DocDocTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name, onValueChange = { name = it },

                        label = { Text(text = stringResource(R.string.name)) },
                    )
                    DocDocTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email, onValueChange = { email = it },
                        label = { Text(text = stringResource(R.string.email)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )
                    DocDocTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = phone, onValueChange = { phone = it },

                        label = { Text(text = stringResource(R.string.phone_number)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    )
                    DocDocTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password, onValueChange = { password = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                        label = { Text(text = stringResource(R.string.password)) },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(painter = painterResource(id = R.drawable.ic_password_eye), contentDescription = "")
                            }
                        })
                    DocDocTextField(

                        modifier = Modifier.fillMaxWidth(),
                        value = passwordConfirmation, onValueChange = { passwordConfirmation = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (showPasswordConfirm) PasswordVisualTransformation() else VisualTransformation.None,
                        label = { Text(text = stringResource(R.string.confirm_password)) },
                        trailingIcon = {
                            IconButton(onClick = { showPasswordConfirm = !showPasswordConfirm }) {
                                Icon(painter = painterResource(id = R.drawable.ic_password_eye), contentDescription = "")
                            }
                        })

                    Spacer(modifier = Modifier.height(32.dp))

                    DocDocButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            registerViewModel.register(
                                UserRegisterRequest(
                                    name,
                                    email,
                                    phone,
                                    password,
                                    "0",
                                    passwordConfirmation
                                )
                            )
                        },
                    ) {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                modifier = Modifier.padding(14.dp),
                                text = stringResource(R.string.create_account)
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



                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.already_have_an_account),
                        style = MaterialTheme.typography.bodySmall,
                    )

                    TextButton(onClick = {
                        navController.navigate(LoginScreenDestination.route)
                    }) {
                        Text(
                            text = stringResource(R.string.log_in),
                            style = MaterialTheme.typography.bodySmall,
                            color = Seed
                        )
                    }
                }



                LaunchedEffect(key1 = resultState) {
                    resultState.let {
                        when (resultState) {
                            is RegisterSuccessResponse -> {
                                registerViewModel.saveUserTokenAndName()
                                coroutineScope.launch {
                                    snackBarError = false
                                    snackbarHostState.showSnackbar(resultState.message)
                                    registerViewModel.resetRegisterResult()
                                }
                                navController.navigate(HomeScreenDestination.route) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }

                            is RegisterFailureResponse -> {
                                when (resultState) {
                                    is EmailTakenRegisterResponse -> {
                                        coroutineScope.launch {
                                            snackBarError = true
                                            snackbarHostState.showSnackbar(resultState.message + "\n" + resultState.data.email[0])
                                            registerViewModel.resetRegisterResult()
                                        }
                                    }

                                    is PhoneTakenRegisterResponse -> {
                                        coroutineScope.launch {
                                            snackBarError = true
                                            snackbarHostState.showSnackbar(resultState.message + "\n" + resultState.data.phone[0])
                                            registerViewModel.resetRegisterResult()
                                        }
                                    }

                                    is EmailAndPhoneTakenRegisterResponse -> {
                                        coroutineScope.launch {
                                            snackBarError = true
                                            snackbarHostState.showSnackbar(resultState.message + "\n" + resultState.data.email[0] + "\n" + resultState.data.phone[0])
                                            registerViewModel.resetRegisterResult()
                                        }
                                    }

                                    is LocalRegisterFailureResponse -> {
                                        coroutineScope.launch {
                                            snackBarError = true
                                            snackbarHostState.showSnackbar(resultState.message)
                                            registerViewModel.resetRegisterResult()
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }

        }

    }

}