package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.errohandling.ErrorHandler
import com.alaishat.mohammad.docdoc.navigaiton.BookedSuccessfullyDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocTopAppBar
import com.alaishat.mohammad.docdoc.resuables.DoctorCard
import com.alaishat.mohammad.docdoc.ui.theme.Gray
import com.alaishat.mohammad.docdoc.vm.AllAppointmentsViewModel
import com.alaishat.mohammad.domain.model.appointments.allappointments.AllAppointmentsResponse
import com.alaishat.mohammad.domain.model.appointments.allappointments.AllAppointmentsSuccessResponse
import com.alaishat.mohammad.domain.model.appointments.core.AppointmentData
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by Mohammad Al-Aishat on Aug/26/2024.
 * DocDoc Project.
 */


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllAppointmentsScreen(
    allAppointmentViewModel: AllAppointmentsViewModel = hiltViewModel(),
    navController: NavHostController,
    onSnackBarHostEvent: (String) -> Unit
) {
    val dadResponse: AllAppointmentsResponse? =
        allAppointmentViewModel.allAppointmentsResponse.collectAsStateWithLifecycle().value

    val successResponse = dadResponse as? AllAppointmentsSuccessResponse
    val appointments: List<AppointmentData> = successResponse?.`data` ?: emptyList()
    val isLoadingAppointments = allAppointmentViewModel.isLoading.collectAsStateWithLifecycle().value

    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()

    val pendingAppointments = appointments.filter { it.status == "pending" }.reversed()
    val completedAppointments = emptyList<AppointmentData>()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            DocDocTopAppBar(text = stringResource(R.string.my_appointments), navController = navController)
        },
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    unselectedContentColor = Gray,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.pending),
                        fontWeight = FontWeight.Bold
                    )
                }
                Tab(
                    selected = pagerState.currentPage == 1,
                    unselectedContentColor = Gray,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }) {
                    Text(text = stringResource(R.string.completed), fontWeight = FontWeight.Bold)
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.Top,
                state = pagerState
            ) { selectedIndex: Int ->
                when (selectedIndex) {
                    0 -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            if (isLoadingAppointments) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                if (pendingAppointments.isEmpty())
                                    Text(text = stringResource(R.string.you_have_no_pending_appointments_yet))
                                else
                                    LazyColumn {
                                        items(pendingAppointments, key = { it.id }) {
                                            val doctor = it.doctor
                                            val formattedTime = getFormattedDate(
                                                startTime = it.appointment_time,
                                                endTime = it.appointment_end_time
                                            )
                                            DoctorCardWithDivider(
                                                onClick = {
                                                    val notes = it.notes.ifBlank { "-" }
                                                    navController.navigate(BookedSuccessfullyDestination.route + "/${doctor.id}/$formattedTime/$notes/${false}")
                                                },
                                                name = it.doctor.name,
                                                specialization = it.doctor.name,
                                                model = it.doctor.photo,
                                                time = formattedTime
                                            )
                                        }
                                    }

                            }
                        }
                    }

                    1 -> {
                        Column(
                            Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            if (isLoadingAppointments)
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            else {
                                if (completedAppointments.isEmpty())
                                    Text(text = stringResource(R.string.you_have_no_completed_appointments_yet))
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = dadResponse) {
        ErrorHandler.handle(
            dadResponse,
            onResetState = { allAppointmentViewModel.resetAllAppointmentsState() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }
}

@Composable
fun DoctorCardWithDivider(
    name: String = "Dr. Jack Sulivan",
    specialization: String = "General",
    onClick: () -> Unit = {},
    model: String = "",
    time: String = "Wed, 17 May | 08:30 AM - 09:00 AM",
) {
    Column(Modifier.padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DoctorCard(
            name = name,
            specialization = specialization,
            onClick = onClick,
            model = model,
            gender = time,
            showGenderAndHidPhoneAndCity = true,
            clickable = true,
        )
    }
    Divider()
}

fun getFormattedDate(startTime: String, endTime: String): String {
    val inputFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a", Locale.ENGLISH)
    val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.ENGLISH)
    val timeFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)

    return try {
        val startDate = inputFormat.parse(startTime)
        val endDate = inputFormat.parse(endTime)

        val formattedDate = dateFormat.format(startDate!!)
        val formattedStartTime = timeFormat.format(startDate)
        val formattedEndTime = timeFormat.format(endDate!!)

        "$formattedDate | $formattedStartTime - $formattedEndTime"
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
