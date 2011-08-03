package no.advide

import no.advide.commands.CommandParser
import no.advide.commands.ProseCommand

class RoomConverter {

  static List<String> toNewStyle(List<String> lines) {
    def document = new Document(lines, [x:0, y:0])
    document.stripTrailingSpaces()

    def commands = new CommandParser(document).parse()
    justifyWordsInProse(commands)
    commands.each { it.replaceWithNewStyle() }

    return document.lines
  }

  static List<String> toOldStyle(List<String> lines) {
    def document = new Document(lines, [x:0, y:0])
    document.stripTrailingSpaces()

    new CommandParser(document).parse().each { it.replaceWithOldStyle() }

    return document.lines
  }

  // det er fortsatt noe muffens med denne - kan det være at ProseCommand skal ha .justify() ?
  static void justifyWordsInProse(commands) {
    commands.getAll(ProseCommand).each { new WordWrapper(it.fragment).justify() }
  }

}
