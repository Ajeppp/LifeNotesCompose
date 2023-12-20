package com.example.lifenotescompose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifenotescompose.R
import com.example.lifenotescompose.app.ButtonComponent
import com.example.lifenotescompose.app.HeaderTextComponent
import com.example.lifenotescompose.app.NormalTextComponent
import com.example.lifenotescompose.navigation.AppRouter
import com.example.lifenotescompose.navigation.Screen
import com.example.lifenotescompose.navigation.SystemBackHandler
import com.example.lifenotescompose.ui.theme.PurpleGrey80


@Composable
fun TermsScreen() {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = PurpleGrey80)
        .padding(16.dp)

    ){
        Column(modifier = Modifier.padding(16.dp)){
            HeaderTextComponent(value = stringResource(id = R.string.terms_header))
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .height(700.dp)
                    .background(color = Color.White)
                    .padding(10.dp)
            ){
                items(listOf(
                    "1. Acceptance of Terms By accessing this app, you are agreeing to be bound by " +
                    "these app Terms and Conditions of Use, all applicable laws and regulations," +
                    " and agree that you are responsible for compliance with any applicable local laws. If you do" +
                    "not agree with any of these terms, you are prohibited from using or accessing this site. The" +
                    " materials contained in this web site are protected by applicable copyright and trade mark law.",
                    "2. Disclaimer of Warranties The materials on LifeNotes app are provided" +
                    " \"as is\". LifeNotes makes no warranties, expressed or implied, and hereby disclaims and negates" +
                    "all other warranties, including without limitation, implied warranties or conditions of" +
                    "merchantability, fitness for a particular purpose, or non-infringement of intellectual property" +
                    "or other violation of rights.",
                    "3. Limitation of Liability In no event shall LifeNotes or its suppliers" +
                    "be liable for any damages (including, without limitation, damages for loss of data or profit, or" +
                    "due to business interruption) arising out of the use or inability to use the materials on" +
                    "LifeNotes app, even if LifeNotes or a LifeNotes authorized representative has been notified" +
                    "orally or in writing of the possibility of such damage. Because some jurisdictions do not allow" +
                    "limitations on implied warranties, or limitations of liability for consequential or incidental" +
                    "damages, these limitations may not apply to you.",
                    "4. Revisions and Errata The materials appearing on LifeNotes app could" +
                    "include technical, typographical, or photographic errors. LifeNotes does not warrant that" +
                    "any of the materials on its app are accurate, complete, or current. LifeNotes may make" +
                    "changes to the materials contained on its app at any time without notice. LifeNotes" +
                    "does not, however, make any commitment to update the materials.",
                    "5. Site Terms of Use Modifications LifeNotes may revise these terms of" +
                    "use for its app at any time without notice. By using this app you are agreeing to be bound by" +
                    "the then current version of these Terms and Conditions of Use.",
                    "6. Governing Law Any claim relating to LifeNotes app shall be governed by" +
                    "the laws of the State of Indonesia without regard to its conflict of law provisions.",
                    "General Terms and Conditions applicable to Use of a Web Site."
                )){line ->
                    Text(
                        text = line,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Justify)}
            }

            Spacer(modifier = Modifier.padding(8.dp))
            ButtonComponent(value = "submit", onButtonClicked = {
                AppRouter.navigateTo(Screen.SignUp)
            }, isEnabled = true)
        }
    }
    SystemBackHandler {
        AppRouter.navigateTo(Screen.SignUp)
    }
}

@Preview
@Composable
fun PreviewTermsScreen() {
    TermsScreen()
}