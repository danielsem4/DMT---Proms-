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
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.Understanding_dialog_text
import dmt_proms.ui.core.generated.resources.Whatsapp
import dmt_proms.ui.core.generated.resources.activities
import dmt_proms.ui.core.generated.resources.app_name
import dmt_proms.ui.core.generated.resources.apps_page_second_assist
import dmt_proms.ui.core.generated.resources.are_you_sure
import dmt_proms.ui.core.generated.resources.arrow_up
import dmt_proms.ui.core.generated.resources.asterisk
import dmt_proms.ui.core.generated.resources.back
import dmt_proms.ui.core.generated.resources.back_confirmation_message
import dmt_proms.ui.core.generated.resources.balloon
import dmt_proms.ui.core.generated.resources.bin
import dmt_proms.ui.core.generated.resources.black_messages
import dmt_proms.ui.core.generated.resources.black_phone
import dmt_proms.ui.core.generated.resources.black_video
import dmt_proms.ui.core.generated.resources.calculator
import dmt_proms.ui.core.generated.resources.call_hana_cohen_pass
import dmt_proms.ui.core.generated.resources.call_to_dentist
import dmt_proms.ui.core.generated.resources.call_to_detist_pass
import dmt_proms.ui.core.generated.resources.call_to_hana_cohen_instruction_pass
import dmt_proms.ui.core.generated.resources.camera
import dmt_proms.ui.core.generated.resources.chat
import dmt_proms.ui.core.generated.resources.check
import dmt_proms.ui.core.generated.resources.chicken
import dmt_proms.ui.core.generated.resources.circle_outlined
import dmt_proms.ui.core.generated.resources.clock
import dmt_proms.ui.core.generated.resources.clock_test
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
import dmt_proms.ui.core.generated.resources.default_name
import dmt_proms.ui.core.generated.resources.default_phone
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
import dmt_proms.ui.core.generated.resources.disk_full
import dmt_proms.ui.core.generated.resources.document_share
import dmt_proms.ui.core.generated.resources.documents
import dmt_proms.ui.core.generated.resources.done
import dmt_proms.ui.core.generated.resources.dont_forget
import dmt_proms.ui.core.generated.resources.drop_down
import dmt_proms.ui.core.generated.resources.drop_up
import dmt_proms.ui.core.generated.resources.dropdown_instructions_app_trial
import dmt_proms.ui.core.generated.resources.dropdown_selected_number
import dmt_proms.ui.core.generated.resources.edit_text_dialog_instruction
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
import dmt_proms.ui.core.generated.resources.elipsa_outlined
import dmt_proms.ui.core.generated.resources.email
import dmt_proms.ui.core.generated.resources.empty_pass
import dmt_proms.ui.core.generated.resources.end
import dmt_proms.ui.core.generated.resources.entry_Oriantation_button_text
import dmt_proms.ui.core.generated.resources.entry_Oriantation_instructions
import dmt_proms.ui.core.generated.resources.entry_Oriantation_title
import dmt_proms.ui.core.generated.resources.entry_Oriantation_welcome_note
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
import dmt_proms.ui.core.generated.resources.entry_instructions_app_trial
import dmt_proms.ui.core.generated.resources.error_icon
import dmt_proms.ui.core.generated.resources.evaluation
import dmt_proms.ui.core.generated.resources.exclamation_mark
import dmt_proms.ui.core.generated.resources.exercise
import dmt_proms.ui.core.generated.resources.exit
import dmt_proms.ui.core.generated.resources.eye
import dmt_proms.ui.core.generated.resources.family_doctor_pass
import dmt_proms.ui.core.generated.resources.feeling_rate_mid
import dmt_proms.ui.core.generated.resources.feeling_rate_no_pain
import dmt_proms.ui.core.generated.resources.feeling_rate_pain
import dmt_proms.ui.core.generated.resources.file_upload
import dmt_proms.ui.core.generated.resources.fill_fields
import dmt_proms.ui.core.generated.resources.filled_circle
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
import dmt_proms.ui.core.generated.resources.football
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
import dmt_proms.ui.core.generated.resources.graphs
import dmt_proms.ui.core.generated.resources.hana_cohen
import dmt_proms.ui.core.generated.resources.hash
import dmt_proms.ui.core.generated.resources.here_persons_number
import dmt_proms.ui.core.generated.resources.hexagon
import dmt_proms.ui.core.generated.resources.chat
import dmt_proms.ui.core.generated.resources.circle_outlined
import dmt_proms.ui.core.generated.resources.clock_test
import dmt_proms.ui.core.generated.resources.default_name
import dmt_proms.ui.core.generated.resources.default_phone
import dmt_proms.ui.core.generated.resources.disk_full
import dmt_proms.ui.core.generated.resources.day_monday
import dmt_proms.ui.core.generated.resources.day_sunday
import dmt_proms.ui.core.generated.resources.day_thursday
import dmt_proms.ui.core.generated.resources.day_tuesday
import dmt_proms.ui.core.generated.resources.day_wednesday
import dmt_proms.ui.core.generated.resources.document_share
import dmt_proms.ui.core.generated.resources.done
import dmt_proms.ui.core.generated.resources.dont_forget
import dmt_proms.ui.core.generated.resources.elipsa_outlined
import dmt_proms.ui.core.generated.resources.empty_pass
import dmt_proms.ui.core.generated.resources.evaluation
import dmt_proms.ui.core.generated.resources.exercise
import dmt_proms.ui.core.generated.resources.exit
import dmt_proms.ui.core.generated.resources.eye
import dmt_proms.ui.core.generated.resources.file_upload
import dmt_proms.ui.core.generated.resources.fill_fields
import dmt_proms.ui.core.generated.resources.filled_circle
import dmt_proms.ui.core.generated.resources.football
import dmt_proms.ui.core.generated.resources.graphs
import dmt_proms.ui.core.generated.resources.hash
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
import dmt_proms.ui.core.generated.resources.key_0
import dmt_proms.ui.core.generated.resources.key_1
import dmt_proms.ui.core.generated.resources.key_2
import dmt_proms.ui.core.generated.resources.key_3
import dmt_proms.ui.core.generated.resources.key_4
import dmt_proms.ui.core.generated.resources.key_5
import dmt_proms.ui.core.generated.resources.key_6
import dmt_proms.ui.core.generated.resources.key_7
import dmt_proms.ui.core.generated.resources.key_8
import dmt_proms.ui.core.generated.resources.key_9
import dmt_proms.ui.core.generated.resources.key_hash
import dmt_proms.ui.core.generated.resources.key_star
import dmt_proms.ui.core.generated.resources.lemon
import dmt_proms.ui.core.generated.resources.like
import dmt_proms.ui.core.generated.resources.listening
import dmt_proms.ui.core.generated.resources.loading
import dmt_proms.ui.core.generated.resources.local_unknown
import dmt_proms.ui.core.generated.resources.lock
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.login_success
import dmt_proms.ui.core.generated.resources.logout
import dmt_proms.ui.core.generated.resources.logout_confirmation
import dmt_proms.ui.core.generated.resources.measurements
import dmt_proms.ui.core.generated.resources.med_icon
import dmt_proms.ui.core.generated.resources.medications
import dmt_proms.ui.core.generated.resources.memory
import dmt_proms.ui.core.generated.resources.messages
import dmt_proms.ui.core.generated.resources.milk
import dmt_proms.ui.core.generated.resources.my_files
import dmt_proms.ui.core.generated.resources.napkin
import dmt_proms.ui.core.generated.resources.neurologist_pass
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.next_text
import dmt_proms.ui.core.generated.resources.no
import dmt_proms.ui.core.generated.resources.no_evaluation_object_to_display
import dmt_proms.ui.core.generated.resources.no_internet
import dmt_proms.ui.core.generated.resources.now_the_contacts_list_will_be_opened_pass
import dmt_proms.ui.core.generated.resources.off
import dmt_proms.ui.core.generated.resources.on
import dmt_proms.ui.core.generated.resources.open_bin
import dmt_proms.ui.core.generated.resources.open_fridge
import dmt_proms.ui.core.generated.resources.ophthalmologist_pass
import dmt_proms.ui.core.generated.resources.orange_juice
import dmt_proms.ui.core.generated.resources.oriantation_season_title
import dmt_proms.ui.core.generated.resources.orthopedist_pass
import dmt_proms.ui.core.generated.resources.paint_clinic_pass
import dmt_proms.ui.core.generated.resources.pass
import dmt_proms.ui.core.generated.resources.password
import dmt_proms.ui.core.generated.resources.peas
import dmt_proms.ui.core.generated.resources.pencil
import dmt_proms.ui.core.generated.resources.pentagon
import dmt_proms.ui.core.generated.resources.person_names
import dmt_proms.ui.core.generated.resources.phone
import dmt_proms.ui.core.generated.resources.phone_number
import dmt_proms.ui.core.generated.resources.pill
import dmt_proms.ui.core.generated.resources.plus
import dmt_proms.ui.core.generated.resources.press_the_dial_button_that_showen_down_pass
import dmt_proms.ui.core.generated.resources.press_the_number_or_the_dial_button_pass
import dmt_proms.ui.core.generated.resources.previous
import dmt_proms.ui.core.generated.resources.profile
import dmt_proms.ui.core.generated.resources.psychiatrist_pass
import dmt_proms.ui.core.generated.resources.purse
import dmt_proms.ui.core.generated.resources.rectangle
import dmt_proms.ui.core.generated.resources.remote_unknown
import dmt_proms.ui.core.generated.resources.request_timeout
import dmt_proms.ui.core.generated.resources.return_button_on_top_left_pass
import dmt_proms.ui.core.generated.resources.rhombus
import dmt_proms.ui.core.generated.resources.rotate_star
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
import dmt_proms.ui.core.generated.resources.uploading
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

import dmt_proms.ui.core.generated.resources.rate
import dmt_proms.ui.core.generated.resources.app
import dmt_proms.ui.core.generated.resources.audioMemory
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
import dmt_proms.ui.core.generated.resources.select_date
import dmt_proms.ui.core.generated.resources.empty_string
import dmt_proms.ui.core.generated.resources.week_day_s
import dmt_proms.ui.core.generated.resources.week_day_m
import dmt_proms.ui.core.generated.resources.week_day_tu
import dmt_proms.ui.core.generated.resources.week_day_w
import dmt_proms.ui.core.generated.resources.week_day_th
import dmt_proms.ui.core.generated.resources.week_day_f
import dmt_proms.ui.core.generated.resources.week_day_sa
import dmt_proms.ui.core.generated.resources.report_medication_taken
import dmt_proms.ui.core.generated.resources.report_message
import dmt_proms.ui.core.generated.resources.save
import dmt_proms.ui.core.generated.resources.date
import dmt_proms.ui.core.generated.resources.time
import dmt_proms.ui.core.generated.resources.dash
import dmt_proms.ui.core.generated.resources.select_date_label
import dmt_proms.ui.core.generated.resources.select_time_label
import dmt_proms.ui.core.generated.resources.search_hint
import dmt_proms.ui.core.generated.resources.report_medication
import dmt_proms.ui.core.generated.resources.set_medication_alarm
import dmt_proms.ui.core.generated.resources.daily
import dmt_proms.ui.core.generated.resources.weekly
import dmt_proms.ui.core.generated.resources.start_date_empty
import dmt_proms.ui.core.generated.resources.end_before_start
import dmt_proms.ui.core.generated.resources.back
import dmt_proms.ui.core.generated.resources.when_notification
import dmt_proms.ui.core.generated.resources.how_many_times_per_day
import dmt_proms.ui.core.generated.resources.select_week_days
import dmt_proms.ui.core.generated.resources.start_time
import dmt_proms.ui.core.generated.resources.notification_start_question
import dmt_proms.ui.core.generated.resources.start_date
import dmt_proms.ui.core.generated.resources.end_date
import dmt_proms.ui.core.generated.resources.first_one_minute_end_body_memory
import dmt_proms.ui.core.generated.resources.leave_end_date_empty
import dmt_proms.ui.core.generated.resources.place_instructions_in_calendar_memory



import dmt_proms.ui.core.generated.resources.profile
import dmt_proms.ui.core.generated.resources.psychiatrist_pass
import dmt_proms.ui.core.generated.resources.purse
import dmt_proms.ui.core.generated.resources.return_button_on_top_left_pass
import dmt_proms.ui.core.generated.resources.rotate_star
import dmt_proms.ui.core.generated.resources.search
import dmt_proms.ui.core.generated.resources.search_at_latter_h_pass
import dmt_proms.ui.core.generated.resources.search_contacts_list_in_the_phone_pass
import dmt_proms.ui.core.generated.resources.search_dentist_number_pass
import dmt_proms.ui.core.generated.resources.search_for_hana_choen_in_contacts_pass
import dmt_proms.ui.core.generated.resources.seasons_instructions_app_trial
import dmt_proms.ui.core.generated.resources.seasons_instructions_app_trial2
import dmt_proms.ui.core.generated.resources.sec_question_instraction
import dmt_proms.ui.core.generated.resources.sec_question_number
import dmt_proms.ui.core.generated.resources.sec_question_title
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
import dmt_proms.ui.core.generated.resources.selected_point
import dmt_proms.ui.core.generated.resources.send
import dmt_proms.ui.core.generated.resources.sent_successfully
import dmt_proms.ui.core.generated.resources.serialization_error
import dmt_proms.ui.core.generated.resources.server_error
import dmt_proms.ui.core.generated.resources.set_health_rate
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
import dmt_proms.ui.core.generated.resources.spin_360
import dmt_proms.ui.core.generated.resources.stand_from_chair
import dmt_proms.ui.core.generated.resources.star_outlined
import dmt_proms.ui.core.generated.resources.start
import dmt_proms.ui.core.generated.resources.store
import dmt_proms.ui.core.generated.resources.summary_hitber_instructions1
import dmt_proms.ui.core.generated.resources.summary_hitber_instructions2
import dmt_proms.ui.core.generated.resources.summary_hitber_title
import dmt_proms.ui.core.generated.resources.table
import dmt_proms.ui.core.generated.resources.take_pills
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
import dmt_proms.ui.core.generated.resources.too_many_requests
import dmt_proms.ui.core.generated.resources.trapeze
import dmt_proms.ui.core.generated.resources.trial_drag_instructions
import dmt_proms.ui.core.generated.resources.trial_drag_title
import dmt_proms.ui.core.generated.resources.trial_draw_instructions
import dmt_proms.ui.core.generated.resources.trial_draw_title
import dmt_proms.ui.core.generated.resources.trial_end_test_instructions
import dmt_proms.ui.core.generated.resources.trial_pinch_instructions
import dmt_proms.ui.core.generated.resources.trial_pinch_title
import dmt_proms.ui.core.generated.resources.triangle
import dmt_proms.ui.core.generated.resources.triangle_90
import dmt_proms.ui.core.generated.resources.unexpected_error
import dmt_proms.ui.core.generated.resources.uploading
import dmt_proms.ui.core.generated.resources.vertical_stroke
import dmt_proms.ui.core.generated.resources.video
import dmt_proms.ui.core.generated.resources.vocal_instructions
import dmt_proms.ui.core.generated.resources.warning
import dmt_proms.ui.core.generated.resources.weather
import dmt_proms.ui.core.generated.resources.welcome
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
import dmt_proms.ui.core.generated.resources.x
import dmt_proms.ui.core.generated.resources.yes
import dmt_proms.ui.core.generated.resources.yoga
import dmt_proms.ui.core.generated.resources.yogurt
import dmt_proms.ui.core.generated.resources.how_well_do_you_feel_memory
import dmt_proms.ui.core.generated.resources.old_phone_ring
import dmt_proms.ui.core.generated.resources.place_rate_memory
import dmt_proms.ui.core.generated.resources.the_rate_is
import dmt_proms.ui.core.generated.resources.how_well_do_you_think_you_will_do_memory
import dmt_proms.ui.core.generated.resources.instructions_text_memory_question_trial
import dmt_proms.ui.core.generated.resources.the_activities_memory
import dmt_proms.ui.core.generated.resources.instructions_text_memory_question_1
import dmt_proms.ui.core.generated.resources. second_one_minute_end_body_memory
import dmt_proms.ui.core.generated.resources.time_ended
import dmt_proms.ui.core.generated.resources.time_ended_for_this_question_memory
import dmt_proms.ui.core.generated.resources.please_place_all_activities_memory
import dmt_proms.ui.core.generated.resources.dateMedications
import dmt_proms.ui.core.generated.resources.took
import dmt_proms.ui.core.generated.resources.timeMedications
import dmt_proms.ui.core.generated.resources.error_start_date_empty
import dmt_proms.ui.core.generated.resources.error_end_before_start
import dmt_proms.ui.core.generated.resources.you_in_the_autumn
import dmt_proms.ui.core.generated.resources.you_in_the_spring
import dmt_proms.ui.core.generated.resources.you_in_the_summer
import dmt_proms.ui.core.generated.resources.you_in_the_winter
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
        val arrowUpIcon = Res.drawable.arrow_up
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
        val blackMessages = Res.drawable.black_messages
        val blackPhone = Res.drawable.black_phone
        val blackVideo = Res.drawable.black_video
        val calculatorIcon = Res.drawable.calculator
        val cameraIcon = Res.drawable.camera
        val chicken = Res.drawable.chicken
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
        val errorIcon = Res.drawable.error_icon
        val exclamationMark = Res.drawable.exclamation_mark
        val grapes = Res.drawable.grapes
        val lemon = Res.drawable.lemon
        val likeIcon = Res.drawable.like
        val milk = Res.drawable.milk
        val openFridge = Res.drawable.open_fridge
        val orangeJuice = Res.drawable.orange_juice
        val peas = Res.drawable.peas
        val profileIcon = Res.drawable.profile
        val purseIcon = Res.drawable.purse
        val rotateStar = Res.drawable.rotate_star
        val searchIcon = Res.drawable.search
        val settingsIcon = Res.drawable.settings
        val speaker = Res.drawable.speaker
        val storeIcon = Res.drawable.store
        val table = Res.drawable.table
        val weatherIcon = Res.drawable.weather
        val whatsappIcon = Res.drawable.whatsapp
        val whiteMessages = Res.drawable.white_messages
        val whitePhone = Res.drawable.white_phone
        val whiteVideo = Res.drawable.white_video
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
        val selected_point = rs.selected_point
        val loading = rs.loading
        val on = Res.string.on
        val off = Res.string.off
        val uploading = rs.uploading
        val send = rs.send
        val measurements = rs.measurements
        val password = rs.password
        val document_share = rs.document_share
        val chat = rs.chat
        val memory = rs.memory
        val hitber = rs.hitber
        val pass = rs.pass
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
        val evaluationText = rs.evaluation

        val took =rs.took
        val dateMedications = rs.dateMedications
        val timeMedications = rs.timeMedications

        val logoutConfirmation = rs.logout_confirmation
        val areYouSure = Res.string.are_you_sure
        val backConfirmationMessage = Res.string.back_confirmation_message

        val done = rs.done
        val no_evaluation_object_to_display = rs.no_evaluation_object_to_display


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
        val error_start_date_empty =rs.error_start_date_empty
        val error_end_before_start =rs.error_end_before_start

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
        val firstQuestionHitberDropDownDropUpIcon =
            Res.string.first_question_hitber_Drop_down_drop_up_Icon

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
        val secondQuestionHitberDialogInstructions =
            Res.string.second_question_hitber_dialog_instructions
        val secondQuestionHitberDialogTitle = Res.string.second_question_hitber_dialog_title
        val secondQuestionHitberTaskInstructions =
            Res.string.second_question_hitber_task_instructions
        val secondQuestionHitberTaskRetryInstructions =
            Res.string.second_question_hitber_task_retry_instructions
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

        val sixthQuestionDefaultMilkToRedNapkinHitber =
            Res.string.sixth_question_default_milk_to_red_napkin_hitber
        val sixthQuestionGrapesToBlueNapkinHitber =
            Res.string.sixth_question_grapes_to_blue_napkin_hitber
        val sixthQuestionChickenToGreenNapkinHitber =
            Res.string.sixth_question_chicken_to_green_napkin_hitber
        val sixthQuestionCanToYellowNapkinHitber =
            Res.string.sixth_question_can_to_yellow_napkin_hitber

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

        val defaultName = Res.string.default_name
        val defaultPhone = Res.string.default_phone

        val key1 = Res.string.key_1
        val key2 = Res.string.key_2
        val key3 = Res.string.key_3
        val key4 = Res.string.key_4
        val key5 = Res.string.key_5
        val key6 = Res.string.key_6
        val key7 = Res.string.key_7
        val key8 = Res.string.key_8
        val key9 = Res.string.key_9
        val key0 = Res.string.key_0
        val keyHash = Res.string.key_hash
        val keyStar = Res.string.key_star

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
        val pressTheDialButtonThatShowenDownPass =
            Res.string.press_the_dial_button_that_showen_down_pass
        val pressTheNumberOrTheDialButtonPass = Res.string.press_the_number_or_the_dial_button_pass
        val searchAtLatterHPass = Res.string.search_at_latter_h_pass
        val searchDentistNumberPass = Res.string.search_dentist_number_pass
        val searchForHanaChoenInContactsPass = Res.string.search_for_hana_choen_in_contacts_pass
        val searchContactsListInThePhonePass = Res.string.search_contacts_list_in_the_phone_pass
        val nowTheContactsListWillBeOpenedPass =
            Res.string.now_the_contacts_list_will_be_opened_pass
        val returnButtonOnTopLeftPass = Res.string.return_button_on_top_left_pass
        val witchContactAreWeLookingForPass = Res.string.witch_contact_are_we_looking_for_pass
        val wrongNumberDialedPleaseTryAgainPass =
            Res.string.wrong_number_dialed_please_try_again_pass
        val firstMissionDoneVocalPass = Res.string.first_mission_done_vocal_pass


        //Medications
        val selectDate = rs.select_date
        val emptyString = rs.empty_string
        val weekDayS = rs.week_day_s
        val weekDayM = rs.week_day_m
        val weekDayTu = rs.week_day_tu
        val weekDayW = rs.week_day_w
        val weekDayTh =rs.week_day_th
        val weekDayF = rs.week_day_f
        val weekDaySa = rs.week_day_sa
        val reportMedicationTaken = rs.report_medication_taken
        val reportMessage =rs.report_message
        val save = rs.save
        val date = rs.date
        val time = rs.time
        val dash = rs.dash
        val selectDateLabel = rs.select_date_label
        val selectTimeLabel = rs.select_time_label
        val searchHint = rs.search_hint
        val reportMedication = rs.report_medication
        val setMedicationAlarm = rs.set_medication_alarm
        val daily = rs.daily
        val weekly = rs.weekly
        val startDateEmpty = rs.start_date_empty
        val endBeforeStart = rs.end_before_start
        val back = rs.back
        val whenNotification =rs.when_notification
        val howManyTimesPerDay = rs.how_many_times_per_day
        val selectWeekDays = rs.select_week_days
        val startTime = rs.start_time
        val notificationStartQuestion = rs.notification_start_question
        val startDate = rs.start_date
        val endDate = rs.end_date
        val leaveEndDateEmpty = rs.leave_end_date_empty

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
        val instructions_text_memory_question_trial = rs.instructions_text_memory_question_trial
        val rate = rs.rate
        val audioMemory = rs.audioMemory
        val old_phone_ring = rs.old_phone_ring
        val place_rate_memory = rs.place_rate_memory
        val the_rate_is = rs.the_rate_is
        val how_well_do_you_think_you_will_do_memory = rs.how_well_do_you_think_you_will_do_memory
        val how_well_do_you_feel_memory = rs.how_well_do_you_feel_memory
        val the_activities_memory = rs.the_activities_memory
        val instructions_text_memory_question_1 = rs.instructions_text_memory_question_1
        val place_instructions_in_calendar_memory =rs.place_instructions_in_calendar_memory

        val time_ended_for_this_question_memory = rs.time_ended_for_this_question_memory
        val time_ended= rs.time_ended

        val second_one_minute_end_body_memory = rs.second_one_minute_end_body_memory
        val first_one_minute_end_body_memory = rs.first_one_minute_end_body_memory
        val please_place_all_activities_memory =rs.please_place_all_activities_memory

// orientation string
    val entryOrientationTitle = Res.string.entry_Oriantation_title
        val entryOrientationWelcomeNote = Res.string.entry_Oriantation_welcome_note
        val entryOrientationInstructions = Res.string.entry_Oriantation_instructions
        val NextText = Res.string.next_text
        val trialDragInstructions = Res.string.trial_drag_instructions
        val trialDragTitle = Res.string.trial_drag_title
        val secQuestionTitle = Res.string.sec_question_title
        val secQuestionInstruction = Res.string.sec_question_instraction
        val secQuestionNumber = Res.string.sec_question_number

        val entryInstructionsAppTrial = Res.string.entry_instructions_app_trial
        val dropdownInstructionsAppTrial = Res.string.dropdown_instructions_app_trial
        val seasonsInstructionsAppTrial = Res.string.seasons_instructions_app_trial
        val orientationSeasonTitle = Res.string.oriantation_season_title


        val trialPinchTitle = Res.string.trial_pinch_title
        val trialPinchInstructions = Res.string.trial_pinch_instructions

        val seasonsInstructionsAppTrial2 = Res.string.seasons_instructions_app_trial2
        val trialDrawInstructions = Res.string.trial_draw_instructions
        val trialEndTestInstructions = Res.string.trial_end_test_instructions
        val editTextDialogInstruction = Res.string.edit_text_dialog_instruction

        val youInTheSummer = Res.string.you_in_the_summer
        val youInTheWinter = Res.string.you_in_the_winter
        val youInTheSpring = Res.string.you_in_the_spring
        val youInTheAutumn = Res.string.you_in_the_autumn

        val vocalInstructions = Res.string.vocal_instructions
        val listening = Res.string.listening
        val feelingRatePain = Res.string.feeling_rate_pain
        val feelingRateMid = Res.string.feeling_rate_mid
        val feelingRateNoPain = Res.string.feeling_rate_no_pain
        val dropdownSelectedNumber = Res.string.dropdown_selected_number

        val trialDrawTitle = Res.string.trial_draw_title

        val setHealthRate = Res.string.set_health_rate


    }


}