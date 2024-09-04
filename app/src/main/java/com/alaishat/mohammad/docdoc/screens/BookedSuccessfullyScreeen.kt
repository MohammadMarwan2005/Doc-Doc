package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.errohandling.ErrorHandler
import com.alaishat.mohammad.docdoc.navigaiton.HomeScreenDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocButton
import com.alaishat.mohammad.docdoc.resuables.DocDocTheme
import com.alaishat.mohammad.docdoc.resuables.DocDocTopAppBar
import com.alaishat.mohammad.docdoc.resuables.DoctorCard
import com.alaishat.mohammad.docdoc.ui.theme.Gray
import com.alaishat.mohammad.docdoc.vm.BookedSuccessfullyViewModel
import com.alaishat.mohammad.domain.model.doctor_details.DoctorDetailsSuccessResponse

/**
 * Created by Mohammad Al-Aishat on Aug/25/2024.
 * DocDoc Project.
 */


@Composable
fun BookedSuccessfullyScreen(
    bookedSuccessfullyViewModel: BookedSuccessfullyViewModel = hiltViewModel(),
    doctorId: Int, formattedTime: String, navController: NavHostController,
    notes: String = "-",
    showConfirmationIcon: Boolean = true,
    isDoctorClickable: Boolean = true,
    onDoctorClicked: () -> Unit = {},
    onSnackBarHostEvent: (String) -> Unit
) {
    bookedSuccessfullyViewModel.getDoctorDetails(doctorId)
    val dadResponse =
        bookedSuccessfullyViewModel.doctorDetailsResponse.collectAsStateWithLifecycle().value
    val doctorDetails: DoctorDetailsSuccessResponse? = dadResponse as? DoctorDetailsSuccessResponse


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            DocDocTopAppBar(text = stringResource(R.string.details), navController = navController, leadingIcon = {
                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            navController.popBackStack(HomeScreenDestination.route, inclusive = false)
                        },
                    painter = painterResource(id = R.drawable.ic_back_button),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            })
        },
        bottomBar = {
            DocDocButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {
                    navController.popBackStack(HomeScreenDestination.route, inclusive = false)
                }) {
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = stringResource(R.string.done)
                )
            }
        }) { paddingValues ->
        val arrangement = if (showConfirmationIcon) Arrangement.Center else Arrangement.Top
        LazyColumn(modifier = Modifier.fillMaxHeight(), verticalArrangement = arrangement) {
            item {
                Column(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    if (showConfirmationIcon)
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_done_check),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = stringResource(R.string.booking_confirmed),
                                style = DocDocTheme.typography.titleSmall,
                                fontSize = TextUnit(18f, TextUnitType.Sp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.booking_information), style = DocDocTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_date),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = stringResource(R.string.date_and_time),
                                style = DocDocTheme.typography.titleSmall,
                                fontSize = TextUnit(14f, TextUnitType.Sp),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = formattedTime,
                                style = MaterialTheme.typography.bodySmall,
                                color = Gray,
                            )
                        }
                    }
                    Divider()
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.doctor_information), style = DocDocTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    doctorDetails?.data?.let { doctor ->
                        DoctorCard(
                            name = doctor.name,
                            specialization = doctor.specialization.name,
                            city = doctor.city.name,
                            phone = doctor.phone,
                            degree = doctor.degree,
                            gender = doctor.gender,
                            showGenderAndHidPhoneAndCity = true,
                            model = doctor.photo,
                            clickable = isDoctorClickable,
                            onClick = onDoctorClicked
                        )
                    }
                    Divider()
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.your_notes), style = DocDocTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = notes, style = DocDocTheme.typography.bodyMedium,
                    )


                }
            }
        }
    }
    LaunchedEffect(key1 = dadResponse) {
        ErrorHandler.handle(
            dadResponse,
            onResetState = { bookedSuccessfullyViewModel.resetDoctorDetailsState() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }
}
