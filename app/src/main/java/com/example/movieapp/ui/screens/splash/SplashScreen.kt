package com.example.movieapp.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.CinemaGold
import com.example.movieapp.ui.theme.CinemaRed
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate: () -> Unit) {
    val iconScale = remember { Animatable(0f) }
    val iconAlpha = remember { Animatable(0f) }
    val titleScale = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val subtitleTranslateY = remember { Animatable(30f) }
    val courseAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        iconAlpha.animateTo(1f, animationSpec = tween(400))
        iconScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        )

        titleAlpha.animateTo(1f, animationSpec = tween(400))
        titleScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        )

        subtitleTranslateY.animateTo(0f, animationSpec = tween(600, easing = FastOutSlowInEasing))
        subtitleAlpha.animateTo(1f, animationSpec = tween(500))

        courseAlpha.animateTo(1f, animationSpec = tween(600))

        delay(1200)
        onNavigate()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A0000),
                        Color(0xFF0A0000),
                        Color(0xFF000000)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Movie,
                contentDescription = "MovieApp",
                modifier = Modifier
                    .size(100.dp)
                    .scale(iconScale.value)
                    .alpha(iconAlpha.value)
                    .shadow(20.dp, RoundedCornerShape(16.dp), ambientColor = CinemaGold.copy(alpha = 0.3f), spotColor = CinemaGold.copy(alpha = 0.3f)),
                tint = CinemaGold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "MovieApp",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = CinemaGold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .scale(titleScale.value)
                    .alpha(titleAlpha.value)
                    .shadow(12.dp, RoundedCornerShape(4.dp), ambientColor = CinemaGold.copy(alpha = 0.2f), spotColor = CinemaGold.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .graphicsLayer(
                        alpha = subtitleAlpha.value,
                        translationY = subtitleTranslateY.value
                    )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Elaborado por",
                        fontSize = 16.sp,
                        color = Color(0xFFCCCCCC),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Julian Cortes Perdomo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CinemaGold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Dispositivos Móviles",
                fontSize = 16.sp,
                color = CinemaRed.copy(alpha = courseAlpha.value),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                letterSpacing = 4.sp,
                modifier = Modifier.alpha(courseAlpha.value)
            )
        }
    }
}
