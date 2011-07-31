package no.advide.commands

import no.advide.FormattedLine

class CommandList extends ArrayList<Command> {

  List<FormattedLine> getFormattedLines() {
    (List<FormattedLine>) this*.formattedLines.flatten()
  }

  List<Command> getAll(commandType) {
    findAll { it?.class == commandType }
  }
}
