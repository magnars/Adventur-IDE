package no.advide

class Editor {
  Document document

  def changeCallbacks = []

  def actions = [
      "right":     { document.moveCursorRight() },
      "left":      { document.moveCursorLeft() },
      "up":        { document.moveCursorUp() },
      "down":      { document.moveCursorDown() },
      "enter":     { document.splitLineAtCursor() },
      "backspace": {
        if (!document.atStartOfLine()) {
          document.removeCharAtCursor()
        } else if (!document.atFirstLine()) {
          document.mergeLineAtCursorWithPrevious()
        }
      }
  ]

  def onChange(callback) {
    changeCallbacks << callback
  }

  def changed() {
    changeCallbacks.each { it.call document }
  }

  def actionTyped(k) {
    def a = actions[k]
    if (a) {
      a.call()
      changed()
    }
  }

  def charTyped(k) {
    document.insertStringAtCursor(k)
    changed()
  }

}
