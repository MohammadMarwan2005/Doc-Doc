package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.errohandling.ErrorHandler
import com.alaishat.mohammad.docdoc.navigaiton.BookedSuccessfullyDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocButton
import com.alaishat.mohammad.docdoc.resuables.DocDocTextField
import com.alaishat.mohammad.docdoc.resuables.DocDocTopAppBar
import com.alaishat.mohammad.docdoc.vm.BookAppointmentViewModel
import com.alaishat.mohammad.domain.model.appointments.bookappointment.invalidtime.InvalidTimeAppointmentResponse
import com.alaishat.mohammad.domain.model.appointments.bookappointment.success.BookAppointmentSuccessResponse
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by Mohammad Al-Aishat on Aug/25/2024.
 * DocDoc Project.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    navController: NavHostController,
    doctorId: Int,
    doctorName: String,
    bookAppointmentViewModel: BookAppointmentViewModel = hiltViewModel(),
    onNewBookAppointments: () -> Unit = { },
    onSnackBarHostEvent: (String) -> Unit
) {
    val now = Date()
    val currentTimeInMillis = now.time

    val initialCalendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = currentTimeInMillis
        add(Calendar.HOUR_OF_DAY, 1)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = initialCalendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = initialCalendar.get(Calendar.MINUTE)
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTimeInMillis,
        initialDisplayMode = DisplayMode.Input
    )

    var notes by rememberSaveable { mutableStateOf("") }
    var invalidTimeError by rememberSaveable { mutableStateOf(false) }
    var invalidTimeErrorMessage by rememberSaveable { mutableStateOf("") }

    val dadResponse = bookAppointmentViewModel.bookAppointmentResponse.collectAsStateWithLifecycle().value
    val isLoading = bookAppointmentViewModel.isLoading.collectAsStateWithLifecycle().value

    val successResponse = dadResponse as? BookAppointmentSuccessResponse
    val invalidTimeResponse = dadResponse as? InvalidTimeAppointmentResponse

    val selectedTimeMillis = datePickerState.selectedDateMillis ?: currentTimeInMillis
    val selectedTimeCalendar = Calendar.getInstance().apply {
        timeInMillis = selectedTimeMillis
        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        set(Calendar.MINUTE, timePickerState.minute)
    }

    if (selectedTimeCalendar.timeInMillis < currentTimeInMillis) {
        invalidTimeError = true
        invalidTimeErrorMessage = stringResource(R.string.you_can_not_book_an_appointment_in_the_past)
    } else {
        invalidTimeError = false
        invalidTimeErrorMessage = ""
    }

    val formatter = SimpleDateFormat("yyyy-M-d H:mm", Locale.getDefault())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        topBar = {
            DocDocTopAppBar(text = doctorName, navController = navController)
        },
        bottomBar = {
            DocDocButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = !invalidTimeError,
                onClick = {

                    selectedTimeCalendar.apply {
                        timeInMillis = selectedTimeMillis
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                    }

                    bookAppointmentViewModel.bookAppointment(
                        doctorId,
                        notes = notes,
                        formattedDateAndTime = formatter.format(selectedTimeCalendar.time).toEnglishNumbers()
                    )
                }) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier.padding(14.dp),
                        text = stringResource(R.string.book_now)
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
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                item {
                    DatePicker(
                        modifier = Modifier.fillMaxWidth(),
                        state = datePickerState,

                        )
                    TimePicker(
                        modifier = Modifier.fillMaxWidth(),
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer),
                        layoutType = TimePickerDefaults.layoutType(),
                    )
                    if (invalidTimeError)
                        Surface(
                            modifier = Modifier,
                            contentColor = MaterialTheme.colorScheme.error
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.errorContainer)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Default.Info,
                                    contentDescription = ""
                                )
                                Text(text = stringResource(R.string.time_is_invalid, invalidTimeErrorMessage))
                            }
                        }
                }
                item {
                    DocDocTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeightIn(min = 300.dp, max = 10000.dp),
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text(text = stringResource(R.string.your_notes)) },
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = successResponse) {
        if (successResponse != null) {
            val humanFormatter = SimpleDateFormat("EEEE, dd MMM yyyy | hh:mm a", Locale.getDefault())
            val humanFormattedTime =
                humanFormatter.format(selectedTimeCalendar.time)
            if (notes.isBlank())
                notes = "-"

            onNewBookAppointments()
            navController.navigate(BookedSuccessfullyDestination.route + "/$doctorId/$humanFormattedTime/$notes/${true}")
        }
    }

    LaunchedEffect(key1 = invalidTimeResponse) {
        invalidTimeResponse?.let {
            invalidTimeError = true
            invalidTimeErrorMessage = it.message
        }
    }

    LaunchedEffect(key1 = dadResponse) {
        ErrorHandler.handle(
            dadResponse,
            onResetState = { bookAppointmentViewModel.restAppointmentsState() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }
}

fun String.toEnglishNumbers(): String {
    var result = ""
    this.forEach {
        result += if (NUMBERS_MAP.containsKey(it))
            NUMBERS_MAP[it]
        else
            it
    }
    return result
}

val NUMBERS_MAP = mapOf(
    '٠' to '0',
    '١' to '1',
    '٢' to '2',
    '٣' to '3',
    '٤' to '4',
    '٥' to '5',
    '٦' to '6',
    '٧' to '7',
    '٨' to '8',
    '٩' to '9',
)