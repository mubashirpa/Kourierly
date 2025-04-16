package com.evaluation.kourierly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.evaluation.kourierly.presentation.customerUpdate.CustomerUpdateScreen
import com.evaluation.kourierly.presentation.cutomerRole.CustomerRoleScreen
import com.evaluation.kourierly.presentation.cutomerRole.CustomerRoleViewModel
import com.evaluation.kourierly.presentation.home.HomeScreen
import com.evaluation.kourierly.presentation.sendOtp.SendOtpScreen
import com.evaluation.kourierly.presentation.verifyOtp.VerifyOtpScreen
import org.koin.androidx.compose.koinViewModel

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
                onVerifyOtpSuccess = { customerId ->
                    navController.navigate(Screen.CustomerRole(args.phoneNumber, customerId)) {
                        popUpTo<Screen.SendOtp> {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<Screen.CustomerRole> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.CustomerRole>()
            val viewModel: CustomerRoleViewModel = koinViewModel()

            CustomerRoleScreen(
                uiState = viewModel.uiState,
                onEvent = viewModel::onEvent,
                onNavigateToCustomerUpdate = { roleId ->
                    navController.navigate(
                        Screen.CustomerUpdate(
                            args.phoneNumber,
                            args.customerId,
                            roleId,
                        ),
                    )
                },
            )
        }
        composable<Screen.CustomerUpdate> {
            CustomerUpdateScreen(
                onUpdateSuccess = { customerName ->
                    navController.navigate(Screen.Home(customerName)) {
                        popUpTo<Screen.CustomerRole> {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<Screen.Home> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Home>()
            HomeScreen(name = args.name)
        }
    }
}
