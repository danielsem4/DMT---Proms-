package org.example.hit.heal.core.presentation

import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.app_name
import dmt_proms.ui.core.generated.resources.`continue`
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
import dmt_proms.ui.core.generated.resources.exit
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
import dmt_proms.ui.core.generated.resources.how_do_you_feel
import dmt_proms.ui.core.generated.resources.login
import dmt_proms.ui.core.generated.resources.next
import dmt_proms.ui.core.generated.resources.previous
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
import dmt_proms.ui.core.generated.resources.seventh_question_black_circle
import dmt_proms.ui.core.generated.resources.seventh_question_blue_circle
import dmt_proms.ui.core.generated.resources.seventh_question_green_circle
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_black_circle
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_blue_circle
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_green_circle
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_title
import dmt_proms.ui.core.generated.resources.seventh_question_hitber_yellow_circle
import dmt_proms.ui.core.generated.resources.seventh_question_yellow_circle
import dmt_proms.ui.core.generated.resources.sixth_question_can_to_yellow_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_chicken_to_green_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_default_milk_to_red_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_grapes_to_blue_napkin_hitber
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_Speaker
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_close_fridge
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_item
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_listen
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_napkin
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_open_fridge
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_table
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_title
import dmt_proms.ui.core.generated.resources.sixth_question_hitber_volume_icon
import dmt_proms.ui.core.generated.resources.start
import dmt_proms.ui.core.generated.resources.summary_hitber_instructions1
import dmt_proms.ui.core.generated.resources.summary_hitber_instructions2
import dmt_proms.ui.core.generated.resources.summary_hitber_title
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_shape_image
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_shape_model
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_shapes
import dmt_proms.ui.core.generated.resources.tenth_question_hitber_title
import dmt_proms.ui.core.generated.resources.third_question_hitber_finish_task
import dmt_proms.ui.core.generated.resources.third_question_hitber_instructions
import dmt_proms.ui.core.generated.resources.third_question_hitber_title

/**
 * Resources used in the application.
 * These resources include icons, strings, and other assets
 * This object made to enable the resources to be used in different modules.
 */

object Resources {

    object Icon {}

    object String {
        val appName = Res.string.app_name
        val login = Res.string.login
        val previous = Res.string.previous
        val next = Res.string.next
        val howDoYouFeel = Res.string.how_do_you_feel

        val exit = Res.string.exit
        val `continue` = Res.string.`continue`
        val start = Res.string.start

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

    }

}