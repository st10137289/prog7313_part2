package com.programming3c.prog7313part2.ui.theme

import androidx.compose.ui.graphics.Color

// BudgetQuest brand palette is white and green to matche our Part 1 design system
// primary: bright green for CTAs, active nav, highlights
// secondary: medium blue for icons and supporting actions
// backgrounds stay white/light-grey

val BudgetGreen = Color(0xFF4CAF50)         // main green: buttons, selected states
val BudgetGreenLight = Color(0xFFA5D6A7)    // lighter green for dark theme primary
val BudgetGreenContainer = Color(0xFFC8E6C9) // container variant (chips, cards)
val BudgetGreenDark = Color(0xFF388E3C)     // deeper green for dark-theme containers

val BudgetBlue = Color(0xFF2196F3)          // secondary: icons, secondary actions
val BudgetBlueLight = Color(0xFF90CAF9)     // secondary in dark theme
val BudgetBlueContainer = Color(0xFFBBDEFB) // secondary container

val BackgroundLight = Color(0xFFFFFFFF)     // pure white: main background
val SurfaceLight = Color(0xFFF5F5F5)        // very light grey: cards and surfaces
val BackgroundDark = Color(0xFF121212)
val SurfaceDark = Color(0xFF1E1E1E)

val OnPrimaryWhite = Color(0xFFFFFFFF)      // white text on green buttons
val TextPrimary = Color(0xFF1C1B1F)         // near-black body text
val TextSecondary = Color(0xFF49454F)       // muted label text
val ErrorRed = Color(0xFFB00020)            // validation errors
