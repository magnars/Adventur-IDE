package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine
import no.advide.RoomNumber

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

  List<String> toNewScript() {
    fragment.lines
  }

  void replaceWithNewStyle() {
    if (!isInNewStyle()) fragment.replaceWith(toNewScript())
  }

  void replaceWithOldStyle() {
    if (!isInOldStyle()) fragment.replaceWith(toOldScript())
  }

  boolean isInOldStyle() {
    fragment.lines == toOldScript()
  }

  boolean isInNewStyle() {
    fragment.lines == toNewScript()
  }

  List<RoomNumber> getRoomNumbers() {
    []
  }

}
