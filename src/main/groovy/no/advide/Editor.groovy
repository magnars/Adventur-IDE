package no.advide

class Editor {
  def lines
  Cursor cursor

  def changeCallbacks = []

  def actions = [
      "right": {
        if (!atEndOfLine()) {
          cursor.x += 1
        } else if (!atLastLine()) {
          cursor.x = 0
          cursor.y += 1
        }
      },
      "left": {
        if (!atStartOfLine()) {
          cursor.x -= 1
        } else if (!atFirstLine()) {
          cursor.y -= 1
          cursor.x = currentLine().size()
        }
      },
      "up": {
        if (!atFirstLine()) {
          cursor.y -= 1
          cursor.x = Math.min(cursor.x, currentLine().size())
        } else if (!atStartOfLine()) {
          cursor.x = 0
        }
      },
      "down": {
        if (!atLastLine()) {
          cursor.y += 1
          cursor.x = Math.min(cursor.x, currentLine().size())
        } else if (!atEndOfLine()) {
          cursor.x = currentLine().size()
        }
      },
      "enter": {
        def split = [pre(), post()]
        lines.remove(cursor.y)
        lines.addAll(cursor.y, split)
        cursor.x = 0
        cursor.y += 1
      },
      "backspace": {
        if (!atStartOfLine()) {
          def pre = currentLine().substring(0, cursor.x - 1)
          lines[cursor.y] = pre + post()
          cursor.x -= 1
        } else if (!atFirstLine()) {
          cursor.x = lines[cursor.y - 1].size()
          lines[cursor.y - 1] += lines[cursor.y]
          lines.remove(cursor.y)
          cursor.y -= 1
        }
      }
  ]

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
    cursor.x += 1
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
    if (textHasMovedUpWithoutCursor(lines)) cursor.y--
    if (textHasMovedDownWithoutCursor(lines)) cursor.y++
    this.lines = lines
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
}
