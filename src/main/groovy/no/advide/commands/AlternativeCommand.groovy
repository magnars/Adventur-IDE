package no.advide.commands

import java.awt.Color
import no.advide.Alternative
import no.advide.DocumentFragment
import no.advide.FormattedLine
import no.advide.RoomNumber

class AlternativeCommand extends Command {

  AlternativeCommand(fragment) {
    super(fragment)
  }

  static boolean matches(DocumentFragment fragment) {
    matchesOldStyle(fragment) || matchesNewStyle(fragment)
  }

  private static def matchesOldStyle(DocumentFragment fragment) {
    fragment.lines.size() > 1 &&
        fragment.lines[0] in ["-", "+"] &&
        fragment.lines[1] =~ /^ ?\d+$/
  }

  private static def matchesNewStyle(DocumentFragment fragment) {
    fragment.lines.first() == "--"
  }

  static int numMatchingLines(DocumentFragment fragment) {
    fragment.lines.size()
  }

  @Override
  List<RoomNumber> getRoomNumbers() {
    alternatives.collect {
      if (it.room?.isInteger()) new RoomNumber(number: it.room.toInteger(), position: fragment.translate([x:0, y:it.index + 1]))
    }.findAll { it != null }
  }

  @Override
  Color getColor() {
    Color.black
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    def lines = super.getFormattedLines()
    matchesNewStyle(fragment) ? formatNewStyle(lines) : formatOldStyle(lines)
  }

  private def formatOldStyle(List<FormattedLine> lines) {
    lines[1].prefix = "Antall alternativer: "

    alternatives.each { alt ->
      lines[alt.index].prefix = "${alt.number}. "
      lines[alt.index + 1]?.prefix = "Rom#: "
      if (alt.requirement) lines[alt.index + 2]?.prefix = "Krav: "
    }
    lines
  }

  private def formatNewStyle(List<FormattedLine> lines) {
    def extraSpace = alternatives.size() >= 10 ? " " : ""
    alternatives.each { alt ->
      if (alt.number == 10) extraSpace = ""
      lines[alt.index].prefix = "${extraSpace}${alt.number}. "
      lines[alt.index + 1]?.prefix = "--> "
    }
    lines
  }

  @Override
  List<String> toNewStyle() {
    (["--"] + alternatives.collect { it.toNewStyle() }).flatten()
  }

  @Override
  List<String> toOldStyle() {
    if (alternatives.any { it.hasRequirement() }) {
      (["+", alternatives.size().toString()] + alternatives.collect { it.toOldStyle_WithRequirements() }).flatten()
    } else {
      (["-", alternatives.size().toString()] + alternatives.collect { it.toOldStyle_NoRequirements() }).flatten()
    }
  }

  List<Alternative> getAlternatives() {
    matchesNewStyle(fragment) ? getNewStyleAlternatives() : getOldStyleAlternatives()
  }

  List<Alternative> getOldStyleAlternatives() {
    def alternatives = []
    def lines = fragment.lines
    int linesBetweenAlternatives = lines.first() == "+" ? 3 : 2
    for (int i = 2; i < lines.size(); i += linesBetweenAlternatives) {
      alternatives << new Alternative(
          index: i,
          number: ((i-2)/linesBetweenAlternatives) + 1,
          text: lines[i],
          room: lines[i+1],
          requirement: linesBetweenAlternatives == 3 ? lines[i+2] : "-"
      )
    }
    return alternatives
  }

  List<Alternative> getNewStyleAlternatives() {
    def alternatives = []
    def lines = fragment.lines
    int linesBetweenAlternatives = 2
    for (int i = 1; i < lines.size(); i += linesBetweenAlternatives) {
      alternatives << new Alternative(
          index: i,
          number: ((i-1)/2) + 1,
          text: lines[i],
          room: lines[i+1]?.split(" ? ")?.first(),
          requirement: lines[i+1]?.contains(" ? ") ? lines[i+1]?.split(" ? ")?.last() : "-"
      )
    }
    return alternatives
  }

}
