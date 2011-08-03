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

  List<String> toOldStyle() {
    fragment.lines
  }

  List<String> toNewStyle() {
    fragment.lines
  }

  void replaceWithNewStyle() {
    if (!isInNewStyle()) fragment.replaceWith(toNewStyle())
  }

  void replaceWithOldStyle() {
    if (!isInOldStyle()) fragment.replaceWith(toOldStyle())
  }

  void optimizeDocument() {}

  boolean isInOldStyle() {
    fragment.lines == toOldStyle()
  }

  boolean isInNewStyle() {
    fragment.lines == toNewStyle()
  }

  List<RoomNumber> getRoomNumbers() {
    []
  }

}
