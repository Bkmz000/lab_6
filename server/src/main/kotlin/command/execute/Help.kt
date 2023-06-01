package server.command.execute

import command.execute.AllExecuteCommands
import execute.packets.ExecutePacket
import org.koin.core.component.KoinComponent
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredMemberProperties

class Help(executePacket: ExecutePacket) : ClientCommand(executePacket), KoinComponent {

    override val name: String = "help"

    private val commandNames  = AllExecuteCommands

    companion object{
        const val info = "Show all commands"
    }


    override fun execute(): String {
        val listOfCommandsWithInfo = ArrayList<String>()

        for(command in commandNames.classesWithNames){
            val propertyWithTheInfo = command.value.companionObject?.declaredMemberProperties?.find { it.name == "info" }
            if(propertyWithTheInfo != null){
                val infoOfCommand = propertyWithTheInfo.getter.call(command.value.companionObjectInstance)
                listOfCommandsWithInfo.add("${command.key} - $infoOfCommand")
            } else {
                listOfCommandsWithInfo.add("${command.key} - no info yet")
            }
        }

        return if (listOfCommandsWithInfo.isNotEmpty()){
            return listOfCommandsWithInfo.toFormatString()
        } else
            "No information about the commands"

    }

    private fun ArrayList<String>.toFormatString() : String{
        val builder = StringBuilder()
        builder.append("The list of all commands:\n")
        for (item in this){
            builder.append("\n")
                .append(item)

        }
        return builder.toString()
    }



}