package no.advide

class TextEditor {
  Document document

  def actions = [
      "right":     { document.moveCursorRight() },
      "left":      { document.moveCursorLeft() },
      "up":        { document.moveCursorUp() },
      "down":      { document.moveCursorDown() },
      "enter":     { document.splitAt(document.cursor.x, document.cursor.y) },
      "backspace": {
        if (!document.atStartOfLine()) {
          document.removeCharBefore(document.cursor.x, document.cursor.y)
        } else if (!document.atFirstLine()) {
          document.mergeLineWithPrevious(document.cursor.y)
        }
      }
  ]

  def charTyped(c) {
    document.insertAt(document.cursor.x, document.cursor.y, c)
    document.moveCursorRight()
    changed()
  }

  def actionTyped(k) {
    def a = actions[k]
    if (a) {
      a.call()
      changed()
    }
  }

  def changeCallbacks = []

  def onChange(callback) {
    changeCallbacks << callback
  }

  def changed() {
    changeCallbacks.each { it.call document }
  }

}
