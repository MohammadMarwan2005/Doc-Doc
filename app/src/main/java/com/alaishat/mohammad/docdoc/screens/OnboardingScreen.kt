package com.alaishat.mohammad.docdoc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.navigaiton.RegisterScreenDestination
import com.alaishat.mohammad.docdoc.resuables.DocDocBodyText
import com.alaishat.mohammad.docdoc.resuables.DocDocButton
import com.alaishat.mohammad.docdoc.resuables.DocDocTitleText

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */

@Composable
fun OnBoardingScreen(
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Icon(
                painter = painterResource(id = R.drawable.on_boarding_top_logo),
                contentDescription = "",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(40.dp))
            TheMiddleOnboardingBackground()
            Spacer(modifier = Modifier.height(16.dp))

            DocDocBodyText(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = stringResource(R.string.manage_and_schedule_all_of_your_medical_appointments_easily_with_docdoc_to_get_a_new_experience),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            DocDocButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                onClick = {
                    navController.navigate(RegisterScreenDestination.route)
                }) {
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = stringResource(R.string.get_started)
                )
            }

        }
    }
}

@Composable
fun TheMiddleOnboardingBackground() {
    Box(modifier = Modifier, contentAlignment = Alignment.BottomCenter) {
        Icon(
            modifier = Modifier
                .padding(bottom = 120.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.onboarding_back_logo),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Image(painter = painterResource(id = R.drawable.onboarding_doctor_image), contentDescription = "")
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.onboarding_linear_effect), contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            DocDocTitleText(
                modifier = Modifier.padding(horizontal = 64.dp),
                text = stringResource(R.string.best_doctor_appointment_app),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                minLines = 2,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
