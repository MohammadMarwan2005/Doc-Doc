package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
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
import com.alaishat.mohammad.docdoc.navigaiton.BookAppointmentDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocButton
import com.alaishat.mohammad.docdoc.resuables.DocDocTopAppBar
import com.alaishat.mohammad.docdoc.resuables.DoctorCard
import com.alaishat.mohammad.docdoc.resuables.TitleWithInfo
import com.alaishat.mohammad.docdoc.ui.theme.Gray
import com.alaishat.mohammad.docdoc.vm.DoctorDetailsViewModel
import com.alaishat.mohammad.domain.model.core.Doctor
import com.alaishat.mohammad.domain.model.doctor_details.DoctorDetailsSuccessResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Aug/24/2024.
 * DocDoc Project.
 */

@Composable
fun DoctorDetailsScreen(
    doctorDetailsViewModel: DoctorDetailsViewModel = hiltViewModel(),
    doctorId: Int,
    navController: NavHostController,
    onSnackBarHostEvent: (String) -> Unit,
    ) {
    doctorDetailsViewModel.getDoctorDetails(doctorId)
    val doctorDadResponse = doctorDetailsViewModel.doctorDetailsResponse.collectAsStateWithLifecycle().value
    val doctor =
        doctorDadResponse as? DoctorDetailsSuccessResponse
    doctor?.let {
        DoctorDetailsScreen(it.data, navController)
    } ?: Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) { CircularProgressIndicator() }

    LaunchedEffect(key1 = doctorDadResponse) {
        ErrorHandler.handle(
            doctorDadResponse,
            onResetState = { doctorDetailsViewModel.resetDoctorDetailsState() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoctorDetailsScreen(doctor: Doctor, navController: NavHostController) {

    val pagerState = rememberPagerState { 3 }
    val scope = rememberCoroutineScope()



    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            DocDocTopAppBar(text = doctor.name, navController = navController)
        },
        bottomBar = {
            DocDocButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {
                    navController.navigate(BookAppointmentDestination.route + "/${doctor.id}" + "/${doctor.name}")
                }) {
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = stringResource(R.string.book_appointment)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
            ) {
                item {
                    DoctorCard(
                        name = doctor.name,
                        specialization = doctor.specialization.name,
                        city = doctor.city.name,
                        phone = doctor.phone,
                        degree = doctor.degree,
                        gender = doctor.gender,
                        showGenderAndHidPhoneAndCity = true,
                        model = doctor.photo,
                        clickable = false
                    )
                }
                item {
                    Column {
                        TabRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
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
                                    text = stringResource(R.string.about),
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
                                Text(text = stringResource(R.string.location), fontWeight = FontWeight.Bold)
                            }
                            Tab(
                                selected = pagerState.currentPage == 2,
                                unselectedContentColor = Gray,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(2)
                                    }
                                }) {
                                Text(text = stringResource(R.string.contacts), fontWeight = FontWeight.Bold)
                            }
                        }
                        HorizontalPager(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 32.dp),
                            verticalAlignment = Alignment.Top,
                            state = pagerState
                        ) { selectedIndex: Int ->
                            when (selectedIndex) {
                                0 -> {
                                    Column(
                                        modifier = Modifier.fillMaxHeight(),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        TitleWithInfo(
                                            title = stringResource(R.string.description),
                                            info = doctor.description
                                        )
                                        TitleWithInfo(
                                            title = stringResource(R.string.specialization),
                                            info = doctor.specialization.name
                                        )
                                        TitleWithInfo(title = stringResource(R.string.degree), info = doctor.degree)
                                        TitleWithInfo(
                                            title = stringResource(R.string.appointment_price),
                                            info = "${doctor.appoint_price} $"
                                        )
                                    }
                                }

                                1 -> {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        TitleWithInfo(
                                            title = stringResource(R.string.gov_and_city),
                                            info = "${doctor.city.governorate?.name ?: ""}, ${doctor.city.name}"
                                        )
                                        TitleWithInfo(title = stringResource(R.string.address), info = doctor.address)
                                        TitleWithInfo(
                                            title = stringResource(R.string.start_and_end_time),
                                            info = stringResource(
                                                R.string.monday_friday,
                                                doctor.start_time,
                                                doctor.end_time
                                            )
                                        )
                                    }
                                }

                                2 -> {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        TitleWithInfo(title = stringResource(R.string.phone), info = doctor.phone)
                                        TitleWithInfo(title = stringResource(id = R.string.email), info = doctor.email)
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
