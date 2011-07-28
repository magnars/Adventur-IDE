package no.advide.commands

import no.advide.FormattedLine

class UnknownCommand implements Command {

  String input

  UnknownCommand(String input) {
    this.input = input
  }

  List<FormattedLine> getLines() {
    return [new FormattedLine(text: this.input)]
  }
}
