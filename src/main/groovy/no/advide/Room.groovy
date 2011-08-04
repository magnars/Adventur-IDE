package no.advide

class Room {
  int number
  File file
  List history
  int historyIndex = 0
  int originalIndex = 0
  def cursor

  Room() {}

  Room(int num, File f) {
    number = num
    file = f
    history = [RoomConverter.toNewStyle(file.readLines('UTF-8'))]
    cursor = [x:0, y:0]
  }

  void save() {
    originalIndex = historyIndex
    file.setText(RoomConverter.toOldStyle(lines).join("\n"), 'UTF-8')
  }

  boolean isModified() {
    lines != original
  }

  def getCursor() {
    [x: Math.min((int)cursor.x, lines[cursor.y].size()), y: Math.min((int)cursor.y, lines.size() - 1)]
  }

  String getName() {
    if (number == -1) "Notatblokk"
    else "Rom $number"
  }

  void setLines(l) { // det ser ut som om et snapshot av cursoren skal være med i historikken
    if (l != lines) {
      history = history.subList(0, historyIndex + 1)
      history << l
      historyIndex += 1
    }
  }

  List<String> getLines() {
    history[historyIndex]
  }

  List<String> getOriginal() {
    history[originalIndex]
  }

  void undo() {
    if (historyIndex > 0) historyIndex--
  }

  void redo() {
    if (historyIndex < history.size() - 1) historyIndex++
  }
}
