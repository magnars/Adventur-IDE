package no.advide

import org.apache.commons.lang3.text.WordUtils

class Adventure {

  static Adventure current

  static void choose() {
    /*

    // Bør bruke java.awt.FileDialog her, pga Mac look-n-feel

    JFileChooser fc = null
    new SwingBuilder().edt {
      fc = fileChooser(dialogTitle: "Velg en mappe med eventyr",
          id: "openDirectoryDialog", fileSelectionMode: JFileChooser.DIRECTORIES_ONLY) {}
    }
    if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) System.exit(0)
    current = new Adventure(fc.selectedFile)
    */
    current = new Adventure(new File("/Users/fimasvee/projects/adventur/eventyr/master"))
  }

  String directoryPath

  Adventure(File directory) {
    directoryPath = directory.absolutePath
  }

  boolean roomExists(int roomNumber) {
    roomFile(roomNumber).exists()
  }

  private File roomFile(int roomNumber) {
    if (roomNumber == -1) { return new File("${directoryPath}/notat.txt") }
    else return new File(pathTo(roomNumber))
  }

  String pathTo(int roomNumber) {
    "${directoryPath}/${subdir(roomNumber)}/A${roomNumber}.txt"
  }

  String subdir(int roomNumber) {
    int hundreds = roomNumber / 100
    hundreds < 10 ? "A0${hundreds}" : "A${hundreds}"
  }

  Room getNotes() {
    getRoom(-1)
  }

  def rooms = [:]

  Room getRoom(int number) {
    if (!rooms[number]) {
      rooms[number] = new Room(number, roomFile(number))
    }
    rooms[number]
  }

  String getName() {
    WordUtils.capitalize(new File(directoryPath).name.replace("_", " "))
  }
}
