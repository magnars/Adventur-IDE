package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.Fix
import no.advide.FormattedLine
import no.advide.RoomNumber
import no.advide.ui.Theme

abstract class Command {

  final DocumentFragment fragment

  public Command(fragment) {
    this.fragment = fragment
  }

  Color getColor() {
    Theme.command
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

  List<Fix> getFixes() {
    isInNewStyle() ? [] : [new Fix(fragment.offset.y, { replaceWithNewStyle() })]
  }

  void replaceWithNewStyle() {
    if (!isInNewStyle()) fragment.replaceWith(toNewStyle())
  }

  void replaceWithOldStyle() {
    if (!isInOldStyle()) fragment.replaceWith(toOldStyle())
  }

  void justifyProse(int width) {}

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
