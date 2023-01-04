/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kutylo.gamemaster.presentation.ui.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.kutylo.gamemaster.presentation.MenuItem
import androidx.wear.compose.material.AutoCenteringParams

@Composable
fun LandingScreen(
    scalingLazyListState: ScalingLazyListState,
    focusRequester: FocusRequester,
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxWidth(),
        anchorType = ScalingLazyListAnchorType.ItemStart,
        state = scalingLazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        autoCentering = AutoCenteringParams(itemIndex = 0, itemOffset = 0)
    ) {
        for (listItem in menuItems) {
            item {
                Chip(
                    onClick = listItem.clickHander,
                    label = {
                        Text(
                            listItem.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
