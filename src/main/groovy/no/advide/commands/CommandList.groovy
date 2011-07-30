package no.advide.commands

import no.advide.FormattedLine

class CommandList extends ArrayList<Command> {

  List<FormattedLine> getFormattedLines() {
    (List<FormattedLine>) this*.formattedLines.flatten()
  }

  List<String> toNewScript() {
    (List<String>) collect { it.toNewScript() }.flatten()
  }

  void updateDocument() {
    each { it.updateDocument() }
  }
}
