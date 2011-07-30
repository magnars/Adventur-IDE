package no.advide

class Document {
  List<String> lines
  def cursor = [x: 0, y: 0]

  Document() {}

  Document(List<String> lines, cursor) {
    this.lines = lines
    this.cursor = cursor
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

  void mergeLineWithPrevious(int index) {
    if (cursor.y == index) cursor.x += lines[index - 1].size()
    if (cursor.y >= index) cursor.y -= 1
    lines[index - 1] += lines[index]
    lines.remove(index)
  }

  void insertCharAtCursor(c) {
    lines[cursor.y] = pre() + c + post()
    moveCursor(dx: 1)
  }

  /* untested
  void stripTrailingSpaces() {
    lines.eachWithIndex { line, i ->
      lines[i] = StringUtils.stripBack(line, " ")
    }
    cursor.x = Math.min(cursor.x, currentLine().size())
  }
  */

  DocumentFragment createFragment(int index, int length) {
    new DocumentFragment(startIndex: index, length: length, document: this)
  }

  void clearFragments() {
  }

}
