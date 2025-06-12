package org.example.hit.heal.core.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Snowshoeing
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.Timer
import dmt_proms.ui.core.generated.resources.activities
import dmt_proms.ui.core.generated.resources.app_name
import dmt_proms.ui.core.generated.resources.clock_test
import dmt_proms.ui.core.generated.resources.dont_forget
import dmt_proms.ui.core.generated.resources.email
import dmt_proms.ui.core.generated.resources.empty_pass
import dmt_proms.ui.core.generated.resources.fill_fields
import dmt_proms.ui.core.generated.resources.graphs
import dmt_proms.ui.core.generated.resources.home
import dmt_proms.ui.core.generated.resources.how_do_you_feel
import dmt_proms.ui.core.generated.resources.invalid_email
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.login_success
import dmt_proms.ui.core.generated.resources.logout
import dmt_proms.ui.core.generated.resources.measurements
import dmt_proms.ui.core.generated.resources.medications
import dmt_proms.ui.core.generated.resources.messages
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.password
import dmt_proms.ui.core.generated.resources.previous
import dmt_proms.ui.core.generated.resources.send
import dmt_proms.ui.core.generated.resources.sent_successfully
import dmt_proms.ui.core.generated.resources.take_pills
import dmt_proms.ui.core.generated.resources.unexpected_error
import dmt_proms.ui.core.generated.resources.welcome
import dmt_proms.ui.core.generated.resources.Res.string as rs

/**
 * Resources used in the application.
 * These resources include icons, strings, and other assets
 * This object made to enable the resources to be used in different modules.
 */

object Resources {

    object Icon {
        val delete = Icons.Default.Delete
        val draw = Icons.Default.Create
        val email = Icons.Filled.Email
        val lock = Icons.Outlined.Lock
        val invisible = Icons.Outlined.VisibilityOff
        val visible = Icons.Outlined.Visibility
        val logout = Icons.AutoMirrored.Filled.Logout
        val graph = Icons.Outlined.AutoGraph
        val meds = Icons.Outlined.Medication
        val measurements = Icons.Outlined.MonitorHeart
        val activities = Icons.Outlined.Snowshoeing
        val clock = Icons.Rounded.Timer
    }

    object String {
        val send = rs.send
        val measurements = rs.measurements
        val password = rs.password
        val email = rs.email
        val clockTest = rs.clock_test
        val activities = rs.activities
        val graphs = rs.graphs
        val medications = rs.medications
        val sentSuccessfully = rs.sent_successfully
        val welcome = rs.welcome
        val empty_password = rs.empty_pass
        val invalid_email = rs.invalid_email
        val fill_fields = rs.fill_fields
        val messages = rs.messages
        val take_pills = rs.take_pills
        val logout = rs.logout
        val home = rs.home
        val dont_forget = rs.dont_forget
        val appName = rs.app_name
        val login = rs.login
        val previous = rs.previous
        val next = rs.next
        val howDoYouFeel = rs.how_do_you_feel
        val loginSuccess = rs.login_success
        val unexpectedError = rs.unexpected_error
    }

}