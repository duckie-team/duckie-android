/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.icon.v2

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.QuackIcon

public val QuackIcon.DuckieTextLogo: ImageVector
    get() {
        if (_icDuckieTextLogo != null) {
            return _icDuckieTextLogo!!
        }
        _icDuckieTextLogo = Builder(
            name = "IcDuckieTextLogo", defaultWidth = 48.0.dp,
            defaultHeight =
                16.0.dp,
                    viewportWidth = 48.0f, viewportHeight = 16.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(35.2292f, 13.2969f)
                    horizontalLineTo(36.197f)
                    lineTo(37.9751f, 5.0226f)
                    horizontalLineTo(37.0073f)
                    lineTo(35.2292f, 13.2969f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(37.6044f, 2.2857f)
                    lineTo(37.2749f, 3.8552f)
                    horizontalLineTo(38.2426f)
                    lineTo(38.5721f, 2.2857f)
                    horizontalLineTo(37.6044f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(8.16f, 12.2963f)
                    lineTo(8.1695f, 12.2438f)
                    curveTo(8.1661f, 12.2624f, 8.1626f, 12.2794f, 8.16f, 12.2963f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(8.1756f, 12.2133f)
                    lineTo(8.1696f, 12.2446f)
                    curveTo(8.1713f, 12.2345f, 8.1739f, 12.2235f, 8.1756f, 12.2133f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(10.2375f, 2.2857f)
                    lineTo(9.2663f, 6.8224f)
                    curveTo(9.1966f, 6.681f, 9.1166f, 6.5456f, 9.0272f, 6.4152f)
                    curveTo(8.8706f, 6.19f, 8.6813f, 5.9903f, 8.4637f, 5.8218f)
                    curveTo(8.2461f, 5.6533f, 7.9966f, 5.5162f, 7.7213f, 5.4146f)
                    curveTo(7.4443f, 5.3122f, 7.1364f, 5.2597f, 6.8043f, 5.2597f)
                    curveTo(6.2186f, 5.2597f, 5.6482f, 5.3977f, 5.1097f, 5.6711f)
                    curveTo(4.5747f, 5.942f, 4.0938f, 6.3077f, 3.6809f, 6.7598f)
                    curveTo(3.2689f, 7.2101f, 2.936f, 7.7308f, 2.6917f, 8.3064f)
                    curveTo(2.4474f, 8.8838f, 2.3226f, 9.4874f, 2.3226f, 10.1003f)
                    curveTo(2.3226f, 10.5439f, 2.3872f, 10.9706f, 2.5153f, 11.3676f)
                    curveTo(2.6435f, 11.7672f, 2.8345f, 12.1244f, 3.0822f, 12.43f)
                    curveTo(3.3317f, 12.7365f, 3.6413f, 12.9845f, 4.0035f, 13.1674f)
                    curveTo(4.3674f, 13.3519f, 4.7837f, 13.4451f, 5.2396f, 13.4451f)
                    curveTo(5.8065f, 13.4451f, 6.3897f, 13.2868f, 6.9747f, 12.9735f)
                    curveTo(7.4366f, 12.7263f, 7.8521f, 12.408f, 8.2134f, 12.0262f)
                    curveTo(8.2074f, 12.055f, 8.2022f, 12.0813f, 8.197f, 12.1067f)
                    curveTo(8.1884f, 12.1456f, 8.1824f, 12.1786f, 8.1755f, 12.2133f)
                    curveTo(8.1738f, 12.2235f, 8.1712f, 12.2353f, 8.1695f, 12.2446f)
                    lineTo(8.1601f, 12.2971f)
                    lineTo(7.9811f, 13.2859f)
                    horizontalLineTo(8.8551f)
                    lineTo(9.065f, 12.2692f)
                    lineTo(9.133f, 11.939f)
                    lineTo(11.1932f, 2.4118f)
                    lineTo(11.2198f, 2.2857f)
                    horizontalLineTo(10.2366f)
                    horizontalLineTo(10.2375f)
                    close()
                    moveTo(8.4628f, 10.5718f)
                    curveTo(8.3777f, 10.841f, 8.2211f, 11.0967f, 7.9983f, 11.3304f)
                    curveTo(7.7695f, 11.5699f, 7.5097f, 11.7867f, 7.2267f, 11.9737f)
                    curveTo(6.9446f, 12.1608f, 6.6461f, 12.3081f, 6.3416f, 12.4131f)
                    curveTo(6.0388f, 12.5172f, 5.7643f, 12.5697f, 5.5269f, 12.5697f)
                    curveTo(5.194f, 12.5697f, 4.8895f, 12.5012f, 4.6203f, 12.3666f)
                    curveTo(4.3493f, 12.2311f, 4.1153f, 12.044f, 3.9252f, 11.8095f)
                    curveTo(3.7334f, 11.5742f, 3.5828f, 11.2957f, 3.477f, 10.9833f)
                    curveTo(3.3712f, 10.6709f, 3.317f, 10.3331f, 3.317f, 9.9801f)
                    curveTo(3.317f, 9.5323f, 3.4108f, 9.0743f, 3.5949f, 8.6205f)
                    curveTo(3.7798f, 8.1651f, 4.037f, 7.7469f, 4.3596f, 7.3769f)
                    curveTo(4.6813f, 7.0087f, 5.0607f, 6.7048f, 5.4874f, 6.4753f)
                    curveTo(5.9106f, 6.2485f, 6.3648f, 6.1325f, 6.8353f, 6.1325f)
                    curveTo(7.0899f, 6.1325f, 7.3411f, 6.1841f, 7.5828f, 6.2866f)
                    curveTo(7.8263f, 6.3898f, 8.0499f, 6.5312f, 8.2452f, 6.7048f)
                    curveTo(8.4413f, 6.8791f, 8.6082f, 7.0891f, 8.7424f, 7.3287f)
                    curveTo(8.8723f, 7.5623f, 8.9592f, 7.8112f, 8.9988f, 8.0669f)
                    lineTo(8.4637f, 10.5701f)
                    lineTo(8.4628f, 10.5718f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(18.0259f, 5.3934f)
                    lineTo(16.9738f, 10.3162f)
                    curveTo(16.7992f, 10.6472f, 16.5842f, 10.9545f, 16.3338f, 11.2288f)
                    curveTo(16.0792f, 11.5081f, 15.8048f, 11.7494f, 15.5175f, 11.9458f)
                    curveTo(15.231f, 12.1422f, 14.9274f, 12.2971f, 14.6177f, 12.4063f)
                    curveTo(14.3098f, 12.5155f, 14.0035f, 12.5706f, 13.7059f, 12.5706f)
                    curveTo(12.8362f, 12.5706f, 12.413f, 12.0863f, 12.413f, 11.0908f)
                    curveTo(12.413f, 10.7547f, 12.468f, 10.339f, 12.5764f, 9.8548f)
                    lineTo(13.5356f, 5.3934f)
                    horizontalLineTo(12.5833f)
                    lineTo(11.588f, 9.9149f)
                    curveTo(11.527f, 10.185f, 11.4839f, 10.4406f, 11.4581f, 10.6734f)
                    curveTo(11.4323f, 10.9062f, 11.4203f, 11.1272f, 11.4203f, 11.3287f)
                    curveTo(11.4203f, 12.7331f, 12.068f, 13.4451f, 13.3454f, 13.4451f)
                    curveTo(14.0216f, 13.4451f, 14.6943f, 13.2664f, 15.3437f, 12.9143f)
                    curveTo(15.8642f, 12.6324f, 16.3313f, 12.2557f, 16.7373f, 11.7909f)
                    curveTo(16.7209f, 11.8662f, 16.7063f, 11.934f, 16.6925f, 11.9958f)
                    curveTo(16.6917f, 12.0008f, 16.6908f, 12.0051f, 16.69f, 12.0093f)
                    lineTo(16.6882f, 12.0152f)
                    curveTo(16.6702f, 12.0999f, 16.6547f, 12.1735f, 16.6427f, 12.237f)
                    lineTo(16.4267f, 13.2859f)
                    horizontalLineTo(17.2973f)
                    lineTo(17.5123f, 12.287f)
                    lineTo(17.5734f, 12.0017f)
                    curveTo(17.5751f, 11.9949f, 17.5768f, 11.9873f, 17.5786f, 11.9805f)
                    lineTo(17.8091f, 10.9096f)
                    lineTo(18.9945f, 5.3943f)
                    horizontalLineTo(18.0259f)
                    verticalLineTo(5.3934f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(25.3265f, 11.2059f)
                    curveTo(25.2319f, 11.3829f, 25.0951f, 11.5556f, 24.9188f, 11.7189f)
                    curveTo(24.7398f, 11.8849f, 24.5325f, 12.0313f, 24.302f, 12.1541f)
                    curveTo(24.0689f, 12.2785f, 23.8177f, 12.3801f, 23.5553f, 12.4563f)
                    curveTo(22.9712f, 12.6273f, 22.394f, 12.6087f, 21.9183f, 12.3742f)
                    curveTo(21.6525f, 12.2438f, 21.4246f, 12.0618f, 21.2396f, 11.8324f)
                    curveTo(21.0521f, 11.6013f, 20.9041f, 11.3236f, 20.7983f, 11.0061f)
                    curveTo(20.6925f, 10.6887f, 20.6383f, 10.3433f, 20.6383f, 9.9801f)
                    curveTo(20.6383f, 9.4637f, 20.7416f, 8.9676f, 20.9454f, 8.5045f)
                    curveTo(21.1502f, 8.0398f, 21.422f, 7.6267f, 21.7532f, 7.2762f)
                    curveTo(22.0835f, 6.9274f, 22.4672f, 6.6463f, 22.8947f, 6.4406f)
                    curveTo(23.3196f, 6.2366f, 23.7644f, 6.1333f, 24.2168f, 6.1333f)
                    curveTo(24.6693f, 6.1333f, 25.0728f, 6.2493f, 25.4039f, 6.477f)
                    curveTo(25.7325f, 6.7039f, 25.9562f, 7.0205f, 26.0697f, 7.4193f)
                    lineTo(26.099f, 7.5217f)
                    horizontalLineTo(27.1485f)
                    curveTo(27.133f, 7.3964f, 27.1089f, 7.2813f, 27.0814f, 7.1721f)
                    lineTo(27.071f, 7.1297f)
                    curveTo(26.9928f, 6.8165f, 26.8663f, 6.5397f, 26.6951f, 6.2984f)
                    curveTo(26.628f, 6.2011f, 26.579f, 6.1452f, 26.579f, 6.1452f)
                    verticalLineTo(6.1469f)
                    curveTo(26.4543f, 5.9996f, 26.3123f, 5.8675f, 26.1497f, 5.7532f)
                    curveTo(25.6869f, 5.4256f, 25.0977f, 5.2605f, 24.3966f, 5.2605f)
                    curveTo(23.7618f, 5.2605f, 23.1528f, 5.3934f, 22.585f, 5.655f)
                    curveTo(22.019f, 5.9158f, 21.5132f, 6.2722f, 21.0805f, 6.7132f)
                    curveTo(20.6469f, 7.1543f, 20.2986f, 7.6707f, 20.0448f, 8.2464f)
                    curveTo(19.7893f, 8.8245f, 19.6603f, 9.4434f, 19.6603f, 10.0859f)
                    curveTo(19.6603f, 10.549f, 19.7308f, 10.9884f, 19.8685f, 11.3922f)
                    curveTo(20.0078f, 11.7977f, 20.2117f, 12.1575f, 20.4749f, 12.4622f)
                    curveTo(20.7398f, 12.7687f, 21.0624f, 13.0108f, 21.4349f, 13.1843f)
                    curveTo(21.8082f, 13.3579f, 22.2332f, 13.4459f, 22.6986f, 13.4459f)
                    curveTo(23.0469f, 13.4459f, 23.4031f, 13.3977f, 23.7566f, 13.302f)
                    curveTo(24.1093f, 13.2063f, 24.4439f, 13.0717f, 24.7502f, 12.8999f)
                    curveTo(25.0573f, 12.7289f, 25.3394f, 12.5223f, 25.5906f, 12.2853f)
                    curveTo(25.7523f, 12.1329f, 25.8925f, 11.9712f, 26.0147f, 11.8028f)
                    lineTo(26.0164f, 11.8044f)
                    curveTo(26.2598f, 11.4853f, 26.3528f, 11.2119f, 26.3768f, 11.1314f)
                    horizontalLineTo(25.3669f)
                    lineTo(25.3265f, 11.2076f)
                    verticalLineTo(11.2059f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(44.3579f, 11.1297f)
                    lineTo(44.3502f, 11.1424f)
                    curveTo(44.2151f, 11.3701f, 44.0474f, 11.5784f, 43.8495f, 11.763f)
                    curveTo(43.6517f, 11.9484f, 43.4332f, 12.1075f, 43.2018f, 12.2362f)
                    curveTo(42.9704f, 12.3649f, 42.7227f, 12.4656f, 42.4655f, 12.5376f)
                    curveTo(41.8143f, 12.7196f, 41.1958f, 12.6628f, 40.7364f, 12.441f)
                    curveTo(40.456f, 12.3056f, 40.2203f, 12.121f, 40.0353f, 11.8925f)
                    curveTo(39.8495f, 11.6631f, 39.7093f, 11.3888f, 39.6181f, 11.0772f)
                    curveTo(39.5261f, 10.7632f, 39.4796f, 10.4245f, 39.4796f, 10.0698f)
                    curveTo(39.4796f, 9.9928f, 39.4822f, 9.9107f, 39.4874f, 9.8243f)
                    curveTo(39.49f, 9.7735f, 39.4934f, 9.7253f, 39.4986f, 9.6779f)
                    curveTo(39.8943f, 9.7041f, 40.2581f, 9.7219f, 40.5816f, 9.7304f)
                    curveTo(40.9343f, 9.7405f, 41.2766f, 9.7456f, 41.6001f, 9.7456f)
                    curveTo(44.3837f, 9.7456f, 45.7962f, 8.9634f, 45.7962f, 7.4218f)
                    curveTo(45.7962f, 7.0713f, 45.7239f, 6.7564f, 45.5812f, 6.4855f)
                    curveTo(45.4392f, 6.2171f, 45.2474f, 5.9886f, 45.0108f, 5.8074f)
                    curveTo(44.776f, 5.6279f, 44.5042f, 5.4899f, 44.2031f, 5.3977f)
                    curveTo(43.9037f, 5.3062f, 43.5889f, 5.2597f, 43.2698f, 5.2597f)
                    curveTo(42.5953f, 5.2597f, 41.9596f, 5.3977f, 41.3816f, 5.6694f)
                    curveTo(40.8061f, 5.9395f, 40.2977f, 6.3035f, 39.8693f, 6.7496f)
                    curveTo(39.4409f, 7.1958f, 39.1029f, 7.7147f, 38.8637f, 8.2912f)
                    curveTo(38.6237f, 8.8686f, 38.5024f, 9.4713f, 38.5024f, 10.0842f)
                    curveTo(38.5024f, 10.5371f, 38.5704f, 10.9714f, 38.7029f, 11.3744f)
                    curveTo(38.8371f, 11.7799f, 39.0384f, 12.1405f, 39.3016f, 12.4453f)
                    curveTo(39.5657f, 12.7517f, 39.8908f, 12.9972f, 40.2685f, 13.175f)
                    curveTo(40.647f, 13.3536f, 41.0848f, 13.4442f, 41.57f, 13.4442f)
                    curveTo(41.9287f, 13.4442f, 42.2874f, 13.396f, 42.6358f, 13.2995f)
                    curveTo(42.9824f, 13.2046f, 43.3171f, 13.0667f, 43.6302f, 12.8897f)
                    curveTo(43.9407f, 12.7145f, 44.2306f, 12.5028f, 44.4921f, 12.2616f)
                    curveTo(44.702f, 12.0677f, 44.8973f, 11.862f, 45.0581f, 11.6275f)
                    lineTo(45.1381f, 11.5064f)
                    curveTo(45.1502f, 11.4878f, 45.1631f, 11.47f, 45.1743f, 11.4514f)
                    lineTo(45.1898f, 11.4269f)
                    lineTo(45.3644f, 11.1289f)
                    horizontalLineTo(44.3588f)
                    lineTo(44.3579f, 11.1297f)
                    close()
                    moveTo(40.111f, 7.934f)
                    curveTo(40.3252f, 7.5792f, 40.5867f, 7.2609f, 40.8887f, 6.9883f)
                    curveTo(41.1889f, 6.7166f, 41.5364f, 6.4931f, 41.9201f, 6.3263f)
                    curveTo(42.3012f, 6.1604f, 42.7244f, 6.0766f, 43.1777f, 6.0766f)
                    curveTo(43.3885f, 6.0766f, 43.6018f, 6.1028f, 43.8134f, 6.1553f)
                    curveTo(44.0199f, 6.2061f, 44.2022f, 6.2874f, 44.3553f, 6.3974f)
                    curveTo(44.5076f, 6.5058f, 44.6341f, 6.6472f, 44.7313f, 6.8165f)
                    curveTo(44.8267f, 6.9824f, 44.8758f, 7.1814f, 44.8758f, 7.4091f)
                    curveTo(44.8758f, 7.5225f, 44.862f, 7.6317f, 44.8353f, 7.7359f)
                    curveTo(44.8087f, 7.84f, 44.7691f, 7.939f, 44.7158f, 8.0313f)
                    curveTo(44.6108f, 8.2159f, 44.4345f, 8.3792f, 44.191f, 8.5172f)
                    curveTo(43.9407f, 8.6595f, 43.6052f, 8.772f, 43.1932f, 8.8533f)
                    curveTo(42.776f, 8.9354f, 42.2547f, 8.9769f, 41.6422f, 8.9769f)
                    curveTo(41.3214f, 8.9769f, 40.9919f, 8.9718f, 40.6607f, 8.9617f)
                    curveTo(40.3674f, 8.9532f, 40.0319f, 8.9363f, 39.6612f, 8.9117f)
                    curveTo(39.7678f, 8.579f, 39.9175f, 8.2514f, 40.1093f, 7.9348f)
                    lineTo(40.111f, 7.934f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(44.838f, 7.7333f)
                    curveTo(44.8113f, 7.8375f, 44.7717f, 7.9365f, 44.7184f, 8.0288f)
                    curveTo(44.7717f, 7.9357f, 44.8113f, 7.8375f, 44.838f, 7.7333f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF222222)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                ) {
                    moveTo(34.7304f, 6.6557f)
                    curveTo(34.7304f, 6.1452f, 34.5704f, 5.7363f, 34.2538f, 5.4408f)
                    curveTo(34.0878f, 5.2859f, 33.9458f, 5.1793f, 33.7618f, 5.1124f)
                    horizontalLineTo(33.7643f)
                    curveTo(33.748f, 5.1065f, 33.7317f, 5.1022f, 33.7162f, 5.0971f)
                    curveTo(33.6912f, 5.0895f, 33.6663f, 5.0811f, 33.6396f, 5.0743f)
                    curveTo(32.9205f, 4.8838f, 32.228f, 5.1598f, 32.228f, 5.1598f)
                    horizontalLineTo(32.2332f)
                    curveTo(31.9089f, 5.2715f, 31.6018f, 5.4654f, 31.2052f, 5.7236f)
                    curveTo(30.6641f, 6.0749f, 29.8882f, 6.7107f, 29.514f, 7.2305f)
                    lineTo(30.6039f, 2.2866f)
                    horizontalLineTo(29.6362f)
                    lineTo(27.2603f, 13.2978f)
                    horizontalLineTo(28.228f)
                    lineTo(29.1519f, 9.0243f)
                    curveTo(29.1656f, 8.9761f, 29.1828f, 8.9346f, 29.1992f, 8.8914f)
                    curveTo(29.2018f, 8.8847f, 29.2043f, 8.877f, 29.2061f, 8.8711f)
                    curveTo(29.219f, 8.8398f, 29.2336f, 8.8068f, 29.2508f, 8.7738f)
                    curveTo(29.2585f, 8.7577f, 29.2671f, 8.7416f, 29.2757f, 8.7247f)
                    curveTo(29.2938f, 8.6908f, 29.3144f, 8.6561f, 29.336f, 8.6214f)
                    curveTo(29.8056f, 7.8646f, 31.0237f, 6.8275f, 31.0237f, 6.8275f)
                    curveTo(32.5308f, 5.5382f, 33.3557f, 5.8616f, 33.5028f, 5.9378f)
                    curveTo(33.5364f, 5.9581f, 33.5691f, 5.9793f, 33.5966f, 6.0021f)
                    curveTo(33.7833f, 6.1553f, 33.9235f, 6.4694f, 33.9235f, 6.7852f)
                    curveTo(33.9235f, 7.2821f, 33.6611f, 7.7435f, 33.1433f, 8.1549f)
                    curveTo(32.6125f, 8.5765f, 31.6843f, 9.0421f, 30.6529f, 9.2546f)
                    lineTo(30.5205f, 9.2817f)
                    lineTo(32.345f, 13.2978f)
                    horizontalLineTo(33.3274f)
                    lineTo(31.6276f, 9.7481f)
                    curveTo(32.5437f, 9.4891f, 33.3962f, 8.9541f, 33.9097f, 8.4631f)
                    curveTo(34.4551f, 7.9416f, 34.7312f, 7.3338f, 34.7312f, 6.6565f)
                    lineTo(34.7304f, 6.6557f)
                    close()
                }
            }
        }
        .build()
        return _icDuckieTextLogo!!
    }

private var _icDuckieTextLogo: ImageVector? = null
