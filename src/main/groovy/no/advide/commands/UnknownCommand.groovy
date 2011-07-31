package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine

class UnknownCommand extends Command {

  String input

  UnknownCommand(DocumentFragment fragment) {
    if (fragment.length != 1) throw new IllegalArgumentException("takes 1 line");
    this.input = fragment.lines.first()
  }

  List<FormattedLine> getFormattedLines() {
    [new FormattedLine(text: this.input, color: Color.black)]
  }

  static boolean matches(Object lines, Object fromIndex) {
    true
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }


}
