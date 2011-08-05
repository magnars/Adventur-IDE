package no.advide

class DocumentFragment {

  Document document
  def offset
  int length

  DocumentFragment(offset, length, Document document) {
    this.document = document
    this.offset = offset
    this.length = length
  }

  def getCursor() {
    if (inside(document.cursor.y)) { [x: document.cursor.x - offset.x, y: document.cursor.y - offset.y] }
  }

  boolean inside(int y) {
    y - offset.y >= 0 && y - offset.y < length
  }

  List<String> getLines() {
    document.lines[ offset.y..(offset.y + length - 1) ].collect { line -> line.substring((int)offset.x)}
  }

  void mergeLineWithPrevious(int y) {
    stripOutsideLeftFromLine(y)
    document.mergeLineWithPrevious(offset.y + y)
  }

  String getOutsideLeft(int y) {
    document.lines[y + offset.y].substring(0, (int)offset.x)
  }

  private void stripOutsideLeftFromLine(int y) {
    for (int i = 0; i < offset.x; i++) document.removeCharBefore(1, offset.y + y)
  }

  void appendTo(int y, String s) {
    document.insertAt(offset.x + lines[y].size(), offset.y + y, s)
  }

  void appendTo_butDontMoveCursor(int y, String s) {
    def old = [x: document.cursor._x, y: document.cursor._y]
    appendTo(y, s)
    document.cursor._x = old.x
    document.cursor._y = old.y
  }

  void splitAt(int x, int y) {
    document.splitAt(offset.x + x, offset.y + y)
    document.insertAt(0, offset.y + y + 1, getOutsideLeft(y))
  }

  void chop(int y) {
    document.removeCharBefore(offset.x + lines[y].size(), offset.y + y)
  }

  void replaceWith(List<String> strings) {
    if (strings.size() != 1) throw new IllegalArgumentException("replaceWith only supports replacing with 1 line for now")
    def hadCursor = cursor != null
    while (length > 1) document.removeLine(offset.y + 1)
    while (hadCursor && cursor == null) document.cursor.up()
    document.replaceLine(offset.y, getOutsideLeft(0) + strings.first())
  }

  def translate(cursor) {
    [x: cursor.x + offset.x, y: cursor.y + offset.y]
  }

  DocumentFragment createFragment(o, length) {
    document.createFragment([x:o.x + offset.x, y: o.y + offset.y], length)
  }

}
