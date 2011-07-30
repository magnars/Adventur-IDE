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

  void removeCharBefore(Integer x, Integer y) {
    if (cursor.y == y && cursor.x >= x) cursor.x -= 1
    def pre = lines[y].substring(0, x - 1)
    def post = lines[y].substring(x)
    lines[y] = pre + post
  }

  void mergeLineWithPrevious(int y) {
    if (cursor.y == y) cursor.x += lines[y - 1].size()
    if (cursor.y >= y) cursor.y -= 1
    lines[y - 1] += lines[y]
    lines.remove(y)
  }

  void splitAt(int x, int y) {
    def pre = lines[y].substring(0, x)
    def post = lines[y].substring(x)
    def split = [pre, post]

    if (cursor.y == y && cursor.x >= x) { cursor.y += 1; cursor.x -= pre.size() }
    else if (cursor.y > y) cursor.y += 1
    lines.remove(y)
    lines.addAll(y, split)
  }

  void insertAt(int x, int y, s) {
    if (cursor.y == y && cursor.x >= x) cursor.x += s.size()
    def pre = lines[y].substring(0, x)
    def post = lines[y].substring(x)
    lines[y] = pre + s + post
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
    new DocumentFragment(startY: index, length: length, document: this)
  }

  void clearFragments() {
  }

}
