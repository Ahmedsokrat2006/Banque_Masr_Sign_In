package com.sokrat.banquemasrsignin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sokrat.banquemasrsignin.ui.theme.BanqueMasrSignInTheme
import com.sokrat.banquemasrsignin.ui.theme.Red
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import com.sokrat.banquemasrsignin.ui.theme.Gray
import com.sokrat.banquemasrsignin.ui.theme.LightRed
import com.sokrat.banquemasrsignin.ui.theme.White
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanqueMasrSignInTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BanqueMasrSignIn(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

fun Context.updateLocale(locale: Locale): Context {
    val config = resources.configuration
    Locale.setDefault(locale)
    config.setLocale(locale)
    return createConfigurationContext(config)
}

@Composable
fun BanqueMasrSignIn(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var currentLocale by remember { mutableStateOf(Locale("en")) }
    var localizedContext by remember { mutableStateOf(context.updateLocale(currentLocale)) }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalLayoutDirection provides
                if (currentLocale.language == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.banque_misr_svg),
                    contentDescription = "Banque Misr Logo",
                    modifier = Modifier
                        .size(106.dp)
                )
                Text(
                    text = stringResource(R.string.AR_to_EN),
                    fontSize = 16.sp,
                    color = Red,
                    fontFamily = FontFamily(Font(R.font.cairo_extrabold)),
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                currentLocale =
                                    if (currentLocale.language == "ar") Locale("en")
                                    else Locale("ar")
                                localizedContext = context.updateLocale(currentLocale)
                            }
                        )
                )
            }

            var userNameField by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = userNameField,
                onValueChange = { userNameField = it },
                label = {
                    Text(
                        text = stringResource(R.string.username),
                        fontWeight = FontWeight.SemiBold,
                        color = Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Gray,
                    unfocusedBorderColor = Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            var passwordText by rememberSaveable { mutableStateOf("") }
            var showPassword by rememberSaveable { mutableStateOf(false) }
            OutlinedTextField(
                value = passwordText, // Your state for the password input
                onValueChange = { passwordText = it },
                label = {
                    Text(
                        text = stringResource(R.string.password),
                        fontWeight = FontWeight.SemiBold,
                        color = Gray
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation =
                    if (showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showPassword = !showPassword
                        }
                    ) {
                        Icon(
                            imageVector =
                                if (showPassword)
                                    Icons.Filled.Visibility
                                else
                                    Icons.Filled.VisibilityOff,
                            contentDescription =
                                if (showPassword)
                                    "Hide password"
                                else
                                    "Show password"
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Gray,
                    unfocusedBorderColor = Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            Text(
                text = stringResource(R.string.forgot_username_password),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(top = 8.dp)
            )

            Button(
                onClick = {},
                shape = RoundedCornerShape(20),
                enabled = userNameField.isNotBlank() && passwordText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = LightRed,
                    containerColor = Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 16.dp)

            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 18.sp,
                    color = White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 12.sp
                        )
                    ) {
                        append(stringResource(R.string.need_help))
                    }
                    append(" ")
                    withLink(
                        LinkAnnotation.Url(
                            url = "https://www.banquemisr.com/",
                            styles =
                                TextLinkStyles(
                                    SpanStyle(
                                        color = Red,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        textDecoration = TextDecoration.Underline,
                                        fontSize = 12.sp
                                    )
                                )
                        )
                    ) {
                        append(stringResource(R.string.contact_us))
                    }

                },
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .padding(top = 60.dp)
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.our_products),
                        contentDescription = "Our products logo",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = stringResource(R.string.our_products),
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.exchange_rate),
                        contentDescription = "Exchange rate logo",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = stringResource(R.string.exchange_rate),
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center

                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.security_tips),
                        contentDescription = "Security tips logo",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = stringResource(R.string.security_tips),
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.nearest_branch_or_atm),
                        contentDescription = "Nearest branch or ATM logo",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = stringResource(R.string.nearest_branch_or_atm),
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center

                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BanqueMasrSignInPreview() {
    BanqueMasrSignInTheme {
        BanqueMasrSignIn()
    }
}