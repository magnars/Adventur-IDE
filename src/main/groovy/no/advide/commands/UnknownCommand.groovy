package no.advide.commands

import no.advide.FormattedLine
import java.awt.Color

class UnknownCommand extends Command {

  String input

  UnknownCommand(List<String> strings) {
    if (strings.size() != 1) throw new IllegalArgumentException("takes 1 line");
    this.input = strings.first()
  }

  List<FormattedLine> getFormattedLines() {
    [new FormattedLine(text: this.input, color: Color.black)]
  }

  List<String> toNewScript() {
    [this.input]
  }

  static boolean matches(Object lines, Object fromIndex) {
    true
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }


}
