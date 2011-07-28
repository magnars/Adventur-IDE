package no.advide.commands

import no.advide.FormattedLine
import no.advide.Cursor

abstract class Command {

  Cursor cursor

  abstract List<FormattedLine> getLines()

  void setActive(Cursor c) { cursor = c }

  boolean isActive() { return cursor != null }

}
