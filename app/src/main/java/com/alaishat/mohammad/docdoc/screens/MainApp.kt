package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.navigaiton.AllAppointmentsDestination
import com.alaishat.mohammad.docdoc.navigaiton.AllSpecializationsDestination
import com.alaishat.mohammad.docdoc.navigaiton.BookAppointmentDestination
import com.alaishat.mohammad.docdoc.navigaiton.BookedSuccessfullyDestination
import com.alaishat.mohammad.docdoc.navigaiton.DocDocBottomNavBar
import com.alaishat.mohammad.docdoc.navigaiton.DoctorDetailsDestination
import com.alaishat.mohammad.docdoc.navigaiton.HomeScreenDestination
import com.alaishat.mohammad.docdoc.navigaiton.LoginScreenDestination
import com.alaishat.mohammad.docdoc.navigaiton.MyProfileDestination
import com.alaishat.mohammad.docdoc.navigaiton.OnBoardingScreenDestination
import com.alaishat.mohammad.docdoc.navigaiton.RegisterScreenDestination
import com.alaishat.mohammad.docdoc.navigaiton.SearchDestination
import com.alaishat.mohammad.docdoc.ui.theme.Seed
import com.alaishat.mohammad.docdoc.vm.MainAppViewModel
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */

val routes = listOf(
    HomeScreenDestination.route,
    AllSpecializationsDestination.route,
    AllAppointmentsDestination.route,
    MyProfileDestination.route,
    SearchDestination.route,
)

@Composable
fun MainApp(
    mainAppViewModel: MainAppViewModel = hiltViewModel(),
) {
    val isFirstVisit = mainAppViewModel.isFirstVisit.collectAsStateWithLifecycle().value
    val containsUserToken = mainAppViewModel.containsUserToken.collectAsStateWithLifecycle().value
    var showBottomNavBar = mainAppViewModel.showBottomNavBar.collectAsStateWithLifecycle().value

    val startWith = if (isFirstVisit == true) {
        OnBoardingScreenDestination.route
    } else {
        if (containsUserToken == true) {
            HomeScreenDestination.route
        } else {
            LoginScreenDestination.route
        }
    }

    val errorSnackbarHostState = remember { SnackbarHostState() }

    val navController = rememberNavController()

    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val selectedItemIndex =
        routes.indexOf(navBackStackEntry.value?.destination?.route ?: DoctorDetailsDestination.route)
    val navItems = mainAppViewModel.navItems.collectAsStateWithLifecycle().value

    val coroutineScope = rememberCoroutineScope()

    fun showError(message: String) = coroutineScope.launch {
        errorSnackbarHostState.currentSnackbarData?.dismiss()
        errorSnackbarHostState.showSnackbar(message = message)
    }

    Scaffold(
        floatingActionButton = {
            if (showBottomNavBar)
                FloatingActionButton(
                    modifier = Modifier
                        .offset(y = 84.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(28.dp),
                    contentColor = Color.White,
                    containerColor = Seed,
                    onClick = {
                        navController.navigate(SearchDestination.route) {
                            launchSingleTop = true
                        }
                    }) {
                    Icon(
                        modifier = Modifier.padding(28.dp),
                        painter = painterResource(id = R.drawable.ic_search), contentDescription = ""
                    )
                }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            if (showBottomNavBar)
                DocDocBottomNavBar(
                    selectedItemIndex = selectedItemIndex,
                    navItems = navItems
                ) {
                    navController.navigate(routes[it]) {
                        launchSingleTop = true
                    }
                }
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.offset(y = 100.dp),
                hostState = errorSnackbarHostState
            ) { snackbarData ->
                Snackbar(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { change, dragAmount ->
                                if (dragAmount > 15f)
                                    coroutineScope.launch { errorSnackbarHostState.currentSnackbarData?.dismiss() }
                            }
                    },
                    snackbarData = snackbarData,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                )
            }
        },
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController, startDestination = startWith
        ) {
            composable(OnBoardingScreenDestination.route) {
                showBottomNavBar = false
                OnBoardingScreen(navController)
            }

            composable(HomeScreenDestination.route) {
                showBottomNavBar = true
                HomeScreen(navController = navController, onSnackBarHostEvent = { showError(message = it) })
            }
            composable(RegisterScreenDestination.route) {
                showBottomNavBar = false
                RegisterScreen(navController = navController)
            }
            composable(LoginScreenDestination.route) {
                showBottomNavBar = false
                LoginScreen(navController)
            }
            composable(
                route = DoctorDetailsDestination.route + "/{${DoctorDetailsDestination.DOCTOR_ID_KEY}}",
                arguments = listOf(
                    navArgument(name = DoctorDetailsDestination.DOCTOR_ID_KEY) {
                        nullable = false
                        defaultValue = "-1"
                    }
                )
            ) {
                showBottomNavBar = true
                val doctorId = it.arguments?.getString(DoctorDetailsDestination.DOCTOR_ID_KEY)?.toInt() ?: -1
                DoctorDetailsScreen(
                    doctorId = doctorId,
                    navController = navController,
                    onSnackBarHostEvent = { showError(it) })
            }
            composable(
                route = BookAppointmentDestination.route + "/{${BookAppointmentDestination.DOCTOR_ID_KEY}}/{${BookAppointmentDestination.DOCTOR_NAME_KEY}}",
                arguments = listOf(
                    navArgument(name = BookAppointmentDestination.DOCTOR_ID_KEY) {
                        nullable = false
                        defaultValue = "-1"
                    },
                    navArgument(name = BookAppointmentDestination.DOCTOR_NAME_KEY) {
                        nullable = false
                        defaultValue = ""
                    }
                )
            ) {
                showBottomNavBar = true
                val doctorId = it.arguments?.getString(BookAppointmentDestination.DOCTOR_ID_KEY)?.toInt() ?: -1
                val doctorName = it.arguments?.getString(BookAppointmentDestination.DOCTOR_NAME_KEY) ?: ""
                BookAppointmentScreen(
                    doctorId = doctorId,
                    navController = navController,
                    doctorName = doctorName,
                    onNewBookAppointments = { mainAppViewModel.increaseAppointmentsBadgesByOne() }
                ) { showError(it) }
            }


            composable(
                route = BookedSuccessfullyDestination.route + "/{${BookedSuccessfullyDestination.DOCTOR_ID_KEY}}/{${BookedSuccessfullyDestination.FORMATTED_TIME_KEY}}/{${BookedSuccessfullyDestination.NOTES_KEY}}/{${BookedSuccessfullyDestination.IS_COMING_FROM_BOOKING_SCREEN_KEY}}",
                arguments = listOf(
                    navArgument(name = BookedSuccessfullyDestination.DOCTOR_ID_KEY) {
                        nullable = false
                        defaultValue = "-1"
                    },
                    navArgument(name = BookedSuccessfullyDestination.FORMATTED_TIME_KEY) {
                        nullable = false
                        defaultValue = ""
                    },
                    navArgument(name = BookedSuccessfullyDestination.NOTES_KEY) {
                        nullable = false
                        defaultValue = ""
                    },
                    navArgument(name = BookedSuccessfullyDestination.IS_COMING_FROM_BOOKING_SCREEN_KEY) {
                        type = NavType.BoolType
                        nullable = false
                        defaultValue = false
                    },
                )
            ) {

                showBottomNavBar = true
                val doctorId = it.arguments?.getString(BookedSuccessfullyDestination.DOCTOR_ID_KEY)?.toInt() ?: -1
                val formattedTime = it.arguments?.getString(BookedSuccessfullyDestination.FORMATTED_TIME_KEY) ?: ""
                val notes = it.arguments?.getString(BookedSuccessfullyDestination.NOTES_KEY) ?: ""
                val isComingFromBookingScreen =
                    it.arguments?.getBoolean(BookedSuccessfullyDestination.IS_COMING_FROM_BOOKING_SCREEN_KEY) ?: true
                BookedSuccessfullyScreen(
                    doctorId = doctorId,
                    formattedTime = formattedTime,
                    navController = navController,
                    notes = notes,
                    isDoctorClickable = false,
                    showConfirmationIcon = isComingFromBookingScreen,
                    onSnackBarHostEvent = { showError(it) })
            }
            composable(route = AllAppointmentsDestination.route) {
                showBottomNavBar = true
                mainAppViewModel.setNewAppointmentsBadges(0)
                AllAppointmentsScreen(navController = navController,
                    onSnackBarHostEvent = { showError(it) })
            }
            composable(AllSpecializationsDestination.route) {
                showBottomNavBar = true
                AllSpecializationsScreen(
                    navController = navController,
                    onSnackBarHostEvent = { showError(message = it) })
            }
            composable(SearchDestination.route) {
                showBottomNavBar = true
                SearchScreen(navController = navController,
                    onSnackBarHostEvent = { showError(it) })
            }
            composable(MyProfileDestination.route) {
                showBottomNavBar = true
                MyProfileScreen(navController = navController,
                    onLogout = { mainAppViewModel.logout(navController) },
                    onSnackBarHostEvent = { showError(it) })

            }
        }
    }
}

