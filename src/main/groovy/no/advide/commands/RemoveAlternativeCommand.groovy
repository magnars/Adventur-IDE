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

  RemoveAlternativeCommand(List<String> strings) {
    if (strings.size() != 1) throw new IllegalArgumentException("takes 1 line");
    input = strings.first()
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    [new FormattedLine(text: input)]
  }

  @Override
  List<String> toNewScript() {
    [input]
  }
}
