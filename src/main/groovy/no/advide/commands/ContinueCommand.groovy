package no.advide.commands

import no.advide.DocumentFragment
import no.advide.FormattedLine

class ContinueCommand extends Command {

  static boolean matches(DocumentFragment fragment) {
    fragment.lines.first() in ["!!!", "-- fortsett --"]
  }

  static int numMatchingLines(DocumentFragment fragment) {
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

  @Override
  List<String> toOldStyle() {
    ["!!!"]
  }

  @Override
  List<String> toNewStyle() {
    ["-- fortsett --"]
  }

}
