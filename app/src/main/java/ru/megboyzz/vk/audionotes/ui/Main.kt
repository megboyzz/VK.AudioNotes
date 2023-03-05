package ru.megboyzz.vk.audionotes.ui
//https://github.com/megboyzz/VK.AudioNotes
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import ru.megboyzz.vk.audionotes.entities.Recording
import ru.megboyzz.vk.audionotes.ui.home.HomeActivity
import ru.megboyzz.vk.audionotes.ui.home.HomeScreen
import ru.megboyzz.vk.audionotes.ui.home.HomeViewModel
import ru.megboyzz.vk.audionotes.navigation.Screen
import ru.megboyzz.vk.audionotes.ui.recordingdetails.RecordingDetailsScreen
import ru.megboyzz.vk.audionotes.ui.recordingdetails.RecordingDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun Main(
    homeViewModel: HomeViewModel,
    recordingDetailsViewModel: RecordingDetailsViewModel,
    activity: HomeActivity
) {
    val navController = rememberNavController()
    homeViewModel.readRecordings()

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            val recordingList: List<Recording> =
                homeViewModel.recordings.collectAsState().value
            HomeScreen(
                homeViewModel = homeViewModel,
                activity = activity,
                onClick = { item ->
                    navController.navigate(
                        "${Screen.RecordingDetails.route}/${item.readableDayTime}/${item.duration}/${item.path}"
                    )
                },
                recordingList = recordingList.toMutableList()
            )
        }
        composable(
            "${Screen.RecordingDetails.route}/{dayAndTime}/{duration}/{path}"
        ) { backStackEntry ->

            recordingDetailsViewModel.initMediaPlayer(
                Uri.fromFile(
                    File(backStackEntry.arguments?.getString("path") ?: "")
                )
            )

            RecordingDetailsScreen(
                backStackEntry.arguments?.getString("dayAndTime") ?: "",
                backStackEntry.arguments?.getString("duration") ?: "",
                backStackEntry.arguments?.getString("path") ?: "",
                recordingDetailsViewModel
            ) { navController.popBackStack() }
        }
    }
}