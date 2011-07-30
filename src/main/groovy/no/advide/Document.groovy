package no.advide

import antlr.StringUtils

class Document {
  List<String> lines
  def cursor = [x: 0, y: 0]
  def lastUpdatedByCommand

  Document(List<String> lines, cursor) {
    this.lines = lines
    this.cursor = cursor
    this.lastUpdatedByCommand = false
  }

  String post() {
    currentLine().substring(cursor.x)
  }

  String pre() {
    currentLine().substring(0, cursor.x)
  }

  String currentLine() {
    lines[cursor.y]
  }

  String previousLine() {
    lines[cursor.y - 1]
  }

  String nextLine() {
    lines[cursor.y + 1]
  }

  boolean atLastLine() {
    lines.size() <= cursor.y + 1
  }

  boolean atEndOfLine() {
    currentLine().size() <= cursor.x
  }

  boolean atFirstLine() {
    cursor.y == 0
  }

  boolean atStartOfLine() {
    cursor.x == 0
  }

  def moveCursor(change) {
    if (change.dx) cursor.x += change.dx
    if (change.dy) cursor.y += change.dy
    if (change.x != null) cursor.x = change.x
    if (change.y != null) cursor.y = change.y
    lastUpdatedByCommand = false
  }

  void moveCursorRight() {
    if (!atEndOfLine()) {
      moveCursor(dx: 1)
    } else if (!atLastLine()) {
      moveCursor(x: 0, dy: 1)
    }
  }

  void moveCursorLeft() {
    if (!atStartOfLine()) {
      moveCursor(dx: -1)
    } else if (!atFirstLine()) {
      moveCursor(dy: -1, x: previousLine().size())
    }
  }

  void moveCursorUp() {
    if (!atFirstLine()) {
      moveCursor(dy: -1, x: Math.min(cursor.x, previousLine().size()))
    } else if (!atStartOfLine()) {
      moveCursor(x: 0)
    }
  }

  void moveCursorDown() {
    if (!atLastLine()) {
      moveCursor(dy: 1, x: Math.min(cursor.x, nextLine().size()))
    } else if (!atEndOfLine()) {
      moveCursor(x: currentLine().size())
    }
  }

  void splitLineAtCursor() {
    def split = [pre(), post()]
    lines.remove(cursor.y)
    lines.addAll(cursor.y, split)
    moveCursor(x: 0, dy: 1)
  }

  void removeCharAtCursor() {
    def pre = currentLine().substring(0, cursor.x - 1)
    lines[cursor.y] = pre + post()
    moveCursor(dx: -1)
  }

  void mergeLineAtCursorWithPrevious() {
    moveCursor(dy: -1, x: previousLine().size())
    lines[cursor.y] += lines[cursor.y + 1]
    lines.remove(cursor.y + 1)
  }

  void insertStringAtCursor(String s) {
    lines[cursor.y] = pre() + s + post()
    moveCursor(dx: s.size())
  }

  /*
   *
   *   Jeg tror ikke updateLines holder seg - commands som vil endre dokumentet
   *   gjør det selv rett på dokumentet istedet --> alle cursor-problemer borte
   *
   */

  void updateLines(List<String> lines) {
    if (!lastUpdatedByCommand) {
      if (textHasMovedUpWithoutCursor(lines)) moveCursor(dy: -1)
      if (textHasMovedDownWithoutCursor(lines)) moveCursor(dy: 1)
    }
    this.lines = stripTrailingSpaces(lines)
  }

  boolean textHasMovedUpWithoutCursor(newLines) {
    def newLinesIsOneShorter = newLines.size() == lines.size() - 1
    def lineAtCursorHasChanged = newLines[cursor.y] != lines[cursor.y]
    def lineAboveMatchesCurrent = newLines[cursor.y - 1] == lines[cursor.y]
    return (newLinesIsOneShorter && lineAtCursorHasChanged && lineAboveMatchesCurrent)
  }

  boolean textHasMovedDownWithoutCursor(newLines) {
    def newLinesIsOneLonger = newLines.size() == lines.size() + 1
    def lineAtCursorHasChanged = newLines[cursor.y] != lines[cursor.y]
    def lineBelowMatchesCurrent = newLines[cursor.y + 1] == lines[cursor.y]
    return (newLinesIsOneLonger && lineAtCursorHasChanged && lineBelowMatchesCurrent)
  }

  private def stripTrailingSpaces(List<String> lines) {
    lines.eachWithIndex { line, i ->
      if (cursor.y != i) lines[i] = StringUtils.stripBack(line, " ")
    }
  }
}
