package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.template.Weather


val mainScenario = Scenario {
    state("start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        action {
            reactions.run {
                //image("https://media.giphy.com/media/ICOgUNjpvO0PC/source.gif")
                say(
                    "Hello! I'm the Weather Bot, and i can show you current weather forecast in any city," +
                            " that is available in my data base. Just write, that you want to see the weather. So, how can I help you:)?"
                )
                /*
                buttons(
                    "Help me!",
                    "How are you?",
                    "What is your name?"
                )
                */
            }
        }
    }
    state("showmeweather") {
        activators {
            intent("showmeweather")
        }
        action {
            reactions.say(
                "Please, write the name of the city, in which you want to see the weather"
            )
            // reactions.image("https://ru.meteotrend.com/tpl/images/meteotrend_sun_and_cloud2.png")
        }
        state("cityname") {
            activators {
                catchAll()
            }
            action {
                val message = request.input
                //reactions.say("Weather in city " + message + " is loading" )
                val weather = Weather()
                val result = weather.getWeatherData(message)
                reactions.say(result)
                reactions.image(weather.getWeatherIcon(message))
                reactions.say("You can write the next city to know weather")
            }

        }
    }

    state("bye") {
        activators {
            intent("Bye")
        }

        action {
            reactions.sayRandom(
                "See you soon!",
                "Bye-bye!"
            )
            reactions.image("https://media.giphy.com/media/EE185t7OeMbTy/source.gif")
        }
    }

    state("smalltalk", noContext = true) {
        activators {
            anyIntent()
        }

        action(caila) {
            activator.topIntent.answer?.let { reactions.say(it) } ?: reactions.go("/fallback")
        }
    }

    fallback {
        reactions.sayRandom(
            "Sorry, I didn't get that...",
            "Sorry, could you repeat please?",
            "Something goes wrong"
        )
    }
}