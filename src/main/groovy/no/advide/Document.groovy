package no.advide

class Document {
  List<String> lines
  def cursor = [x:0, y:0]

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

  void moveCursorRight() {
    if (!atEndOfLine()) {
      cursor.x += 1
    } else if (!atLastLine()) {
      cursor.y += 1
      cursor.x = 0
    }
  }

  void moveCursorLeft() {
    if (!atStartOfLine()) {
      cursor.x -= 1
    } else if (!atFirstLine()) {
      cursor.y -= 1
      cursor.x = currentLine().size()
    }
  }

  void moveCursorUp() {
    if (!atFirstLine()) {
      cursor.y -= 1
      cursor.x = Math.min(cursor.x, currentLine().size())
    } else if (!atStartOfLine()) {
      cursor.x = 0
    }
  }

  void moveCursorDown() {
    if (!atLastLine()) {
      cursor.y += 1
      cursor.x = Math.min(cursor.x, currentLine().size())
    } else if (!atEndOfLine()) {
      cursor.x = currentLine().size()
    }
  }

  void splitLineAtCursor() {
    def split = [pre(), post()]
    lines.remove(cursor.y)
    lines.addAll(cursor.y, split)
    cursor.x = 0
    cursor.y += 1
  }

  void removeCharAtCursor() {
    def pre = currentLine().substring(0, cursor.x - 1)
    lines[cursor.y] = pre + post()
    cursor.x -= 1
  }

  void mergeLineWithPrevious(int index) {
    if (cursor.y == index) cursor.x += lines[index - 1].size()
    if (cursor.y >= index) cursor.y -= 1
    lines[index - 1] += lines[index]
    lines.remove(index)
  }

  void insertCharAtCursor(c) {
    lines[cursor.y] = pre() + c + post()
    cursor.x += 1
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
