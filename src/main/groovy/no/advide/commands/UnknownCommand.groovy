package no.advide.commands

import no.advide.FormattedLine
import java.awt.Color

class UnknownCommand extends Command {

  String input

  UnknownCommand(String input) {
    this.input = input
  }

  List<FormattedLine> getLines() {
    return [new FormattedLine(text: this.input, color: Color.black)]
  }

  static boolean matches(Object lines, Object fromIndex) {
    true
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }


}
