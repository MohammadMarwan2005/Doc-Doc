package com.alaishat.mohammad.docdoc.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.errohandling.ErrorHandler
import com.alaishat.mohammad.docdoc.navigaiton.DoctorDetailsDestination
import com.alaishat.mohammad.docdoc.navigaiton.HomeScreenDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocTopAppBar
import com.alaishat.mohammad.docdoc.vm.AllSpecializationsViewModel
import com.alaishat.mohammad.domain.model.all_specialization.response.AllSpecializationsResponse
import com.alaishat.mohammad.domain.model.all_specialization.response.success.AllSpecializationSuccessResponse
import com.alaishat.mohammad.domain.model.all_specialization.response.success.SpecializationsFullData
import com.alaishat.mohammad.domain.model.filtered_doctors.FilteredDoctorsSuccessResponse

/**
 * Created by Mohammad Al-Aishat on Aug/31/2024.
 * DocDoc Project.
 */

@SuppressLint("SuspiciousIndentation")
@Composable
fun AllSpecializationsScreen(
    navController: NavHostController,
    allSpecializationsViewModel: AllSpecializationsViewModel = hiltViewModel(),
    onSnackBarHostEvent: (String) -> Unit,
) {

    allSpecializationsViewModel.getAllSpecializationsResponse()

    val allSpecializationsDadResponse: AllSpecializationsResponse? =
        allSpecializationsViewModel.allSpecializationsResponse.collectAsStateWithLifecycle().value

    val allSpecializationSuccessResponse = allSpecializationsDadResponse as? AllSpecializationSuccessResponse
    val specializations: List<SpecializationsFullData>? = allSpecializationSuccessResponse?.data

    var selected by remember {
        mutableStateOf(0)
    }
    specializations?.let {
        allSpecializationsViewModel.getFilteredDoctorsBySpec(specId = specializations[selected].id)
    }


    val filteredDoctorsDadResponse = allSpecializationsViewModel.filteredDoctorsBySpecResponse.collectAsStateWithLifecycle().value

    val filteredDoctors: FilteredDoctorsSuccessResponse? =
        filteredDoctorsDadResponse as? FilteredDoctorsSuccessResponse


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        topBar = {
            DocDocTopAppBar(text = stringResource(R.string.all_specializations), navController = navController, leadingIcon = {
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

        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(24.dp))
                    specializations?.let {
                        DoctorSpecialityRow(
                            showTitle = false,
                            specs = it, selected = selected, onClick = { index ->
                            selected = index
                                allSpecializationsViewModel.resetFilteredDoctorsBySpec()
                                allSpecializationsViewModel.getFilteredDoctorsBySpec(specId = specializations[index].id)
                        })
                    } ?: Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {CircularProgressIndicator()}
                    Spacer(modifier = Modifier.height(24.dp))
                    filteredDoctors?.let {
                        RecommendedDoctorRow(doctorsData = it.data, onClick = { clickedDoctorId ->
                            navController.navigate(DoctorDetailsDestination.route + "/$clickedDoctorId")
                        })
                    } ?: Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {CircularProgressIndicator()}
                }
            }

        }
    }
    LaunchedEffect(key1 = allSpecializationsDadResponse) {
        ErrorHandler.handle(
            allSpecializationsDadResponse,
            onResetState = { allSpecializationsViewModel.resetAllSpecializationState() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }
    LaunchedEffect(key1 = allSpecializationsDadResponse) {
        ErrorHandler.handle(
            filteredDoctorsDadResponse,
            onResetState = { allSpecializationsViewModel.resetFilteredDoctorsBySpec() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }

}
