package no.advide

import no.advide.commands.CommandList
import no.advide.commands.CommandParser

class Page {
  Document document
  CommandList commands

  Page(document) {
    this.document = document
    this.commands = new CommandParser(this.document).parse()
  }

  RoomNumber getTargetRoomNumber() {
    commands.roomNumbers.find { it.position.y >= document.cursor.y }
  }

  RoomNumber getCurrentRoomNumber() {
    commands.roomNumbers.find { it.position.y == document.cursor.y }
  }

  RoomNumber getNextRoomNumber() {
    commands.roomNumbers.find { it.position.y > document.cursor.y }
  }

  RoomNumber getPreviousRoomNumber() {
    def prev = commands.roomNumbers.findAll { it.position.y < document.cursor.y }
    prev.empty ? null : prev.last()
  }

  void moveCursorTo(RoomNumber number) {
    if (number) cursor = number.position
  }

  List<Fix> getFixes() {
    commands.fixes
  }

  Fix getNextFix() {
    commands.fixes.find { it.line >= cursor.y }
  }

  void justifyProse() {
    commands.each { it.justifyProse(80) }
  }

  void changeToOldStyle() {
    commands.each { it.replaceWithOldStyle() }
  }

  void changeToNewStyle() {
    commands.each { it.replaceWithNewStyle() }
  }

  def getCursor() {
    document.cursor
  }

  void setCursor(cursor) {
    document.cursor = cursor
  }

  def getLines() {
    document.lines
  }

}
