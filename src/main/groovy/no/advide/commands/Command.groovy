package no.advide.commands

import no.advide.Cursor
import no.advide.Document
import no.advide.FormattedLine

abstract class Command {

  Cursor cursor
  Cursor localCursor

  abstract List<FormattedLine> getFormattedLines()

  abstract List<String> toNewScript()

  void updateDocument(Document document) {}

  void setCursor(Cursor cursor, localCursorY) {
    this.cursor = cursor
    this.localCursor = new Cursor(x: cursor.x, y: localCursorY)
  }

  boolean isActive() { cursor != null }

}
