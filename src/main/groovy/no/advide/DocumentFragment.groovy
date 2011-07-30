package no.advide

class DocumentFragment {

  Document document
  int startY
  int length

  List<String> getLines() {
    document.lines[ startY..(startY + length - 1) ]
  }

  void mergeLineWithPrevious(int y) {
    document.mergeLineWithPrevious(startY + y)
    length -= 1
  }

  void appendTo(int y, String s) {
    document.insertAt(lines[y].size(), startY + y, s)
  }

  void splitAt(int x, int y) {
    document.splitAt(x, startY + y)
    length += 1
  }

  void chop(int y) {
    document.removeCharBefore(lines[y].size(), startY + y)
  }
}
