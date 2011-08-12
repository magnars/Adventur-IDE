package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine
import no.advide.RoomNumber

class AlternativeCommand extends Command {

  List alternatives

  AlternativeCommand(fragment) {
    super(fragment)
  }

  static boolean matches(DocumentFragment fragment) {
    fragment.lines.size() > 1 &&
      fragment.lines[0] in ["-", "+"] &&
      fragment.lines[1] =~ /^\d+$/
  }

  static int numMatchingLines(DocumentFragment fragment) {
    fragment.lines.size()
  }

  @Override
  List<RoomNumber> getRoomNumbers() {
    def numbers= []
    int alternativesStartingPosition = 2
    int linesBetweenAlternatives = fragment.lines.first() == "+" ? 3 : 2
    int numberOffset = 1
    for (int i = alternativesStartingPosition + numberOffset; i < fragment.lines.size(); i += linesBetweenAlternatives) {
      String line = fragment.lines[i];
      if (line.isInteger()) {
        numbers << new RoomNumber(number: line.toInteger(), position: fragment.translate([x:0, y:i]))
      }
    }
    return numbers
  }

  @Override
  Color getColor() {
    Color.black
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    def lines = super.getFormattedLines()
    lines[1].prefix = "Antall alternativer: "
    int linesBetweenAlternatives = fragment.lines.first() == "+" ? 3 : 2
    for (int i = 2; i < fragment.lines.size(); i += linesBetweenAlternatives) {
      lines[i].prefix = "${((i-2)/linesBetweenAlternatives) + 1}. "
      lines[i+1]?.prefix = "Rom#: "
      if (linesBetweenAlternatives > 2) lines[i+2]?.prefix = "Krav: "
    }
    return lines
  }


}
