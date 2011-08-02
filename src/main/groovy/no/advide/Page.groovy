package no.advide

import no.advide.commands.CommandList
import no.advide.commands.CommandParser
import no.advide.commands.ProseCommand

class Page {
  String name
  File file
  Document document
  CommandList commands
  List<String> original

  Page() {}

  Page(String name, File file) {
    this.name = name
    this.file = file
    document = loadDocument(file)
    parseCommandsAndReformatDocument()
    original = commands.toOldScript()
  }

  private Document loadDocument(File file) {
    new Document(file.readLines('UTF-8'), [x: 0, y: 0])
  }

  private def parseCommandsAndReformatDocument() {
    document.stripTrailingSpaces()
    updateCommands()
    commands.each { it.replaceWithNewStyle() }
  }

  void updateCommands() {
    commands = new CommandParser(document).parse()
    justifyWordsInProse() // er denne riktig her? den har blitt flyttet mye, ser ok ut her enn så lenge
  }

  void justifyWordsInProse() {
    commands.getAll(ProseCommand).each { new WordWrapper(it.fragment).justify() }
  }

  void save() {
    original = commands.toOldScript()
    file.setText(original.join("\n"), 'UTF-8')
  }

  boolean isModified() {
    commands.toOldScript() != original
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

  @Override
  String toString() {
    "Page[$name]"
  }


}
