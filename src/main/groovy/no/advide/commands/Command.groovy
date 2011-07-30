package no.advide.commands

import no.advide.FormattedLine
import no.advide.Cursor

abstract class Command {

  Cursor cursor
  Cursor localCursor

  abstract List<FormattedLine> getFormattedLines()

  abstract List<String> toNewScript()

  void setCursor(Cursor cursor, localCursorY) {
    this.cursor = cursor
    this.localCursor = new Cursor(x: cursor.x, y: localCursorY)
  }

  boolean isActive() { cursor != null }

}
