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
import dmt_proms.ui.core.generated.resources.disk_full
import dmt_proms.ui.core.generated.resources.day_monday
import dmt_proms.ui.core.generated.resources.day_sunday
import dmt_proms.ui.core.generated.resources.day_thursday
import dmt_proms.ui.core.generated.resources.day_tuesday
import dmt_proms.ui.core.generated.resources.day_wednesday
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
import dmt_proms.ui.core.generated.resources.remember_all
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
import dmt_proms.ui.core.generated.resources.talk_to_family
import dmt_proms.ui.core.generated.resources.unexpected_error
import dmt_proms.ui.core.generated.resources.vertical_stroke
import dmt_proms.ui.core.generated.resources.warning
import dmt_proms.ui.core.generated.resources.use_diary
import dmt_proms.ui.core.generated.resources.welcome
import dmt_proms.ui.core.generated.resources.x
import dmt_proms.ui.core.generated.resources.yoga
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
import dmt_proms.ui.core.generated.resources.app
import dmt_proms.ui.core.generated.resources.backpack
import dmt_proms.ui.core.generated.resources.bedroom
import dmt_proms.ui.core.generated.resources.bedroom_block
import dmt_proms.ui.core.generated.resources.book_icon
import dmt_proms.ui.core.generated.resources.book
import dmt_proms.ui.core.generated.resources.book_agenda
import dmt_proms.ui.core.generated.resources.bottle
import dmt_proms.ui.core.generated.resources.call_accept
import dmt_proms.ui.core.generated.resources.call_remove
import dmt_proms.ui.core.generated.resources.coffee
import dmt_proms.ui.core.generated.resources.coffee_agenda
import dmt_proms.ui.core.generated.resources.coffeee_icon
import dmt_proms.ui.core.generated.resources.delete_icon
import dmt_proms.ui.core.generated.resources.dress
import dmt_proms.ui.core.generated.resources.dumbbell_agenda
import dmt_proms.ui.core.generated.resources.dumbbell_icon
import dmt_proms.ui.core.generated.resources.glasses
import dmt_proms.ui.core.generated.resources.keys
import dmt_proms.ui.core.generated.resources.kitchen_block
import dmt_proms.ui.core.generated.resources.move_agenda
import dmt_proms.ui.core.generated.resources.move_icon
import dmt_proms.ui.core.generated.resources.phone
import dmt_proms.ui.core.generated.resources.records
import dmt_proms.ui.core.generated.resources.salon_block
import dmt_proms.ui.core.generated.resources.shoes
import dmt_proms.ui.core.generated.resources.stethoscope_icon
import dmt_proms.ui.core.generated.resources.stethoscope_agenda
import dmt_proms.ui.core.generated.resources.teach_agenda
import dmt_proms.ui.core.generated.resources.teach_icon
import dmt_proms.ui.core.generated.resources.wallet


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



        //MemoryTestModule
        val appIcon = Res.drawable.app
        val backpackIcon = Res.drawable.backpack
        val bedroomBlockIcon = Res.drawable.bedroom_block
        val bookIcon = Res.drawable.book_icon
        val bookImage = Res.drawable.book
        val bookScheduleIcon = Res.drawable.book_agenda
        val bottleImage = Res.drawable.bottle
        val callAccept= Res.drawable.call_accept
        val callDecline = Res.drawable.call_remove
        val coffeeImage = Res.drawable.coffee
        val coffeeScheduleIcon = Res.drawable.coffee_agenda
        val coffeIcon = Res.drawable.coffeee_icon
        val deleteIcon = Res.drawable.delete_icon
        val dressImage = Res.drawable.dress
        val dumbbellScheduleIcon = Res.drawable.dumbbell_agenda
        val dumblertIcon = Res.drawable.dumbbell_icon
        val glassesImage = Res.drawable.glasses
        val keysImage = Res.drawable.keys
        val kitchenBlockIcon = Res.drawable.kitchen_block
        val moveScheduleIcon = Res.drawable.move_agenda
        val moveIcon = Res.drawable.move_icon
        val phoneImage = Res.drawable.phone
        val recordsIcon = Res.drawable.records
        val salonBlockIcon = Res.drawable.salon_block
        val shoesImages = Res.drawable.shoes
        val stethoscopeImage = Res.drawable.stethoscope_icon
        val stethascopeScheduleIcon = Res.drawable.stethoscope_agenda
        val teachScheduleIcon = Res.drawable.teach_agenda
        val teachIcon = Res.drawable.teach_icon
        val walletImage = Res.drawable.wallet



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