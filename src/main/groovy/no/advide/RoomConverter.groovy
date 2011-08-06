package no.advide

import no.advide.commands.CommandParser

class RoomConverter {

  static List<String> toNewStyle(lines) {
    lines = replaceWithNewStyle(lines)
    lines = justifyProse(lines)
    return lines
  }

  private static def justifyProse(List<String> lines) {
    def document = new Document(lines + [], [x: 0, y: 0])
    def page = new Page(document)
    page.justifyProse()
    return document.lines
  }

  private static def replaceWithNewStyle(lines) {
    def document = new Document(lines, [x: 0, y: 0])
    document.stripTrailingSpaces()
    new CommandParser(document).parse().each { it.replaceWithNewStyle() }
    return document.lines
  }

  static List<String> toOldStyle(lines) {
    def document = new Document(lines, [x:0, y:0])
    document.stripTrailingSpaces()
    new CommandParser(document).parse().each { it.replaceWithOldStyle() }
    return document.lines
  }

}
