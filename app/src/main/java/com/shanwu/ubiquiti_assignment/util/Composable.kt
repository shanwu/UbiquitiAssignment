package com.shanwu.ubiquiti_assignment.util
import com.shanwu.ubiquiti_assignment.R
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanwu.ubiquiti_assignment.data.SiteAirQuality

/**
 * Common UI elements
 */

@Composable
fun ListViewItems(
    modifier: Modifier = Modifier,
    list: List<SiteAirQuality>
) {
    LazyColumn(modifier = modifier.padding(top = 15.dp)) {
        items(list) {
            if (stringResource(id = R.string.status_good) == it.status) {
                ListItemGood(modifier = modifier, it)
            } else {
                ListItemCommon(modifier = modifier, it) { context, airInfo ->
                    Toast.makeText(
                        context,
                        "$airInfo",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

@Composable
fun ListItemGood(modifier: Modifier = Modifier, airQuality: SiteAirQuality) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(false, onClick = {})
    ) {
        Row(
            modifier = modifier.getItemModifier(),
            horizontalArrangement = Arrangement.Start
        ) {
            Row(modifier = modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${airQuality.siteId}",
                    modifier = modifier.width(50.dp),
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Column(modifier = modifier.fillMaxWidth()) {
                    BoldTextOneLine(airQuality.siteName)
                    TextOneLine(text = airQuality.county, textAlign = TextAlign.Start)
                }
            }
            Row(modifier = modifier.weight(1f)) {
                Column(
                    modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp), horizontalAlignment = Alignment.End
                ) {
                    TextOneLine(text = airQuality.pmLevel.toString(), textAlign = TextAlign.Start)
                    TextOneLine(
                        text = stringResource(id = R.string.msg_status_good),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
        ItemDivider()
    }
}


@Composable
fun ListItemCommon(
    modifier: Modifier = Modifier,
    airQuality: SiteAirQuality,
    onItemClick: (Context, SiteAirQuality) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(context, airQuality) },
    ) {
        Row(
            modifier = modifier.getItemModifier(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextOneLine(
                modifier = modifier.width(50.dp),
                text = airQuality.siteId.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )

            Column(modifier.weight(1f)) {
                BoldTextOneLine(text = airQuality.siteName)
                TextOneLine(text = airQuality.county, textAlign = TextAlign.Start)
            }

            Column(
                modifier
                    .weight(1f)
                    .padding(end = 10.dp), horizontalAlignment = Alignment.End
            ) {
                TextOneLine(text = airQuality.pmLevel.toString())
                TextOneLine(text = airQuality.status)
            }

            Icon(Icons.Filled.KeyboardArrowRight, null, tint = Color.DarkGray)
        }
        ItemDivider()
    }

}

@Composable
fun BoldTextOneLine(text: String) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                append(text)
            }
        }, maxLines = 1, overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun TextOneLine(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = fontSize,
        textAlign = textAlign
    )
}

@Composable
fun ItemDivider() = Divider(modifier = Modifier.padding(start = 15.dp, end = 15.dp))

fun Modifier.getItemModifier(): Modifier = this
    .fillMaxWidth()
    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)