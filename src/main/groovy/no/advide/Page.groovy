package no.advide

import no.advide.commands.CommandList
import no.advide.commands.CommandParser

class Page {
  Document document
  CommandList commands

  Page(room) {
    document = new Document(room.lines, room.cursor)
    commands = new CommandParser(document).parse()
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

}
