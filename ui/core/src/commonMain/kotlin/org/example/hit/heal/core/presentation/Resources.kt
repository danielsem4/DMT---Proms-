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
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.Understanding_dialog_text
import dmt_proms.ui.core.generated.resources.Whatsapp
import dmt_proms.ui.core.generated.resources.app_name
import dmt_proms.ui.core.generated.resources.apps_page_second_assist
import dmt_proms.ui.core.generated.resources.arrow_key
import dmt_proms.ui.core.generated.resources.asterisk
import dmt_proms.ui.core.generated.resources.ball
import dmt_proms.ui.core.generated.resources.balloon
import dmt_proms.ui.core.generated.resources.black_circle
import dmt_proms.ui.core.generated.resources.black_messages
import dmt_proms.ui.core.generated.resources.black_phone
import dmt_proms.ui.core.generated.resources.black_video
import dmt_proms.ui.core.generated.resources.blue_napkin
import dmt_proms.ui.core.generated.resources.calculator
import dmt_proms.ui.core.generated.resources.call_hana_cohen_pass
import dmt_proms.ui.core.generated.resources.call_to_dentist
import dmt_proms.ui.core.generated.resources.call_to_detist_pass
import dmt_proms.ui.core.generated.resources.call_to_hana_cohen_instruction_pass
import dmt_proms.ui.core.generated.resources.camera
import dmt_proms.ui.core.generated.resources.check
import dmt_proms.ui.core.generated.resources.chicken
import dmt_proms.ui.core.generated.resources.circle
import dmt_proms.ui.core.generated.resources.clock
import dmt_proms.ui.core.generated.resources.close
import dmt_proms.ui.core.generated.resources.close_fridge
import dmt_proms.ui.core.generated.resources.close_icon
import dmt_proms.ui.core.generated.resources.coca_cola
import dmt_proms.ui.core.generated.resources.cone
import dmt_proms.ui.core.generated.resources.contact
import dmt_proms.ui.core.generated.resources.contact_details
import dmt_proms.ui.core.generated.resources.contact_page_first_assist
import dmt_proms.ui.core.generated.resources.contacts
import dmt_proms.ui.core.generated.resources.contacts_page_first_assist
import dmt_proms.ui.core.generated.resources.contacts_page_second_assist
import dmt_proms.ui.core.generated.resources.contacts_page_thired_assist
import dmt_proms.ui.core.generated.resources.`continue`
import dmt_proms.ui.core.generated.resources.cottage
import dmt_proms.ui.core.generated.resources.delete_number
import dmt_proms.ui.core.generated.resources.dentist_number_showen_call_him_pass
import dmt_proms.ui.core.generated.resources.dentist_pass
import dmt_proms.ui.core.generated.resources.dentist_phone_number
import dmt_proms.ui.core.generated.resources.dermatologist_pass
import dmt_proms.ui.core.generated.resources.device_app_title
import dmt_proms.ui.core.generated.resources.device_apps_title
import dmt_proms.ui.core.generated.resources.dial
import dmt_proms.ui.core.generated.resources.dial_keys
import dmt_proms.ui.core.generated.resources.diale_to_dentist
import dmt_proms.ui.core.generated.resources.dialer
import dmt_proms.ui.core.generated.resources.dialer_opened
import dmt_proms.ui.core.generated.resources.dialer_opened_pass
import dmt_proms.ui.core.generated.resources.dialer_page_dentis_number_appeared
import dmt_proms.ui.core.generated.resources.dialer_page_second_assist
import dmt_proms.ui.core.generated.resources.dialer_page_thired_assist
import dmt_proms.ui.core.generated.resources.dialer_page_wrong_action_one
import dmt_proms.ui.core.generated.resources.dialog_close
import dmt_proms.ui.core.generated.resources.dialog_exclamation_mark
import dmt_proms.ui.core.generated.resources.dialog_like
import dmt_proms.ui.core.generated.resources.dialog_speaker
import dmt_proms.ui.core.generated.resources.documents
import dmt_proms.ui.core.generated.resources.drop_down
import dmt_proms.ui.core.generated.resources.drop_up
import dmt_proms.ui.core.generated.resources.eighth_question_answer_hitber_version_1
import dmt_proms.ui.core.generated.resources.eighth_question_answer_hitber_version_2
import dmt_proms.ui.core.generated.resources.eighth_question_answer_hitber_version_3
import dmt_proms.ui.core.generated.resources.eighth_question_answer_hitber_version_4
import dmt_proms.ui.core.generated.resources.eighth_question_hitber_close_icon
import dmt_proms.ui.core.generated.resources.eighth_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.eighth_question_hitber_title
import dmt_proms.ui.core.generated.resources.eighth_question_word_hitber_I
import dmt_proms.ui.core.generated.resources.eighth_question_word_hitber_dinner
import dmt_proms.ui.core.generated.resources.eighth_question_word_hitber_grandma
import dmt_proms.ui.core.generated.resources.eighth_question_word_hitber_had
import dmt_proms.ui.core.generated.resources.eighth_question_word_hitber_with
import dmt_proms.ui.core.generated.resources.eighth_question_word_hitber_yesterday
import dmt_proms.ui.core.generated.resources.ellipse
import dmt_proms.ui.core.generated.resources.email
import dmt_proms.ui.core.generated.resources.end
import dmt_proms.ui.core.generated.resources.entry_hitber_good_luck
import dmt_proms.ui.core.generated.resources.entry_hitber_instructions1
import dmt_proms.ui.core.generated.resources.entry_hitber_instructions2
import dmt_proms.ui.core.generated.resources.entry_hitber_instructions3
import dmt_proms.ui.core.generated.resources.entry_hitber_instructions4
import dmt_proms.ui.core.generated.resources.entry_hitber_instructions5
import dmt_proms.ui.core.generated.resources.entry_hitber_instructions6
import dmt_proms.ui.core.generated.resources.entry_hitber_instructions7
import dmt_proms.ui.core.generated.resources.entry_hitber_note
import dmt_proms.ui.core.generated.resources.entry_hitber_title
import dmt_proms.ui.core.generated.resources.error_icon
import dmt_proms.ui.core.generated.resources.exclamation_mark
import dmt_proms.ui.core.generated.resources.exit
import dmt_proms.ui.core.generated.resources.family_doctor_pass
import dmt_proms.ui.core.generated.resources.finish_first_mission_pass
import dmt_proms.ui.core.generated.resources.first_instructions_pass
import dmt_proms.ui.core.generated.resources.first_mission_done_vocal_pass
import dmt_proms.ui.core.generated.resources.first_mission_pass
import dmt_proms.ui.core.generated.resources.first_question_hitber_Drop_down_drop_up_Icon
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer1_1
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer1_2
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer1_3
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer1_4
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer1_5
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer1_6
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer1_7
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_1
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_10
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_11
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_12
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_2
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_3
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_4
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_5
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_6
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_7
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_8
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer2_9
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_1
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_2
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_3
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_4
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_5
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_6
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_7
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer3_8
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_1
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_10
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_2
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_3
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_4
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_5
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_6
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_7
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_8
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer4_9
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_1
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_2
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_3
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_4
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_5
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_6
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_7
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer5_8
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer6_1
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer6_2
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer6_3
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer6_4
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer6_5
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer6_6
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer7_1
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer7_2
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer7_3
import dmt_proms.ui.core.generated.resources.first_question_hitber_answer7_4
import dmt_proms.ui.core.generated.resources.first_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.first_question_hitber_question1
import dmt_proms.ui.core.generated.resources.first_question_hitber_question2
import dmt_proms.ui.core.generated.resources.first_question_hitber_question3
import dmt_proms.ui.core.generated.resources.first_question_hitber_question4
import dmt_proms.ui.core.generated.resources.first_question_hitber_question5
import dmt_proms.ui.core.generated.resources.first_question_hitber_question6
import dmt_proms.ui.core.generated.resources.first_question_hitber_question7
import dmt_proms.ui.core.generated.resources.first_question_hitber_title
import dmt_proms.ui.core.generated.resources.fmpt
import dmt_proms.ui.core.generated.resources.fmpt_meaning
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_ball
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_balloon
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_lemon
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_pencil
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_pic1
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_pic2
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_ruler
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_table
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_title
import dmt_proms.ui.core.generated.resources.fourth_question_hitber_what_in_the_pic
import dmt_proms.ui.core.generated.resources.going_back_to_apss_screen_pass
import dmt_proms.ui.core.generated.resources.grapes
import dmt_proms.ui.core.generated.resources.green_napkin
import dmt_proms.ui.core.generated.resources.hana_cohen
import dmt_proms.ui.core.generated.resources.hash_tag
import dmt_proms.ui.core.generated.resources.here_persons_number
import dmt_proms.ui.core.generated.resources.hexagon
import dmt_proms.ui.core.generated.resources.horizontal_line
import dmt_proms.ui.core.generated.resources.chat
import dmt_proms.ui.core.generated.resources.clock_test
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
import dmt_proms.ui.core.generated.resources.lemon
import dmt_proms.ui.core.generated.resources.like
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.login_success
import dmt_proms.ui.core.generated.resources.logout
import dmt_proms.ui.core.generated.resources.measurements
import dmt_proms.ui.core.generated.resources.medications
import dmt_proms.ui.core.generated.resources.memory
import dmt_proms.ui.core.generated.resources.messages
import dmt_proms.ui.core.generated.resources.milk
import dmt_proms.ui.core.generated.resources.my_files
import dmt_proms.ui.core.generated.resources.neurologist_pass
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.password
import dmt_proms.ui.core.generated.resources.no
import dmt_proms.ui.core.generated.resources.now_the_contacts_list_will_be_opened_pass
import dmt_proms.ui.core.generated.resources.open_fridge
import dmt_proms.ui.core.generated.resources.ophthalmologist_pass
import dmt_proms.ui.core.generated.resources.orange_juice
import dmt_proms.ui.core.generated.resources.orthopedist_pass
import dmt_proms.ui.core.generated.resources.paint_clinic_pass
import dmt_proms.ui.core.generated.resources.peas
import dmt_proms.ui.core.generated.resources.pencil
import dmt_proms.ui.core.generated.resources.pentagon
import dmt_proms.ui.core.generated.resources.person_names
import dmt_proms.ui.core.generated.resources.phone
import dmt_proms.ui.core.generated.resources.phone_number
import dmt_proms.ui.core.generated.resources.plus
import dmt_proms.ui.core.generated.resources.press_the_dial_button_that_showen_down_pass
import dmt_proms.ui.core.generated.resources.press_the_number_or_the_dial_button_pass
import dmt_proms.ui.core.generated.resources.previous
import dmt_proms.ui.core.generated.resources.send
import dmt_proms.ui.core.generated.resources.sent_successfully
import dmt_proms.ui.core.generated.resources.take_pills
import dmt_proms.ui.core.generated.resources.unexpected_error
import dmt_proms.ui.core.generated.resources.welcome
import dmt_proms.ui.core.generated.resources.Res.string as rs
import dmt_proms.ui.core.generated.resources.profile
import dmt_proms.ui.core.generated.resources.psychiatrist_pass
import dmt_proms.ui.core.generated.resources.purse
import dmt_proms.ui.core.generated.resources.rectangle
import dmt_proms.ui.core.generated.resources.red_napkin
import dmt_proms.ui.core.generated.resources.return_button_on_top_left_pass
import dmt_proms.ui.core.generated.resources.rhomb
import dmt_proms.ui.core.generated.resources.right_triangle
import dmt_proms.ui.core.generated.resources.rotate_star
import dmt_proms.ui.core.generated.resources.ruler
import dmt_proms.ui.core.generated.resources.search
import dmt_proms.ui.core.generated.resources.search_at_latter_h_pass
import dmt_proms.ui.core.generated.resources.search_contacts_list_in_the_phone_pass
import dmt_proms.ui.core.generated.resources.search_dentist_number_pass
import dmt_proms.ui.core.generated.resources.search_for_hana_choen_in_contacts_pass
import dmt_proms.ui.core.generated.resources.second_part_of_test_instructions_pass
import dmt_proms.ui.core.generated.resources.second_question_hitber_asterisk
import dmt_proms.ui.core.generated.resources.second_question_hitber_check
import dmt_proms.ui.core.generated.resources.second_question_hitber_close
import dmt_proms.ui.core.generated.resources.second_question_hitber_cone
import dmt_proms.ui.core.generated.resources.second_question_hitber_dialog_icon
import dmt_proms.ui.core.generated.resources.second_question_hitber_dialog_instructions
import dmt_proms.ui.core.generated.resources.second_question_hitber_dialog_title
import dmt_proms.ui.core.generated.resources.second_question_hitber_dialog_understand
import dmt_proms.ui.core.generated.resources.second_question_hitber_ellipse
import dmt_proms.ui.core.generated.resources.second_question_hitber_hash_tag
import dmt_proms.ui.core.generated.resources.second_question_hitber_hexagon
import dmt_proms.ui.core.generated.resources.second_question_hitber_pentagon
import dmt_proms.ui.core.generated.resources.second_question_hitber_plus
import dmt_proms.ui.core.generated.resources.second_question_hitber_rectangle
import dmt_proms.ui.core.generated.resources.second_question_hitber_rhomb
import dmt_proms.ui.core.generated.resources.second_question_hitber_right_triangle
import dmt_proms.ui.core.generated.resources.second_question_hitber_star
import dmt_proms.ui.core.generated.resources.second_question_hitber_task
import dmt_proms.ui.core.generated.resources.second_question_hitber_task_instructions
import dmt_proms.ui.core.generated.resources.second_question_hitber_task_retry_instructions
import dmt_proms.ui.core.generated.resources.second_question_hitber_title
import dmt_proms.ui.core.generated.resources.second_question_hitber_trapezoid
import dmt_proms.ui.core.generated.resources.second_question_hitber_triangle
import dmt_proms.ui.core.generated.resources.settings
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_black_circle
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_blue_circle
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_green_circle
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_title
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_yellow_circle
import dmt_proms.ui.core.generated.resources.sixth_question_can_to_yellow_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_chicken_to_green_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_default_milk_to_red_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_grapes_to_blue_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_close_fridge
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_item
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_listen
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_napkin
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_open_fridge
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_table
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_title
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_volume_icon
import dmt_proms.ui.core.generated.resources.speaker
import dmt_proms.ui.core.generated.resources.star
import dmt_proms.ui.core.generated.resources.start
import dmt_proms.ui.core.generated.resources.store
import dmt_proms.ui.core.generated.resources.summary_hitber_instructions1
import dmt_proms.ui.core.generated.resources.summary_hitber_instructions2
import dmt_proms.ui.core.generated.resources.summary_hitber_title
import dmt_proms.ui.core.generated.resources.table
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_shape_image
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_shape_model
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_shapes
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_title
import dmt_proms.ui.core.generated.resources.test_record_pass
import dmt_proms.ui.core.generated.resources.thanks_coffe
import dmt_proms.ui.core.generated.resources.thanks_vocal_pass
import dmt_proms.ui.core.generated.resources.the_pass_test
import dmt_proms.ui.core.generated.resources.third_question_hitber_finish_task
import dmt_proms.ui.core.generated.resources.third_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.third_question_hitber_title
import dmt_proms.ui.core.generated.resources.trapezoid
import dmt_proms.ui.core.generated.resources.triangle
import dmt_proms.ui.core.generated.resources.vertical_line
import dmt_proms.ui.core.generated.resources.video
import dmt_proms.ui.core.generated.resources.weather
import dmt_proms.ui.core.generated.resources.welcome_pass
import dmt_proms.ui.core.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.ui.core.generated.resources.what_you_need_to_do
import dmt_proms.ui.core.generated.resources.whatsapp
import dmt_proms.ui.core.generated.resources.white_messages
import dmt_proms.ui.core.generated.resources.white_phone
import dmt_proms.ui.core.generated.resources.white_video
import dmt_proms.ui.core.generated.resources.witch_contact_are_we_looking_for_pass
import dmt_proms.ui.core.generated.resources.wrong_app_second_assist
import dmt_proms.ui.core.generated.resources.wrong_app_thired_assist
import dmt_proms.ui.core.generated.resources.wrong_app_title
import dmt_proms.ui.core.generated.resources.wrong_number_dialed_please_try_again_pass
import dmt_proms.ui.core.generated.resources.yellow_napkin
import dmt_proms.ui.core.generated.resources.yes
import dmt_proms.ui.core.generated.resources.yogurt

/**
 * Resources used in the application.
 * These resources include icons, strings, and other assets
 * This object made to enable the resources to be used in different modules.
 */

object Resources {

    object Icon {
        val asterisk = Res.drawable.asterisk
        val arrowKey = Res.drawable.arrow_key
        val ball = Res.drawable.ball
        val balloon = Res.drawable.balloon
        val blackCircle = Res.drawable.black_circle
        val blackMessages = Res.drawable.black_messages
        val blackPhone = Res.drawable.black_phone
        val blackVideo = Res.drawable.black_video
        val blueNapkin = Res.drawable.blue_napkin
        val calculatorIcon = Res.drawable.calculator
        val cameraIcon = Res.drawable.camera
        val check = Res.drawable.check
        val chicken = Res.drawable.chicken
        val circle = Res.drawable.circle
        val clockIcon = Res.drawable.clock
        val close = Res.drawable.close
        val closeFridge = Res.drawable.close_fridge
        val closeIcon = Res.drawable.close_icon
        val cocaCola = Res.drawable.coca_cola
        val cone = Res.drawable.cone
        val contactsIcon = Res.drawable.contacts
        val cottage = Res.drawable.cottage
        val deleteNumberIcon = Res.drawable.delete_number
        val dialKeysIcon = Res.drawable.dial_keys
        val dialogSpeakerIcon = Res.drawable.dialog_speaker
        val documentsIcon = Res.drawable.documents
        val dropDown = Res.drawable.drop_down
        val dropUp = Res.drawable.drop_up
        val ellipse = Res.drawable.ellipse
        val emailIcon = Res.drawable.email
        val errorIcon = Res.drawable.error_icon
        val exclamationMark = Res.drawable.exclamation_mark
        val exitIcon = Res.drawable.exit
        val grapes = Res.drawable.grapes
        val greenNapkin = Res.drawable.green_napkin
        val hashTag = Res.drawable.hash_tag
        val hexagon = Res.drawable.hexagon
        val horizontalLine = Res.drawable.horizontal_line
        val lemon = Res.drawable.lemon
        val likeIcon = Res.drawable.like
        val milk = Res.drawable.milk
        val openFridge = Res.drawable.open_fridge
        val orangeJuice = Res.drawable.orange_juice
        val peas = Res.drawable.peas
        val pencil = Res.drawable.pencil
        val pentagon = Res.drawable.pentagon
        val plusIcon = Res.drawable.plus
        val profileIcon = Res.drawable.profile
        val purseIcon = Res.drawable.purse
        val rectangle = Res.drawable.rectangle
        val redNapkin = Res.drawable.red_napkin
        val rhomb = Res.drawable.rhomb
        val rightTriangle = Res.drawable.right_triangle
        val rotateStar = Res.drawable.rotate_star
        val ruler = Res.drawable.ruler
        val searchIcon = Res.drawable.search
        val settingsIcon = Res.drawable.settings
        val speaker = Res.drawable.speaker
        val star = Res.drawable.star
        val storeIcon = Res.drawable.store
        val table = Res.drawable.table
        val trapezoid = Res.drawable.trapezoid
        val triangle = Res.drawable.triangle
        val verticalLine = Res.drawable.vertical_line
        val weatherIcon = Res.drawable.weather
        val whatsappIcon = Res.drawable.whatsapp
        val whiteMessages = Res.drawable.white_messages
        val whitePhone = Res.drawable.white_phone
        val whiteVideo = Res.drawable.white_video
        val yellowNapkin = Res.drawable.yellow_napkin
        val yogurt = Res.drawable.yogurt
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

        val exit = Res.string.exit
        val `continue` = Res.string.`continue`
        val start = Res.string.start
        val yes = Res.string.yes
        val no = Res.string.no
        val plus = Res.string.plus
        val search = Res.string.search

        val entryHitberTitle = Res.string.entry_hitber_title
        val entryHitberNote = Res.string.entry_hitber_note
        val entryHitberInstructions1 = Res.string.entry_hitber_instructions1
        val entryHitberInstructions2 = Res.string.entry_hitber_instructions2
        val entryHitberInstructions3 = Res.string.entry_hitber_instructions3
        val entryHitberInstructions4 = Res.string.entry_hitber_instructions4
        val entryHitberInstructions5 = Res.string.entry_hitber_instructions5
        val entryHitberInstructions6 = Res.string.entry_hitber_instructions6
        val entryHitberInstructions7 = Res.string.entry_hitber_instructions7
        val entryHitberGoodLuck = Res.string.entry_hitber_good_luck

        val firstQuestionHitberTitle = Res.string.first_question_hitber_title
        val firstQuestionHitberInstructions = Res.string.first_question_hitber_instructions
        val firstQuestionHitberDropDownDropUpIcon = Res.string.first_question_hitber_Drop_down_drop_up_Icon

        val firstQuestionHitberQuestion1 = Res.string.first_question_hitber_question1
        val firstQuestionHitberAnswer1_1 = Res.string.first_question_hitber_answer1_1
        val firstQuestionHitberAnswer1_2 = Res.string.first_question_hitber_answer1_2
        val firstQuestionHitberAnswer1_3 = Res.string.first_question_hitber_answer1_3
        val firstQuestionHitberAnswer1_4 = Res.string.first_question_hitber_answer1_4
        val firstQuestionHitberAnswer1_5 = Res.string.first_question_hitber_answer1_5
        val firstQuestionHitberAnswer1_6 = Res.string.first_question_hitber_answer1_6
        val firstQuestionHitberAnswer1_7 = Res.string.first_question_hitber_answer1_7

        val firstQuestionHitberQuestion2 = Res.string.first_question_hitber_question2
        val firstQuestionHitberAnswer2_1 = Res.string.first_question_hitber_answer2_1
        val firstQuestionHitberAnswer2_2 = Res.string.first_question_hitber_answer2_2
        val firstQuestionHitberAnswer2_3 = Res.string.first_question_hitber_answer2_3
        val firstQuestionHitberAnswer2_4 = Res.string.first_question_hitber_answer2_4
        val firstQuestionHitberAnswer2_5 = Res.string.first_question_hitber_answer2_5
        val firstQuestionHitberAnswer2_6 = Res.string.first_question_hitber_answer2_6
        val firstQuestionHitberAnswer2_7 = Res.string.first_question_hitber_answer2_7
        val firstQuestionHitberAnswer2_8 = Res.string.first_question_hitber_answer2_8
        val firstQuestionHitberAnswer2_9 = Res.string.first_question_hitber_answer2_9
        val firstQuestionHitberAnswer2_10 = Res.string.first_question_hitber_answer2_10
        val firstQuestionHitberAnswer2_11 = Res.string.first_question_hitber_answer2_11
        val firstQuestionHitberAnswer2_12 = Res.string.first_question_hitber_answer2_12

        val firstQuestionHitberQuestion3 = Res.string.first_question_hitber_question3
        val firstQuestionHitberAnswer3_1 = Res.string.first_question_hitber_answer3_1
        val firstQuestionHitberAnswer3_2 = Res.string.first_question_hitber_answer3_2
        val firstQuestionHitberAnswer3_3 = Res.string.first_question_hitber_answer3_3
        val firstQuestionHitberAnswer3_4 = Res.string.first_question_hitber_answer3_4
        val firstQuestionHitberAnswer3_5 = Res.string.first_question_hitber_answer3_5
        val firstQuestionHitberAnswer3_6 = Res.string.first_question_hitber_answer3_6
        val firstQuestionHitberAnswer3_7 = Res.string.first_question_hitber_answer3_7
        val firstQuestionHitberAnswer3_8 = Res.string.first_question_hitber_answer3_8

        val firstQuestionHitberQuestion4 = Res.string.first_question_hitber_question4
        val firstQuestionHitberAnswer4_1 = Res.string.first_question_hitber_answer4_1
        val firstQuestionHitberAnswer4_2 = Res.string.first_question_hitber_answer4_2
        val firstQuestionHitberAnswer4_3 = Res.string.first_question_hitber_answer4_3
        val firstQuestionHitberAnswer4_4 = Res.string.first_question_hitber_answer4_4
        val firstQuestionHitberAnswer4_5 = Res.string.first_question_hitber_answer4_5
        val firstQuestionHitberAnswer4_6 = Res.string.first_question_hitber_answer4_6
        val firstQuestionHitberAnswer4_7 = Res.string.first_question_hitber_answer4_7
        val firstQuestionHitberAnswer4_8 = Res.string.first_question_hitber_answer4_8
        val firstQuestionHitberAnswer4_9 = Res.string.first_question_hitber_answer4_9
        val firstQuestionHitberAnswer4_10 = Res.string.first_question_hitber_answer4_10

        val firstQuestionHitberQuestion5 = Res.string.first_question_hitber_question5
        val firstQuestionHitberAnswer5_1 = Res.string.first_question_hitber_answer5_1
        val firstQuestionHitberAnswer5_2 = Res.string.first_question_hitber_answer5_2
        val firstQuestionHitberAnswer5_3 = Res.string.first_question_hitber_answer5_3
        val firstQuestionHitberAnswer5_4 = Res.string.first_question_hitber_answer5_4
        val firstQuestionHitberAnswer5_5 = Res.string.first_question_hitber_answer5_5
        val firstQuestionHitberAnswer5_6 = Res.string.first_question_hitber_answer5_6
        val firstQuestionHitberAnswer5_7 = Res.string.first_question_hitber_answer5_7
        val firstQuestionHitberAnswer5_8 = Res.string.first_question_hitber_answer5_8

        val firstQuestionHitberQuestion6 = Res.string.first_question_hitber_question6
        val firstQuestionHitberAnswer6_1 = Res.string.first_question_hitber_answer6_1
        val firstQuestionHitberAnswer6_2 = Res.string.first_question_hitber_answer6_2
        val firstQuestionHitberAnswer6_3 = Res.string.first_question_hitber_answer6_3
        val firstQuestionHitberAnswer6_4 = Res.string.first_question_hitber_answer6_4
        val firstQuestionHitberAnswer6_5 = Res.string.first_question_hitber_answer6_5
        val firstQuestionHitberAnswer6_6 = Res.string.first_question_hitber_answer6_6

        val firstQuestionHitberQuestion7 = Res.string.first_question_hitber_question7
        val firstQuestionHitberAnswer7_1 = Res.string.first_question_hitber_answer7_1
        val firstQuestionHitberAnswer7_2 = Res.string.first_question_hitber_answer7_2
        val firstQuestionHitberAnswer7_3 = Res.string.first_question_hitber_answer7_3
        val firstQuestionHitberAnswer7_4 = Res.string.first_question_hitber_answer7_4

        val secondQuestionHitberTitle = Res.string.second_question_hitber_title
        val secondQuestionHitberTask = Res.string.second_question_hitber_task
        val secondQuestionHitberDialogInstructions = Res.string.second_question_hitber_dialog_instructions
        val secondQuestionHitberDialogTitle = Res.string.second_question_hitber_dialog_title
        val secondQuestionHitberTaskInstructions = Res.string.second_question_hitber_task_instructions
        val secondQuestionHitberTaskRetryInstructions = Res.string.second_question_hitber_task_retry_instructions
        val secondQuestionHitberDialogIcon = Res.string.second_question_hitber_dialog_icon
        val secondQuestionHitberUnderstand = Res.string.second_question_hitber_dialog_understand
        val secondQuestionHitberEllipse = Res.string.second_question_hitber_ellipse
        val secondQuestionHitberRectangle = Res.string.second_question_hitber_rectangle
        val secondQuestionHitberPentagon = Res.string.second_question_hitber_pentagon
        val secondQuestionHitberStar = Res.string.second_question_hitber_star
        val secondQuestionHitberHashTag = Res.string.second_question_hitber_hash_tag
        val secondQuestionHitberRhomb = Res.string.second_question_hitber_rhomb
        val secondQuestionHitberCone = Res.string.second_question_hitber_cone
        val secondQuestionHitberPlus = Res.string.second_question_hitber_plus
        val secondQuestionHitberHexagon = Res.string.second_question_hitber_hexagon
        val secondQuestionHitberRightTriangle = Res.string.second_question_hitber_right_triangle
        val secondQuestionHitberAsterisk = Res.string.second_question_hitber_asterisk
        val secondQuestionHitberCheck = Res.string.second_question_hitber_check
        val secondQuestionHitberTrapezoid = Res.string.second_question_hitber_trapezoid
        val secondQuestionHitberTriangle = Res.string.second_question_hitber_triangle
        val secondQuestionHitberClose = Res.string.second_question_hitber_close

        val thirdQuestionHitberTitle = Res.string.third_question_hitber_title
        val thirdQuestionHitberInstructions = Res.string.third_question_hitber_instructions
        val thirdQuestionHitberFinishTask = Res.string.third_question_hitber_finish_task

        val fourthQuestionHitberTitle = Res.string.fourth_question_hitber_title
        val fourthQuestionHitberInstructions = Res.string.fourth_question_hitber_instructions
        val fourthQuestionHitberWhatInThePic = Res.string.fourth_question_hitber_what_in_the_pic
        val fourthQuestionHitberPic1 = Res.string.fourth_question_hitber_pic1
        val fourthQuestionHitberPic2 = Res.string.fourth_question_hitber_pic2
        val fourthQuestionHitberPencil = Res.string.fourth_question_hitber_pencil
        val fourthQuestionHitberRuler = Res.string.fourth_question_hitber_ruler
        val fourthQuestionHitberTable = Res.string.fourth_question_hitber_table
        val fourthQuestionHitberBall = Res.string.fourth_question_hitber_ball
        val fourthQuestionHitberBalloon = Res.string.fourth_question_hitber_balloon
        val fourthQuestionHitberLemon = Res.string.fourth_question_hitber_lemon

        val sixthQuestionHitberTitle = Res.string.sixth_question_hitber_title
        val sixthQuestionHitberInstructions = Res.string.sixth_question_hitber_instructions
        val sixthQuestionHitberVolumeIcon = Res.string.sixth_question_hitber_volume_icon
        val sixthQuestionHitberListen = Res.string.sixth_question_hitber_listen
        val sixthQuestionHitberOpenFridge = Res.string.sixth_question_hitber_open_fridge
        val sixthQuestionHitberCloseFridge = Res.string.sixth_question_hitber_close_fridge
        val sixthQuestionHitberTable = Res.string.sixth_question_hitber_table
        val sixthQuestionHitberItem = Res.string.sixth_question_hitber_item
        val sixthQuestionHitberNapkin = Res.string.sixth_question_hitber_napkin

        val sixthQuestionDefaultMilkToRedNapkinHitber = Res.string.sixth_question_default_milk_to_red_napkin_hitber
        val sixthQuestionGrapesToBlueNapkinHitber = Res.string.sixth_question_grapes_to_blue_napkin_hitber
        val sixthQuestionChickenToGreenNapkinHitber = Res.string.sixth_question_chicken_to_green_napkin_hitber
        val sixthQuestionCanToYellowNapkinHitber = Res.string.sixth_question_can_to_yellow_napkin_hitber

        val seventhQuestionHitberTitle = Res.string.seventh_question_hitber_title
        val seventhQuestionHitberGreenCircle = Res.string.seventh_question_hitber_green_circle
        val seventhQuestionHitberYellowCircle = Res.string.seventh_question_hitber_yellow_circle
        val seventhQuestionHitberBlackCircle = Res.string.seventh_question_hitber_black_circle
        val seventhQuestionHitberBlueCircle = Res.string.seventh_question_hitber_blue_circle

        val eighthQuestionHitberTitle = Res.string.eighth_question_hitber_title
        val eighthQuestionHitberInstructions = Res.string.eighth_question_hitber_instructions
        val eighthQuestionHitberCloseIcon = Res.string.eighth_question_hitber_close_icon
        val eighthQuestionHitberWordYesterday = Res.string.eighth_question_word_hitber_yesterday
        val eighthQuestionHitberWordI = Res.string.eighth_question_word_hitber_I
        val eighthQuestionHitberWordHad = Res.string.eighth_question_word_hitber_had
        val eighthQuestionHitberWordDinner = Res.string.eighth_question_word_hitber_dinner
        val eighthQuestionHitberWordWith = Res.string.eighth_question_word_hitber_with
        val eighthQuestionHitberWordGrandma = Res.string.eighth_question_word_hitber_grandma
        val eighthQuestionAnswerHitberVersion1 = Res.string.eighth_question_answer_hitber_version_1
        val eighthQuestionAnswerHitberVersion2 = Res.string.eighth_question_answer_hitber_version_2
        val eighthQuestionAnswerHitberVersion3 = Res.string.eighth_question_answer_hitber_version_3
        val eighthQuestionAnswerHitberVersion4 = Res.string.eighth_question_answer_hitber_version_4

        val tenthQuestionHitberTitle = Res.string.tenth_question_hitber_title
        val tenthQuestionHitberInstructions = Res.string.tenth_question_hitber_instructions
        val tenthQuestionHitberShapeModel = Res.string.tenth_question_hitber_shape_model
        val tenthQuestionHitberShapes = Res.string.tenth_question_hitber_shapes
        val tenthQuestionHitberShapeImage = Res.string.tenth_question_hitber_shape_image

        val summaryHitberTitle = Res.string.summary_hitber_title
        val summaryHitberInstructions1 = Res.string.summary_hitber_instructions1
        val summaryHitberInstructions2 = Res.string.summary_hitber_instructions2


        val hanaCohen = Res.string.hana_cohen
        val personNames = Res.array.person_names
        val phoneNumber = Res.string.phone_number

        val thanksCoffe = Res.string.thanks_coffe
        val fmpt = Res.string.fmpt
        val fmptMeaning = Res.string.fmpt_meaning

        val dentistPass = Res.string.dentist_pass
        val familyDoctorPass = Res.string.family_doctor_pass
        val paintClinicPass = Res.string.paint_clinic_pass
        val dermatologistPass = Res.string.dermatologist_pass
        val orthopedistPass = Res.string.orthopedist_pass
        val neurologistPass = Res.string.neurologist_pass
        val ophthalmologistPass = Res.string.ophthalmologist_pass
        val psychiatristPass = Res.string.psychiatrist_pass

        val deviceAppsTitle = Res.string.device_apps_title
        val deviceAppTitle = Res.string.device_app_title
        val wrongAppTitle = Res.string.wrong_app_title

        val firstMissionPass = Res.string.first_mission_pass
        val callToHanaCohenInstructionPass = Res.string.call_to_hana_cohen_instruction_pass
        val callToDentist = Res.string.call_to_dentist

        val whatYouNeedToDo = Res.string.what_you_need_to_do
        val dialerOpened = Res.string.dialer_opened

        val appsPageSecondAssist = Res.string.apps_page_second_assist
        val wrongAppSecondAssist = Res.string.wrong_app_second_assist
        val wrongAppThirdAssist = Res.string.wrong_app_thired_assist

        val contactsPageFirstAssist = Res.string.contacts_page_first_assist
        val contactsPageSecondAssist = Res.string.contacts_page_second_assist
        val contactsPageThirdAssist = Res.string.contacts_page_thired_assist

        val contactPageFirstAssist = Res.string.contact_page_first_assist
        val finishFirstMissionPass = Res.string.finish_first_mission_pass

        val dialerPageSecondAssist = Res.string.dialer_page_second_assist
        val dialeToDentist = Res.string.diale_to_dentist
        val dialerPageThirdAssist = Res.string.dialer_page_thired_assist

        val dialerPageWrongActionOne = Res.string.dialer_page_wrong_action_one
        val dialerPageDentistNumberAppeared = Res.string.dialer_page_dentis_number_appeared

        val herePersonsNumber = Res.string.here_persons_number
        val thePassTest = Res.string.the_pass_test

        val calculator = Res.string.calculator
        val settings = Res.string.settings
        val camera = Res.string.camera
        val store = Res.string.store
        val clock = Res.string.clock
        val contacts = Res.string.contacts
        val purse = Res.string.purse
        val weather = Res.string.weather
        val myFiles = Res.string.my_files
        val phone = Res.string.phone
        val video = Res.string.video
        val whatsapp = Res.string.Whatsapp

        val contact = Res.string.contact
        val contactDetails = Res.string.contact_details
        val dialKeys = Res.string.dial_keys

        val dentistPhoneNumber = Res.string.dentist_phone_number
        val dialer = Res.string.dialer
        val deleteNumber = Res.string.delete_number
        val dial = Res.string.dial

        val welcomePass = Res.string.welcome_pass
        val end = Res.string.end

        val dialogSpeaker = Res.string.dialog_speaker
        val dialogExclamationMark = Res.string.dialog_exclamation_mark
        val dialogClose = Res.string.dialog_close
        val dialogLike = Res.string.dialog_like
        val understandingDialogText = Res.string.Understanding_dialog_text


        // Recordings
        val testRecordPass = Res.string.test_record_pass
        val firstInstructionsPass = Res.string.first_instructions_pass
        val callHanaCohenPass = Res.string.call_hana_cohen_pass
        val secondPartOfTestInstructionsPass = Res.string.second_part_of_test_instructions_pass
        val thanksVocalPass = Res.string.thanks_vocal_pass
        val whatDoYouNeedToDoPass = Res.string.what_do_you_need_to_do_pass
        val callToDetistPass = Res.string.call_to_detist_pass
        val dentistNumberShowenCallHimPass = Res.string.dentist_number_showen_call_him_pass
        val dialerOpenedPass = Res.string.dialer_opened_pass
        val goingBackToAppsScreenPass = Res.string.going_back_to_apss_screen_pass
        val pressTheDialButtonThatShowenDownPass = Res.string.press_the_dial_button_that_showen_down_pass
        val pressTheNumberOrTheDialButtonPass = Res.string.press_the_number_or_the_dial_button_pass
        val searchAtLatterHPass = Res.string.search_at_latter_h_pass
        val searchDentistNumberPass = Res.string.search_dentist_number_pass
        val searchForHanaChoenInContactsPass = Res.string.search_for_hana_choen_in_contacts_pass
        val searchContactsListInThePhonePass = Res.string.search_contacts_list_in_the_phone_pass
        val nowTheContactsListWillBeOpenedPass = Res.string.now_the_contacts_list_will_be_opened_pass
        val returnButtonOnTopLeftPass = Res.string.return_button_on_top_left_pass
        val witchContactAreWeLookingForPass = Res.string.witch_contact_are_we_looking_for_pass
        val wrongNumberDialedPleaseTryAgainPass = Res.string.wrong_number_dialed_please_try_again_pass
        val firstMissionDoneVocalPass = Res.string.first_mission_done_vocal_pass

    }

}