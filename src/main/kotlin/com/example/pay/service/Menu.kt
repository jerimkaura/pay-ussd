package com.example.pay.service

import org.springframework.stereotype.Service

@Service
class Menu {
    private lateinit var text: String
    private lateinit var sessionId: String

    fun mainMenuRegistered(name: String): String {
        return "Welcome $name Reply with \n1. Send money \n2. Withdraw \n3. Check balance\n"
    }

    fun mainMenuUnRegistered(): String {
        return "CON Welcome to this app. Reply with\n1. Register\n"
    }

    fun registerMenu(textArray: ArrayList<String>, phoneNumber: String): String {
        when (textArray.size) {
            1 -> {
                return "CON Please enter your full name:"
            }
            2 -> {
                return "CON Please set your PIN:"
            }
            3 -> {
                return "CON Please re-enter your PIN:"
            }
            4 -> {
                val name = textArray[1]
                val pin = textArray[2]
                val confirmPin = textArray[3]
                if (pin != confirmPin) {
                    return "END Your pins do not match. Please try again."
                } else {
                    return "END You have have been registered"
                }
            }
            else -> return ""
        }
    }

    fun sendMoney(textArray: ArrayList<String>, senderPhoneNumber: String): String {
        val receiver: String? = null
        val nameOfReceiver: String? = null
        val response: String = ""
        when (textArray.size) {
            1 -> {
                return "CON Enter mobile number of the receiver:"
            }
            2 -> {
                return "CON Enter amount:"
            }
            3 -> {
                return "CON Enter your PIN:"
            }

            4 -> {
                val receiverMobile = textArray[1]
                val receiverMobileWithCountryCode = addCountryCodeToPhoneNumber(receiverMobile)
                return "Send ${textArray[2]} to <Put a person's name here> - $receiverMobile \n1. Confirm\n2. Cancel\n 98 Back\n 00 Main menu"
            }
            5 -> {
                return if (textArray[4] == "1") {
                    val pin = textArray[3]
                    val amount = textArray[2]
                    "END We are processing your request. You will an SMS shortly"
                } else if (textArray[4] == "2") {
                    "END Cancelled. Thank you for using our service."
                } else if (textArray[4] == "98") {
                    "END You have requested to go back to one step - re-enter PIN"
                } else if (textArray[4] == "00") {
                    "END You have requested to gon back to main menu - to start all over again"
                } else {
                    "END Invalid entry"
                }
            }
            else -> {
                return ""
            }
        }
    }

    private fun addCountryCodeToPhoneNumber(receiverMobile: String): String {
        return "+254${receiverMobile.substring(1)}"
    }

    fun middleWare(text: String): String {
        return goBack(goToMainMenu(text))
    }

    fun withDrawMoneyMenu(textArray: ArrayList<String>): String {
        return "CON To be implemented"
    }

    fun checkBalanceMenu(textArray: ArrayList<String>): String {
        return "CON To be implemented"
    }

    fun goBack(text: String): String {
        val explodedString = text.split("*").toMutableList()
        while (explodedString.contains("98")) {
            val firstIndex = explodedString.indexOf("98")
            explodedString.removeAt(firstIndex)
        }
        return "*$explodedString"
    }

    fun goToMainMenu(text: String): String {
        val explodedString = text.split("*").toMutableList()
        while (explodedString.contains("00")) {
            val firstIndex = explodedString.indexOf("00")
            explodedString.removeAt(firstIndex)
        }
        return "*$explodedString"
    }
}