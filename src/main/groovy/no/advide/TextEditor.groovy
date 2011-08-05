package no.advide

class TextEditor extends EventEmitter {
  Document document

  TextEditor(Document document) {
    this.document = document
  }

  def actions = [
      "right":     { document.cursor.right() },
      "left":      { document.cursor.left() },
      "up":        { document.cursor.up() },
      "down":      { document.cursor.down() },
      "enter":     { enter() },
      "backspace": { backspace() }
  ]

  def enter() {
    def indentation = currentIndentation()
    document.splitAt(document.cursor.x, document.cursor.y)
    document.insertAt(document.cursor.x, document.cursor.y, indentation)
  }

  String currentIndentation() {
    int x = 0
    String currentLine = document.lines[document.cursor.y]
    while (currentLine.startsWith("  ", x)) x += 2
    return " " * x
  }

  def backspace() {
    if (document.cursor.x != 0) {
      document.removeCharBefore(document.cursor.x, document.cursor.y)
    } else if (!document.cursor.y != 0) {
      document.mergeLineWithPrevious(document.cursor.y)
    }
  }

  def charTyped(c) {
    document.insertAt(document.cursor.x, document.cursor.y, c)
    changed()
  }

  def actionTyped(k) {
    def a = actions[k]
    if (a) {
      a.call()
      changed()
    }
  }

  def onChange(callback) {
    on('change', callback)
  }

  def changed() {
    emit('change', document)
  }

}
