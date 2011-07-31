package no.advide

class DocumentFragment {

  Document document
  int startY
  int length

  def getCursor() {
    if (inside(document.cursor.y))
      [x: document.cursor.x, y: document.cursor.y - startY]
  }

  boolean inside(int y) {
    y - startY >= 0 && y - startY < length
  }

  List<String> getLines() {
    document.lines[ startY..(startY + length - 1) ]
  }

  void mergeLineWithPrevious(int y) {
    document.mergeLineWithPrevious(startY + y)
  }

  void appendTo(int y, String s) {
    document.insertAt(lines[y].size(), startY + y, s)
  }

  void splitAt(int x, int y) {
    document.splitAt(x, startY + y)
  }

  void chop(int y) {
    document.removeCharBefore(lines[y].size(), startY + y)
  }
}
