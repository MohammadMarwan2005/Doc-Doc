package com.alaishat.mohammad.docdoc.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.docdoc.navigaiton.DocDocBottomNavBarItem
import com.alaishat.mohammad.docdoc.navigaiton.LoginScreenDestination
import com.alaishat.mohammad.docdoc.screens.routes
import com.alaishat.mohammad.domain.usecase.ClearUserTokeAndNameUseCase
import com.alaishat.mohammad.domain.usecase.ContainsUserTokenUseCase
import com.alaishat.mohammad.domain.usecase.IsFirstVisitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */

@HiltViewModel
class MainAppViewModel @Inject constructor(
    private val isFirstVisitUseCase: IsFirstVisitUseCase,
    private val containsUserTokenUseCase: ContainsUserTokenUseCase,
    private val clearUserTokeAndNameUseCase: ClearUserTokeAndNameUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _navItems: MutableStateFlow<List<DocDocBottomNavBarItem>> = MutableStateFlow(
        listOf(
            DocDocBottomNavBarItem(
                title = context.getString(R.string.home),
                route = routes[0],
                selectedIcon = R.drawable.ic_selected_home,
                unselectedIcon = R.drawable.ic_unselected_home,
            ),
            DocDocBottomNavBarItem(
                title = context.getString(R.string.specializations),
                route = routes[1],
                selectedIcon = R.drawable.ic_selected_specs,
                unselectedIcon = R.drawable.ic_unselected_specs,
            ),

            DocDocBottomNavBarItem(
                title = context.getString(R.string.appointments),
                route = routes[1],
                selectedIcon = R.drawable.ic_selected_calendar,
                unselectedIcon = R.drawable.ic_unselected_calendar,
                hasNews = true,
                badgeCount = 0
            ),
            DocDocBottomNavBarItem(
                title = context.getString(R.string.my_profile),
                route = routes[1],
                selectedIcon = R.drawable.ic_selected_profile,
                unselectedIcon = R.drawable.ic_unselected_profile,
            ),
        )
    )
    val navItems = _navItems.asStateFlow()

    private val _isFirstVisit: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isFirstVisit = _isFirstVisit.asStateFlow()

    private val _containsUserToken: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val containsUserToken = _containsUserToken.asStateFlow()

    private val _showBottomNavBar: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showBottomNavBar = _showBottomNavBar.asStateFlow()

    init {
        viewModelScope.launch {
            _isFirstVisit.value = isFirstVisitUseCase()
            _containsUserToken.value = containsUserTokenUseCase()
        }
    }
    fun logout(navController: NavHostController) {
        _showBottomNavBar.value = false
        viewModelScope.launch {
            clearUserTokeAndNameUseCase()
            navController.navigate(LoginScreenDestination.route) {
                popUpTo(0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    fun setNewAppointmentsBadges(newNumber: Int) {
        _navItems.value[2].badgeCount = newNumber
    }

    fun increaseAppointmentsBadgesByOne() {
        _navItems.value[2].badgeCount++
    }


}