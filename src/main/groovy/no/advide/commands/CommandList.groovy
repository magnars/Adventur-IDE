package no.advide.commands

import no.advide.FormattedLine
import no.advide.RoomNumber

class CommandList extends ArrayList<Command> {

  List<FormattedLine> getFormattedLines() {
    (List<FormattedLine>) this*.formattedLines.flatten()
  }

  List<Command> getAll(commandType) {
    findAll { it?.class == commandType }
  }

  List<String> toOldScript() {
    collect { it.toOldScript() }.flatten()
  }

  List<RoomNumber> getRoomNumbers() {
    (List<RoomNumber>) collect { it.getRoomNumbers() }.flatten()
  }
}
