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
    return new File(pathTo(roomNumber))
  }

  String pathTo(int roomNumber) {
    "${directoryPath}/${subdir(roomNumber)}/A${roomNumber}.txt"
  }

  String subdir(int roomNumber) {
    int hundreds = roomNumber / 100
    hundreds < 10 ? "A0${hundreds}" : "A${hundreds}"
  }

  Page loadRoom(int roomNumber) {
    getPage("Rom ${roomNumber}", roomFile(roomNumber))
  }

  Page loadNotes() {
    getPage('Notatblokk', new File("${directoryPath}/notat.txt"))
  }

  def pages = [:]

  Page getPage(String name, File file) {
    if (!pages[name]) {
      pages[name] = new Page(name, file)
    }
    pages[name]
  }

  String getName() {
    WordUtils.capitalize(new File(directoryPath).name.replace("_", " "))
  }
}
