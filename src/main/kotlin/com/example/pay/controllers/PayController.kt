package com.example.pay.controllers

import com.example.pay.model.Ussd
import com.example.pay.service.Menu
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.awt.SystemColor.text


@RestController
@RequestMapping("api/pay")
class PayController(private val menu: Menu) {
    var isUserRegistered: Boolean = false

    @PostMapping(
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun ussd(value: Map<String, String>): String {
        println(value)
        if (value["text"] == "" && isUserRegistered) {
            return "CON ${menu.mainMenuRegistered("<>Put a person's name here")}"
        } else if (value["text"] == "" && isUserRegistered == false) {
            return menu.mainMenuUnRegistered()
        } else if (isUserRegistered == false) {
            val textArray = value["text"]?.split("*")
            when (textArray?.get(0)) {
                "1" -> {
                    menu.registerMenu(textArray as ArrayList<String>, value["phoneNumber"]!!)
                }
                else -> {
                    return "END Invalid choice. Please try again"
                }
            }
        } else {
            val textArray = value["text"]?.split("*")
            when (textArray?.get(0)) {
                "1" -> {
                    menu.sendMoney(textArray as ArrayList<String>, value["sessionId"]!!)
                }
                "2" -> {
                    menu.withDrawMoneyMenu(textArray as ArrayList<String>)
                }
                "3" -> {
                    menu.checkBalanceMenu(textArray as ArrayList<String>)
                }
                else -> {
                    return "END Invalid menu \n"
                }
            }
        }

        return ""
    }
}