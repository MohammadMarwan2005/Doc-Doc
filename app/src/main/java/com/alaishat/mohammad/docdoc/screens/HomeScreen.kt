package com.alaishat.mohammad.docdoc.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.errohandling.ErrorHandler
import com.alaishat.mohammad.docdoc.navigaiton.DoctorDetailsDestination
import com.alaishat.mohammad.docdoc.navigaiton.SearchDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocTheme
import com.alaishat.mohammad.docdoc.resuables.DoctorCard
import com.alaishat.mohammad.docdoc.resuables.TitleWithSeeAllTextButtonRow
import com.alaishat.mohammad.docdoc.ui.theme.DocDocSurface
import com.alaishat.mohammad.docdoc.ui.theme.Seed
import com.alaishat.mohammad.docdoc.vm.HomeViewModel
import com.alaishat.mohammad.domain.model.all_specialization.response.success.SpecializationsFullData
import com.alaishat.mohammad.domain.model.core.Doctor
import com.alaishat.mohammad.domain.model.home.Data
import com.alaishat.mohammad.domain.model.home.HomeSuccessResponse
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onSnackBarHostEvent: (String) -> Unit,
) {


    homeViewModel.setFirstVisit()
    val userInfoState = homeViewModel.userTokenAndName.collectAsStateWithLifecycle().value
    val username = userInfoState.second

    val homeDadResponse = homeViewModel.homeResponse.collectAsStateWithLifecycle().value
    val homeSuccessResponse: List<Data>? = (homeDadResponse as? HomeSuccessResponse)?.data

    val config = LocalConfiguration.current
    val cardPortion = if (config.orientation == Configuration.ORIENTATION_PORTRAIT) 0.25f else 0.1f

    val isLoading = homeViewModel.isLoading.collectAsStateWithLifecycle().value

    val lazyColumnState = rememberLazyListState()

    val refreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(state = refreshState, onRefresh = {
        homeViewModel.getHomeResponse()
    }) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            TopHomeBar()
            LazyColumn(
                state = lazyColumnState
            ) {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        HomeBannerWithAnImage(
                            username = username ?: "",
                            cardPortion,
                            onFindNearbyClick = { navController.navigate(SearchDestination.route) })
                        Spacer(modifier = Modifier.height(24.dp))
                        TitleWithSeeAllTextButtonRow(title = stringResource(R.string.recommended_doctors))
                        Spacer(modifier = Modifier.height(16.dp))
                        if (isLoading)
                            CircularProgressIndicator()
                        else
                            homeSuccessResponse?.let {
                                RecommendedCategorizedDoctors(navController = navController, data = homeSuccessResponse)
                            }

                    }
                }
            }
        }
    }


    LaunchedEffect(key1 = homeDadResponse) {
        ErrorHandler.handle(
            homeDadResponse,
            onResetState = { homeViewModel.resetHomeResponse() },
            onSnackBarHostEvent = { onSnackBarHostEvent(it) }
        )
    }

}

@Composable
fun TopHomeBar() {
    Row(
        Modifier
            .padding(top = 12.dp, bottom = 16.dp)
            .padding(horizontal = 32.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.on_boarding_top_logo),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun HomeBanner(username: String, cardPortion: Float, onFindNearbyClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Seed)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(cardPortion),
            painter = painterResource(id = R.drawable.banner_back_pattern3), contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(cardPortion)
                    .weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = stringResource(R.string.welcome_book_and_schedule_with_nearest_doctor, username),
                    style = DocDocTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
                Button(
                    onClick = onFindNearbyClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Seed)
                ) {
                    Text(text = stringResource(R.string.find_nearby))
                }
            }
        }
    }
}

@Composable
fun HomeBannerWithAnImage(
    username: String,
    cardPortion: Float,
    onFindNearbyClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxHeight(cardPortion),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            HomeBanner(username = username, cardPortion = cardPortion, onFindNearbyClick = onFindNearbyClick)
        }
        Image(
            modifier = Modifier
                .fillMaxHeight(0f)
                .padding(end = 16.dp),
            painter = painterResource(id = R.drawable.doctor_ill_2), contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun DoctorSpecialityRow(
    specs: List<SpecializationsFullData>,
    selected: Int = 0,
    showTitle: Boolean = true,
    onClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (showTitle) TitleWithSeeAllTextButtonRow(title = stringResource(R.string.doctor_speciality))
        LazyRow(verticalAlignment = Alignment.CenterVertically) {
            items(specs.size, key = { specs[it].id }) {
                SpecialityComp(
                    name = specs[it].name,
                    selected = selected == it,
                    onClick = {
                        onClick(it)
                    }
                )
            }
        }
    }
}

@Composable
fun SpecialityComp(
    onClick: () -> Unit = {},
    painter: Painter = painterResource(id = R.drawable.doctor),
    name: String = "General",
    selected: Boolean = true,
) {

    val myMod = if (selected) Modifier.border(width = 4.dp, color = Seed, shape = CircleShape) else Modifier
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .then(myMod)
                .clickable { onClick() }
                .background(DocDocSurface), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painter,
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.requiredSize(if (selected) 88.dp else 72.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}


@Composable
fun RecommendedCategorizedDoctors(
    navController: NavHostController,
    data: List<Data>,
) {
    Column {
        data.forEach { data ->
            Text(text = data.name)
            Spacer(modifier = Modifier.height(8.dp))
            RecommendedDoctorRow(
                doctorsData = data.doctors,
                onClick = {
                    navController.navigate(DoctorDetailsDestination.route + "/$it")
                })
        }
    }
}

@Composable
fun RecommendedDoctorRow(
    doctorsData: List<Doctor> = emptyList(),
    onClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        for (doctor in doctorsData) {
            DoctorCard(
                name = doctor.name,
                specialization = doctor.specialization.name,
                city = doctor.city.name,
                model = doctor.photo,
                degree = doctor.degree,
                phone = doctor.phone,
                onClick = {
                    onClick(doctor.id)
                }
            )
        }
    }
}
