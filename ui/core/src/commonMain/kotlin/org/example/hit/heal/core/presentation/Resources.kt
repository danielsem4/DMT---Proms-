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
import dmt_proms.ui.core.generated.resources.day_monday
import dmt_proms.ui.core.generated.resources.day_sunday
import dmt_proms.ui.core.generated.resources.day_thursday
import dmt_proms.ui.core.generated.resources.day_tuesday
import dmt_proms.ui.core.generated.resources.day_wednesday
import dmt_proms.ui.core.generated.resources.document_share
import dmt_proms.ui.core.generated.resources.dont_forget
import dmt_proms.ui.core.generated.resources.email
import dmt_proms.ui.core.generated.resources.empty_pass
import dmt_proms.ui.core.generated.resources.fill_fields
import dmt_proms.ui.core.generated.resources.graphs
import dmt_proms.ui.core.generated.resources.hitber
import dmt_proms.ui.core.generated.resources.home
import dmt_proms.ui.core.generated.resources.hour_09_00
import dmt_proms.ui.core.generated.resources.hour_10_00
import dmt_proms.ui.core.generated.resources.hour_11_00
import dmt_proms.ui.core.generated.resources.hour_12_00
import dmt_proms.ui.core.generated.resources.hour_13_00
import dmt_proms.ui.core.generated.resources.hour_14_00
import dmt_proms.ui.core.generated.resources.hour_15_00
import dmt_proms.ui.core.generated.resources.hour_16_00
import dmt_proms.ui.core.generated.resources.book_circle
import dmt_proms.ui.core.generated.resources.dumbbell_circle
import dmt_proms.ui.core.generated.resources.move_circle
import dmt_proms.ui.core.generated.resources.lecturer_circle
import dmt_proms.ui.core.generated.resources.coffee_circle
import dmt_proms.ui.core.generated.resources.stethoscope_circle
import dmt_proms.ui.core.generated.resources.book_circle_text
import dmt_proms.ui.core.generated.resources.dumbbell_circle_text
import dmt_proms.ui.core.generated.resources.move_circle_text
import dmt_proms.ui.core.generated.resources.lecturer_circle_text
import dmt_proms.ui.core.generated.resources.coffee_circle_text
import dmt_proms.ui.core.generated.resources.stethoscope_circle_text
import dmt_proms.ui.core.generated.resources.build_schedule_continuation
import dmt_proms.ui.core.generated.resources.instruction_text_agenda_continuation
import dmt_proms.ui.core.generated.resources.continue_button
import dmt_proms.ui.core.generated.resources.build_schedule
import dmt_proms.ui.core.generated.resources.place_activities
import dmt_proms.ui.core.generated.resources.room_title
import dmt_proms.ui.core.generated.resources.incoming_call_title
import dmt_proms.ui.core.generated.resources.incoming_call_text
import dmt_proms.ui.core.generated.resources.call_from
import dmt_proms.ui.core.generated.resources.phone_number
import dmt_proms.ui.core.generated.resources.thank_you_title
import dmt_proms.ui.core.generated.resources.thank_you_description
import dmt_proms.ui.core.generated.resources.memory_instruction
import dmt_proms.ui.core.generated.resources.start_button
import dmt_proms.ui.core.generated.resources.how_do_you_feel
import dmt_proms.ui.core.generated.resources.invalid_email
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.login_success
import dmt_proms.ui.core.generated.resources.logout
import dmt_proms.ui.core.generated.resources.measurements
import dmt_proms.ui.core.generated.resources.medications
import dmt_proms.ui.core.generated.resources.memory
import dmt_proms.ui.core.generated.resources.messages
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.password
import dmt_proms.ui.core.generated.resources.previous
import dmt_proms.ui.core.generated.resources.remember_all
import dmt_proms.ui.core.generated.resources.send
import dmt_proms.ui.core.generated.resources.sent_successfully
import dmt_proms.ui.core.generated.resources.take_pills
import dmt_proms.ui.core.generated.resources.talk_to_family
import dmt_proms.ui.core.generated.resources.unexpected_error
import dmt_proms.ui.core.generated.resources.use_diary
import dmt_proms.ui.core.generated.resources.welcome
import dmt_proms.ui.core.generated.resources.what_will_you_do
import dmt_proms.ui.core.generated.resources.good_luck
import dmt_proms.ui.core.generated.resources.press_start_to_begin
import dmt_proms.ui.core.generated.resources.memory_title
import dmt_proms.ui.core.generated.resources.task_duration
import dmt_proms.ui.core.generated.resources.task_continuity
import dmt_proms.ui.core.generated.resources.read_instructions
import dmt_proms.ui.core.generated.resources.complete_all_tasks
import dmt_proms.ui.core.generated.resources.listen_instruction_tip
import dmt_proms.ui.core.generated.resources.quiet_room_tip
import dmt_proms.ui.core.generated.resources.instructions
import dmt_proms.ui.core.generated.resources.bedroom
import dmt_proms.ui.core.generated.resources.living_room
import dmt_proms.ui.core.generated.resources.kitchen
import dmt_proms.ui.core.generated.resources.drag_and_place_instruction
import dmt_proms.ui.core.generated.resources.delete
import dmt_proms.ui.core.generated.resources.rate_your_feelings
import dmt_proms.ui.core.generated.resources.rate_expected_success
import dmt_proms.ui.core.generated.resources.rate




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
        val unexpectedError = rs.unexpected_error



        //MemoryTestModule
        val day_sunday = rs.day_sunday
        val day_monday = rs.day_monday
        val day_tuesday = rs.day_tuesday
        val day_wednesday = rs.day_wednesday
        val day_thursday = rs.day_thursday

        val hour_09_00 = rs.hour_09_00
        val hour_10_00 = rs.hour_10_00
        val hour_11_00 = rs.hour_11_00
        val hour_12_00 = rs.hour_12_00
        val hour_13_00 = rs.hour_13_00
        val hour_14_00 = rs.hour_14_00
        val hour_15_00 = rs.hour_15_00
        val hour_16_00 = rs.hour_16_00

        val book_circle = rs.book_circle
        val dumbbell_circle = rs.dumbbell_circle
        val move_circle = rs.move_circle
        val lecturer_circle = rs.lecturer_circle
        val coffee_circle = rs.coffee_circle
        val stethoscope_circle = rs.stethoscope_circle

        val book_circle_text = rs.book_circle_text
        val dumbbell_circle_text = rs.dumbbell_circle_text
        val move_circle_text = rs.move_circle_text
        val lecturer_circle_text = rs.lecturer_circle_text
        val coffee_circle_text = rs.coffee_circle_text
        val stethoscope_circle_text = rs.stethoscope_circle_text

        val build_schedule_continuation = rs.build_schedule_continuation
        val instruction_text_agenda_continuation = rs.instruction_text_agenda_continuation
        val continue_button = rs.continue_button
        val build_schedule = rs.build_schedule
        val place_activities = rs.place_activities
        val memory_instruction = rs.memory_instruction
        val start_button =rs.start_button
        val whatWillYouDo = rs.what_will_you_do

        val remember_all = rs.remember_all
        val talk_to_family = rs.talk_to_family
        val use_diary = rs.use_diary

        val room_title = rs.room_title
        val incoming_call_title = rs.incoming_call_title
        val incoming_call_text = rs.incoming_call_text

        val call_from = rs.call_from
        val phone_number = rs.phone_number
        val thank_you_title = rs.thank_you_title
        val thank_you_description = rs.thank_you_description

        val memory_title = rs.memory_title
        val task_duration = rs.task_duration
        val task_continuity = rs.task_continuity
        val read_instructions = rs.read_instructions
        val complete_all_tasks = rs.complete_all_tasks
        val listen_instruction_tip = rs.listen_instruction_tip
        val quiet_room_tip = rs.quiet_room_tip
        val good_luck = rs.good_luck
        val press_start_to_begin = rs.press_start_to_begin
        val instructions = rs.instructions
        val bedroom = rs.bedroom
        val living_room = rs.living_room
        val kitchen = rs.kitchen
        val drag_and_place_instruction = rs.drag_and_place_instruction
        val delete = rs.delete
        val rate_your_feelings = rs.rate_your_feelings
        val rate_expected_success = rs.rate_expected_success
        val rate = rs.rate


    }


}