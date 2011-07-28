package no.advide.commands

import no.advide.FormattedLine

class CommandList extends ArrayList<Command> {

  List<FormattedLine> getLines() {
    (List<FormattedLine>) this*.lines.flatten()
  }
}
