package org.example.hit.heal.core.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Share
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
import dmt_proms.ui.core.generated.resources.chat
import dmt_proms.ui.core.generated.resources.clock_test
import dmt_proms.ui.core.generated.resources.disk_full
import dmt_proms.ui.core.generated.resources.document_share
import dmt_proms.ui.core.generated.resources.dont_forget
import dmt_proms.ui.core.generated.resources.email
import dmt_proms.ui.core.generated.resources.empty_pass
import dmt_proms.ui.core.generated.resources.fill_fields
import dmt_proms.ui.core.generated.resources.graphs
import dmt_proms.ui.core.generated.resources.hitber
import dmt_proms.ui.core.generated.resources.home
import dmt_proms.ui.core.generated.resources.how_do_you_feel
import dmt_proms.ui.core.generated.resources.invalid_email
import dmt_proms.ui.core.generated.resources.local_unknown
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.login_success
import dmt_proms.ui.core.generated.resources.logout
import dmt_proms.ui.core.generated.resources.measurements
import dmt_proms.ui.core.generated.resources.medications
import dmt_proms.ui.core.generated.resources.memory
import dmt_proms.ui.core.generated.resources.messages
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.no_internet
import dmt_proms.ui.core.generated.resources.password
import dmt_proms.ui.core.generated.resources.previous
import dmt_proms.ui.core.generated.resources.remote_unknown
import dmt_proms.ui.core.generated.resources.request_timeout
import dmt_proms.ui.core.generated.resources.send
import dmt_proms.ui.core.generated.resources.sent_successfully
import dmt_proms.ui.core.generated.resources.serialization_error
import dmt_proms.ui.core.generated.resources.server_error
import dmt_proms.ui.core.generated.resources.take_pills
import dmt_proms.ui.core.generated.resources.too_many_requests
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
        val document_share = Icons.Default.Share
        val chat = Icons.Default.ChatBubble
        val memory = Icons.Default.Memory
        val hitber = Icons.Default.Quiz
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
        val document_share = rs.document_share
        val chat = rs.chat
        val memory = rs.memory
        val hitber = rs.hitber
        val email = rs.email
        val clockTest = rs.clock_test
        val activities = rs.activities
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
        val login = rs.login
        val next = rs.next
        val loginSuccess = rs.login_success
        val previous = rs.previous
        val graphs = rs.graphs
        val appName = rs.app_name
        val howDoYouFeel = rs.how_do_you_feel

        // Error messages
        val noInternet = rs.no_internet
        val diskFull = rs.disk_full
        val localUnknown = rs.local_unknown
        val requestTimeout = rs.request_timeout
        val tooManyRequests = rs.too_many_requests
        val serverError = rs.server_error
        val serializationError = rs.serialization_error
        val remoteUnknown = rs.remote_unknown
        val unexpectedError = rs.unexpected_error

    }

}