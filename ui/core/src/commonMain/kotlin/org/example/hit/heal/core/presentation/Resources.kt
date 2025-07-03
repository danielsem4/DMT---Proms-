package org.example.hit.heal.core.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.activities
import dmt_proms.ui.core.generated.resources.app_name
import dmt_proms.ui.core.generated.resources.arrow_down
import dmt_proms.ui.core.generated.resources.asterisk
import dmt_proms.ui.core.generated.resources.balloon
import dmt_proms.ui.core.generated.resources.bin
import dmt_proms.ui.core.generated.resources.chat
import dmt_proms.ui.core.generated.resources.check
import dmt_proms.ui.core.generated.resources.circle_outlined
import dmt_proms.ui.core.generated.resources.clock
import dmt_proms.ui.core.generated.resources.clock_test
import dmt_proms.ui.core.generated.resources.document_share
import dmt_proms.ui.core.generated.resources.dont_forget
import dmt_proms.ui.core.generated.resources.elipsa_outlined
import dmt_proms.ui.core.generated.resources.email
import dmt_proms.ui.core.generated.resources.empty_pass
import dmt_proms.ui.core.generated.resources.evaluation
import dmt_proms.ui.core.generated.resources.exercise
import dmt_proms.ui.core.generated.resources.eye
import dmt_proms.ui.core.generated.resources.file_upload
import dmt_proms.ui.core.generated.resources.fill_fields
import dmt_proms.ui.core.generated.resources.filled_circle
import dmt_proms.ui.core.generated.resources.football
import dmt_proms.ui.core.generated.resources.graphs
import dmt_proms.ui.core.generated.resources.hash
import dmt_proms.ui.core.generated.resources.hexagon
import dmt_proms.ui.core.generated.resources.hide_eye
import dmt_proms.ui.core.generated.resources.hitbear
import dmt_proms.ui.core.generated.resources.hitber
import dmt_proms.ui.core.generated.resources.home
import dmt_proms.ui.core.generated.resources.horizontal_stroke
import dmt_proms.ui.core.generated.resources.how_do_you_feel
import dmt_proms.ui.core.generated.resources.invalid_email
import dmt_proms.ui.core.generated.resources.jogging
import dmt_proms.ui.core.generated.resources.local_unknown
import dmt_proms.ui.core.generated.resources.lock
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.login_success
import dmt_proms.ui.core.generated.resources.logout
import dmt_proms.ui.core.generated.resources.measurements
import dmt_proms.ui.core.generated.resources.med_icon
import dmt_proms.ui.core.generated.resources.medications
import dmt_proms.ui.core.generated.resources.memory
import dmt_proms.ui.core.generated.resources.messages
import dmt_proms.ui.core.generated.resources.napkin
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.no_internet
import dmt_proms.ui.core.generated.resources.open_bin
import dmt_proms.ui.core.generated.resources.password
import dmt_proms.ui.core.generated.resources.pencil
import dmt_proms.ui.core.generated.resources.pentagon
import dmt_proms.ui.core.generated.resources.pill
import dmt_proms.ui.core.generated.resources.plus
import dmt_proms.ui.core.generated.resources.previous
import dmt_proms.ui.core.generated.resources.rectangle
import dmt_proms.ui.core.generated.resources.remote_unknown
import dmt_proms.ui.core.generated.resources.request_timeout
import dmt_proms.ui.core.generated.resources.rhombus
import dmt_proms.ui.core.generated.resources.ruler
import dmt_proms.ui.core.generated.resources.send
import dmt_proms.ui.core.generated.resources.sent_successfully
import dmt_proms.ui.core.generated.resources.serialization_error
import dmt_proms.ui.core.generated.resources.server_error
import dmt_proms.ui.core.generated.resources.spin_360
import dmt_proms.ui.core.generated.resources.stand_from_chair
import dmt_proms.ui.core.generated.resources.star_outlined
import dmt_proms.ui.core.generated.resources.take_pills
import dmt_proms.ui.core.generated.resources.too_many_requests
import dmt_proms.ui.core.generated.resources.trapeze
import dmt_proms.ui.core.generated.resources.triangle
import dmt_proms.ui.core.generated.resources.triangle_90
import dmt_proms.ui.core.generated.resources.unexpected_error
import dmt_proms.ui.core.generated.resources.vertical_stroke
import dmt_proms.ui.core.generated.resources.warning
import dmt_proms.ui.core.generated.resources.welcome
import dmt_proms.ui.core.generated.resources.x
import dmt_proms.ui.core.generated.resources.yoga
import dmt_proms.ui.core.generated.resources.Res.string as rs

/**
 * Resources used in the application.
 * These resources include icons, strings, and other assets
 * This object made to enable the resources to be used in different modules.
 */

object Resources {

    object Icon {

        val emailIcon = Res.drawable.email
        val clockIcon = Res.drawable.clock
        val spinIcon = Res.drawable.spin_360
        val balloonIcon = Res.drawable.balloon
        val closedEyeIcon = Res.drawable.hide_eye
        val openEyeIcon = Res.drawable.eye
        val logoutIcon = Res.drawable.logout
        val chatIcon = Res.drawable.chat
        val fileUploadIcon = Res.drawable.file_upload
        val pencilIcon = Res.drawable.pencil
        val exerciseIcon = Res.drawable.exercise
        val yogaIcon = Res.drawable.yoga
        val lockIcon = Res.drawable.lock
        val ballIcon = Res.drawable.football
        val standFromChairIcon = Res.drawable.stand_from_chair
        val pillIcon = Res.drawable.pill
        val warningIcon = Res.drawable.warning
        val joggingIcon = Res.drawable.jogging

//        val lemonIcon = Res.drawable.lemon
        val napkinIcon = Res.drawable.napkin
//        val tableIcon = Res.drawable.table
        val rulerIcon = Res.drawable.ruler

        val plusIcon = Res.drawable.plus
        val arrowDownIcon = Res.drawable.arrow_down
        val asteriskIcon = Res.drawable.asterisk
        val binIcon = Res.drawable.bin
        val openBinIcon = Res.drawable.open_bin
        val checkIcon = Res.drawable.check
        val outlineCircleIcon = Res.drawable.circle_outlined
        val filledCircleIcon = Res.drawable.filled_circle
        val hitbearModuleIcon = Res.drawable.hitbear
        val memoryModuleIcon = Res.drawable.memory
        val elipsaIcon = Res.drawable.elipsa_outlined
        val hashMarkIcon = Res.drawable.hash
        val hexagonIcon = Res.drawable.hexagon
        val pentagonIcon = Res.drawable.pentagon
        val triangleIcon = Res.drawable.triangle
        val triangle90Icon = Res.drawable.triangle_90
        val horizontalStrokeIcon = Res.drawable.horizontal_stroke
        val verticalStrokeIcon = Res.drawable.vertical_stroke
        val medIcon = Res.drawable.med_icon
        val evaluationLogo = Res.drawable.evaluation
        val rectangleIcon = Res.drawable.rectangle
        val rhombusIcon = Res.drawable.rhombus
        val starOutlineIcon = Res.drawable.star_outlined
        val trapezeIcon = Res.drawable.trapeze
        val xIcon = Res.drawable.x
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
        val unexpectedError = rs.unexpected_error
    }

}