/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.icon.v1

import team.duckie.app.android.common.compose.R
import team.duckie.quackquack.material.icon.QuackIcon as QuackV2Icon
import team.duckie.quackquack.ui.icon.QuackIcon as QuackV1Icon

val QuackV1Icon.Companion.DefaultProfile get() = R.drawable.ic_default_profile
val QuackV1Icon.Companion.Notice get() = R.drawable.ic_notice_24

val QuackV1Icon.Companion.Create get() = R.drawable.ic_create_24

val QuackV1Icon.Companion.Crown get() = R.drawable.ic_crown_12

// Quack V2 의 QuackIcon 을 통해 Quack V1 의 drawable Resource 를 가져오는 확장 변수

val QuackV2Icon.ArrowBackId: Int get() = QuackV1Icon.ArrowBack.drawableId

val QuackV2Icon.ArrowRightId: Int get() = QuackV1Icon.ArrowRight.drawableId

val QuackV2Icon.ArrowSendId: Int get() = QuackV1Icon.ArrowSend.drawableId

val QuackV2Icon.CloseId: Int get() = QuackV1Icon.Close.drawableId

val QuackV2Icon.SearchId: Int get() = QuackV1Icon.Search.drawableId

val QuackV2Icon.MoreId: Int get() = QuackV1Icon.More.drawableId

val QuackV2Icon.CameraId: Int get() = QuackV1Icon.Camera.drawableId

val QuackV2Icon.TextLogoId: Int get() = QuackV1Icon.TextLogo.drawableId

val QuackV2Icon.ProfileId: Int get() = QuackV1Icon.Profile.drawableId

val QuackV2Icon.DefaultProfileId: Int get() = QuackV1Icon.DefaultProfile

val QuackV2Icon.CheckId: Int get() = QuackV1Icon.Check.drawableId

val QuackV2Icon.CreateId: Int get() = QuackV1Icon.Create

val QuackV2Icon.AreaId: Int get() = QuackV1Icon.Area.drawableId

val Int.toQuackV1Icon: QuackV1Icon?
    get() = when (this) {
        QuackV1Icon.ArrowBack.drawableId -> QuackV1Icon.ArrowBack
        QuackV1Icon.ArrowRight.drawableId -> QuackV1Icon.ArrowRight
        QuackV1Icon.ArrowSend.drawableId -> QuackV1Icon.ArrowSend
        QuackV1Icon.Close.drawableId -> QuackV1Icon.Close
        QuackV1Icon.Search.drawableId -> QuackV1Icon.Search
        QuackV1Icon.More.drawableId -> QuackV1Icon.More
        QuackV1Icon.Camera.drawableId -> QuackV1Icon.Camera
        QuackV1Icon.TextLogo.drawableId -> QuackV1Icon.TextLogo
        QuackV1Icon.Check.drawableId -> QuackV1Icon.Check
        QuackV1Icon.Area.drawableId -> QuackV1Icon.Area
        else -> null
    }
