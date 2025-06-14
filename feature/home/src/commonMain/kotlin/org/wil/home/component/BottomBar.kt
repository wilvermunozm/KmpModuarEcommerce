package org.wil.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nutrisport.shared.IconPrimary
import com.nutrisport.shared.IconSecondary
import com.nutrisport.shared.SurfaceLighter
import org.jetbrains.compose.resources.painterResource
import org.wil.home.domain.BottomBarDestination

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onSelect: (BottomBarDestination) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 12.dp))
            .background(SurfaceLighter)
            .padding(
                vertical = 24.dp,
                horizontal = 36.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomBarDestination.entries.forEach { destination ->
            val animatedTint by animateColorAsState(
                targetValue = if (selected) IconSecondary else IconPrimary
            )
            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    modifier = Modifier.clickable { onSelect(destination) },
                    painter = painterResource(destination.icon),
                    contentDescription = "Bottom Bar destination icon",
                    tint = animatedTint
                )
                if (destination == BottomBarDestination.Cart) {
                    AnimatedContent(
                        targetState = null
                    ) { customerState ->
                        //if (customerState.isSuccess() && customerState.getSuccessData().cart.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .offset(x = 4.dp, y = (-4).dp)
                                .clip(CircleShape)
                                .background(IconSecondary)
                        )
                        // }
                    }
                }
            }
        }
    }
}