package no.advide.commands

import java.awt.Color
import no.advide.Adventure
import no.advide.DocumentFragment
import no.advide.FormattedLine

class RemoveAlternativeCommand extends Command {

  def input
  int roomNumber

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^#\d+$/
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }

  RemoveAlternativeCommand(DocumentFragment fragment) {
    if (fragment.length != 1) throw new IllegalArgumentException("takes 1 line");
    input = fragment.lines.first()
    roomNumber = input.substring(1).toInteger()
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    def line = new FormattedLine(text: input)
    if (!Adventure.current.roomExists(roomNumber)) {
      line.formatSubstring(1, Color.red)
    }
    [line]
  }

}
