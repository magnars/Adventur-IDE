package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine

abstract class Command {

  DocumentFragment fragment

  public Command(fragment) {
    this.fragment = fragment
  }

  Color getColor() {
    new Color(60, 60, 200)
  }

  List<FormattedLine> getFormattedLines() {
    fragment.lines.collect { new FormattedLine(text: it, color: color)}
  }

  List<String> toOldScript() {
    fragment.lines
  }

}
