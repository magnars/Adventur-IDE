package no.advide.commands

import no.advide.FormattedLine

class CommandList extends ArrayList<Command> implements Command {

  List<FormattedLine> getLines() {
    (List<FormattedLine>) collect {c -> c.getLines()}.flatten()
  }
}
