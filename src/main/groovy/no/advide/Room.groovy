package no.advide

class Room {
  int number
  File file
  List<String> lines
  List<String> original
  def cursor

  Room() {}

  Room(int num, File f) {
    number = num
    file = f
    lines = RoomConverter.toNewStyle(file.readLines('UTF-8'))
    original = lines
    cursor = [x:0, y:0]
  }

  void save() {
    original = this.lines
    file.setText(RoomConverter.toOldStyle(original).join("\n"), 'UTF-8')
  }

  boolean isModified() {
    lines != original
  }

  String getName() {
    if (number == -1) "Notatblokk"
    else "Rom $number"
  }

}
