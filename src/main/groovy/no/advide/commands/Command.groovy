package no.advide.commands

import no.advide.FormattedLine
import no.advide.Cursor

abstract class Command {

  Cursor cursor
  Integer localCursorY

  abstract List<FormattedLine> getFormattedLines()

  abstract List<String> toNewScript()

  void setCursor(Cursor cursor, localCursorY) {
    this.cursor = cursor
    this.localCursorY = localCursorY
  }

  boolean isActive() { cursor != null }

}
