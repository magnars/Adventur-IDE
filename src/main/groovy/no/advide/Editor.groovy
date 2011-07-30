package no.advide

import antlr.StringUtils

class Editor {
  def lines
  Cursor cursor

  def changeCallbacks = []

  def actions = [
      "right": {
        if (!atEndOfLine()) {
          moveCursor(dx: 1)
        } else if (!atLastLine()) {
          moveCursor(x: 0, dy: 1)
        }
      },
      "left": {
        if (!atStartOfLine()) {
          moveCursor(dx: -1)
        } else if (!atFirstLine()) {
          moveCursor(dy: -1, x: previousLine().size())
        }
      },
      "up": {
        if (!atFirstLine()) {
          moveCursor(dy: -1, x: Math.min(cursor.x, previousLine().size()))
        } else if (!atStartOfLine()) {
          moveCursor(x: 0)
        }
      },
      "down": {
        if (!atLastLine()) {
          moveCursor(dy: 1, x: Math.min(cursor.x, nextLine().size()))
        } else if (!atEndOfLine()) {
          moveCursor(x: currentLine().size())
        }
      },
      "enter": {
        def split = [pre(), post()]
        lines.remove(cursor.y)
        lines.addAll(cursor.y, split)
        moveCursor(x: 0, dy: 1)
      },
      "backspace": {
        if (!atStartOfLine()) {
          def pre = currentLine().substring(0, cursor.x - 1)
          lines[cursor.y] = pre + post()
          moveCursor(dx: -1)
        } else if (!atFirstLine()) {
          moveCursor(dy: -1, x: previousLine().size())
          lines[cursor.y] += lines[cursor.y + 1]
          lines.remove(cursor.y + 1)
        }
      }
  ]

  def moveCursor(change) {
    if (change.dx) cursor.x += change.dx
    if (change.dy) cursor.y += change.dy
    if (change.x != null) cursor.x = change.x
    if (change.y != null) cursor.y = change.y
    cursor.lastUpdatedByCommand = false
  }

  def onChange(callback) {
    changeCallbacks << callback
  }

  def changed() {
    changeCallbacks.each { it.call lines, cursor }
  }

  def actionTyped(k) {
    def a = actions[k]
    if (a) {
      a.call()
      changed()
    }
  }

  def charTyped(k) {
    lines[cursor.y] = pre() + k + post()
    moveCursor(dx: 1)
    changed()
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

  void updateLines(List<String> lines) {
    if (!cursor.lastUpdatedByCommand) {
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
