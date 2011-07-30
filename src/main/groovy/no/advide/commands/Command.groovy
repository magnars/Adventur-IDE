package no.advide.commands

import no.advide.FormattedLine

abstract class Command {

  abstract List<FormattedLine> getFormattedLines()

  abstract List<String> toNewScript()

  void updateDocument() {}

}
