package no.advide

import no.advide.commands.CommandParser

class RoomConverter {

  static List<String> toNewStyle(List<String> lines) {
    def document = new Document(lines, [x:0, y:0])
    document.stripTrailingSpaces()

    new CommandParser(document).parse().each { it.replaceWithNewStyle() }

    return document.lines
  }

  static List<String> toOldStyle(List<String> lines) {
    def document = new Document(lines, [x:0, y:0])
    document.stripTrailingSpaces()

    new CommandParser(document).parse().each { it.replaceWithOldStyle() }

    return document.lines
  }

}
