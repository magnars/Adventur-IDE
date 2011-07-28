package no.advide.commands

import no.advide.FormattedLine

class RemoveAlternativeCommand extends Command {

  def input

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^#\d+$/
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }

  RemoveAlternativeCommand(strings) {
    input = strings
  }

  @Override
  List<FormattedLine> getLines() {
    return [new FormattedLine(text: input[0])]
  }
}
