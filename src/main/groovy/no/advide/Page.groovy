package no.advide

import no.advide.commands.CommandList

class Page {
  Document document
  CommandList commands

  Page() {}

  Page(document, commands) {
    this.document = document
    this.commands = commands
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
