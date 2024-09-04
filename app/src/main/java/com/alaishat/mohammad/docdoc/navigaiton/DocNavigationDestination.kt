package com.alaishat.mohammad.docdoc.navigaiton

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */
interface DocNavigationDestination {
    val route: String
}

object OnBoardingScreenDestination : DocNavigationDestination {
    override val route: String = "OnBoardingScreen"
}

object RegisterScreenDestination : DocNavigationDestination {
    override val route: String = "RegisterScreen"
}

object LoginScreenDestination : DocNavigationDestination {
    override val route: String = "LoginScreen"
}

object HomeScreenDestination : DocNavigationDestination {
    override val route: String = "HomeScreenDestination"
}

object DoctorDetailsDestination : DocNavigationDestination {
    override val route: String = "DoctorDetailsDestination"
    const val DOCTOR_ID_KEY = "DOCTOR_ID_KEY"
}

object BookAppointmentDestination : DocNavigationDestination {
    override val route: String = "BookAppointmentDestination"
    const val DOCTOR_ID_KEY = "DOCTOR_ID_KEY"
    const val DOCTOR_NAME_KEY = "DOCTOR_NAME_KEY"
}

object BookedSuccessfullyDestination : DocNavigationDestination {
    override val route: String = "BookedSuccessfullyDestination"
    const val DOCTOR_ID_KEY = "DOCTOR_ID_KEY"
    const val FORMATTED_TIME_KEY = "FORMATTED_TIME_KEY"
    const val NOTES_KEY: String = "NOTES_KEY"
    const val IS_COMING_FROM_BOOKING_SCREEN_KEY: String = "IS_COMING_FROM_BOOKING_SCREEN_KEY"

}

object AllAppointmentsDestination : DocNavigationDestination {
    override val route: String = "AllAppointmentsDestination"
}

object MyProfileDestination : DocNavigationDestination {
    override val route: String = "MyProfileDestination"
}

object SearchDestination : DocNavigationDestination {
    override val route: String = "SearchDestination"
}

object AllSpecializationsDestination : DocNavigationDestination {
    override val route: String = "AllSpecializationsDestination"
}

