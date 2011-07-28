package no.advide.commands

import no.advide.FormattedLine

interface Command {

  List<FormattedLine> getLines()

}
