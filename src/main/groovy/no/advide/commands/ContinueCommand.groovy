package no.advide.commands

import no.advide.FormattedLine

class ContinueCommand extends Command {
  static boolean matches(List<String> strings, int i) {
    strings[i] in ["!!!", "-- fortsett --"]
  }

  static int numMatchingLines(List<String> strings, int i) {
    1
  }

  ContinueCommand(fragment) {
    super(fragment)
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    def lines = super.formattedLines
    lines.first().hasSeparatorLine = true
    return lines
  }

}
