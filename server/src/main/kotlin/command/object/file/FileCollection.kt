package server.command.`object`.file

import org.koin.core.component.KoinComponent
import java.io.File

object FileCollection : KoinComponent {

    private const val fileName = "C:/itmo/labs/kotlin/server/lab_5/sample.json"

    fun getFile() : File? {
        return if (File(fileName).isFile) {
             File(fileName)
        } else
        {
            null
        }
    }
}