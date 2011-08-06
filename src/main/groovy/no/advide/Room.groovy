package no.advide

class Room {
  final int number
  final File file
  List history
  int historyIndex = 0
  int originalIndex = 0

  Room() {}

  Room(int num, File f) {
    number = num
    file = f
    history = [[
        lines: RoomConverter.toNewStyle(file.readLines('UTF-8')),
        cursor: [x:0, y:0, scrollTop:0]
    ]]
  }

  Document createDocument() {
    new Document(lines, cursor)
  }

  void update(Document document) {
    lines = [] + document.lines
    cursor = [x:document.cursor._x, y:document.cursor._y, scrollTop:document.cursor.scrollTop]
  }

  void save() {
    originalIndex = historyIndex
    file.setText(RoomConverter.toOldStyle(lines).join("\n"), 'UTF-8')
  }

  boolean isModified() {
    lines != original
  }

  String getName() {
    if (number == -1) "Notatblokk"
    else "Rom $number"
  }

  void setLines(l) {
    if (l != lines) {
      history = history.subList(0, historyIndex + 1)
      history << [lines: l, cursor: getCursor()]
      historyIndex += 1
    }
  }

  void setCursor(c) {
    history[historyIndex].cursor = c
  }

  def getCursor() {
    def c = history[historyIndex].cursor
    [x:c.x, y:c.y, scrollTop:c.scrollTop]
  }

  List<String> getLines() {
    [] + history[historyIndex].lines
  }

  List<String> getOriginal() {
    [] + history[originalIndex].lines
  }

  void undo() {
    if (historyIndex > 0) historyIndex--
  }

  void redo() {
    if (historyIndex < history.size() - 1) historyIndex++
  }

}
