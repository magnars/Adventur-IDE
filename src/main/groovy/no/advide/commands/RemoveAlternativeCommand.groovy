package no.advide.commands

import no.advide.FormattedLine
import no.advide.Adventure
import java.awt.Color

class RemoveAlternativeCommand extends Command {

  def input
  int roomNumber

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^#\d+$/
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }

  RemoveAlternativeCommand(List<String> strings) {
    if (strings.size() != 1) throw new IllegalArgumentException("takes 1 line");
    input = strings.first()
    roomNumber = input.substring(1).toInteger()
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    if (Adventure.roomExists(roomNumber)) {
      [new FormattedLine(text: input)]
    } else {
      [new FormattedLine(text: input, color: Color.red)]
    }
  }

  @Override
  List<String> toNewScript() {
    [input]
  }
}
