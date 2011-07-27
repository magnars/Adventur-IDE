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
      }
  ]

  TextLayout getTextLayout() {
    new TextLayout(lines: lines, cursor: cursor)
  }

  def onChange(callback) {
    changeCallbacks << callback
  }

  def changed() {
    def tl = getTextLayout()
    changeCallbacks.each { c -> c.call tl }
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

  private String post() {
    return currentLine().substring(cursor.x)
  }

  private String pre() {
    return currentLine().substring(0, cursor.x)
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
}
