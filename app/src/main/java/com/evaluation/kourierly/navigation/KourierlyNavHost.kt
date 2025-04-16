package com.evaluation.kourierly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.evaluation.kourierly.presentation.cutomerRole.CustomerRoleScreen
import com.evaluation.kourierly.presentation.sendOtp.SendOtpScreen
import com.evaluation.kourierly.presentation.verifyOtp.VerifyOtpScreen

@Composable
fun KourierlyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Screen = Screen.SendOtp,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Screen.SendOtp> {
            SendOtpScreen(
                onSendOtpSuccess = { phoneNumber ->
                    navController.navigate(Screen.VerifyOtp(phoneNumber))
                },
            )
        }
        composable<Screen.VerifyOtp> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.VerifyOtp>()
            VerifyOtpScreen(
                phoneNumber = args.phoneNumber,
                onVerifyOtpSuccess = {
                    navController.navigate(Screen.CustomerRole) {
                        popUpTo<Screen.SendOtp> {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<Screen.CustomerRole> {
            CustomerRoleScreen()
        }
    }
}
